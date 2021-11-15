package com.rms.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.rms.service.RateService;
import com.rms.vo.RateSurchargeVO;
import com.rms.vo.RateVO;
import com.rms.vo.SurchargeVatVO;

@RunWith(SpringRunner.class)
@WebMvcTest(RateController.class)
@WithMockUser(username = "test", password = "test", roles = "ADMIN")
public class RateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RateService rateService;

    @Test
    public void getAllSurchargesTest() throws Exception {
        RateVO rateVO = new RateVO(1l, "Rate Description 1", LocalDate.now(), LocalDate.now(), 1000);
        RateSurchargeVO rateSurchargeVO = getSurchrageDetails().calculateSurchargeOnRate(rateVO);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/surcharges").accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        Mockito.when(rateService.getAllRates()).thenReturn(Arrays.asList(rateSurchargeVO));
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String expacted = "[{\"id\":1,\"rateDescription\":\"Rate Description 1\",\"rateEffectiveDate\":\""
                + LocalDate.now() + "\",\"rateExpirationDate\":\"" + LocalDate.now()
                + "\",\"amount\":1000,\"surcharge\":" + rateSurchargeVO.getSurcharge() + ", \"tax\":"
                + rateSurchargeVO.getTax() + ", \"totalTax\":1250}]";
        JSONAssert.assertEquals(expacted, mvcResult.getResponse().getContentAsString(), true);
    }

    @Test
    public void getSurchargeByIdTest() throws Exception {
        RateVO rateVO = new RateVO(1l, "Rate Description 1", LocalDate.now(), LocalDate.now(), 1000);
        RateSurchargeVO rateSurchargeVO = getSurchrageDetails().calculateSurchargeOnRate(rateVO);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/surcharge/1").accept(MediaType.APPLICATION_JSON);
        Mockito.when(rateService.getRate(1l)).thenReturn(rateSurchargeVO);
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String expacted = "{\"id\":1,\"rateDescription\":\"Rate Description 1\",\"rateEffectiveDate\":\""
                + LocalDate.now() + "\",\"rateExpirationDate\":\"" + LocalDate.now()
                + "\",\"amount\":1000,\"surcharge\":" + rateSurchargeVO.getSurcharge() + ", \"tax\":"
                + rateSurchargeVO.getTax() + ", \"totalTax\":1250}";
        JSONAssert.assertEquals(expacted, mvcResult.getResponse().getContentAsString(), true);
    }

    @Test
    public void insertRateTest() throws Exception {
        String rate = "{\"id\":1,\"rateDescription\":\"Rate Description 1\",\"rateEffectiveDate\":\"" + LocalDate.now()
                + "\",\"rateExpirationDate\":\"" + LocalDate.now() + "\",\"amount\":1000,\"totalTax\":1250}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/surcharge").content(rate)
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);
        RateVO rateVO = new RateVO(1l, "Rate Description 1", LocalDate.now(), LocalDate.now(), 1000);
        RateSurchargeVO rateSurchargeVO = getSurchrageDetails().calculateSurchargeOnRate(rateVO);
        Mockito.when(rateService.saveRate(rateVO)).thenReturn(rateSurchargeVO);
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String expacted = "{\"id\":1,\"rateDescription\":\"Rate Description 1\",\"rateEffectiveDate\":\""
                + LocalDate.now() + "\",\"rateExpirationDate\":\"" + LocalDate.now()
                + "\",\"amount\":1000,\"surcharge\":" + rateSurchargeVO.getSurcharge() + ", \"tax\":"
                + rateSurchargeVO.getTax() + ", \"totalTax\":1250}";
        JSONAssert.assertEquals(expacted, mvcResult.getResponse().getContentAsString(), true);
    }

    @Test
    public void updateRateTest() throws Exception {
        String rate = "{\"id\":1,\"rateDescription\":\"Rate Description 1\",\"rateEffectiveDate\":\"" + LocalDate.now()
                + "\",\"rateExpirationDate\":\"" + LocalDate.now() + "\",\"amount\":1000,\"totalTax\":1250}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/surcharge").content(rate)
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);
        RateVO rateVO = new RateVO(1l, "Rate Description 1", LocalDate.now(), LocalDate.now(), 1000);
        RateSurchargeVO rateSurchargeVO = getSurchrageDetails().calculateSurchargeOnRate(rateVO);
        Mockito.when(rateService.updateRate(rateVO)).thenReturn(rateSurchargeVO);
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String expacted = "{\"id\":1,\"rateDescription\":\"Rate Description 1\",\"rateEffectiveDate\":\""
                + LocalDate.now() + "\",\"rateExpirationDate\":\"" + LocalDate.now()
                + "\",\"amount\":1000,\"surcharge\":" + rateSurchargeVO.getSurcharge() + ", \"tax\":"
                + rateSurchargeVO.getTax() + ", \"totalTax\":1250}";
        JSONAssert.assertEquals(expacted, mvcResult.getResponse().getContentAsString(), true);
    }

    @Test
    public void deleteRateTest() throws Exception {
        RequestBuilder requestBuilder =
                MockMvcRequestBuilders.delete("/surcharge/1").accept(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        assertEquals(HttpStatus.NO_CONTENT.value(), mvcResult.getResponse().getStatus());
    }

    private SurchargeVatVO getSurchrageDetails() {
        try {
            return new TestRestTemplate().getForObject("https://surcharge.free.beeceptor.com/surcharge",
                    SurchargeVatVO.class);
        } catch (Exception e) {
            return new SurchargeVatVO(200, 50);
        }
    }

}

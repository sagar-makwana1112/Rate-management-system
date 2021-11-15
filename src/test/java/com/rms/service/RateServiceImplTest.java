package com.rms.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.rms.dao.RateDao;
import com.rms.entity.RateEntity;
import com.rms.exception.SaveUpdateEntityException;
import com.rms.vo.RateSurchargeVO;
import com.rms.vo.RateVO;
import com.rms.vo.SurchargeVatVO;

@RunWith(SpringRunner.class)
public class RateServiceImplTest {

    @Mock
    private RateDao rateDao;

    @Mock
    private RateService rateService;

    private List<RateSurchargeVO> rateSurchargeVOs;
    private RateSurchargeVO rateSurchargeVO;
    private RateVO rateVO;

    @Before
    public void setUp() {
        rateVO = new RateVO(1l, "Rate Description 1", LocalDate.now(), LocalDate.now(), 1000);
        rateSurchargeVOs = new ArrayList<>(1);
        rateSurchargeVO = getSurchrageDetails().calculateSurchargeOnRate(rateVO);
        rateSurchargeVOs.add(rateSurchargeVO);
        rateDao.save(RateEntity.toRateEntity(rateVO));
    }

    @Test
    public void testGetAllRates() {
        when(rateService.getAllRates()).thenReturn(rateSurchargeVOs);
        List<RateSurchargeVO> rateVos = rateService.getAllRates();
        assertEquals(rateVos, rateSurchargeVOs);
    }

    @Test
    public void testSaveRate() {
        when(rateService.saveRate(rateVO)).thenReturn(getSurchrageDetails().calculateSurchargeOnRate(rateVO));
        assertEquals(rateService.saveRate(rateVO), rateSurchargeVO);
    }

    @Test
    public void testGetRate() {
        when(rateService.getRate(1l)).thenReturn(rateSurchargeVO);
        assertEquals(rateService.getRate(1l), rateSurchargeVO);
    }

    @Test
    public void testUpdateRate() {
        rateVO = new RateVO(1l, "Rate Description 1", LocalDate.now(), LocalDate.now(), 2000);
        when(rateService.updateRate(rateVO)).thenReturn(getSurchrageDetails().calculateSurchargeOnRate(rateVO));
        assertEquals(Integer.parseInt(rateService.updateRate(rateVO).getAmount().toString()), 2000);
    }

    @Test(expected = EntityNotFoundException.class)
    public void entityNotFoundExceptionTest() {
        when(rateService.getRate(5l)).thenThrow(EntityNotFoundException.class);
        rateService.getRate(5l);
    }

    @Test(expected = SaveUpdateEntityException.class)
    public void saveUpdateEntityExceptionTest() {
        rateVO = new RateVO(1l, "Rate Description 1", LocalDate.now(), LocalDate.now(), 2000);
        when(rateService.updateRate(rateVO)).thenThrow(SaveUpdateEntityException.class);
        rateService.updateRate(rateVO);
    }

    public SurchargeVatVO getSurchrageDetails() {
        try {
            return new TestRestTemplate().getForObject("https://surcharge.free.beeceptor.com/surcharge",
                    SurchargeVatVO.class);
        } catch (Exception e) {
            return new SurchargeVatVO(200, 50);
        }
    }

}

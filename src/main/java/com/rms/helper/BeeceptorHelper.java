package com.rms.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.rms.vo.SurchargeVatVO;

import io.github.resilience4j.retry.annotation.Retry;

/**
 * Helper for getting surcharges data from beeceptor service.
 */
@Component
public class BeeceptorHelper {

    private static final String BEECEPTORSERVICE = "beeceptorservice";
    private Logger log = LoggerFactory.getLogger(BeeceptorHelper.class);

    @Autowired
    private RestTemplate restTemplate;

    @Retry(name = BEECEPTORSERVICE, fallbackMethod = "beeceptorFallBack")
    public SurchargeVatVO getSurchargeDetail() {
        log.debug("Calling rest call for getting surcharge details");
        return restTemplate.getForObject("https://surcharge.free.beeceptor.com/surcharge", SurchargeVatVO.class);
    }

    /**
     * Fall back for beeceptor service.
     *
     * @param e {@link Exception}
     * @return Default {@link SurchargeVatVO} with default value.
     *
     */
    public SurchargeVatVO beeceptorFallBack(Exception e) {
        log.debug("surcharge service is down so returning default surcharge details");
        return new SurchargeVatVO(200, 50);
    }

}

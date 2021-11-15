package com.rms.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.rms.dao.RateDao;
import com.rms.entity.RateEntity;
import com.rms.exception.SaveUpdateEntityException;
import com.rms.service.RateService;
import com.rms.vo.RateSurchargeVO;
import com.rms.vo.RateVO;
import com.rms.vo.SurchargeVatVO;

/**
 * Rate Service implementation.
 */
@Service
@Transactional
public class RateServiceImpl implements RateService {

    private static final String INTERNAL_SERVER_ERROR_PLEASE_CONTACT_ADMIN_MESSAGE =
            "Internal server error. Please contact admin";
    private static final String RATE_ID_NOT_FOUND_IN_RMS_MESSAGE = "RateId not found in RMS";

    private Logger log = LoggerFactory.getLogger(RateServiceImpl.class);

    @Autowired
    private RateDao rateDao;

    @Override
    public List<RateSurchargeVO> getAllRates() {
        List<RateVO> rateVOs = new ArrayList<>();
        rateDao.findAll().forEach(rate -> rateVOs.add(RateVO.toRateVO(rate)));
        log.debug("Total rate found is : {}", rateVOs.size());
        return getSurchargeDetail().calculateSurchrgeOnRates(rateVOs);
    }

    @Override
    public RateSurchargeVO saveRate(RateVO rateVO) {
        try {
            RateEntity rateEntity = RateEntity.toRateEntity(rateVO);
            rateDao.save(rateEntity);
            log.debug("Rate is saved : {}", rateEntity);
            rateVO = RateVO.toRateVO(rateEntity);
            return getSurchargeDetail().calculateSurchargeOnRate(rateVO);
        } catch (Exception e) {
            throw new SaveUpdateEntityException(INTERNAL_SERVER_ERROR_PLEASE_CONTACT_ADMIN_MESSAGE);
        }
    }

    @Override
    public RateSurchargeVO getRate(Long id) {
        RateEntity entity = rateDao.findById(id).orElse(null);
        if (entity == null) {
            throw new EntityNotFoundException(RATE_ID_NOT_FOUND_IN_RMS_MESSAGE);
        }
        return getSurchargeDetail().calculateSurchargeOnRate(RateVO.toRateVO(entity));
    }

    @Override
    public RateSurchargeVO updateRate(RateVO rateVO) {
        try {
            RateEntity entity = rateDao.findById(rateVO.getId()).orElse(null);
            if (entity == null) {
                throw new EntityNotFoundException(RATE_ID_NOT_FOUND_IN_RMS_MESSAGE);
            }
            RateEntity rateEntity = RateEntity.toRateEntity(rateVO);
            rateDao.save(rateEntity);
            log.debug("Rate is saved : {}", rateEntity);
            return getSurchargeDetail().calculateSurchargeOnRate(RateVO.toRateVO(rateEntity));
        } catch (Exception e) {
            throw new SaveUpdateEntityException(INTERNAL_SERVER_ERROR_PLEASE_CONTACT_ADMIN_MESSAGE);
        }
    }

    @Override
    public void deleteRate(Long id) {
        RateEntity entity = rateDao.findById(id).orElse(null);
        if (entity == null) {
            throw new EntityNotFoundException(RATE_ID_NOT_FOUND_IN_RMS_MESSAGE);
        }
        rateDao.deleteById(id);
    }

    public SurchargeVatVO getSurchargeDetail() {
        log.debug("Calling rest call for getting surcharge details");
        try {
            return new RestTemplate().getForObject("https://surcharge.free.beeceptor.com/surcharge",
                    SurchargeVatVO.class);
        } catch (Exception e) {
            log.debug("Exception while getting surcharge details");
            return new SurchargeVatVO(200, 50);
        }
    }

}

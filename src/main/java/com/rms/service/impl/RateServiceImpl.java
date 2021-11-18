package com.rms.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rms.dao.RateDao;
import com.rms.entity.RateEntity;
import com.rms.exception.SaveUpdateEntityException;
import com.rms.helper.BeeceptorHelper;
import com.rms.service.RateService;
import com.rms.vo.RateSurchargeVO;
import com.rms.vo.RateVO;

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

    @Autowired
    private BeeceptorHelper beeceptorHelper;

    @Override
    public List<RateSurchargeVO> getAllRates() {
        List<RateEntity> rateEntities = rateDao.findByOrderByIdAsc();
        log.debug("Total rate found is : {}", rateEntities.size());
        List<RateVO> rateVOs = rateEntities.parallelStream().map(RateVO::toRateVO).collect(Collectors.toList());
        return beeceptorHelper.getSurchargeDetail().calculateSurchrgeOnRates(rateVOs);
    }

    @Override
    public RateSurchargeVO saveRate(RateVO rateVO) {
        try {
            RateEntity rateEntity = RateEntity.toRateEntity(rateVO);
            rateDao.save(rateEntity);
            log.debug("Rate is saved : {}", rateEntity);
            rateVO = RateVO.toRateVO(rateEntity);
            return beeceptorHelper.getSurchargeDetail().calculateSurchargeOnRate(rateVO);
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
        return beeceptorHelper.getSurchargeDetail().calculateSurchargeOnRate(RateVO.toRateVO(entity));
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
            return beeceptorHelper.getSurchargeDetail().calculateSurchargeOnRate(RateVO.toRateVO(rateEntity));
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

}

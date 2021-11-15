package com.rms.service;

import java.util.List;

import com.rms.vo.RateSurchargeVO;
import com.rms.vo.RateVO;

/**
 * Service layer of rate.
 */
public interface RateService {

    /**
     * Get all surcharges.
     *
     * @return list of RateVO
     */
    List<RateSurchargeVO> getAllRates();

    /**
     * Save Rate.
     *
     * @param rateVO details of rate.
     * @return saved rate.
     */
    RateSurchargeVO saveRate(RateVO rateVO);

    /**
     * Get rate by id.
     *
     * @param id of rate.
     * @return RateVO details of rate.
     *
     */
    RateSurchargeVO getRate(Long id);

    /**
     * Update Rate.
     *
     * @param rateVO details of rate.
     * @return updated rate.
     */
    RateSurchargeVO updateRate(RateVO rateVO);

    /**
     * Delete rate.
     *
     * @param rate id for delete rate.
     */
    void deleteRate(Long id);

}

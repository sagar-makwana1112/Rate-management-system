package com.rms.vo;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SurchargeVatVO {

    private Integer surcharge;
    private Integer tax;

    /**
     * Calculate surcharge based on surchage and tax amount.
     *
     * @param rateVOs for actual amount.
     * @return List of RateSurchargeVO with rate and surchage details.
     */
    public List<RateSurchargeVO> calculateSurchrgeOnRates(List<RateVO> rateVOs) {
        List<RateSurchargeVO> rateSurchargeVOs = new ArrayList<>(rateVOs.size());
        rateVOs.parallelStream().forEach(rateVO -> rateSurchargeVOs.add(calculateSurchargeOnRate(rateVO)));
        return rateSurchargeVOs;
    }

    /**
     * Calculate surcharge based on surchage and tax amount.
     *
     * @param rateVO for actual amount.
     * @return RateSurchargeVO with rate and surchage details.
     */
    public RateSurchargeVO calculateSurchargeOnRate(RateVO rateVO) {
        RateSurchargeVO rateSurchargeVO = new RateSurchargeVO();
        rateSurchargeVO.setId(rateVO.getId());
        rateSurchargeVO.setRateDescription(rateVO.getRateDescription());
        rateSurchargeVO.setRateEffectiveDate(rateVO.getRateEffectiveDate());
        rateSurchargeVO.setRateExpirationDate(rateVO.getRateExpirationDate());
        rateSurchargeVO.setAmount(rateVO.getAmount());
        rateSurchargeVO.setSurcharge(getSurcharge());
        rateSurchargeVO.setTax(getTax());
        rateSurchargeVO.setTotalTax(getSurcharge() + getTax() + rateVO.getAmount());
        return rateSurchargeVO;
    }
}

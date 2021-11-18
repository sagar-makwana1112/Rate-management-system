package com.rms.vo;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Details of surcharge with surcharge and tax value.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SurchargeVatVO {

    private Integer surcharge;
    private Integer tax;

    /**
     * Calculate surcharge based on surcharge and tax amount.
     *
     * @param rateVOs for actual amount.
     * @return List of RateSurchargeVO with rate and surcharge details.
     */
    public List<RateSurchargeVO> calculateSurchrgeOnRates(List<RateVO> rateVOs) {
        return rateVOs.parallelStream().map(this::calculateSurchargeOnRate).collect(Collectors.toList());
    }

    /**
     * Calculate surcharge based on surcharge and tax amount.
     *
     * @param rateVO for actual amount.
     * @return RateSurchargeVO with rate and surcharge details.
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

package com.rms.vo;

import java.time.LocalDate;

import lombok.Data;

/**
 * VO for rate and surcharge total tax.
 */
@Data
public class RateSurchargeVO {

    private Long id;
    private String rateDescription;
    private LocalDate rateEffectiveDate;
    private LocalDate rateExpirationDate;
    private Integer amount;
    private Integer surcharge;
    private Integer tax;
    private Integer totalTax;

}

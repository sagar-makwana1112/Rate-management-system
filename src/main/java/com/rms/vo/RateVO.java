package com.rms.vo;

import java.time.LocalDate;
import java.util.Objects;

import com.rms.entity.RateEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * VO for rate entity.
 */
@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class RateVO {

    private Long id;
    private String rateDescription;
    private LocalDate rateEffectiveDate;
    private LocalDate rateExpirationDate;
    private Integer amount;

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RateVO)) {
            return false;
        }
        RateVO other = (RateVO) obj;
        return Objects.equals(id, other.id);
    }

    public static RateVO toRateVO(RateEntity rate) {
        return rate == null ? null
                : new RateVO(rate.getId(), rate.getRateDescription(), rate.getRateEffectiveDate(),
                        rate.getRateExpirationDate(), rate.getAmount());
    }
}

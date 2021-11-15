package com.rms.entity;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.rms.vo.RateVO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Entity for rate (RATE)
 */
@Entity
@Table(name = "RATE")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "RateId", nullable = false)
    private Long id;

    @Column(name = "RateDescription")
    private String rateDescription;

    @Column(name = "RateEffectiveDate", nullable = false)
    private LocalDate rateEffectiveDate;

    @Column(name = "RateExpirationDate", nullable = false)
    private LocalDate rateExpirationDate;

    @Column(name = "Amount", nullable = false)
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
        if (!(obj instanceof RateEntity)) {
            return false;
        }
        RateEntity other = (RateEntity) obj;
        return Objects.equals(id, other.id);
    }

    public static RateEntity toRateEntity(RateVO rateVO) {
        return rateVO == null ? null
                : new RateEntity(rateVO.getId(), rateVO.getRateDescription(), rateVO.getRateEffectiveDate(),
                        rateVO.getRateExpirationDate(), rateVO.getAmount());
    }

}

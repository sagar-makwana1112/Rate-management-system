package com.rms.dao;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.rms.entity.RateEntity;

/**
 * Data access layer for rate.
 */
@Repository
public interface RateDao extends PagingAndSortingRepository<RateEntity, Long> {

    /**
     * Find all Rate order by id ASC.
     *
     * @return list of {@link RateEntity}
     */
    List<RateEntity> findByOrderByIdAsc();

}

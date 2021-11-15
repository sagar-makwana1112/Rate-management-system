package com.rms.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.rms.entity.RateEntity;

/**
 * Data access layer for rate.
 */
@Repository
public interface RateDao extends PagingAndSortingRepository<RateEntity, Long>{

}

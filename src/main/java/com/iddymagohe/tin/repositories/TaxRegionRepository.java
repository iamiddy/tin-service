package com.iddymagohe.tin.repositories;

import com.iddymagohe.tin.domain.TaxRegion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

import java.math.BigInteger;

/**
 * Created by iddymagohe on 7/27/14.
 */

@org.springframework.stereotype.Repository
public interface TaxRegionRepository extends CrudRepository<TaxRegion,BigInteger> {
    TaxRegion findByRegionIgnoreCase(String region);
}

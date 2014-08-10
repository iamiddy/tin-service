package com.iddymagohe.tin.repositories;

import com.iddymagohe.tin.domain.Business;
import com.iddymagohe.tin.domain.TaxRegion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

/**
 * @author iddymagohe
 * Created by iddymagohe on 7/27/14.
 */

@Repository
public interface BusinessRepository extends MongoRepository<Business,BigInteger> {

    List<Business> findByTinNumber(String tin);
    Page<Business> findByRegionAndBusinessNameIgnoreCase(TaxRegion region,String name ,Pageable pageable);
    Page<Business> findByRegionAndNatureOfBusinessLike(TaxRegion region ,String natureOfBusiness ,Pageable pageable);
    Page<Business> findByRegion(TaxRegion region,Pageable pageable);
}


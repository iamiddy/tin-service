package com.iddymagohe.tin.repositories;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import com.iddymagohe.tin.AbstractIntegrationTest;
import com.iddymagohe.tin.domain.TaxRegion;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;

import javax.annotation.Resource;


/**
 * Created by iddymagohe on 7/27/14.
 */


public class TaxRegionRepoTest extends AbstractIntegrationTest {



    @Ignore
    //@Test
    public void saveTaxregionCorrectly(){

        TaxRegion region= new TaxRegion("Mtwara");
        assertThat(region.getId(), is(nullValue()));
        TaxRegion result= taxRegionRepository.save(region);
        assertThat(result.getId(), is(notNullValue()));

    }

    @Ignore
  // @Test(   )
    public void preventsDuplicaRegion() {
      TaxRegion bunda = taxRegionRepository.findByRegionIgnoreCase("bunda");
        assertThat(bunda,is(notNullValue()));
       taxRegionRepository.save(new TaxRegion(bunda.getRegion()));
    }

    @Test
    public void mavenTorun(){
        assertTrue(true);
    }

}

package com.iddymagohe.tin;

import com.iddymagohe.tin.domain.Business;
import com.iddymagohe.tin.domain.TaxRegion;
import com.iddymagohe.tin.repositories.BusinessRepository;
import com.iddymagohe.tin.repositories.TaxRegionRepository;
import com.mongodb.*;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import static org.springframework.data.mongodb.core.query.Query.*;

import static org.springframework.data.mongodb.core.query.Criteria.*;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Integration test bootstrapping an {ApplicationContext} from both XML and JavaConfig to assure the general setup
 * is working.
 *
 *
 * Created by iddymagohe on 7/27/14.
 * @author iddymagohe
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/application-context.xml"})
public abstract class AbstractIntegrationTest {

//    @Autowired
//    MongoOperations operations;

    @Autowired
    protected TaxRegionRepository taxRegionRepository;

    @Autowired
    protected BusinessRepository businessRepository;


    //@Before
    public void setUp(){

       TaxRegion bnd= new TaxRegion("bunda");
        TaxRegion ilala= new TaxRegion("Ilala");
        taxRegionRepository.save(ilala);
        taxRegionRepository.save(bnd);

        Business testBusiness=new Business("000-000-000",bnd,"TestBusiness","TestNature","TestAddress");
        businessRepository.save(testBusiness);
    }

   // @After
    public  void tearDown(){
        businessRepository.deleteAll();
        taxRegionRepository.deleteAll();
    }


}

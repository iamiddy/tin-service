package com.iddymagohe.tin.repositories;

import com.iddymagohe.tin.rest.util.ReadXSLSUtil;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by iddymagohe on 8/9/14.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/application-context.xml"})
public class ReadXSLUtilTest {

    public static final String TIN_REGEX= "^[0-9-]{9,11}$";

   @Autowired
   private  ReadXSLSUtil xslsUtil;

    @Ignore
    public void loadIlala(){
            xslsUtil.readFileData("ILALA.xls");
    }

    @Test
    public void checkTin(){
        assertTrue("103122163".matches(TIN_REGEX));
        assertTrue("103-122-163".matches(TIN_REGEX));
    }

}

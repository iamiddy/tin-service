package com.iddymagohe.tin.rest.controllers;

import com.iddymagohe.tin.domain.Business;
import com.iddymagohe.tin.domain.TaxRegion;
import com.iddymagohe.tin.repositories.BusinessRepository;
import com.iddymagohe.tin.repositories.TaxRegionRepository;
import com.iddymagohe.tin.rest.dto.BusinessEntry;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.HateoasPageableHandlerMethodArgumentResolver;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by iddymagohe on 7/26/14.
 */



@Controller
@Api(value = "businesses", description = "business API")
@RequestMapping(value="/business")
public class TZTINController {

    @Autowired
    protected BusinessRepository businessRepository;

    @Autowired
    TaxRegionRepository taxRegionRepository;

    @Autowired
    ConversionService conversionService;

   // @RequestMapping(value="/tins")
    public String hello(Model model){
        model.addAttribute("message","Welcome to the Tanzanian TIN service");
        return "index";
    }

    @ApiOperation(value = "Get Business by TIN-NUMBER", notes = "Returns business by TIN_NUMBER")
    @RequestMapping(value="/{tin-number}",produces = "application/json",method = RequestMethod.GET)
     public HttpEntity<Resources<Resource<Business>>> findBusiness(@PathVariable("tin-number") String tin_number)

    {
        Resources<Resource<Business>>businessResources=Resources.wrap(businessRepository.findByTinNumber(StringUtils.remove(tin_number,'-')));
        return new ResponseEntity<>(businessResources,HttpStatus.OK);
    }

    @ApiOperation(value = "Delete Business by TIN-NUMBER", notes = "Returns deleted businesses")
    @RequestMapping(value="/{tin-number}",produces = "application/json",method = RequestMethod.DELETE)
    public HttpEntity<Resources<Resource<Business>>> removeAllBusinessByTin(@PathVariable("tin-number") String tin_number)

    {
        List<Business> buss=businessRepository.findByTinNumber(StringUtils.remove(tin_number,'-'));
        businessRepository.delete(buss);
        Resources<Resource<Business>>businessResources=Resources.wrap(buss);
        return new ResponseEntity<>(businessResources,HttpStatus.OK);
    }

    @ApiOperation(value = "Register business", notes = "Location header of the registered business")
    @RequestMapping(value="/",consumes ="application/json", produces = "application/json",method = RequestMethod.POST)
    public HttpEntity<Void> registerBusiness(@RequestBody BusinessEntry businessEntry){
        Business business=conversionService.convert(businessEntry,Business.class);

        final List<Business> businessByThisTin = businessRepository.findByTinNumber(business.getTinNumber());
        if(!businessByThisTin.contains(business)) {
            TaxRegion region = taxRegionRepository.findByRegionIgnoreCase(business.getRegion().getRegion().trim());
            if (null == region) {
                region = new TaxRegion(business.getRegion().getRegion().trim());
                taxRegionRepository.save(region);
            }

            business.setRegion(region);
            businessRepository.save(business);

        }

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(linkTo(methodOn(TZTINController.class).findBusiness(business.getTinNumber())).toUri());
        return new ResponseEntity<>(headers,HttpStatus.CREATED);
    }

    @ApiOperation(value = "Get Business by Region", notes = "Returns business by region")
    @RequestMapping(value="/",produces = "application/json",method = RequestMethod.GET)
    public HttpEntity<PagedResources<Resource<Business>>> searchBusinesses(@RequestParam("tax_region") String region,
                                                                           @RequestParam(required=false) int page,
                                                                           @RequestParam(required=false) int size
                                                                           )

    {
      PagedResourcesAssembler<Business> assembler=new PagedResourcesAssembler<>(new HateoasPageableHandlerMethodArgumentResolver(),null);
        TaxRegion regionFromDB = taxRegionRepository.findByRegionIgnoreCase(region.trim());

        Page<Business> businesses = businessRepository.findByRegion(regionFromDB, new PageRequest(page, size));
        //businessRepository.findByRegionAndBusinessNameIgnoreCase(regionFromDB,nature, new PageRequest(page, size));
               // businessRepository.findByRegion(regionFromDB, new PageRequest(page, size));
        return new ResponseEntity<>(assembler.toResource(businesses),HttpStatus.OK);
    }
}

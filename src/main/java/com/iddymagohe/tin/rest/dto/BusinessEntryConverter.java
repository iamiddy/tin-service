package com.iddymagohe.tin.rest.dto;

import com.iddymagohe.tin.domain.Business;
import com.iddymagohe.tin.domain.TaxRegion;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

/**@author iddymagohe
 * Created by iddymagohe on 8/10/14.
 */
public class BusinessEntryConverter implements Converter<BusinessEntry,Business> {


    @Override
    public Business convert(BusinessEntry businessEntry) {
        return new Business(StringUtils.remove(businessEntry.getTin_number().trim(), '-'),
                new TaxRegion(businessEntry.getTax_region().trim()),
                businessEntry.getBusiness_name().trim(),
                businessEntry.getNatureOf_business().trim(),
                businessEntry.getStreet_address().trim());
    }
}

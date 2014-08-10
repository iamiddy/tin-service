package com.iddymagohe.tin.rest.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * Created by iddymagohe on 8/10/14.
 */


@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, isGetterVisibility = JsonAutoDetect.Visibility.NONE)
@ApiModel("Business Entry")
public class BusinessEntry {

    @ApiModelProperty(required = true)
    private String tin_number;
    @ApiModelProperty(required = true)
    private String business_name;
    @ApiModelProperty(required = true)
    private String natureOf_business;
    @ApiModelProperty(required = true)
    private String tax_region;
    @ApiModelProperty(required = true)
    private String street_address;

    public String getTin_number() {
        return tin_number;
    }

    public void setTin_number(String tin_number) {
        this.tin_number = tin_number;
    }

    public String getBusiness_name() {
        return business_name;
    }

    public void setBusiness_name(String business_name) {
        this.business_name = business_name;
    }

    public String getNatureOf_business() {
        return natureOf_business;
    }

    public void setNatureOf_business(String natureOf_business) {
        this.natureOf_business = natureOf_business;
    }

    public String getTax_region() {
        return tax_region;
    }

    public void setTax_region(String tax_region) {
        this.tax_region = tax_region;
    }

    public String getStreet_address() {
        return street_address;
    }

    public void setStreet_address(String street_address) {
        this.street_address = street_address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BusinessEntry that = (BusinessEntry) o;

        if (!business_name.equalsIgnoreCase(that.business_name)) return false;
        if (!natureOf_business.equalsIgnoreCase(that.natureOf_business)) return false;
        if (!tax_region.equalsIgnoreCase(that.tax_region)) return false;
        if (!tin_number.equalsIgnoreCase(that.tin_number)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = tin_number.hashCode();
        result = 31 * result + business_name.hashCode();
        result = 31 * result + natureOf_business.hashCode();
        result = 31 * result + tax_region.hashCode();
        return result;
    }
}

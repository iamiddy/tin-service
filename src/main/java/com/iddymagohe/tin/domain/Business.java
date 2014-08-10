package com.iddymagohe.tin.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.math.BigInteger;

/**
 * @author iddymagohe
 * Created by iddymagohe on 7/27/14.
 */

@Document(collection = "businesses")
@TypeAlias("businesses")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, isGetterVisibility = JsonAutoDetect.Visibility.NONE)
public class Business {

    @Id
    @JsonIgnore
    private BigInteger id;

    @Indexed()
    private String tinNumber;

    @DBRef
    private TaxRegion region;
    private String businessName;

    @Indexed
    private String natureOfBusiness;

    private String address;


    public Business(String tinNumber, TaxRegion region, String businessName, String natureOfBusiness, String address) {

        Assert.isTrue(!StringUtils.isEmpty(tinNumber), "TIN number can not be null");
        Assert.isTrue(!StringUtils.isEmpty(businessName), "Business Name can not be null");
        this.tinNumber = tinNumber;
        this.region = region;
        this.businessName = businessName;
        this.natureOfBusiness = natureOfBusiness;
        this.address = address;
    }

    public BigInteger getId() {
        return id;
    }


    public String getTinNumber() {
        return tinNumber;
    }

    public void setTinNumber(String tinNumber) {
        this.tinNumber = tinNumber;
    }

    public TaxRegion getRegion() {
        return region;
    }

    public void setRegion(TaxRegion region) {
        this.region = region;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getNatureOfBusiness() {
        return natureOfBusiness;
    }

    public void setNatureOfBusiness(String natureOfBusiness) {
        this.natureOfBusiness = natureOfBusiness;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Business business = (Business) o;

        if (!businessName.equalsIgnoreCase(business.businessName)) return false;
        if (!natureOfBusiness.equalsIgnoreCase(business.natureOfBusiness)) return false;
        if (!region.equals(business.region)) return false;
        if (!tinNumber.equalsIgnoreCase(business.tinNumber)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = tinNumber.hashCode();
        result = 31 * result + region.hashCode();
        result = 31 * result + businessName.hashCode();
        result = 31 * result + natureOfBusiness.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Business{" +
                "id=" + id +
                ", tinNumber='" + tinNumber + '\'' +
                ", region=" + region +
                ", businessName='" + businessName + '\'' +
                ", natureOfBusiness='" + natureOfBusiness + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}

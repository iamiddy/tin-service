package com.iddymagohe.tin.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.math.BigInteger;

/**
 * @author iddymagohe
 * Created by iddymagohe on 7/27/14.
 */

@Document(collection = "taxRegions")
@TypeAlias("taxRegion")
public class TaxRegion {

    @Id
    @JsonIgnore
    private BigInteger id;

    @Indexed(unique = true)
    private String region;

    public TaxRegion(String region) {

        Assert.isTrue(!StringUtils.isEmpty(region)," Tax region can not be null");
        this.region = region;
    }

    public BigInteger getId() {
        return id;
    }


    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Override
    public String toString() {
        return "TaxRegion{" +
                "id=" + id +
                ", region='" + region + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TaxRegion region1 = (TaxRegion) o;

        if (!region.equalsIgnoreCase(region1.region)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return region.hashCode();
    }
}

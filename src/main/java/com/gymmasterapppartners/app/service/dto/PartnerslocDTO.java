package com.gymmasterapppartners.app.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import com.gymmasterapppartners.app.domain.enumeration.regionEnum;

/**
 * A DTO for the {@link com.gymmasterapppartners.app.domain.Partnersloc} entity.
 */
public class PartnerslocDTO implements Serializable {
    
    private Long id;

    @NotNull
    private regionEnum region;

    @NotNull
    @Size(min = 3, max = 66)
    private String address;

    @NotNull
    @Max(value = 999999)
    private Integer postalCode;

    @NotNull
    private Instant openTime;

    @NotNull
    private Instant closeTime;

    @NotNull
    private String pocName;

    @NotNull
    private Integer pocNo;

    @NotNull
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
    private String pocEmail;


    private Long partnersId;

    private String partnersCompanyName;
    
    private String partnersUserName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public regionEnum getRegion() {
        return region;
    }

    public void setRegion(regionEnum region) {
        this.region = region;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(Integer postalCode) {
        this.postalCode = postalCode;
    }

    public Instant getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Instant openTime) {
        this.openTime = openTime;
    }

    public Instant getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Instant closeTime) {
        this.closeTime = closeTime;
    }

    public String getPocName() {
        return pocName;
    }

    public void setPocName(String pocName) {
        this.pocName = pocName;
    }

    public Integer getPocNo() {
        return pocNo;
    }

    public void setPocNo(Integer pocNo) {
        this.pocNo = pocNo;
    }

    public String getPocEmail() {
        return pocEmail;
    }

    public void setPocEmail(String pocEmail) {
        this.pocEmail = pocEmail;
    }

    public Long getPartnersId() {
        return partnersId;
    }

    public void setPartnersId(Long partnersId) {
        this.partnersId = partnersId;
    }

    public String getPartnersCompanyName() {
        return partnersCompanyName;
    }

    public void setPartnersCompanyName(String partnersCompanyName) {
        this.partnersCompanyName = partnersCompanyName;
    }
    
    public String getPartnersUserName() {
        return partnersUserName;
    }

    public void setPartnersUserName(String partnersUserName) {
        this.partnersUserName = partnersUserName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PartnerslocDTO)) {
            return false;
        }

        return id != null && id.equals(((PartnerslocDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PartnerslocDTO{" +
            "id=" + getId() +
            ", region='" + getRegion() + "'" +
            ", address='" + getAddress() + "'" +
            ", postalCode=" + getPostalCode() +
            ", openTime='" + getOpenTime() + "'" +
            ", closeTime='" + getCloseTime() + "'" +
            ", pocName='" + getPocName() + "'" +
            ", pocNo=" + getPocNo() +
            ", pocEmail='" + getPocEmail() + "'" +
            ", partnersId=" + getPartnersId() +
            ", partnersCompanyName='" + getPartnersCompanyName() + "'" +
            ", partnersUserName='" + getPartnersUserName() + "'" +
            "}";
    }
}

package com.gymmasterapppartners.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.Instant;

import com.gymmasterapppartners.app.domain.enumeration.regionEnum;

/**
 * A Partnersloc.
 */
@Entity
@Table(name = "partnersloc")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "partnersloc")
public class Partnersloc implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "region", nullable = false)
    private regionEnum region;

    @NotNull
    @Size(min = 3, max = 66)
    @Column(name = "address", length = 66, nullable = false)
    private String address;

    @NotNull
    @Max(value = 999999)
    @Column(name = "postal_code", nullable = false)
    private Integer postalCode;

    @NotNull
    @Column(name = "open_time", nullable = false)
    private Instant openTime;

    @NotNull
    @Column(name = "close_time", nullable = false)
    private Instant closeTime;

    @NotNull
    @Column(name = "poc_name", nullable = false)
    private String pocName;

    @NotNull
    @Column(name = "poc_no", nullable = false)
    private Integer pocNo;

    @NotNull
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
    @Column(name = "poc_email", nullable = false)
    private String pocEmail;

    @ManyToOne
    @JsonIgnoreProperties(value = "partnerslocs", allowSetters = true)
    private Partners partners;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public regionEnum getRegion() {
        return region;
    }

    public Partnersloc region(regionEnum region) {
        this.region = region;
        return this;
    }

    public void setRegion(regionEnum region) {
        this.region = region;
    }

    public String getAddress() {
        return address;
    }

    public Partnersloc address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPostalCode() {
        return postalCode;
    }

    public Partnersloc postalCode(Integer postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public void setPostalCode(Integer postalCode) {
        this.postalCode = postalCode;
    }

    public Instant getOpenTime() {
        return openTime;
    }

    public Partnersloc openTime(Instant openTime) {
        this.openTime = openTime;
        return this;
    }

    public void setOpenTime(Instant openTime) {
        this.openTime = openTime;
    }

    public Instant getCloseTime() {
        return closeTime;
    }

    public Partnersloc closeTime(Instant closeTime) {
        this.closeTime = closeTime;
        return this;
    }

    public void setCloseTime(Instant closeTime) {
        this.closeTime = closeTime;
    }

    public String getPocName() {
        return pocName;
    }

    public Partnersloc pocName(String pocName) {
        this.pocName = pocName;
        return this;
    }

    public void setPocName(String pocName) {
        this.pocName = pocName;
    }

    public Integer getPocNo() {
        return pocNo;
    }

    public Partnersloc pocNo(Integer pocNo) {
        this.pocNo = pocNo;
        return this;
    }

    public void setPocNo(Integer pocNo) {
        this.pocNo = pocNo;
    }

    public String getPocEmail() {
        return pocEmail;
    }

    public Partnersloc pocEmail(String pocEmail) {
        this.pocEmail = pocEmail;
        return this;
    }

    public void setPocEmail(String pocEmail) {
        this.pocEmail = pocEmail;
    }

    public Partners getPartners() {
        return partners;
    }

    public Partnersloc partners(Partners partners) {
        this.partners = partners;
        return this;
    }

    public void setPartners(Partners partners) {
        this.partners = partners;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Partnersloc)) {
            return false;
        }
        return id != null && id.equals(((Partnersloc) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Partnersloc{" +
            "id=" + getId() +
            ", region='" + getRegion() + "'" +
            ", address='" + getAddress() + "'" +
            ", postalCode=" + getPostalCode() +
            ", openTime='" + getOpenTime() + "'" +
            ", closeTime='" + getCloseTime() + "'" +
            ", pocName='" + getPocName() + "'" +
            ", pocNo=" + getPocNo() +
            ", pocEmail='" + getPocEmail() + "'" +
            "}";
    }
}

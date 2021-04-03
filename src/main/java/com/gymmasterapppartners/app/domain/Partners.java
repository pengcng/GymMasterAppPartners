package com.gymmasterapppartners.app.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Partners.
 */
@Entity
@Table(name = "partners")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "partners")
public class Partners implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 30)
    @Column(name = "company_name", length = 30, nullable = false)
    private String companyName;

    @NotNull
    @Size(max = 30)
    @Column(name = "user_name", length = 30, nullable = false, unique = true)
    private String userName;

    @NotNull
    @Column(name = "jhi_type", nullable = false)
    private String type;

    @NotNull
    @Column(name = "active_ind", nullable = false)
    private Boolean activeInd;

    @OneToMany(mappedBy = "partners")
    private Set<Partnersloc> partnerslocs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public Partners companyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getUserName() {
        return userName;
    }

    public Partners userName(String userName) {
        this.userName = userName;
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getType() {
        return type;
    }

    public Partners type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean isActiveInd() {
        return activeInd;
    }

    public Partners activeInd(Boolean activeInd) {
        this.activeInd = activeInd;
        return this;
    }

    public void setActiveInd(Boolean activeInd) {
        this.activeInd = activeInd;
    }

    public Set<Partnersloc> getPartnerslocs() {
        return partnerslocs;
    }

    public Partners partnerslocs(Set<Partnersloc> partnerslocs) {
        this.partnerslocs = partnerslocs;
        return this;
    }

    public Partners addPartnersloc(Partnersloc partnersloc) {
        this.partnerslocs.add(partnersloc);
        partnersloc.setPartners(this);
        return this;
    }

    public Partners removePartnersloc(Partnersloc partnersloc) {
        this.partnerslocs.remove(partnersloc);
        partnersloc.setPartners(null);
        return this;
    }

    public void setPartnerslocs(Set<Partnersloc> partnerslocs) {
        this.partnerslocs = partnerslocs;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Partners)) {
            return false;
        }
        return id != null && id.equals(((Partners) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Partners{" +
            "id=" + getId() +
            ", companyName='" + getCompanyName() + "'" +
            ", userName='" + getUserName() + "'" +
            ", type='" + getType() + "'" +
            ", activeInd='" + isActiveInd() + "'" +
            "}";
    }
}

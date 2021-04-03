package com.gymmasterapppartners.app.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.gymmasterapppartners.app.domain.Partners} entity.
 */
public class PartnersDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(max = 30)
    private String companyName;

    @NotNull
    @Size(max = 30)
    private String userName;

    @NotNull
    private String type;

    @NotNull
    private Boolean activeInd;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean isActiveInd() {
        return activeInd;
    }

    public void setActiveInd(Boolean activeInd) {
        this.activeInd = activeInd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PartnersDTO)) {
            return false;
        }

        return id != null && id.equals(((PartnersDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PartnersDTO{" +
            "id=" + getId() +
            ", companyName='" + getCompanyName() + "'" +
            ", userName='" + getUserName() + "'" +
            ", type='" + getType() + "'" +
            ", activeInd='" + isActiveInd() + "'" +
            "}";
    }
}

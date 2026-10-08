package com.reunitex;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "families")
public class Family {

    @Id
    private String familyId;

    private String mobile;

    private String identityType;

    private String identityReference;

    public Family() {
    }

    public Family(
            String familyId,
            String mobile,
            String identityType,
            String identityReference) {

        this.familyId = familyId;
        this.mobile = mobile;
        this.identityType = identityType;
        this.identityReference = identityReference;
    }

    public String getFamilyId() {
        return familyId;
    }

    public void setFamilyId(String familyId) {
        this.familyId = familyId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIdentityType() {
        return identityType;
    }

    public void setIdentityType(String identityType) {
        this.identityType = identityType;
    }

    public String getIdentityReference() {
        return identityReference;
    }

    public void setIdentityReference(String identityReference) {
        this.identityReference = identityReference;
    }
}
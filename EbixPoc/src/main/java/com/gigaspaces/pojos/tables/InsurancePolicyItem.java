package com.gigaspaces.pojos.tables;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

/**
 * @author Denys_Novikov
 * Date: 12/21/18
 */
@Entity
@Table(name = "tblInsurancePolicyItems")
@SpaceClass
public class InsurancePolicyItem {

    @Id
    @Column(name = "Id")
    private Integer id;
    @Column(name = "reference")
    private String reference;
    @Column(name = "country")
    private String country;
    @Column(name = "state")
    private String state;
    @Column(name = "city")
    private String city;
    @Column(name = "tiv")
    private Long tiv;
    @Column(name = "inception")
    private Date inception;
    @Column(name = "expiry")
    private Date expiry;
    @Column(name = "policyaid")
    private Integer policyaId;
    @Column(name = "TempPolicyItemID")
    private Integer tempPolicyItemId;

    public InsurancePolicyItem() {}

    @SpaceId
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getTiv() {
        return tiv;
    }

    public void setTiv(Long tiv) {
        this.tiv = tiv;
    }

    public Date getInception() {
        return inception;
    }

    public void setInception(Date inception) {
        this.inception = inception;
    }

    public Date getExpiry() {
        return expiry;
    }

    public void setExpiry(Date expiry) {
        this.expiry = expiry;
    }

    public Integer getPolicyaId() {
        return policyaId;
    }

    public void setPolicyaId(Integer policyaId) {
        this.policyaId = policyaId;
    }

    public Integer getTempPolicyItemId() {
        return tempPolicyItemId;
    }

    public void setTempPolicyItemId(Integer tempPolicyItemId) {
        this.tempPolicyItemId = tempPolicyItemId;
    }
}

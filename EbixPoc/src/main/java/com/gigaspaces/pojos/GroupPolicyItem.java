package com.gigaspaces.pojos;

import java.sql.Date;

/**
 * @author Denys_Novikov
 * Date: 12/24/18
 */
public class GroupPolicyItem {

    private Integer id;
    private String reference;
    private String country;
    private String state;
    private String city;
    private Long tiv;
    private Date inception;
    private Date expiry;
    private Integer policyaid;
    private Integer tempPolicyItemId;
    private Integer groupingId;
    private Long line;
    private String policyReference;

    public GroupPolicyItem() {}

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

    public Integer getPolicyaid() {
        return policyaid;
    }

    public void setPolicyaid(Integer policyaid) {
        this.policyaid = policyaid;
    }

    public Integer getTempPolicyItemId() {
        return tempPolicyItemId;
    }

    public void setTempPolicyItemId(Integer tempPolicyItemId) {
        this.tempPolicyItemId = tempPolicyItemId;
    }

    public Integer getGroupingId() {
        return groupingId;
    }

    public void setGroupingId(Integer groupingId) {
        this.groupingId = groupingId;
    }

    public Long getLine() {
        return line;
    }

    public void setLine(Long line) {
        this.line = line;
    }

    public String getPolicyReference() {
        return policyReference;
    }

    public void setPolicyReference(String policyReference) {
        this.policyReference = policyReference;
    }
}

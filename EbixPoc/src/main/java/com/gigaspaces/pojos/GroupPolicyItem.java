package com.gigaspaces.pojos;

import java.sql.Date;

/**
 * @author Denys_Novikov
 * Date: 12/24/18
 */
public class GroupPolicyItem {

    private int id;
    private String reference;
    private String country;
    private String state;
    private String city;
    private long tiv;
    private Date inception;
    private Date expiry;
    private int policyaid;
    private int tempPolicyItemId;
    private int groupingId;
    private long line;
    private String policyReference;

    public GroupPolicyItem() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public long getTiv() {
        return tiv;
    }

    public void setTiv(long tiv) {
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

    public int getPolicyaid() {
        return policyaid;
    }

    public void setPolicyaid(int policyaid) {
        this.policyaid = policyaid;
    }

    public int getTempPolicyItemId() {
        return tempPolicyItemId;
    }

    public void setTempPolicyItemId(int tempPolicyItemId) {
        this.tempPolicyItemId = tempPolicyItemId;
    }

    public int getGroupingId() {
        return groupingId;
    }

    public void setGroupingId(int groupingId) {
        this.groupingId = groupingId;
    }

    public long getLine() {
        return line;
    }

    public void setLine(long line) {
        this.line = line;
    }

    public String getPolicyReference() {
        return policyReference;
    }

    public void setPolicyReference(String policyReference) {
        this.policyReference = policyReference;
    }
}

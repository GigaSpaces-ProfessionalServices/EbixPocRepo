package com.gigaspaces.pojos;

import java.sql.Date;

/**
 * @author Denys_Novikov
 * Date: 12/24/18
 */
public class Coverage {

    private Integer id;
    private String reference;
    private String country;
    private String state;
    private String city;
    private Date inception;
    private Date expiry;
    private Integer policyaid;
    private Integer groupingId;
    private String coverageId;
    private Long value;
    private String currencyId;
    private Boolean ai;
    private Integer coverageDependencyId;
    private Long percentage;
    private Integer periodOfIndemnity;
    private Long sumTIVForExcess;
    private Long apportionExcess;
    private Long sumTIVForLimit;
    private Long apportionLimit;
    private Long sumTIVForExcessCSL;
    private Long sumTIVForLimitCSL;
    private Long newExposure;

    public Coverage() {}

    public Coverage(Coverage source) {
        this.id = source.id;
        this.reference = source.reference;
        this.country = source.country;
        this.state = source.state;
        this.city = source.city;
        this.inception = source.inception;
        this.expiry = source.expiry;
        this.policyaid = source.policyaid;
        this.groupingId = source.groupingId;
        this.coverageId = source.coverageId;
        this.value = source.value;
        this.currencyId = source.currencyId;
        this.ai = source.ai;
        this.coverageDependencyId = source.coverageDependencyId;
        this.percentage = source.percentage;
        this.periodOfIndemnity = source.periodOfIndemnity;
        this.sumTIVForExcess = source.sumTIVForExcess;
        this.apportionExcess = source.apportionExcess;
        this.sumTIVForLimit = source.sumTIVForLimit;
        this.apportionLimit = source.apportionLimit;
        this.sumTIVForExcessCSL = source.sumTIVForExcessCSL;
        this.sumTIVForLimitCSL = source.sumTIVForLimitCSL;
        this.newExposure = source.newExposure;
    }

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

    public Integer getGroupingId() {
        return groupingId;
    }

    public void setGroupingId(Integer groupingId) {
        this.groupingId = groupingId;
    }

    public String getCoverageId() {
        return coverageId;
    }

    public void setCoverageId(String coverageId) {
        this.coverageId = coverageId;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public Boolean getAi() {
        return ai;
    }

    public void setAi(Boolean ai) {
        this.ai = ai;
    }

    public Long getPercentage() {
        return percentage;
    }

    public void setPercentage(Long percentage) {
        this.percentage = percentage;
    }

    public Integer getPeriodOfIndemnity() {
        return periodOfIndemnity;
    }

    public void setPeriodOfIndemnity(Integer periodOfIndemnity) {
        this.periodOfIndemnity = periodOfIndemnity;
    }

    public Long getSumTIVForExcess() {
        return sumTIVForExcess;
    }

    public void setSumTIVForExcess(Long sumTIVForExcess) {
        this.sumTIVForExcess = sumTIVForExcess;
    }

    public Long getApportionExcess() {
        return apportionExcess;
    }

    public void setApportionExcess(Long apportionExcess) {
        this.apportionExcess = apportionExcess;
    }

    public Long getSumTIVForLimit() {
        return sumTIVForLimit;
    }

    public void setSumTIVForLimit(Long sumTIVForLimit) {
        this.sumTIVForLimit = sumTIVForLimit;
    }

    public Long getApportionLimit() {
        return apportionLimit;
    }

    public void setApportionLimit(Long apportionLimit) {
        this.apportionLimit = apportionLimit;
    }

    public Long getSumTIVForExcessCSL() {
        return sumTIVForExcessCSL;
    }

    public void setSumTIVForExcessCSL(Long sumTIVForExcessCSL) {
        this.sumTIVForExcessCSL = sumTIVForExcessCSL;
    }

    public Long getSumTIVForLimitCSL() {
        return sumTIVForLimitCSL;
    }

    public void setSumTIVForLimitCSL(Long sumTIVForLimitCSL) {
        this.sumTIVForLimitCSL = sumTIVForLimitCSL;
    }

    public Long getNewExposure() {
        return newExposure;
    }

    public void setNewExposure(Long newExposure) {
        this.newExposure = newExposure;
    }

    public Integer getCoverageDependencyId() {
        return coverageDependencyId;
    }

    public void setCoverageDependencyId(Integer coverageDependencyId) {
        this.coverageDependencyId = coverageDependencyId;
    }
}

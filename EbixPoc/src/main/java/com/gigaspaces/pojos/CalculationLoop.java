package com.gigaspaces.pojos;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Objects;

/**
 * @author Denys_Novikov
 * Date: 12/26/18
 */
public class CalculationLoop {

    private Integer id;
    private Integer policyaid;
    private Integer groupingId;
    private String coverageId;
    private BigDecimal TIV;
    private String covCurrency;
    private Boolean covAi;
    private Integer coverageDependencyId;
    private BigDecimal percentage;
    private Integer periodOfIndemnity;
    private BigDecimal sumTIVForExcess;
    private BigDecimal apportionExcess;
    private BigDecimal sumTIVForLimit;
    private BigDecimal apportionLimit;
    private BigDecimal sumTIVForExcessCSL;
    private BigDecimal sumTIVForLimitCSL;
    private BigDecimal newExposure;
    private BigDecimal limit;
    private BigDecimal excess;
    private BigDecimal deductible;
    private BigDecimal newLimit;
    private BigDecimal newExcess;
    private BigDecimal newDeductible;
    private Boolean csl;
    private Integer cslSeq;
    private String limitType;
    private Integer lhSeq;
    private String lSeq;
    private String limitHeaderName;
    private Boolean riLimit;
    private BigDecimal afterExcess;
    private Boolean maxSeq;
    private BigDecimal finalExposure;
    private String policyReference;
    private BigDecimal line;
    private String reference;
    private String country;
    private String state;
    private String city;
    private Date inception;
    private Date expiry;

    private String calcAudit = "";
    private Boolean excessBreach;
    private Boolean limitBreach;


    public CalculationLoop() {
    }

    public CalculationLoop(CalculationLoop source) {
        this.id = source.id;
        this.policyaid = source.policyaid;
        this.groupingId = source.groupingId;
        this.coverageId = source.coverageId;
        this.TIV = source.TIV;
        this.covCurrency = source.covCurrency;
        this.covAi = source.covAi;
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
        this.limit = source.limit;
        this.excess = source.excess;
        this.deductible = source.deductible;
        this.newLimit = source.newLimit;
        this.newExcess = source.newExcess;
        this.newDeductible = source.newDeductible;
        this.csl = source.csl;
        this.cslSeq = source.cslSeq;
        this.limitType = source.limitType;
        this.lhSeq = source.lhSeq;
        this.lSeq = source.lSeq;
        this.limitHeaderName = source.limitHeaderName;
        this.riLimit = source.riLimit;
        this.afterExcess = source.afterExcess;
        this.maxSeq = source.maxSeq;
        this.finalExposure = source.finalExposure;
        this.policyReference = source.policyReference;
        this.line = source.line;
        this.reference = source.reference;
        this.country = source.country;
        this.state = source.state;
        this.city = source.city;
        this.inception = source.inception;
        this.expiry = source.expiry;
        this.calcAudit = source.calcAudit;
        this.excessBreach = source.excessBreach;
        this.limitBreach = source.limitBreach;
    }

    public void copyMatchingFields(Combination source) {
        this.id = source.getId();
        this.policyaid = source.getPolicyaid();
        this.groupingId = source.getGroupingId();
        this.coverageId = source.getCoverageId();
        this.TIV = source.getTIV();
        this.covCurrency = source.getCovCurrency();
        this.covAi = source.getCovAi();
        this.coverageDependencyId = source.getCoverageDependencyId();
        this.percentage = source.getPercentage();
        this.periodOfIndemnity = source.getPeriodOfIndemnity();
        this.sumTIVForExcess = source.getSumTIVForExcess();
        this.apportionExcess = source.getApportionExcess();
        this.sumTIVForLimit = source.getSumTIVForLimit();
        this.apportionLimit = source.getApportionLimit();
        this.sumTIVForExcessCSL = source.getSumTIVForExcessCSL();
        this.sumTIVForLimitCSL = source.getSumTIVForLimitCSL();
        this.newExposure = source.getNewExposure();
        this.limit = source.getLimit();
        this.excess = source.getExcess();
        this.deductible = source.getDeductible();
        this.newLimit = source.getNewLimit();
        this.newExcess = source.getNewExcess();
        this.newDeductible = source.getNewDeductible();
        this.csl = source.getCsl();
        this.cslSeq = source.getCslSeq();
        this.limitType = source.getLimitType();
        this.lhSeq = source.getLhSeq();
        this.lSeq = source.getlSeq();
        this.limitHeaderName = source.getLimitHeaderName();
        this.riLimit = source.getRiLimit();
        this.afterExcess = source.getAfterExcess();
        this.maxSeq = source.getMaxSeq();
        this.finalExposure = source.getFinalExposure();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public BigDecimal getTIV() {
        return TIV;
    }

    public void setTIV(BigDecimal TIV) {
        this.TIV = TIV;
    }

    public String getCovCurrency() {
        return covCurrency;
    }

    public void setCovCurrency(String covCurrency) {
        this.covCurrency = covCurrency;
    }

    public Boolean getCovAi() {
        return covAi;
    }

    public void setCovAi(Boolean covAi) {
        this.covAi = covAi;
    }

    public Integer getCoverageDependencyId() {
        return coverageDependencyId;
    }

    public void setCoverageDependencyId(Integer coverageDependencyId) {
        this.coverageDependencyId = coverageDependencyId;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }

    public Integer getPeriodOfIndemnity() {
        return periodOfIndemnity;
    }

    public void setPeriodOfIndemnity(Integer periodOfIndemnity) {
        this.periodOfIndemnity = periodOfIndemnity;
    }

    public BigDecimal getSumTIVForExcess() {
        return sumTIVForExcess;
    }

    public void setSumTIVForExcess(BigDecimal sumTIVForExcess) {
        this.sumTIVForExcess = sumTIVForExcess;
    }

    public BigDecimal getApportionExcess() {
        return apportionExcess;
    }

    public void setApportionExcess(BigDecimal apportionExcess) {
        this.apportionExcess = apportionExcess;
    }

    public BigDecimal getSumTIVForLimit() {
        return sumTIVForLimit;
    }

    public void setSumTIVForLimit(BigDecimal sumTIVForLimit) {
        this.sumTIVForLimit = sumTIVForLimit;
    }

    public BigDecimal getApportionLimit() {
        return apportionLimit;
    }

    public void setApportionLimit(BigDecimal apportionLimit) {
        this.apportionLimit = apportionLimit;
    }

    public BigDecimal getSumTIVForExcessCSL() {
        return sumTIVForExcessCSL;
    }

    public void setSumTIVForExcessCSL(BigDecimal sumTIVForExcessCSL) {
        this.sumTIVForExcessCSL = sumTIVForExcessCSL;
    }

    public BigDecimal getSumTIVForLimitCSL() {
        return sumTIVForLimitCSL;
    }

    public void setSumTIVForLimitCSL(BigDecimal sumTIVForLimitCSL) {
        this.sumTIVForLimitCSL = sumTIVForLimitCSL;
    }

    public BigDecimal getNewExposure() {
        return newExposure;
    }

    public void setNewExposure(BigDecimal newExposure) {
        this.newExposure = newExposure;
    }

    public BigDecimal getLimit() {
        return limit;
    }

    public void setLimit(BigDecimal limit) {
        this.limit = limit;
    }

    public BigDecimal getExcess() {
        return excess;
    }

    public void setExcess(BigDecimal excess) {
        this.excess = excess;
    }

    public BigDecimal getDeductible() {
        return deductible;
    }

    public void setDeductible(BigDecimal deductible) {
        this.deductible = deductible;
    }

    public BigDecimal getNewLimit() {
        return newLimit;
    }

    public void setNewLimit(BigDecimal newLimit) {
        this.newLimit = newLimit;
    }

    public BigDecimal getNewExcess() {
        return newExcess;
    }

    public void setNewExcess(BigDecimal newExcess) {
        this.newExcess = newExcess;
    }

    public BigDecimal getNewDeductible() {
        return newDeductible;
    }

    public void setNewDeductible(BigDecimal newDeductible) {
        this.newDeductible = newDeductible;
    }

    public Boolean getCsl() {
        return csl;
    }

    public void setCsl(Boolean csl) {
        this.csl = csl;
    }

    public Integer getCslSeq() {
        return cslSeq;
    }

    public void setCslSeq(Integer cslSeq) {
        this.cslSeq = cslSeq;
    }

    public String getLimitType() {
        return limitType;
    }

    public void setLimitType(String limitType) {
        this.limitType = limitType;
    }

    public Integer getLhSeq() {
        return lhSeq;
    }

    public void setLhSeq(Integer lhSeq) {
        this.lhSeq = lhSeq;
    }

    public String getlSeq() {
        return lSeq;
    }

    public void setlSeq(String lSeq) {
        this.lSeq = lSeq;
    }

    public String getLimitHeaderName() {
        return limitHeaderName;
    }

    public void setLimitHeaderName(String limitHeaderName) {
        this.limitHeaderName = limitHeaderName;
    }

    public Boolean getRiLimit() {
        return riLimit;
    }

    public void setRiLimit(Boolean riLimit) {
        this.riLimit = riLimit;
    }

    public BigDecimal getAfterExcess() {
        return afterExcess;
    }

    public void setAfterExcess(BigDecimal afterExcess) {
        this.afterExcess = afterExcess;
    }

    public Boolean getMaxSeq() {
        return maxSeq;
    }

    public void setMaxSeq(Boolean maxSeq) {
        this.maxSeq = maxSeq;
    }

    public BigDecimal getFinalExposure() {
        return finalExposure;
    }

    public void setFinalExposure(BigDecimal finalExposure) {
        this.finalExposure = finalExposure;
    }

    public String getPolicyReference() {
        return policyReference;
    }

    public void setPolicyReference(String policyReference) {
        this.policyReference = policyReference;
    }

    public BigDecimal getLine() {
        return line;
    }

    public void setLine(BigDecimal line) {
        this.line = line;
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

    public String getCalcAudit() {
        return calcAudit;
    }

    public void setCalcAudit(String calcAudit) {
        this.calcAudit = calcAudit;
    }

    public Boolean getExcessBreach() {
        return excessBreach;
    }

    public void setExcessBreach(Boolean excessBreach) {
        this.excessBreach = excessBreach;
    }

    public Boolean getLimitBreach() {
        return limitBreach;
    }

    public void setLimitBreach(Boolean limitBreach) {
        this.limitBreach = limitBreach;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CalculationLoop that = (CalculationLoop) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(policyaid, that.policyaid) &&
                Objects.equals(groupingId, that.groupingId) &&
                Objects.equals(coverageId, that.coverageId) &&
                Objects.equals(TIV, that.TIV) &&
                Objects.equals(covCurrency, that.covCurrency) &&
                Objects.equals(covAi, that.covAi) &&
                Objects.equals(coverageDependencyId, that.coverageDependencyId) &&
                Objects.equals(percentage, that.percentage) &&
                Objects.equals(periodOfIndemnity, that.periodOfIndemnity) &&
                Objects.equals(sumTIVForExcess, that.sumTIVForExcess) &&
                Objects.equals(apportionExcess, that.apportionExcess) &&
                Objects.equals(sumTIVForLimit, that.sumTIVForLimit) &&
                Objects.equals(apportionLimit, that.apportionLimit) &&
                Objects.equals(sumTIVForExcessCSL, that.sumTIVForExcessCSL) &&
                Objects.equals(sumTIVForLimitCSL, that.sumTIVForLimitCSL) &&
                Objects.equals(newExposure, that.newExposure) &&
                Objects.equals(limit, that.limit) &&
                Objects.equals(excess, that.excess) &&
                Objects.equals(deductible, that.deductible) &&
                Objects.equals(newLimit, that.newLimit) &&
                Objects.equals(newExcess, that.newExcess) &&
                Objects.equals(newDeductible, that.newDeductible) &&
                Objects.equals(csl, that.csl) &&
                Objects.equals(cslSeq, that.cslSeq) &&
                Objects.equals(limitType, that.limitType) &&
                Objects.equals(lhSeq, that.lhSeq) &&
                Objects.equals(lSeq, that.lSeq) &&
                Objects.equals(limitHeaderName, that.limitHeaderName) &&
                Objects.equals(riLimit, that.riLimit) &&
                Objects.equals(afterExcess, that.afterExcess) &&
                Objects.equals(maxSeq, that.maxSeq) &&
                Objects.equals(finalExposure, that.finalExposure) &&
                Objects.equals(policyReference, that.policyReference) &&
                Objects.equals(line, that.line) &&
                Objects.equals(reference, that.reference) &&
                Objects.equals(country, that.country) &&
                Objects.equals(state, that.state) &&
                Objects.equals(city, that.city) &&
                Objects.equals(inception, that.inception) &&
                Objects.equals(expiry, that.expiry) &&
                Objects.equals(calcAudit, that.calcAudit) &&
                Objects.equals(excessBreach, that.excessBreach) &&
                Objects.equals(limitBreach, that.limitBreach);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, policyaid, groupingId, coverageId, TIV, covCurrency, covAi, coverageDependencyId, percentage, periodOfIndemnity, sumTIVForExcess, apportionExcess, sumTIVForLimit, apportionLimit, sumTIVForExcessCSL, sumTIVForLimitCSL, newExposure, limit, excess, deductible, newLimit, newExcess, newDeductible, csl, cslSeq, limitType, lhSeq, lSeq, limitHeaderName, riLimit, afterExcess, maxSeq, finalExposure, policyReference, line, reference, country, state, city, inception, expiry, calcAudit, excessBreach, limitBreach);
    }
}

package com.gigaspaces.pojos;

import java.math.BigDecimal;
import java.math.MathContext;
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
    private BigDecimal apportionExcess; // x100
    private BigDecimal sumTIVForLimit; // x100
    private BigDecimal apportionLimit; // x100
    private BigDecimal sumTIVForExcessCSL;
    private BigDecimal sumTIVForLimitCSL;
    private BigDecimal newExposure;
    private BigDecimal limit;
    private BigDecimal excess;
    private BigDecimal deductible;
    private BigDecimal newLimit;  // x100
    private BigDecimal newExcess;  // x100
    private BigDecimal newDeductible;
    private Boolean csl;
    private Integer cslSeq;
    private String limitType;
    private Integer lhSeq;
    private String lSeq;
    private String limitHeaderName;
    private Boolean riLimit;
    private BigDecimal afterExcess;  // x100
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

    public void copyMatchingFields(Combination source) {
        this.id = source.getId();
        this.policyaid = source.getPolicyaid();
        this.groupingId = source.getGroupingId();
        this.coverageId = source.getCoverageId();
        this.TIV = source.getTIV() != null ? new BigDecimal(source.getTIV(), new MathContext(20)).setScale(8) : null;
        this.covCurrency = source.getCovCurrency();
        this.covAi = source.getCovAi();
        this.coverageDependencyId = source.getCoverageDependencyId();
        this.percentage = source.getPercentage() != null ? new BigDecimal(source.getPercentage(), new MathContext(20)).setScale(8) : null;
        this.periodOfIndemnity = source.getPeriodOfIndemnity();
        this.sumTIVForExcess = source.getSumTIVForExcess() != null ? new BigDecimal(source.getSumTIVForExcess(), new MathContext(20)).setScale(8) : null;
        this.apportionExcess = source.getApportionExcess() != null ? new BigDecimal(source.getApportionExcess(), new MathContext(20)).setScale(8) : null;
        this.sumTIVForLimit = source.getSumTIVForLimit() != null ? new BigDecimal(source.getSumTIVForLimit(), new MathContext(20)).setScale(8) : null;
        this.apportionLimit = source.getApportionLimit() != null ? new BigDecimal(source.getApportionLimit(), new MathContext(20)).setScale(8) : null;
        this.sumTIVForExcessCSL = source.getSumTIVForExcessCSL() != null ? new BigDecimal(source.getSumTIVForExcessCSL(), new MathContext(20)).setScale(8) : null;
        this.sumTIVForLimitCSL = source.getSumTIVForLimitCSL() != null ? new BigDecimal(source.getSumTIVForLimitCSL(), new MathContext(20)).setScale(8) : null;
        this.newExposure = source.getNewExposure() != null ? new BigDecimal(source.getNewExposure(), new MathContext(20)).setScale(8) : null;
        this.limit = source.getLimit() != null ? new BigDecimal(source.getLimit(), new MathContext(20)).setScale(8) : null;
        this.excess = source.getExcess() != null ? new BigDecimal(source.getExcess(), new MathContext(20)).setScale(8) : null;
        this.deductible = source.getDeductible() != null ? new BigDecimal(source.getDeductible(), new MathContext(20)).setScale(8) : null;
        this.newLimit = source.getNewLimit() != null ? new BigDecimal(source.getNewLimit(), new MathContext(20)).setScale(8) : null;
        this.newExcess = source.getNewExcess() != null ? new BigDecimal(source.getNewExcess(), new MathContext(20)).setScale(8) : null;
        this.newDeductible = source.getNewDeductible() != null ? new BigDecimal(source.getNewDeductible(), new MathContext(20)).setScale(8) : null;
        this.csl = source.getCsl();
        this.cslSeq = source.getCslSeq();
        this.limitType = source.getLimitType();
        this.lhSeq = source.getLhSeq();
        this.lSeq = source.getlSeq();
        this.limitHeaderName = source.getLimitHeaderName();
        this.riLimit = source.getRiLimit();
        this.afterExcess = source.getAfterExcess() != null ? new BigDecimal(source.getAfterExcess(), new MathContext(20)).setScale(8) : null;
        this.maxSeq = source.getMaxSeq();
        this.finalExposure = source.getFinalExposure() != null ? new BigDecimal(source.getFinalExposure(), new MathContext(20)).setScale(8) : null;
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


    @Override
    public String toString() {
        return "CalculationLoop{" +
                "id=" + id +
                ", policyaid=" + policyaid +
                ", groupingId=" + groupingId +
                ", coverageId='" + coverageId + '\'' +
                ", TIV=" + TIV +
                ", covCurrency='" + covCurrency + '\'' +
                ", covAi=" + covAi +
                ", coverageDependencyId=" + coverageDependencyId +
                ", percentage=" + percentage +
                ", periodOfIndemnity=" + periodOfIndemnity +
                ", sumTIVForExcess=" + sumTIVForExcess +
                ", apportionExcess=" + apportionExcess +
                ", sumTIVForLimit=" + sumTIVForLimit +
                ", apportionLimit=" + apportionLimit +
                ", sumTIVForExcessCSL=" + sumTIVForExcessCSL +
                ", sumTIVForLimitCSL=" + sumTIVForLimitCSL +
                ", newExposure=" + newExposure +
                ", limit=" + limit +
                ", excess=" + excess +
                ", deductible=" + deductible +
                ", newLimit=" + newLimit +
                ", newExcess=" + newExcess +
                ", newDeductible=" + newDeductible +
                ", csl=" + csl +
                ", cslSeq=" + cslSeq +
                ", limitType='" + limitType + '\'' +
                ", lhSeq=" + lhSeq +
                ", lSeq='" + lSeq + '\'' +
                ", limitHeaderName='" + limitHeaderName + '\'' +
                ", riLimit=" + riLimit +
                ", afterExcess=" + afterExcess +
                ", maxSeq=" + maxSeq +
                ", finalExposure=" + finalExposure +
                ", policyReference='" + policyReference + '\'' +
                ", line=" + line +
                ", reference='" + reference + '\'' +
                ", country='" + country + '\'' +
                ", state='" + state + '\'' +
                ", city='" + city + '\'' +
                ", inception=" + inception +
                ", expiry=" + expiry +
                ", calcAudit='" + calcAudit + '\'' +
                ", excessBreach=" + excessBreach +
                ", limitBreach=" + limitBreach +
                '}';
    }
}

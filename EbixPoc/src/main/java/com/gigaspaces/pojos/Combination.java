package com.gigaspaces.pojos;

import java.math.BigDecimal;

/**
 * @author Denys_Novikov
 * Date: 12/26/18
 */
public class Combination {

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

    public Combination() {
    }

    public Combination(Combination source) {
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
}

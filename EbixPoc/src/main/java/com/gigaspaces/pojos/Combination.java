package com.gigaspaces.pojos;

/**
 * @author Denys_Novikov
 * Date: 12/26/18
 */
public class Combination {

    private Integer id;
    private Integer policyaid;
    private Integer groupingId;
    private String coverageId;
    private Long TIV;
    private String covCurrency;
    private Boolean covAi;
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
    private Long limit;
    private Long excess;
    private Long deductible;
    private Long newLimit;
    private Long newExcess;
    private Long newDeductible;
    private Boolean csl;
    private Integer cslSeq;
    private String limitType;
    private Integer lhSeq;
    private String lSeq;
    private String limitHeaderName;
    private Boolean riLimit;
    private Long afterExcess;
    private Boolean maxSeq;
    private Long finalExposure;

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

    public Long getTIV() {
        return TIV;
    }

    public void setTIV(Long TIV) {
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

    public Long getLimit() {
        return limit;
    }

    public void setLimit(Long limit) {
        this.limit = limit;
    }

    public Long getExcess() {
        return excess;
    }

    public void setExcess(Long excess) {
        this.excess = excess;
    }

    public Long getDeductible() {
        return deductible;
    }

    public void setDeductible(Long deductible) {
        this.deductible = deductible;
    }

    public Long getNewLimit() {
        return newLimit;
    }

    public void setNewLimit(Long newLimit) {
        this.newLimit = newLimit;
    }

    public Long getNewExcess() {
        return newExcess;
    }

    public void setNewExcess(Long newExcess) {
        this.newExcess = newExcess;
    }

    public Long getNewDeductible() {
        return newDeductible;
    }

    public void setNewDeductible(Long newDeductible) {
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

    public Long getAfterExcess() {
        return afterExcess;
    }

    public void setAfterExcess(Long afterExcess) {
        this.afterExcess = afterExcess;
    }

    public Boolean getMaxSeq() {
        return maxSeq;
    }

    public void setMaxSeq(Boolean maxSeq) {
        this.maxSeq = maxSeq;
    }

    public Long getFinalExposure() {
        return finalExposure;
    }

    public void setFinalExposure(Long finalExposure) {
        this.finalExposure = finalExposure;
    }
}

package com.gigaspaces.pojos;

import java.math.BigDecimal;

/**
 * @author Denys_Novikov
 * Date: 12/26/18
 */
public class QueryLimit {

    private int id;
    private int policyaid;
    private int groupingId;
    private BigDecimal line;
    private String policyReference;
    private String status;
    private BigDecimal limit;
    private BigDecimal excess;
    private BigDecimal deductible;
    private BigDecimal newLimit;
    private BigDecimal newExcess;
    private BigDecimal newDeductible;
    private Boolean csl;
    private Integer cslSeq;
    private String coverageId;
    private String limitType;
    private Integer lhSeq;
    private Boolean riLimit;
    private String lSeq;
    private String limitHeaderName;
    private BigDecimal afterExcess;
    private Boolean maxSeq;
    private BigDecimal finalExposure;
    private String reference;

    public QueryLimit() {
    }

    public QueryLimit(QueryLimit source) {
        this.id = source.id;
        this.policyaid = source.policyaid;
        this.groupingId = source.groupingId;
        this.line = source.line;
        this.policyReference = source.policyReference;
        this.status = source.status;
        this.limit = source.limit;
        this.excess = source.excess;
        this.deductible = source.deductible;
        this.newLimit = source.newLimit;
        this.newExcess = source.newExcess;
        this.newDeductible = source.newDeductible;
        this.csl = source.csl;
        this.cslSeq = source.cslSeq;
        this.coverageId = source.coverageId;
        this.limitType = source.limitType;
        this.lhSeq = source.lhSeq;
        this.riLimit = source.riLimit;
        this.lSeq = source.lSeq;
        this.limitHeaderName = source.limitHeaderName;
        this.afterExcess = source.afterExcess;
        this.maxSeq = source.maxSeq;
        this.finalExposure = source.finalExposure;
        this.reference = source.reference;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGroupingId() {
        return groupingId;
    }

    public void setGroupingId(int groupingId) {
        this.groupingId = groupingId;
    }

    public BigDecimal getLine() {
        return line;
    }

    public void setLine(BigDecimal line) {
        this.line = line;
    }

    public String getPolicyReference() {
        return policyReference;
    }

    public void setPolicyReference(String policyReference) {
        this.policyReference = policyReference;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getCoverageId() {
        return coverageId;
    }

    public void setCoverageId(String coverageId) {
        this.coverageId = coverageId;
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

    public Boolean getRiLimit() {
        return riLimit;
    }

    public void setRiLimit(Boolean riLimit) {
        this.riLimit = riLimit;
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

    public BigDecimal getAfterExcess() {
        return afterExcess;
    }

    public void setAfterExcess(BigDecimal afterExcess) {
        this.afterExcess = afterExcess;
    }

    public boolean getMaxSeq() {
        return maxSeq;
    }

    public void setMaxSeq(boolean maxSeq) {
        this.maxSeq = maxSeq;
    }

    public BigDecimal getFinalExposure() {
        return finalExposure;
    }

    public void setFinalExposure(BigDecimal finalExposure) {
        this.finalExposure = finalExposure;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public int getPolicyaid() {
        return policyaid;
    }

    public void setPolicyaid(int policyaid) {
        this.policyaid = policyaid;
    }
}

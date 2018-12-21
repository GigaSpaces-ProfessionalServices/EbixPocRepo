package com.gigaspaces.pojos;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @author Denys_Novikov
 * Date: 12/21/18
 */
@Entity
@Table(name = "tblLimits")
@SpaceClass
public class Limit {

    @Id
    @Column(name = "LimitID")
    private Integer limitId;
    @Column(name = "LimitHeaderID")
    private Integer limitHeaderId;
    @Column(name = "LimitHeaderName")
    private String limitHeaderName;
    @Column(name = "LimitHeaderSequence")
    private Integer limitHeaderSequence;
    @Column(name = "LimitSequence")
    private String limitSequence;
    @Column(name = "Limit")
    private BigDecimal limit;
    @Column(name = "Excess")
    private BigDecimal excess;
    @Column(name = "Deductible")
    private BigDecimal deductible;
    @Column(name = "CSL")
    private Boolean csl;
    @Column(name = "LimitType")
    private String limitType;
    @Column(name = "PolicyID")
    private Integer policyId;
    @Column(name = "RILimit")
    private Boolean riLimit;
    @Column(name = "TempLimitID")
    private Integer tempLimitId;
    @Column(name = "TempLimitHeaderID")
    private Integer tempLimitHeaderId;

    public Limit() {}

    @SpaceId
    public Integer getLimitId() {
        return limitId;
    }

    public void setLimitId(Integer limitId) {
        this.limitId = limitId;
    }

    public Integer getLimitHeaderId() {
        return limitHeaderId;
    }

    public void setLimitHeaderId(Integer limitHeaderId) {
        this.limitHeaderId = limitHeaderId;
    }

    public String getLimitHeaderName() {
        return limitHeaderName;
    }

    public void setLimitHeaderName(String limitHeaderName) {
        this.limitHeaderName = limitHeaderName;
    }

    public Integer getLimitHeaderSequence() {
        return limitHeaderSequence;
    }

    public void setLimitHeaderSequence(Integer limitHeaderSequence) {
        this.limitHeaderSequence = limitHeaderSequence;
    }

    public String getLimitSequence() {
        return limitSequence;
    }

    public void setLimitSequence(String limitSequence) {
        this.limitSequence = limitSequence;
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

    public Boolean getCsl() {
        return csl;
    }

    public void setCsl(Boolean csl) {
        this.csl = csl;
    }

    public String getLimitType() {
        return limitType;
    }

    public void setLimitType(String limitType) {
        this.limitType = limitType;
    }

    public Integer getPolicyId() {
        return policyId;
    }

    public void setPolicyId(Integer policyId) {
        this.policyId = policyId;
    }

    public Boolean getRiLimit() {
        return riLimit;
    }

    public void setRiLimit(Boolean riLimit) {
        this.riLimit = riLimit;
    }

    public Integer getTempLimitId() {
        return tempLimitId;
    }

    public void setTempLimitId(Integer tempLimitId) {
        this.tempLimitId = tempLimitId;
    }

    public Integer getTempLimitHeaderId() {
        return tempLimitHeaderId;
    }

    public void setTempLimitHeaderId(Integer tempLimitHeaderId) {
        this.tempLimitHeaderId = tempLimitHeaderId;
    }
}

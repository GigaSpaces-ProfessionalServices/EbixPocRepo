package com.gigaspaces.pojos.tables;

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
@Table(name = "tblInsurancePolicyItemCoverageValue")
@SpaceClass
public class InsurancePolicyItemCoverageValue {

    @Id
    @Column(name = "PolicyItemCoverageID")
    private Long policyItemCoverageId;
    @Column(name = "PolicyID")
    private Integer policyId;
    @Column(name = "PolicyHeaderID")
    private Integer policyHeaderId;
    @Column(name = "PolicyHeaderName")
    private String policyHeaderName;
    @Column(name = "PolicyItemID")
    private Integer policyItemId;
    @Column(name = "CoverageID")
    private String coverageId;
    @Column(name = "Value")
    private BigDecimal value;
    @Column(name = "CurrencyID")
    private String currencyId;
    @Column(name = "AI")
    private Boolean ai;
    @Column(name = "CoverageDependencyID")
    private Integer coverageDependencyId;
    @Column(name = "Percentage")
    private BigDecimal percentage;
    @Column(name = "PeriodOfIndemnity")
    private Integer periodOfIndemnity;

    public InsurancePolicyItemCoverageValue() {}

    @SpaceId
    public Long getPolicyItemCoverageId() {
        return policyItemCoverageId;
    }

    public void setPolicyItemCoverageId(Long policyItemCoverageId) {
        this.policyItemCoverageId = policyItemCoverageId;
    }

    public Integer getPolicyId() {
        return policyId;
    }

    public void setPolicyId(Integer policyId) {
        this.policyId = policyId;
    }

    public Integer getPolicyHeaderId() {
        return policyHeaderId;
    }

    public void setPolicyHeaderId(Integer policyHeaderId) {
        this.policyHeaderId = policyHeaderId;
    }

    public String getPolicyHeaderName() {
        return policyHeaderName;
    }

    public void setPolicyHeaderName(String policyHeaderName) {
        this.policyHeaderName = policyHeaderName;
    }

    public Integer getPolicyItemId() {
        return policyItemId;
    }

    public void setPolicyItemId(Integer policyItemId) {
        this.policyItemId = policyItemId;
    }

    public String getCoverageId() {
        return coverageId;
    }

    public void setCoverageId(String coverageId) {
        this.coverageId = coverageId;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
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
}

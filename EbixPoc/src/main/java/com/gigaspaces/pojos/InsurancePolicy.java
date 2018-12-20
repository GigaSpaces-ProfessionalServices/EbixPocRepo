package com.gigaspaces.pojos;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

/**
 * @author Denys_Novikov
 * Date: 12/20/18
 */
@Entity
@Table(name = "tblInsurancePolicy")
@SpaceClass
public class InsurancePolicy {

    @Id
    @Column(name = "Id")
    private Integer id;
    @Column(name = "Reference")
    private String reference;
    @Column(name = "Line")
    private Long line;
    @Column(name = "Status")
    private String status;
    @Column(name = "inception")
    private Date inception;
    @Column(name = "expiry")
    private Date expiry;
    @Column(name = "limit")
    private Long limit;
    @Column(name = "excess")
    private Long excess;
    @Column(name = "UnlimitedLimit")
    private Boolean unlimitedLimit;
    @Column(name = "temppolicyid")
    private Integer tempPolicyId;

    public InsurancePolicy() {
    }

    @SpaceId
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

    public Long getLine() {
        return line;
    }

    public void setLine(Long line) {
        this.line = line;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Boolean isUnlimitedLimit() {
        return unlimitedLimit;
    }

    public void setUnlimitedLimit(Boolean unlimitedLimit) {
        this.unlimitedLimit = unlimitedLimit;
    }

    public Integer getTempPolicyId() {
        return tempPolicyId;
    }

    public void setTempPolicyId(Integer tempPolicyId) {
        this.tempPolicyId = tempPolicyId;
    }
}

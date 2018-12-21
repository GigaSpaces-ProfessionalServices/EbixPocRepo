package com.gigaspaces.pojos;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Denys_Novikov
 * Date: 12/21/18
 */
@Entity
@Table(name = "tblLimitCoverage")
@SpaceClass
public class LimitCoverage {

    @Id
    @Column(name = "LimitCoverage")
    private Integer limitCoverage;
    @Column(name = "LimitID")
    private Integer limitId;
    @Column(name = "CoverageID")
    private String coverageId;
    @Column(name = "CSLSeq")
    private Integer cslSeq;

    public LimitCoverage() {}

    @SpaceId
    public Integer getLimitCoverage() {
        return limitCoverage;
    }

    public void setLimitCoverage(Integer limitCoverage) {
        this.limitCoverage = limitCoverage;
    }

    public Integer getLimitId() {
        return limitId;
    }

    public void setLimitId(Integer limitId) {
        this.limitId = limitId;
    }

    public String getCoverageId() {
        return coverageId;
    }

    public void setCoverageId(String coverageId) {
        this.coverageId = coverageId;
    }

    public Integer getCslSeq() {
        return cslSeq;
    }

    public void setCslSeq(Integer cslSeq) {
        this.cslSeq = cslSeq;
    }
}

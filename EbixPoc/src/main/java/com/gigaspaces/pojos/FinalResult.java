package com.gigaspaces.pojos;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceId;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author Denys_Novikov
 * Date: 12/26/18
 */
@SpaceClass
public class FinalResult {

    private Integer id;
    private Integer policyaid;
    private Integer groupingId;
    private String country;
    private String state;
    private String coverageId;
    private BigDecimal gross;
    private BigDecimal net;

    public FinalResult() {
    }

    @SpaceId
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

    public String getCoverageId() {
        return coverageId;
    }

    public void setCoverageId(String coverageId) {
        this.coverageId = coverageId;
    }

    public BigDecimal getGross() {
        return gross;
    }

    public void setGross(BigDecimal gross) {
        this.gross = gross;
    }

    public BigDecimal getNet() {
        return net;
    }

    public void setNet(BigDecimal net) {
        this.net = net;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FinalResult that = (FinalResult) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(policyaid, that.policyaid) &&
                Objects.equals(groupingId, that.groupingId) &&
                Objects.equals(country, that.country) &&
                Objects.equals(state, that.state) &&
                Objects.equals(coverageId, that.coverageId) &&
                Objects.equals(gross, that.gross) &&
                Objects.equals(net, that.net);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, policyaid, groupingId, country, state, coverageId, gross, net);
    }

    @Override
    public String toString() {
        return "FinalResult{" +
                "id=" + id +
                ", policyaid=" + policyaid +
                ", groupingId=" + groupingId +
                ", country='" + country + '\'' +
                ", state='" + state + '\'' +
                ", coverageId='" + coverageId + '\'' +
                ", gross=" + gross +
                ", net=" + net +
                '}';
    }
}

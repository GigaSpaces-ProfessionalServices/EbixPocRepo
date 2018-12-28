package com.gigaspaces.pojos;

/**
 * @author Denys_Novikov
 * Date: 12/27/18
 */
public class Max {
    private Integer maxlh;
    private String maxl; // last after sorting alphabetically
    private Integer id;
    private Integer policyaid;
    private Integer groupingId;

    public Max(Integer maxlh, String maxl, Integer id, Integer policyaid, Integer groupingId) {
        this.maxlh = maxlh;
        this.maxl = maxl;
        this.id = id;
        this.policyaid = policyaid;
        this.groupingId = groupingId;
    }

    public Integer getMaxlh() {
        return maxlh;
    }

    public void setMaxlh(Integer maxlh) {
        this.maxlh = maxlh;
    }

    public String getMaxl() {
        return maxl;
    }

    public void setMaxl(String maxl) {
        this.maxl = maxl;
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
}

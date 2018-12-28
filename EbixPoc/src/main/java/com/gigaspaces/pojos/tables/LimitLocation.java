package com.gigaspaces.pojos.tables;

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
@Table(name = "tblLimitLocation")
@SpaceClass
public class LimitLocation {

    @Id
    @Column(name = "LimitLocationID")
    private Integer limitLocationId;
    @Column(name = "LimitID")
    private Integer limitId;
    @Column(name = "LocationID")
    private Integer locationId;

    public LimitLocation() {}

    @SpaceId
    public Integer getLimitLocationId() {
        return limitLocationId;
    }

    public void setLimitLocationId(Integer limitLocationId) {
        this.limitLocationId = limitLocationId;
    }

    public Integer getLimitId() {
        return limitId;
    }

    public void setLimitId(Integer limitId) {
        this.limitId = limitId;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }
}

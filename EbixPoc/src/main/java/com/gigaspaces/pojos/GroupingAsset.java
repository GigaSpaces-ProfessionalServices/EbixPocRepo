package com.gigaspaces.pojos;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceId;

import java.util.Objects;

/**
 * @author Denys_Novikov
 * Date: 12/24/18
 */
@SpaceClass
public class GroupingAsset {

    private static int currentId = 0;
    private int id;
    private String country;
    private String state;
    private String city;

    public GroupingAsset() {}

    public GroupingAsset(String country, String state) {
        id = ++currentId;
        this.country = country;
        this.state = state;
    }

    @SpaceId(autoGenerate = true)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupingAsset that = (GroupingAsset) o;
        return Objects.equals(country, that.country) &&
                Objects.equals(state, that.state) &&
                Objects.equals(city, that.city);
    }

    @Override
    public int hashCode() {

        return Objects.hash(country, state, city);
    }

    @Override
    public String toString() {
        return "GroupingAsset{" +
                "id=" + id +
                ", country='" + country + '\'' +
                ", state='" + state + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}

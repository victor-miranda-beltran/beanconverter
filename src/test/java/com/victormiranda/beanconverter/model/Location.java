package com.victormiranda.beanconverter.model;

import com.victormiranda.beanconverter.annotation.Convertible;

/**
 * Created by victor on 18/09/15.
 */
@Convertible(to = LocationDTO.class)
public class Location {

    private Integer id;

    private String name;

    private String searchName;

    public Location() {
    }

    public Location(Integer id, String name, String searchName) {
        this.id = id;
        this.name = name;
        this.searchName = searchName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }
}

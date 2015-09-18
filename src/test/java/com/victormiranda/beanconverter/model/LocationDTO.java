package com.victormiranda.beanconverter.model;

/**
 * Created by victor on 18/09/15.
 */
public class LocationDTO {

    private Integer id;

    private String name;

    private String searchName;

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
package com.victormiranda.beanconverter.model;

import com.victormiranda.beanconverter.annotation.Convertible;

/**
 * Created by victor on 11/09/2015.
 */
@Convertible(to = CountryDTO.class)
public class CountryModel {
    private Integer id;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
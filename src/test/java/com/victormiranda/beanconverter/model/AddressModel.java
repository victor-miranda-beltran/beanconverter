package com.victormiranda.beanconverter.model;


import com.victormiranda.beanconverter.annotation.Mapping;

/**
 * Created by victor on 10/09/15.
 */
public class AddressModel {
    private Integer id;

    private String name;

    @Mapping(source=AddressDTO.class, field="property")
    private String propertyDifferentName;

    private CountryModel country;

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

    public String getPropertyDifferentName() {
        return propertyDifferentName;
    }

    public void setPropertyDifferentName(String propertyDifferentName) {
        this.propertyDifferentName = propertyDifferentName;
    }

    public CountryModel getCountry() {
        return country;
    }

    public void setCountry(CountryModel country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "AddressModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", propertyDifferentName='" + propertyDifferentName + '\'' +
                ", country=" + country +
                '}';
    }
}

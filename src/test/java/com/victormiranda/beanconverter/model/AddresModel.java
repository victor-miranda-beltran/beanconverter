package com.victormiranda.beanconverter.model;

import com.victormiranda.beanconverter.Mapping;

/**
 * Created by victor on 10/09/15.
 */
public class AddresModel {
    private Integer id;
    private String name;

    @Mapping(source=AddressDTO.class, field="property")
    private String propertyDifferentName;
}

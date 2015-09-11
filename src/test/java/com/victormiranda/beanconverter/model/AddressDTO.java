package com.victormiranda.beanconverter.model;

import com.victormiranda.beanconverter.Mapping;

/**
 * Created by victor on 10/09/15.
 */
public class AddressDTO {
    private Integer id;
    private String name;

    @Mapping(source=AddresModel.class, field="propertyDifferentName")
    private String property;
}

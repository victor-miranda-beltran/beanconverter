package com.victormiranda.beanconverter.model;

import com.victormiranda.beanconverter.Mapping;

import java.lang.reflect.Field;

/**
 * Created by victor on 10/09/15.
 */
public class Match {
    private final Field sourceField;
    private final Field destinationField;
    private Mapping mapping;

    public Match(final Field sourceField, final Field destinationField) {
        this.sourceField = sourceField;
        this.destinationField = destinationField;
    }

    public Match(final Field sourceField, final Field destinationField, final Mapping annotationMapping) {
        this.sourceField = sourceField;
        this.destinationField = destinationField;
        this.mapping = annotationMapping;
    }

    public Field getSourceField() {
        return sourceField;
    }

    public Field getDestinationField() {
        return destinationField;
    }

    public Mapping getMapping() {
        return mapping;
    }

    @Override
    public String toString() {
        return "Match{" +
                "sourceField=" + sourceField +
                ", destinationField=" + destinationField +
                ", mapping=" + mapping +
                '}';
    }
}

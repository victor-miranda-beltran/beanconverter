package com.victormiranda.beanconverter.model;

import java.lang.reflect.Field;

/**
 * Created by victor on 10/09/15.
 */
public class Match {
    private final Field sourceField;
    private final Field destinationField;

    public Match(final Field sourceField, final Field destinationField) {
        this.sourceField = sourceField;
        this.destinationField = destinationField;
    }

    public Field getSourceField() {
        return sourceField;
    }

    public Field getDestinationField() {
        return destinationField;
    }
}

package com.victormiranda.beanconverter.model;

import com.victormiranda.beanconverter.annotation.Mapping;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 *
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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Match match = (Match) o;
        return Objects.equals(sourceField, match.sourceField) &&
                Objects.equals(destinationField, match.destinationField) &&
                Objects.equals(mapping, match.mapping);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceField, destinationField, mapping);
    }
}

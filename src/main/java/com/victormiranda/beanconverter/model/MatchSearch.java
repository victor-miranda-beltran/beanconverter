package com.victormiranda.beanconverter.model;

import java.util.Objects;

/**
 *
 */
public class MatchSearch {
    private final Class sourceClass;
    private final Class destinationClass;

    public MatchSearch(Class sourceClass, Class destinationClass) {
        this.sourceClass = sourceClass;
        this.destinationClass = destinationClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MatchSearch that = (MatchSearch) o;
        return Objects.equals(sourceClass, that.sourceClass) &&
                Objects.equals(destinationClass, that.destinationClass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceClass, destinationClass);
    }

    @Override
    public String toString() {
        return "MatchSearch{" +
                "sourceClass=" + sourceClass +
                ", destinationClass=" + destinationClass +
                '}';
    }
}

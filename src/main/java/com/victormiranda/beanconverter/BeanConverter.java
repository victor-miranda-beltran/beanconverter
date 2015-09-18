package com.victormiranda.beanconverter;

import com.victormiranda.beanconverter.exception.ConversionError;

import java.util.Collection;
import java.util.List;

public interface BeanConverter {
    /**
     * Convert from a object to another
     * @param objectSource
     * @param classDestination
     * @param <T>
     * @return
     * @throws ConversionError
     */
    <T> T convert(Object objectSource, Class<T> classDestination)
            throws ConversionError;

    /**
     * Convert from a collection of objects to a list of another type
     * @param objectCollection
     * @param classDestination
     * @param <T>
     * @return
     * @throws ConversionError
     */
    <T> List<T> convert(Collection<Object> objectCollection, Class<T> classDestination) throws ConversionError;
}

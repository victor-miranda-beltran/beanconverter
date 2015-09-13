package com.victormiranda.beanconverter;

import com.victormiranda.beanconverter.exception.ConversionError;

@FunctionalInterface
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
}

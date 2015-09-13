package com.victormiranda.beanconverter.exception;

/**
 *
 */
public class ConversionError extends Exception {

    public ConversionError(ReflectiveOperationException e) {
        super(e);
    }
}

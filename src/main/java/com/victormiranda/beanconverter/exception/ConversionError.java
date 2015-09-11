package com.victormiranda.beanconverter.exception;

/**
 * Created by victor on 10/09/15.
 */
public class ConversionError extends Exception {

    public ConversionError(ReflectiveOperationException e) {
        super(e);
    }
}

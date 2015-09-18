package com.victormiranda.beanconverter.reflection;

import com.victormiranda.beanconverter.annotation.Convertible;
import com.victormiranda.beanconverter.exception.ConversionError;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.OperationNotSupportedException;
import java.lang.reflect.Field;
import java.util.Optional;

/**
 *
 */
public class ReflectionUtil {

    private static final Logger LOGGER = LogManager.getLogger(ReflectionUtil.class);

    private ReflectionUtil() throws OperationNotSupportedException {
        throw new OperationNotSupportedException();
    }

    public static Optional<Field> getMatchingField(final Field destinationField, final Class sourceClass) {
        try {
            final Field sourceField = sourceClass.getDeclaredField(destinationField.getName());
            if (isAssignable(destinationField, sourceField)) {
                return Optional.of(sourceField);
            }
        } catch (NoSuchFieldException e) {
            LOGGER.trace(e);
        }

        return Optional.empty();
    }

    public static Object getValue(final Object source, final Field sourceField) throws ConversionError {
        final boolean accessible = sourceField.isAccessible();

        if (!accessible) {
            sourceField.setAccessible(true);
        }

        try {
            return sourceField.get(source);
        } catch (IllegalAccessException e) {
            LOGGER.error(e);
            throw new ConversionError(e);
        } finally {
            if (!accessible) {
                sourceField.setAccessible(false);
            }
        }
    }

    public static void setValue(final Object object, final Field field, final Object value) throws ConversionError {
        final boolean accessible = field.isAccessible();

        if (!accessible) {
            field.setAccessible(true);
        }

        try {
             field.set(object, value);
        } catch (IllegalAccessException e) {
            LOGGER.error(e);
            throw new ConversionError(e);
        } finally {
            if (!accessible) {
                field.setAccessible(false);
            }
        }
    }

    public static Field getPairingField(Class source, Field destinationField) {
        Field pairingField = null;

        Field candidate =  getField(source, destinationField.getName());

        if (candidate != null && isAssignable(destinationField, candidate)) {
            pairingField = candidate;
        }

        return pairingField;
    }

    private static boolean isAssignable(Field destinationField, Field sourceField) {
        final Class destinationType = destinationField.getType();
        final Class sourceType = sourceField.getType();

        if (destinationType.equals(sourceType) || destinationType.isAssignableFrom(sourceType)) {
            return true;
        }

        final Convertible convertible = sourceField.getType().getDeclaredAnnotation(Convertible.class);

        if (convertible != null) {
            return convertible.to().isAssignableFrom(destinationType);
        }

        if (destinationType.isPrimitive()) {
            PrimitiveType primitiveType =
                    PrimitiveType.getByTypeName(destinationType.getName());

            return primitiveType.allow(sourceType);
        }

        if (sourceType.isPrimitive()) {
            PrimitiveType primitiveType =
                    PrimitiveType.getByTypeName(sourceType.getName());
            return primitiveType.wrapperClass.isAssignableFrom(destinationType);
        }

        LOGGER.debug("Rejecting from " + destinationType + " to " + sourceType);

        return false;
    }

    public static Field getField(Class source, String fieldName) {
        Field field = null;

        try {
            field = source.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            LOGGER.debug(e);
        }

        return field;
    }
}

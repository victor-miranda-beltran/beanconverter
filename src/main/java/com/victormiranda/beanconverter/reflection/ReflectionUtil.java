package com.victormiranda.beanconverter.reflection;

import com.victormiranda.beanconverter.exception.ConversionError;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.OperationNotSupportedException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Created by victor on 10/09/15.
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

    private static boolean isAssignable(Field destinationField, Field sourceField) {
        final Class destinationType = destinationField.getType();
        final Class sourceType = sourceField.getType();

        if (destinationType.equals(sourceType) || destinationType.isAssignableFrom(sourceType)) {
            return true;
        }

        if (destinationType.isPrimitive()) {
            PrimitiveType primitiveType =
                    PrimitiveType.getByTypeName(destinationType.getName());

            return primitiveType.allow(sourceType);
        }

        LOGGER.debug("Rejecting from " + destinationType + " to " + sourceType);

        return false;
    }

    public static Field getPairingField(Class source, String destinationField) {
        Field pairingField = null;

        try {
            return source.getDeclaredField(destinationField);
        } catch (NoSuchFieldException e) {
            LOGGER.debug(e);
        }

        return pairingField;
    }
}

enum PrimitiveType {
    BOOLEAN("bool", Boolean.class, Collections.EMPTY_LIST),
    BYTE("byte",Byte.class, Collections.EMPTY_LIST),
    SHORT("short",Short.class, Arrays.asList(BYTE)),
    INT("int",Integer.class, Arrays.asList(SHORT, BYTE)),
    LONG("long",Long.class, Arrays.asList(INT, SHORT, BYTE)),
    FLOAT("float",Float.class, Arrays.asList(LONG, INT, SHORT, BYTE)),
    DOUBLE("double",Double.class, Arrays.asList(FLOAT, LONG, INT, SHORT, BYTE));

    final Class wrapperClass;
    final String typeName;
    final transient List<PrimitiveType> allowedConversions;

    PrimitiveType(String typeName, Class wrapperClass, List<PrimitiveType> allowedConversions) {
        this.typeName = typeName;
        this.wrapperClass = wrapperClass;
        this.allowedConversions = allowedConversions;
    }

    public static PrimitiveType getByTypeName(final String typeName) {
        for (PrimitiveType primitiveType : PrimitiveType.values()) {
            if (primitiveType.typeName.equals(typeName)) {
                return primitiveType;
            }
        }

        throw new IllegalArgumentException();
    }

    public boolean allow(Class destinationType) {
        PrimitiveType destinationPrimitiveType = getByTypeName(destinationType.getName());

        return this.allowedConversions.contains(destinationPrimitiveType);
    }
}

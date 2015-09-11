package com.victormiranda.beanconverter;

import com.victormiranda.beanconverter.annotation.Convertible;
import com.victormiranda.beanconverter.exception.ConversionError;
import com.victormiranda.beanconverter.model.Match;
import com.victormiranda.beanconverter.reflection.ReflectionUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Set;

/**
 * Created by victor on 10/09/15.
 */
public final class BeanConverter {

    private static final Logger LOGGER = LogManager.getLogger(BeanConverter.class);

    private BeanConverter() {
        throw new UnsupportedOperationException();
    }

    public static final <T> T convert(final Object objectSource, final Class<T> classDestination)
            throws ConversionError {
        final T result;

        try {
            result = classDestination.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new ConversionError(e);
        }

        if (objectSource == null) {
            return result;
        }

        final Class classSource = objectSource.getClass();

        final Set<Match> matches = MatchDetector.getMatches(classSource, classDestination);

        matches.stream().forEach(m -> {
            try {
                applyMatch(result, objectSource, m);
            } catch (ConversionError conversionError) {
                LOGGER.debug(conversionError);
            }
        });

        return result;
    }

    private static void applyMatch(final Object object, final Object source, final Match match) throws ConversionError {
        final Object sourceValue;
        final Convertible convertible;

        if ((convertible = match.getSourceField().getType().getDeclaredAnnotation(Convertible.class)) != null) {
            Object convertibleValue = ReflectionUtil.getValue(source, match.getSourceField());
            sourceValue = convert(convertibleValue, convertible.to() );
        } else {
             sourceValue = ReflectionUtil.getValue(source, match.getSourceField());
        }

        if (sourceValue != null) {
            ReflectionUtil.setValue(object, match.getDestinationField(), sourceValue);
        }
    }
}

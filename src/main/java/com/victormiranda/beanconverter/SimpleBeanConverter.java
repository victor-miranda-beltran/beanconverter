package com.victormiranda.beanconverter;

import com.victormiranda.beanconverter.annotation.Convertible;
import com.victormiranda.beanconverter.exception.ConversionError;
import com.victormiranda.beanconverter.model.Match;
import com.victormiranda.beanconverter.reflection.ReflectionUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Set;

public final class SimpleBeanConverter implements BeanConverter {

    private static final Logger LOGGER = LogManager.getLogger(SimpleBeanConverter.class);

    private final MatchDetector matchDetector;

    public SimpleBeanConverter(final MatchDetector matchDetector) {
        this.matchDetector = matchDetector;
    }

    @Override
    public final <T> T convert(final Object objectSource, final Class<T> classDestination)
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

        final Set<Match> matches = matchDetector.getMatches(classSource, classDestination);

        matches.stream().forEach(m -> {
            try {
                applyMatch(result, objectSource, m);
            } catch (ConversionError conversionError) {
                LOGGER.debug(conversionError);
            }
        });

        return result;
    }

    private void applyMatch(final Object object, final Object source, final Match match) throws ConversionError {
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

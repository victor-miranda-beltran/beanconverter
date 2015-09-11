package com.victormiranda.beanconverter;

import com.victormiranda.beanconverter.exception.ConversionError;
import com.victormiranda.beanconverter.model.Match;
import com.victormiranda.beanconverter.reflection.ReflectionUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.OperationNotSupportedException;
import java.util.Set;

/**
 * Created by victor on 10/09/15.
 */
public final class BeanConverter {

    private static final Logger LOGGER = LogManager.getLogger(BeanConverter.class);

    private BeanConverter() throws OperationNotSupportedException {
        throw new OperationNotSupportedException();
    }

    public static final Object convert(final Object objectSource, final Class classDestination)
            throws ConversionError {

        final Object result;

        try {
            result = classDestination.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new ConversionError(e);
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
        final Object sourceValue = ReflectionUtil.getValue(source, match.getSourceField());

        ReflectionUtil.setValue(object, match.getDestinationField(), sourceValue);
    }
}

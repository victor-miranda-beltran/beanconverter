package com.victormiranda.beanconverter;

import com.victormiranda.beanconverter.model.Match;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.OperationNotSupportedException;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by victor on 10/09/15.
 */
public final class MatchDetector {

    private static final Logger LOGGER = LogManager.getLogger(MatchDetector.class);

    private MatchDetector() throws OperationNotSupportedException {
        throw new OperationNotSupportedException();
    }

    public static Set<Match> getMatches(final Class source, final Class destination) {
        final Set<Match> matches = new HashSet<>();

        final Field[] destinationFields = destination.getDeclaredFields();

        for (Field destinationField : destinationFields) {
            try {
                final Field pairingField = source.getDeclaredField(destinationField.getName());

                if (pairingField != null) {
                    matches.add(new Match(pairingField, destinationField));
                }
            } catch (NoSuchFieldException e) {
                LOGGER.trace(e);
            }
        }

        return matches;
    }
}

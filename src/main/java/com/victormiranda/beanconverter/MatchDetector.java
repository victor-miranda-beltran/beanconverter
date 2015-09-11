package com.victormiranda.beanconverter;

import com.victormiranda.beanconverter.model.Match;
import com.victormiranda.beanconverter.reflection.ReflectionUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.OperationNotSupportedException;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Optional;
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
            final Match match = getMatch(source, destinationField);
            if (match != null) {
                matches.add(getMatch(source, destinationField));
            }
        }

        return matches;
    }

    private static Match getMatch(Class source, Field destinationField) {
        Match match = null;

        Field pairingField = ReflectionUtil.getPairingField(source, destinationField.getName());
        Mapping annotationMapping = null;

        if (pairingField == null) {
            annotationMapping = getMapping(source, destinationField);
            pairingField = ReflectionUtil.getPairingField(source, annotationMapping.field());
        }
        if (pairingField != null) {
            match = new Match(pairingField, destinationField, annotationMapping);
        }

        return match;
    }

    private static Mapping getMapping(Class source, Field destinationField) {
        Mapping sourceMapping = null;

        Mapping[] mappings = destinationField.getDeclaredAnnotationsByType(Mapping.class);

        for (Mapping mapping : mappings) {
            if (mapping.source().equals(source)) {
                sourceMapping = mapping;
                break;
            }
        }

        return sourceMapping;
    }
}

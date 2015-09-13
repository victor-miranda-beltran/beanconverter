package com.victormiranda.beanconverter;

import com.victormiranda.beanconverter.annotation.Mapping;
import com.victormiranda.beanconverter.model.Match;
import com.victormiranda.beanconverter.model.MatchSearch;
import com.victormiranda.beanconverter.reflection.ReflectionUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MatchDetector {

    private static final Logger LOGGER = LogManager.getLogger(MatchDetector.class);

    private static Map<MatchSearch, Set<Match>> matchSearchCache = new HashMap<>();

    public Set<Match> getMatches(final Class source, final Class destination) {
        final MatchSearch currentSearch = new MatchSearch(source, destination);

        if (matchSearchCache.containsKey(currentSearch)) {
            LOGGER.debug("Cache hit: " +  currentSearch);

            return matchSearchCache.get(currentSearch);
        }

        final Set<Match> matches = new HashSet<>();

        final Field[] destinationFields = destination.getDeclaredFields();

        for (Field destinationField : destinationFields) {
            final Match match = getMatch(source, destinationField);
            if (match != null) {
                matches.add(match);
            }
        }

        matchSearchCache.put(currentSearch, matches);

        return matches;
    }

    private Match getMatch(Class source, Field destinationField) {
        Match match = null;

        Field pairingField = ReflectionUtil.getPairingField(source, destinationField);
        Mapping annotationMapping = null;

        if (pairingField == null && (annotationMapping = getMapping(source, destinationField)) !=  null) {
            LOGGER.debug("Annotation mapping detected " +  annotationMapping.field());

            Field destinationMappingField = ReflectionUtil.getField(source, annotationMapping.field());
            pairingField = ReflectionUtil.getPairingField(source, destinationMappingField);
        }
        if (pairingField != null) {
            match = new Match(pairingField, destinationField, annotationMapping);
        }

        return match;
    }

    private Mapping getMapping(final Class source, final Field destinationField) {
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

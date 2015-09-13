package com.victormiranda.beanconverter;

import com.victormiranda.beanconverter.exception.ConversionError;
import com.victormiranda.beanconverter.model.AddressDTO;
import com.victormiranda.beanconverter.model.AddressModel;
import com.victormiranda.beanconverter.model.Match;
import com.victormiranda.beanconverter.model.MatchSearch;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

/**
 * Created by victor on 11/09/15.
 */
public class MatchDetectorTest {

    private MatchDetector matchDetector;

    @Before
    public void setUp() {
        matchDetector = new MatchDetector();
    }

    @Test
    public void testConvertWithBasicMapping() throws ConversionError {
        final Set<Match> matches = matchDetector.getMatches(AddressModel.class, AddressDTO.class);

        matches.forEach(m -> System.out.println(m));

        Assert.assertEquals(matches.size(), 5);
    }

    @Test
    public void testCache() throws NoSuchFieldException, IllegalAccessException {
        matchDetector.getMatches(AddressModel.class, AddressDTO.class);

        Field cacheField = MatchDetector.class.getDeclaredField("matchSearchCache");
        cacheField.setAccessible(true);
        Map<MatchSearch, Set<Match>> cache = (Map<MatchSearch, Set<Match>>) cacheField.get(MatchDetector.class);

        Assert.assertSame(cache.size(), 1);
    }
}
package com.victormiranda.beanconverter;

import com.victormiranda.beanconverter.exception.ConversionError;
import com.victormiranda.beanconverter.model.AddresModel;
import com.victormiranda.beanconverter.model.AddressDTO;
import com.victormiranda.beanconverter.model.Match;
import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

/**
 * Created by victor on 11/09/15.
 */
public class MatchDetectorTest {

    @Test
    public void testConvertWithBasicMapping() throws ConversionError {
        final Set<Match> matches = MatchDetector.getMatches(AddresModel.class, AddressDTO.class);

        matches.forEach(m -> System.out.println(m));

        Assert.assertEquals(matches.size(), 3);
    }
}
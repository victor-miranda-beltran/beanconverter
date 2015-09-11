package com.victormiranda.beanconverter.reflection;

import com.victormiranda.beanconverter.exception.ConversionError;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Optional;

/**
 * Created by victor on 10/09/15.
 */
public class ReflectionUtilTest {

    @Test
    public void testSimpleMatching() throws NoSuchFieldException {
        Destination destination = new Destination();
        Field destinationField = destination.getClass().getDeclaredField("val");

        Optional<Field> res = ReflectionUtil.getMatchingField(destinationField, Source.class);

        Assert.assertTrue(res.isPresent());
    }

    @Test
    public void testPrimitiveMatching() throws NoSuchFieldException {
        Destination destination = new Destination();
        Field destinationField = destination.getClass().getDeclaredField("primitiveInt");

        Optional<Field> res = ReflectionUtil.getMatchingField(destinationField, Source.class);

        Assert.assertTrue(res.isPresent());
    }

    @Test
    public void testPrimitiveCastingMatching() throws NoSuchFieldException {
        Destination destination = new Destination();
        Field destinationField = destination.getClass().getDeclaredField("primitiveCasting");

        Optional<Field> res = ReflectionUtil.getMatchingField(destinationField, Source.class);

        Assert.assertTrue(res.isPresent());
    }

    @Test
    public void testIsAType() throws NoSuchFieldException {
        Destination destination = new Destination();
        Field destinationField = destination.getClass().getDeclaredField("inheritance");

        Optional<Field> res = ReflectionUtil.getMatchingField(destinationField, Source.class);

        Assert.assertTrue(res.isPresent());
    }

    @Test
    public void testNotMatchingDifferentTypes() throws NoSuchFieldException {
        Destination destination = new Destination();
        Field destinationField = destination.getClass().getDeclaredField("differentType");

        Optional<Field> res = ReflectionUtil.getMatchingField(destinationField, Source.class);

        Assert.assertFalse(res.isPresent());
    }

    @Test
    public void testGetValue() throws NoSuchFieldException, ConversionError {
        Destination destination = new Destination();
        destination.val = 4;

        Field destinationField = destination.getClass().getDeclaredField("val");

        Object value = ReflectionUtil.getValue(destination, destinationField);

        Assert.assertEquals(destination.val, value);
    }

    @Test
    public void testGetValueFromPrivateField() throws NoSuchFieldException, ConversionError {
        Destination destination = new Destination();
        destination.setPrivateField(4);

        Field destinationField = destination.getClass().getDeclaredField("privateField");

        Object value = ReflectionUtil.getValue(destination, destinationField);

        Assert.assertEquals(destination.getPrivateField(), value);
    }

    @Test
    public void testSetValue() throws NoSuchFieldException, ConversionError {
        Destination destination = new Destination();

        Field destinationField = destination.getClass().getDeclaredField("val");

        ReflectionUtil.setValue(destination, destinationField, 5);

        Assert.assertEquals(destination.val, new Integer(5));
    }
}

class Source {
    private Integer privateField;
    int primitiveInt;
    int primitiveCasting;
    Integer val;
    String name;
    String inheritance;
    Integer differentType;
}

class Destination {
    private Integer privateField;
    int primitiveInt;
    long primitiveCasting;
    Integer val;
    String name;
    Object inheritance;
    String differentType;

    public void setPrivateField(int privateField) {
        this.privateField = privateField;
    }

    public Integer getPrivateField() {
        return privateField;
    }
}
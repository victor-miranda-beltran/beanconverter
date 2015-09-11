package com.victormiranda.beanconverter.reflection;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by victor on 11/09/15.
 */
public class PrimitiveTypeTest {

    @Test
    public void testByName() {
        assertEquals(PrimitiveType.getByTypeName("int"), PrimitiveType.INT);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testByInexistentName() {
        assertEquals(PrimitiveType.getByTypeName("inexsistent"), PrimitiveType.INT);
    }

}
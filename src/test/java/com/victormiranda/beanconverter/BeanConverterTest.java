package com.victormiranda.beanconverter;

import com.victormiranda.beanconverter.exception.ConversionError;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by victor on 10/09/15.
 */
public class BeanConverterTest {

    @Test
    public void testConvert() throws Exception {
        final SourceBean sourceBean = new SourceBean();

        final Object destinationBean = BeanConverter.convert(sourceBean, DestinationBean.class);

        Assert.assertTrue(destinationBean instanceof DestinationBean);
    }

    @Test(expected = ConversionError.class)
    public void testConvertToClassWithoutPublicConstructor() throws ConversionError {
        BeanConverter.convert(new SourceBean(), DestinationBeanNonPublicConstructor.class);
    }

    @Test
    public void testResultConversion() throws ConversionError {
        final SourceBean sourceBean = new SourceBean();

        sourceBean.setId(3);
        sourceBean.setName("Paco");

        final DestinationBean destinationBean = (DestinationBean) BeanConverter.convert(sourceBean, DestinationBean.class);

        Assert.assertEquals(sourceBean.getId(), destinationBean.getId());
        Assert.assertEquals(sourceBean.getName(), destinationBean.getName());
    }
}

class SourceBean {
    private int id;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class DestinationBean {
    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class DestinationBeanNonPublicConstructor {
    private DestinationBeanNonPublicConstructor() {
    }
}
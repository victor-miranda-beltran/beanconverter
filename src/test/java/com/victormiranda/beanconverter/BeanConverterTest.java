package com.victormiranda.beanconverter;

import com.victormiranda.beanconverter.exception.ConversionError;
import com.victormiranda.beanconverter.model.AddressDTO;
import com.victormiranda.beanconverter.model.AddressModel;
import com.victormiranda.beanconverter.model.CountryModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by victor on 10/09/15.
 */
public class BeanConverterTest {

    private BeanConverter beanConverter;

    @Before
    public void setUp() {
        beanConverter = new SimpleBeanConverter(new MatchDetector());
    }

    @Test
    public void testConvert() throws Exception {
        final SourceBean sourceBean = new SourceBean();

        final DestinationBean destinationBean = beanConverter.convert(sourceBean, DestinationBean.class);

        Assert.assertTrue(destinationBean instanceof DestinationBean);
    }

    @Test(expected = ConversionError.class)
    public void testConvertToClassWithoutPublicConstructor() throws ConversionError {
        beanConverter.convert(new SourceBean(), DestinationBeanNonPublicConstructor.class);
    }

    @Test
    public void testResultConversion() throws ConversionError {
        final SourceBean sourceBean = new SourceBean();

        sourceBean.setId(3);
        sourceBean.setName("Paco");

        final DestinationBean destinationBean = beanConverter.convert(sourceBean, DestinationBean.class);

        Assert.assertEquals(sourceBean.getId(), destinationBean.getId());
        Assert.assertEquals(sourceBean.getName(), destinationBean.getName());
    }

    @Test
    public void testArrayConversion() throws ConversionError {
        final SourceBean sourceBean = new SourceBean();

        Integer[] array = {4,3,2};
        sourceBean.setArrayIntegers(array);

        final DestinationBean destinationBean = beanConverter.convert(sourceBean, DestinationBean.class);

        Assert.assertArrayEquals(sourceBean.getArrayIntegers(), destinationBean.getArrayIntegers());
    }

    @Test
    public void testConvertWithSimpleMapping() throws ConversionError {
        final AddressModel addressModel = new AddressModel();

        addressModel.setId(1);
        addressModel.setName("Name");
        addressModel.setPropertyDifferentName("rebelProperty");

        CountryModel countryModel = new CountryModel();

        countryModel.setId(1);
        countryModel.setName("Ireland");

        addressModel.setCountry(countryModel);

        final AddressDTO addressDTO = beanConverter.convert(addressModel, AddressDTO.class);

        Assert.assertEquals(addressDTO.getProperty(), addressModel.getPropertyDifferentName());
    }

}

class SourceBean {
    private int id;
    private String name;
    private Integer[] arrayIntegers;

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

    public Integer[] getArrayIntegers() {
        return arrayIntegers;
    }

    public void setArrayIntegers(Integer[] arrayIntegers) {
        this.arrayIntegers = arrayIntegers;
    }
}

class DestinationBean {
    private Integer id;
    private String name;
    private Integer[] arrayIntegers;

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

    public Integer[] getArrayIntegers() {
        return arrayIntegers;
    }

    public void setArrayIntegers(Integer[] arrayIntegers) {
        this.arrayIntegers = arrayIntegers;
    }
}

class DestinationBeanNonPublicConstructor {
    private DestinationBeanNonPublicConstructor() {
    }
}
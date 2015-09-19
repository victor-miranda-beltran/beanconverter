package com.victormiranda.beanconverter;

import com.victormiranda.beanconverter.exception.ConversionError;
import com.victormiranda.beanconverter.model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

    @Test
    public void testConvertCollection() throws ConversionError {
        final List<Location> locationList = new ArrayList<>();

        locationList.add(new Location(1, "dublin", "dublin"));
        locationList.add(new Location(2, "cork", "cork"));
        locationList.add(new Location(3, "galway", "galway"));

        final List<LocationDTO> convertedLocationDTOList = beanConverter.convertCollection(locationList, LocationDTO.class);

        Assert.assertEquals(locationList.size(), convertedLocationDTOList.size());

        //TODO change to a  loop
        Assert.assertEquals(locationList.get(0).getId(), convertedLocationDTOList.get(0).getId());
        Assert.assertEquals(locationList.get(0).getName(), convertedLocationDTOList.get(0).getName());

        Assert.assertEquals(locationList.get(1).getId(), convertedLocationDTOList.get(1).getId());
        Assert.assertEquals(locationList.get(1).getName(), convertedLocationDTOList.get(1).getName());

        Assert.assertEquals(locationList.get(2).getId(), convertedLocationDTOList.get(2).getId());
        Assert.assertEquals(locationList.get(2).getName(), convertedLocationDTOList.get(2).getName());
    }

    @Test
    public void testConvertComplexMapping() throws ConversionError {
        final CategorySearch categorySearch = new CategorySearch();

        categorySearch.setId(1);

        Location location = new Location();
        location.setId(3);
        location.setName("Dublin");
        location.setSearchName("dublin");

        categorySearch.setLocation(location);

        CategorySearchDTO categorySearchDTO = beanConverter.convert(categorySearch, CategorySearchDTO.class);

        Assert.assertEquals(categorySearch.getLocation().getName(), categorySearchDTO.getLocation().getName());
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
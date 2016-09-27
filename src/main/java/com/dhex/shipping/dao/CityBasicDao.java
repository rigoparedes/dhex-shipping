package com.dhex.shipping.dao;

import com.dhex.shipping.exceptions.DuplicatedEntityException;
import com.dhex.shipping.model.City;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class CityBasicDao implements CityDao {
    private static final String DUPLICATED_NAME_ERROR_MESSAGE = "City name %s already exists";
    private Set<City> cities;
    private long sequentialId;

    public CityBasicDao() {
        cities = new HashSet<>();
        sequentialId = 0;
    }

    @Override
    public City insert(City city) {
        city.setId(++sequentialId);
        boolean wasAdded = cities.add(city);
        if (!wasAdded) {
            String errorMessage = String.format(DUPLICATED_NAME_ERROR_MESSAGE, city.getName());
            throw new DuplicatedEntityException(errorMessage);
        }
        return city;
    }
}

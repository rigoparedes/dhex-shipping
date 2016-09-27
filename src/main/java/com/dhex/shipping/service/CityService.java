package com.dhex.shipping.service;

import com.dhex.shipping.dao.CityDao;
import com.dhex.shipping.model.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CityService {
    private CityDao cityDao;

    @Autowired
    public CityService(CityDao cityDao) {
        this.cityDao = cityDao;
    }

    public City create(City city) {
        return cityDao.insert(city);
    }

}

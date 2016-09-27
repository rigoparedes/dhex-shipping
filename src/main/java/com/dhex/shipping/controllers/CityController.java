package com.dhex.shipping.controllers;

import com.dhex.shipping.exceptions.DuplicatedEntityException;
import com.dhex.shipping.model.City;
import com.dhex.shipping.service.CityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;
import java.net.URISyntaxException;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

public class CityController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CityController.class);
    private CityService cityService;

    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @RequestMapping(method = POST)
    public ResponseEntity<City> create(@RequestBody City city) throws URISyntaxException {
        City newCity = cityService.create(city);
        return ResponseEntity
                .created(new URI("/city/" + city.getCountryId() + "/" + city.getId()))
                .body(newCity);
    }

    @ExceptionHandler(value = DuplicatedEntityException.class)
    public ResponseEntity handle(DuplicatedEntityException ex) {
        LOGGER.info("City already existed.", ex);
        return ResponseEntity.badRequest().body("City is duplicated");
    }

}

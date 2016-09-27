package com.dhex.shipping.controllers;

import com.dhex.shipping.model.City;
import com.dhex.shipping.service.CityService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URISyntaxException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.CREATED;

@RunWith(MockitoJUnitRunner.class)
public class CityControllerTest {

    @Mock
    private CityService cityService;
    private CityController controller;

    @Before
    public void setUp() throws Exception {
        controller = new CityController(cityService);
    }

    @Test
    public void shouldReturn201IfCityWasCreated() throws URISyntaxException {
        // Arrange
        City city = new City(1, "Lima");

        // Act
        HttpStatus responseStatusCode = controller.create(city).getStatusCode();

        // Assert
        assertThat(responseStatusCode, is(CREATED));
    }

    @Test
    public void shouldReturnTheCity() throws URISyntaxException {
        // Arrange
        City city = new City(1, "Lima");
        City newCity = new City(1, 1, "Lima");
        when(cityService.create(city))
                .thenReturn(newCity);

        // Act
        ResponseEntity<City> response = controller.create(city);

        // Assert
        assertThat(response.getBody(), is(newCity));
    }

}

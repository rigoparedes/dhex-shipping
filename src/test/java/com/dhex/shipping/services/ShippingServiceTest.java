package com.dhex.shipping.services;

import com.dhex.shipping.exceptions.InvalidArgumentDhexException;
import com.dhex.shipping.exceptions.ShippingNotFoundException;
import com.dhex.shipping.model.ShippingRequest;
import com.dhex.shipping.model.ShippingStatus;
import com.dhex.shipping.service.SendingRequestParameterList;
import com.dhex.shipping.service.ShippingService;
import org.junit.Test;

import java.time.OffsetDateTime;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

public class ShippingServiceTest {

    @Test
    public void shouldReturnAnIdAndDateWhenRegistering() {
        // Arrange
        long sendingCost = 0;
        SendingRequestParameterList parameterList =
                new SendingRequestParameterList("Juan Perez", "Jorge Quispe", "Calle Los Negocios 444", sendingCost, null);

        // Act
        ShippingRequest shippingRequest = new ShippingService()
                .registerRequest(parameterList);

        // Asserts
        assertsForGeneratedId(shippingRequest.getId(), "J");
        assertThat(shippingRequest.getSendingCost(), is((sendingCost + 3) * 1.18));
        assertThat(shippingRequest.getRegistrationMoment(), is(notNullValue()));
    }

    private void assertsForGeneratedId(String generatedId, String firstLetter) {
        // Asserts
        assertThat(generatedId.length(), is(23));
        assertThat(generatedId.substring(0, 1), is(firstLetter));

        OffsetDateTime dateTime = OffsetDateTime.now();
        assertThat(generatedId.substring(1, 5), is(String.valueOf(dateTime.getYear())));
        assertThat(generatedId.substring(5, 7), is(String.format("%02d", dateTime.getMonthValue())));
        assertThat(generatedId.substring(7, 23), is("0000000000000001"));
    }

    @Test(expected = InvalidArgumentDhexException.class)
    public void shouldThrowExceptionWhenCostIsNegative() {
        SendingRequestParameterList parameterList =
                new SendingRequestParameterList("Juan Perez", "Cristhian Gonzales", "Calle Los Negocios 444", -1, null);

        new ShippingService().registerRequest(parameterList);
    }

    @Test(expected = InvalidArgumentDhexException.class)
    public void shouldThrowExceptionWhenReceiverIsNull() {
        SendingRequestParameterList parameterList =
                new SendingRequestParameterList(null, "Jorge Quispe", "Calle Los Negocios 444", 10, null);

        new ShippingService().registerRequest(parameterList);
    }

    @Test(expected = InvalidArgumentDhexException.class)
    public void shouldThrowExceptionWhenReceiverIsEmpty() {
        SendingRequestParameterList parameterList =
                new SendingRequestParameterList("", "Jorge Quispe", "Calle Los Negocios 444", 101, null);

        new ShippingService().registerRequest(parameterList);
    }

    @Test(expected = InvalidArgumentDhexException.class)
    public void shouldThrowExceptionWhenSenderIsNull() {
        SendingRequestParameterList parameterList =
                new SendingRequestParameterList("Receiver", null, "Calle Los Negocios 444", 100, null);

        new ShippingService().registerRequest(parameterList);
    }

    @Test(expected = InvalidArgumentDhexException.class)
    public void shouldThrowExceptionWhenSenderIsEmpty() {
        SendingRequestParameterList parameterList =
                new SendingRequestParameterList("Receiver", "", "Calle Los Negocios 444", 100, null);

        new ShippingService().registerRequest(parameterList);
    }

    @Test(expected = InvalidArgumentDhexException.class)
    public void shouldThrowExceptionWhenDestinationAddressIsNull() {
        SendingRequestParameterList parameterList =
                new SendingRequestParameterList("Receiver", "Sender", null, 100, null);

        new ShippingService().registerRequest(parameterList);
    }

    @Test(expected = InvalidArgumentDhexException.class)
    public void shouldThrowExceptionWhenDestinationAddressIsEmpty() {
        SendingRequestParameterList parameterList =
                new SendingRequestParameterList("Receiver", "Sender", "", 100, null);

        new ShippingService().registerRequest(parameterList);
    }

    @Test
    public void shouldReturnAnIdWhenRegisteringStatus() {
        // Arrange
        SendingRequestParameterList parameterList =
                new SendingRequestParameterList("Juan Perez", "Jorge Quispe", "Calle Los Negocios 444", 10, null);

        // Act
        ShippingService shippingService = new ShippingService();
        ShippingRequest shippingRequest = shippingService.registerRequest(parameterList);
        ShippingStatus shippingStatus = shippingService.registerStatus(shippingRequest.getId(), null, null, null);

        // Asserts
        assertThat(shippingStatus.getId(), is(notNullValue()));
        assertThat(shippingStatus.getStatus(), is(nullValue()));
    }

    @Test(expected = ShippingNotFoundException.class)
    public void shouldThrowExceptionWhenShippingNotExists() {
        ShippingService shippingService = new ShippingService();
        shippingService.registerStatus(null, null, null, null);
    }

    @Test(expected = ShippingNotFoundException.class)
    public void shouldThrowExceptionWhenTrackStatusAndShippingNotExists() {
        ShippingService shippingService = new ShippingService();
        shippingService.trackStatusOf(null);
    }
}
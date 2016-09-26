package com.dhex.shipping.services;

import com.dhex.shipping.model.ShippingRequest;
import com.dhex.shipping.service.SendingRequestParameterList;
import com.dhex.shipping.service.ShippingService;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.*;

public class ShippingServiceTest {
    @Test
    public void shouldReturnAnIdAndDateWhenRegistering() {
        ShippingRequest shippingRequest = new ShippingService()
                .registerRequest(new SendingRequestParameterList("Juan Perez", "Jorge Quispe", "Calle Los Negocios 444", 133, null));

        assertThat(shippingRequest.getId(), is(notNullValue()));
        assertThat(shippingRequest.getRegistrationMoment(), is(notNullValue()));
    }
}
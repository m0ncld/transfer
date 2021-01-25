package com.challenge.transfer.exchange.rate;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

class ExchangeRatesExternalDtoTest {

    @Test
    void textEquals() {
        EqualsVerifier
                .forClass(ExchangeRatesExternalDto.class)
                .suppress(Warning.NONFINAL_FIELDS)
                .usingGetClass().verify();
    }

}
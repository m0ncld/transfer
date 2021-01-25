package com.challenge.transfer.util;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class CurrencyTest {

    @Test
    void textEquals() {
        EqualsVerifier.forClass(Currency.class).usingGetClass().verify();
    }

}
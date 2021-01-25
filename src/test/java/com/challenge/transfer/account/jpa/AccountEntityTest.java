package com.challenge.transfer.account.jpa;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

class AccountEntityTest {

    @Test
    void textEquals() {
        EqualsVerifier
                .forClass(AccountEntity.class)
                .suppress(Warning.SURROGATE_KEY)
                .usingGetClass().verify();
    }

}
package com.challenge.transfer.account.jpa;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class AccountEntityPKTest {

    @Test
    void textEquals() {
        EqualsVerifier.forClass(AccountEntityPK.class).usingGetClass().verify();
    }

}
package com.challenge.transfer.account;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class AccountIdTest {

    @Test
    void textEquals() {
        EqualsVerifier.forClass(AccountId.class).usingGetClass().verify();
    }

}
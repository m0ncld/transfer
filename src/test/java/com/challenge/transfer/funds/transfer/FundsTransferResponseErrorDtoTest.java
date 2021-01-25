package com.challenge.transfer.funds.transfer;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

class FundsTransferResponseErrorDtoTest {

    @Test
    void textEquals() {
        EqualsVerifier
                .forClass(FundsTransferResponseErrorDto.class)
                .usingGetClass()
                .verify();
    }

}
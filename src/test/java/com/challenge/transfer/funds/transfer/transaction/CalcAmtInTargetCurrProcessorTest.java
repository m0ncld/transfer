package com.challenge.transfer.funds.transfer.transaction;

import com.challenge.transfer.account.AccountId;
import com.challenge.transfer.funds.transfer.FundsTransferRequestDto;
import com.challenge.transfer.util.Currency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CalcAmtInTargetCurrProcessorTest {

    private CalcAmtInTargetCurrProcessor instance;

    private ProcessingContext context;

    @BeforeEach
    void setUp() {
        instance = new CalcAmtInTargetCurrProcessor();

        AccountId source = new AccountId(0, Currency.EUR);
        AccountId target = new AccountId(1, Currency.USD);
        BigDecimal amount = BigDecimal.TEN;
        FundsTransferRequestDto dto = mock(FundsTransferRequestDto.class);
        when(dto.getSource()).thenReturn(source);
        when(dto.getTarget()).thenReturn(target);
        when(dto.getAmount()).thenReturn(amount);
        context = new ProcessingContext("trans-id", dto);
    }

    @Test
    void getName() {
        String result = instance.getName();
        assertNotNull(result);
    }

    @Test
    void process() throws ProcessingException {
        BigDecimal exchangeRate = new BigDecimal("1.234567890");
        context.setExchangeRate(exchangeRate);

        // Invoke tested method
        instance.process(context);

        // Verify result
        assertTrue(context.getAmountInTargetCurrency().isPresent());
        BigDecimal amount = context.getAmountInTargetCurrency().get();
        assertEquals(0, new BigDecimal("12.34").compareTo(amount));
    }

    @Test
    void processNoExchangeRate() throws ProcessingException {
        // Invoke tested method
        assertThrows(ProcessingException.class, () -> instance.process(context));
    }
}
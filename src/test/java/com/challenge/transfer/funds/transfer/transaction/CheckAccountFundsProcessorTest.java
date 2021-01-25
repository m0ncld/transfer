package com.challenge.transfer.funds.transfer.transaction;

import com.challenge.transfer.account.AccountDto;
import com.challenge.transfer.account.AccountId;
import com.challenge.transfer.funds.transfer.FundsTransferRequestDto;
import com.challenge.transfer.util.Currency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CheckAccountFundsProcessorTest {

    private CheckAccountFundsProcessor instance;

    private ProcessingContext context;

    @BeforeEach
    void setUp() {
        instance = new CheckAccountFundsProcessor();

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
    void processingSufficient() {
        AccountDto account = new AccountDto(0, Currency.EUR, new BigDecimal("11"));
        context.setSourceAccount(account);

        assertDoesNotThrow(() ->  instance.process(context));
    }

    @Test
    void processingEqual() {
        AccountDto account = new AccountDto(0, Currency.EUR, new BigDecimal("10"));
        context.setSourceAccount(account);

        assertDoesNotThrow(() -> instance.process(context));
    }

    @Test
    void processingNoSufficient() {
        AccountDto account = new AccountDto(0, Currency.EUR, new BigDecimal("9"));
        context.setSourceAccount(account);

        ProcessingException exception
                = assertThrows(ProcessingException.class, () -> instance.process(context));
        assertEquals(ProcessingException.BALANCE_NOT_SUFF_EC, exception.getErrorCode());
    }

    @Test
    void processingNoAccount() {
        ProcessingException exception
                = assertThrows(ProcessingException.class, () -> instance.process(context));
        assertEquals(ProcessingException.ACCT_NOT_EXIST_EC, exception.getErrorCode());
    }
}
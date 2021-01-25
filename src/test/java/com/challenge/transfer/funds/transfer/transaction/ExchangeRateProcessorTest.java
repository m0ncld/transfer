package com.challenge.transfer.funds.transfer.transaction;

import com.challenge.transfer.account.AccountId;
import com.challenge.transfer.exchange.rate.ExchangeRateService;
import com.challenge.transfer.funds.transfer.FundsTransferRequestDto;
import com.challenge.transfer.util.Currency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ExchangeRateProcessorTest {

    private ExchangeRateProcessor instance;

    private ProcessingContext context;

    @Mock
    private ExchangeRateService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        instance = new ExchangeRateProcessor(service);

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
    void processing() throws ProcessingException {
        // Setup mocks
        when(service.getRateBetween(Currency.EUR, Currency.USD)).thenReturn(Optional.of(BigDecimal.ONE));

        // Invoke tested method
        instance.process(context);

        // Verify method
        assertTrue(context.getExchangeRate().isPresent());
        assertSame(BigDecimal.ONE, context.getExchangeRate().get());
    }

    @Test
    void processingNoExchangeRate() {
        // Setup mocks
        when(service.getRateBetween(Currency.EUR, Currency.USD)).thenReturn(Optional.empty());

        // Invoke tested method
        ProcessingException exception =
                assertThrows(ProcessingException.class, () -> instance.process(context));
        assertEquals(ProcessingException.RATE_NOT_EXIST_EC, exception.getErrorCode());

    }

}
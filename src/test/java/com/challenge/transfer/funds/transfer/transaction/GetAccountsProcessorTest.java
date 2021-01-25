package com.challenge.transfer.funds.transfer.transaction;

import com.challenge.transfer.account.AccountController;
import com.challenge.transfer.account.AccountDto;
import com.challenge.transfer.account.AccountId;
import com.challenge.transfer.funds.transfer.FundsTransferRequestDto;
import com.challenge.transfer.util.Currency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GetAccountsProcessorTest {

    private GetAccountsProcessor instance;

    private ProcessingContext context;

    @Mock
    private AccountController controller;

    private AccountDto sourceAccount;
    private AccountDto targetAccount;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        instance = new GetAccountsProcessor(controller);

        AccountId source = new AccountId(0, Currency.EUR);
        AccountId target = new AccountId(1, Currency.USD);
        BigDecimal amount = BigDecimal.TEN;
        FundsTransferRequestDto dto = mock(FundsTransferRequestDto.class);
        when(dto.getSource()).thenReturn(source);
        when(dto.getTarget()).thenReturn(target);
        when(dto.getAmount()).thenReturn(amount);
        context = new ProcessingContext("trans-id", dto);

        sourceAccount = new AccountDto(source.getOwnerId(), source.getCurrency(), BigDecimal.TEN);
        targetAccount = new AccountDto(target.getOwnerId(), target.getCurrency(), BigDecimal.ONE);
    }

    @Test
    void getName() {
        String result = instance.getName();
        assertNotNull(result);
    }

    @Test
    void processing() throws ProcessingException {
        // Setup mocks
        when(controller.get(any())).thenReturn(Arrays.asList(sourceAccount, targetAccount));

        // Invoke tested method
        instance.process(context);

        // Verify method
        assertTrue(context.getSourceAccount().isPresent());
        assertTrue(context.getTargetAccount().isPresent());
        assertSame(sourceAccount, context.getSourceAccount().get());
        assertSame(targetAccount, context.getTargetAccount().get());
    }

    @Test
    void processingWrongOrder() throws ProcessingException {
        // Setup mocks
        when(controller.get(any())).thenReturn(Arrays.asList(targetAccount, sourceAccount));

        // Invoke tested method
        instance.process(context);

        // Verify method
        assertTrue(context.getSourceAccount().isPresent());
        assertTrue(context.getTargetAccount().isPresent());
        assertSame(sourceAccount, context.getSourceAccount().get());
        assertSame(targetAccount, context.getTargetAccount().get());
    }

    @Test
    void processingEmptyList() {
        // Setup mocks
        when(controller.get(any())).thenReturn(Collections.emptyList());

        // Invoke tested method
        ProcessingException exception = assertThrows(ProcessingException.class, () -> instance.process(context));

        // Verify method
        assertEquals(ProcessingException.ACCT_NOT_EXIST_EC, exception.getErrorCode());
    }

    @Test
    void processingOnlySource() {
        // Setup mocks
        when(controller.get(any())).thenReturn(Collections.singletonList(sourceAccount));

        // Invoke tested method
        ProcessingException exception = assertThrows(ProcessingException.class, () -> instance.process(context));

        // Verify method
        assertEquals(ProcessingException.ACCT_NOT_EXIST_EC, exception.getErrorCode());
    }

    @Test
    void processingOnlyTarget() {
        // Setup mocks
        when(controller.get(any())).thenReturn(Collections.singletonList(targetAccount));

        // Invoke tested method
        ProcessingException exception = assertThrows(ProcessingException.class, () -> instance.process(context));

        // Verify method
        assertEquals(ProcessingException.ACCT_NOT_EXIST_EC, exception.getErrorCode());
    }

    @Test
    void processingTwoSources() {
        // Setup mocks
        when(controller.get(any())).thenReturn(Arrays.asList(sourceAccount, sourceAccount));

        // Invoke tested method
        ProcessingException exception = assertThrows(ProcessingException.class, () -> instance.process(context));

        // Verify method
        assertEquals(ProcessingException.ACCT_NOT_EXIST_EC, exception.getErrorCode());
    }

    @Test
    void processingTwoTargets() {
        // Setup mocks
        when(controller.get(any())).thenReturn(Arrays.asList(targetAccount, targetAccount));

        // Invoke tested method
        ProcessingException exception = assertThrows(ProcessingException.class, () -> instance.process(context));

        // Verify method
        assertEquals(ProcessingException.ACCT_NOT_EXIST_EC, exception.getErrorCode());
    }

}
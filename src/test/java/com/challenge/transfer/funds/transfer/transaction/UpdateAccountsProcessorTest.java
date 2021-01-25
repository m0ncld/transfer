package com.challenge.transfer.funds.transfer.transaction;

import com.challenge.transfer.account.AccountController;
import com.challenge.transfer.account.AccountDto;
import com.challenge.transfer.account.AccountId;
import com.challenge.transfer.funds.transfer.FundsTransferRequestDto;
import com.challenge.transfer.util.Currency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UpdateAccountsProcessorTest {

    private UpdateAccountsProcessor instance;

    private ProcessingContext context;

    @Mock
    private AccountController controller;

    @Captor
    private ArgumentCaptor<Iterable<AccountDto>> accountsCaptor;

    private AccountDto sourceAccount;
    private AccountDto targetAccount;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        instance = new UpdateAccountsProcessor(controller);

        AccountId source = new AccountId(0, Currency.EUR);
        AccountId target = new AccountId(1, Currency.USD);
        BigDecimal amount = new BigDecimal("6");
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
        // Setup context
        context.setSourceAccount(sourceAccount);
        context.setTargetAccount(targetAccount);
        context.setAmountInTargetCurrency(new BigDecimal("12"));

        // Invoke tested method
        instance.process(context);

        // Verify method
        verify(controller, times(1)).createOrEdit(accountsCaptor.capture());
        List<AccountDto> captured = StreamSupport
                .stream(accountsCaptor.getValue().spliterator(), true)
                .collect(Collectors.toList());
        assertEquals(2, captured.size());

        AccountDto sourceResult = captured.get(0);
        assertEquals(sourceAccount.getOwnerId(), sourceResult.getOwnerId());
        assertEquals(sourceAccount.getCurrency(), sourceResult.getCurrency());
        assertEquals(0, new BigDecimal("4").compareTo(sourceResult.getBalance()));

        AccountDto targetResult = captured.get(1);
        assertEquals(targetAccount.getOwnerId(), targetResult.getOwnerId());
        assertEquals(targetAccount.getCurrency(), targetResult.getCurrency());
        assertEquals(0, new BigDecimal("13").compareTo(targetResult.getBalance()));
    }

    @Test
    void processingNoSourceAccount() {
        // Setup context
        context.setSourceAccount(null);
        context.setTargetAccount(targetAccount);
        context.setAmountInTargetCurrency(new BigDecimal("12"));

        // Invoke tested method
        ProcessingException exception = assertThrows(ProcessingException.class, () -> instance.process(context));

        // Verify method
        assertEquals(ProcessingException.ACCT_NOT_EXIST_EC, exception.getErrorCode());
    }

    @Test
    void processingNoTargetAccount() {
        // Setup context
        context.setSourceAccount(sourceAccount);
        context.setTargetAccount(null);
        context.setAmountInTargetCurrency(new BigDecimal("12"));

        // Invoke tested method
        ProcessingException exception = assertThrows(ProcessingException.class, () -> instance.process(context));

        // Verify method
        assertEquals(ProcessingException.ACCT_NOT_EXIST_EC, exception.getErrorCode());
    }

    @Test
    void processingNoExchangeRate() {
        // Setup context
        context.setSourceAccount(sourceAccount);
        context.setTargetAccount(targetAccount);
        context.setAmountInTargetCurrency(null);

        // Invoke tested method
        ProcessingException exception = assertThrows(ProcessingException.class, () -> instance.process(context));

        // Verify method
        assertEquals(ProcessingException.RATE_NOT_EXIST_EC
                , exception.getErrorCode());
    }

    @Test
    void processingTheSameAccount() {
        // Setup context
        AccountId source = new AccountId(0, Currency.EUR);
        FundsTransferRequestDto dto = mock(FundsTransferRequestDto.class);
        BigDecimal amount = new BigDecimal("6");
        when(dto.getSource()).thenReturn(source);
        when(dto.getTarget()).thenReturn(source);
        when(dto.getAmount()).thenReturn(amount);
        context = new ProcessingContext("trans-id", dto);

        // Invoke tested method
        assertDoesNotThrow(() -> instance.process(context));

        // Verify method
        verify(controller, never()).createOrEdit(ArgumentMatchers.<Iterable<AccountDto>>any());
    }

}
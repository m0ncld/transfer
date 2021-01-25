package com.challenge.transfer.account;

import com.challenge.transfer.util.Currency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountApiTest {

    private AccountController controller;
    private AccountApi api;

    @BeforeEach
    void setUp() {
        controller = mock(AccountController.class);
        api = new AccountApi(controller);
    }

    @Test
    void getAll() {
        // Prepare controller method
        Iterable<AccountDto> accounts = new ArrayList<>(0);
        when(controller.getAll()).thenReturn(accounts);

        // Invoke testing method
        Iterable<AccountDto> result = api.getAll();

        // Verify result
        verify(controller, only()).getAll();
        assertSame(accounts, result);
    }

    @Test
    void create() {
        // Prepare return value
        AccountDto parameter = new AccountDto(0, Currency.USD, BigDecimal.TEN);

        // Prepare controller method
        AccountDto returnedValue = new AccountDto(0, Currency.USD, BigDecimal.TEN);
        when(controller.create(parameter)).thenReturn(returnedValue);

        // Invoke testing method
        AccountDto result = api.create(parameter);

        // Verify result
        assertSame(returnedValue, result);
        assertNotSame(parameter, result);

        ArgumentCaptor<AccountDto> argumentCaptor = ArgumentCaptor.forClass(AccountDto.class);
        verify(controller, times(1)).create(argumentCaptor.capture());
        assertSame(parameter, argumentCaptor.getValue());
    }

    @Test
    void edit() {
        // Prepare return value
        AccountDto parameter = new AccountDto(0, Currency.USD, BigDecimal.TEN);

        // Prepare controller method
        AccountDto returnedValue = new AccountDto(0, Currency.USD, BigDecimal.TEN);
        when(controller.createOrEdit(parameter)).thenReturn(returnedValue);

        // Invoke testing method
        AccountDto result = api.edit(parameter);

        // Verify result
        assertSame(returnedValue, result);
        assertNotSame(parameter, result);

        ArgumentCaptor<AccountDto> argumentCaptor = ArgumentCaptor.forClass(AccountDto.class);
        verify(controller, times(1)).createOrEdit(argumentCaptor.capture());
        assertSame(parameter, argumentCaptor.getValue());
    }

    @Test
    void delete() {
        // Prepare return value
        Integer requestOwnerId = 0;
        Currency requestCurrency = Currency.USD;

        // Invoke testing method
        api.delete(requestOwnerId, requestCurrency);

        // Verify result
        ArgumentCaptor<Integer> argumentOwnerIdCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Currency> argumentCurrencyCaptor = ArgumentCaptor.forClass(Currency.class);
        verify(controller, times(1))
                .delete(argumentOwnerIdCaptor.capture(), argumentCurrencyCaptor.capture());
        assertSame(requestOwnerId, argumentOwnerIdCaptor.getValue());
        assertSame(requestCurrency, argumentCurrencyCaptor.getValue());
    }
}
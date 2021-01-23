package com.challenge.transfer.account;

import com.challenge.transfer.util.Currency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

class AccountControllerTest {
    private AccountController instance;

    @Mock
    private AccountRepository repository;

    @Captor
    private ArgumentCaptor<Iterable<AccountId>> idsArgumentCapture;

    @Captor
    private ArgumentCaptor<Iterable<AccountDto>> dtosArgumentCapture;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        instance = new AccountController(repository);
    }

    @Test
    void getAll() {
        // Prepare controller method
        Iterable<AccountDto> accounts = new ArrayList<>(0);
        when(repository.findAll()).thenReturn(accounts);

        // Invoke testing method
        Iterable<AccountDto> result = instance.getAll();

        // Verify result
        verify(repository, only()).findAll();
        assertSame(accounts, result);
    }

    @Test
    void get() {
        // Prepare return value
        Integer requestOwnerId = 0;
        Currency requestCurrency = Currency.USD;

        // Prepare controller method
        Optional<AccountDto> returnedValue = Optional.of(new AccountDto(0, Currency.USD, BigDecimal.TEN));
        when(repository.findById(requestOwnerId,requestCurrency)).thenReturn(returnedValue);

        // Invoke testing method
        Optional<AccountDto> result = instance.get(requestOwnerId, requestCurrency);

        // Verify result
        ArgumentCaptor<Integer> argumentOwnerIdCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Currency> argumentCurrencyCaptor = ArgumentCaptor.forClass(Currency.class);
        verify(repository, only())
                .findById(argumentOwnerIdCaptor.capture(), argumentCurrencyCaptor.capture());
        assertSame(requestOwnerId, argumentOwnerIdCaptor.getValue());
        assertSame(requestCurrency, argumentCurrencyCaptor.getValue());
        assertSame(returnedValue, result);
    }

    @Test
    void getBuIds() {
        // Prepare return value
        Iterable<AccountId> parameter = Collections.singletonList(new AccountId(0, Currency.EUR));

        // Prepare controller method
        Iterable<AccountDto> accounts =
                Collections.singletonList(new AccountDto(0, Currency.EUR, BigDecimal.TEN));
        when(repository.findAllById(parameter)).thenReturn(accounts);

        // Invoke testing method
        Iterable<AccountDto> result = instance.get(parameter);

        // Verify result
        assertSame(accounts, result);
        verify(repository, only()).findAllById(idsArgumentCapture.capture());
        assertSame(idsArgumentCapture.getValue(), parameter);

    }

    @Test
    void create() {
        // Prepare return value
        AccountDto parameter = new AccountDto(0, Currency.USD, BigDecimal.TEN);

        // Prepare controller method
        AccountDto returnedValue = new AccountDto(0, Currency.USD, BigDecimal.TEN);
        when(repository.save(parameter)).thenReturn(returnedValue);

        // Invoke testing method
        AccountDto result = instance.create(parameter);

        // Verify result
        assertSame(returnedValue, result);
        assertNotSame(parameter, result);

        ArgumentCaptor<AccountDto> argumentCaptor = ArgumentCaptor.forClass(AccountDto.class);
        verify(repository, times(1)).save(argumentCaptor.capture());
        assertSame(parameter, argumentCaptor.getValue());
    }

    @Test
    void edit() {
        // Prepare return value
        AccountDto parameter = new AccountDto(0, Currency.USD, BigDecimal.TEN);

        // Prepare controller method
        AccountDto returnedValue = new AccountDto(0, Currency.USD, BigDecimal.TEN);
        when(repository.save(parameter)).thenReturn(returnedValue);

        // Invoke testing method
        AccountDto result = instance.edit(parameter);

        // Verify result
        assertSame(returnedValue, result);
        assertNotSame(parameter, result);

        ArgumentCaptor<AccountDto> argumentCaptor = ArgumentCaptor.forClass(AccountDto.class);
        verify(repository, only()).save(argumentCaptor.capture());
        assertSame(parameter, argumentCaptor.getValue());
    }

    @Test
    void editMultiple() {
        // Prepare return value
        List<AccountDto> parameter = Collections.singletonList(new AccountDto(0, Currency.USD, BigDecimal.TEN));

        // Prepare controller method
        List<AccountDto> returnedValue
                = Collections.singletonList(new AccountDto(0, Currency.USD, BigDecimal.TEN));
        when(repository.saveAll(parameter)).thenReturn(returnedValue);

        // Invoke testing method
        Iterable<AccountDto> result = instance.edit(parameter);

        // Verify result
        assertSame(returnedValue, result);
        assertNotSame(parameter, result);

        verify(repository, only()).saveAll(dtosArgumentCapture.capture());
        assertSame(parameter, dtosArgumentCapture.getValue());
    }

    @Test
    void delete() {
        // Prepare return value
        Integer requestOwnerId = 0;
        Currency requestCurrency = Currency.USD;

        // Invoke testing method
        instance.delete(requestOwnerId, requestCurrency);

        // Verify result
        ArgumentCaptor<Integer> argumentOwnerIdCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Currency> argumentCurrencyCaptor = ArgumentCaptor.forClass(Currency.class);
        verify(repository, only())
                .deleteById(argumentOwnerIdCaptor.capture(), argumentCurrencyCaptor.capture());
        assertSame(requestOwnerId, argumentOwnerIdCaptor.getValue());
        assertSame(requestCurrency, argumentCurrencyCaptor.getValue());
    }
}
package com.challenge.transfer.account.jpa;

import com.challenge.transfer.account.AccountDto;
import com.challenge.transfer.account.AccountId;
import com.challenge.transfer.util.Currency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AccountRepositoryJPATest {

    private AccountRepositoryJPA instance;

    @Mock
    private AccountCrudRepository crudRepository;

    @Mock
    private AccountConverter converter;

    @Captor
    private ArgumentCaptor<AccountDto> converterFromCaptor;

    @Captor
    private ArgumentCaptor<AccountEntity> repositoryCaptor;

    @Captor
    private ArgumentCaptor<AccountEntity> converterToCaptor;

    @Captor
    private ArgumentCaptor<List<AccountEntity>> repositoryListCaptor;

    @Captor
    private ArgumentCaptor<List<AccountEntityPK>> repositoryPksListCaptor;

    private AccountDto converterDto;
    private AccountEntity converterEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        instance = new AccountRepositoryJPA(converter, crudRepository);

        // Setup converter
        converterDto = createDto();
        converterEntity = createEntity();
        when(converter.convertFrom(any())).thenReturn(converterEntity);
        when(converter.convertTo(any())).thenReturn(converterDto);
    }

    @Test
    void save() {
        // Setup parameter
        AccountDto parameter = createDto();

        // Setup repository
        AccountEntity repositoryEntity = createEntity();
        when(crudRepository.save(any())).thenReturn(repositoryEntity);

        // Invoke tested method
        AccountDto result = instance.save(parameter);

        // Validate result
        assertNotSame(parameter, result);
        assertSame(converterDto, result);

        verify(converter, times(1)).convertFrom(converterFromCaptor.capture());
        assertSame(parameter, converterFromCaptor.getValue());

        verify(crudRepository, times(1)).save(repositoryCaptor.capture());
        assertSame(converterEntity, repositoryCaptor.getValue());

        verify(converter, times(1)).convertTo(converterToCaptor.capture());
        assertSame(repositoryEntity, converterToCaptor.getValue());
    }

    @Test
    void saveAll() {
        // Setup parameter
        List<AccountDto> parameter = Arrays.asList(createDto(), createDto());

        // Setup repository
        Iterable<AccountEntity> repositoryEntity = Arrays.asList(createEntity(), createEntity());
        when(crudRepository.saveAll(any())).thenReturn(repositoryEntity);

        // Invoke tested method
        Iterable<AccountDto> result = instance.saveAll(parameter);

        // Validate result
        assertNotSame(parameter, result);
        assertEquals(2, ((ArrayList) result).size());
        result.forEach(res -> assertEquals(converterDto, res));

        verify(converter, times(2)).convertFrom(any());

        verify(crudRepository, only()).saveAll(repositoryListCaptor.capture());
        repositoryListCaptor.getValue().forEach(
                captured -> assertEquals(converterEntity, captured)
        );

        verify(converter, times(2)).convertTo(any());
    }

    @Test
    void findById() {
        // Setup parameter
        int ownerID = 0;
        Currency currency = Currency.EUR;

        // Setup repository
        AccountEntity repositoryEntity = createEntity();
        when(crudRepository.findById(any())).thenReturn(Optional.of(repositoryEntity));

        // Invoke tested method
        Optional<AccountDto> result = instance.findById(ownerID, currency);

        // Validate result
        assertTrue(result.isPresent());
        assertSame(converterDto, result.get());

        verify(converter, never()).convertFrom(any());

        ArgumentCaptor<AccountEntityPK> pkCaptur = ArgumentCaptor.forClass(AccountEntityPK.class);
        verify(crudRepository, only()).findById(pkCaptur.capture());
        assertEquals(0, pkCaptur.getValue().getOwnerId());
        assertEquals(Currency.EUR.getCode(), pkCaptur.getValue().getCurrency());

        verify(converter, only()).convertTo(converterToCaptor.capture());
        assertSame(repositoryEntity, converterToCaptor.getValue());
    }

    @Test
    void existsByIdExisted() {
        // Setup parameter
        int ownerID = 0;
        Currency currency = Currency.EUR;

        // Setup repository
        AccountEntity repositoryEntity = createEntity();
        when(crudRepository.existsById(any())).thenReturn(true);

        // Invoke tested method
        boolean result = instance.existsById(ownerID, currency);

        // Validate result
        assertTrue(result);

        verify(converter, never()).convertFrom(any());

        ArgumentCaptor<AccountEntityPK> pkCaptur = ArgumentCaptor.forClass(AccountEntityPK.class);
        verify(crudRepository, only()).existsById(pkCaptur.capture());
        assertEquals(0, pkCaptur.getValue().getOwnerId());
        assertEquals(Currency.EUR.getCode(), pkCaptur.getValue().getCurrency());

        verify(converter, never()).convertTo(any());
    }

    @Test
    void existsByIdNotExisted() {
        // Setup parameter
        int ownerID = 0;
        Currency currency = Currency.EUR;

        // Setup repository
        AccountEntity repositoryEntity = createEntity();
        when(crudRepository.existsById(any())).thenReturn(false);

        // Invoke tested method
        boolean result = instance.existsById(ownerID, currency);

        // Validate result
        assertFalse(result);

        verify(converter, never()).convertFrom(any());

        ArgumentCaptor<AccountEntityPK> pkCaptur = ArgumentCaptor.forClass(AccountEntityPK.class);
        verify(crudRepository, only()).existsById(pkCaptur.capture());
        assertEquals(0, pkCaptur.getValue().getOwnerId());
        assertEquals(Currency.EUR.getCode(), pkCaptur.getValue().getCurrency());

        verify(converter, never()).convertTo(any());
    }

    @Test
    void findAll() {
        // Setup repository
        Iterable<AccountEntity> repositoryEntity = Arrays.asList(createEntity(), createEntity());
        when(crudRepository.findAll()).thenReturn(repositoryEntity);

        // Invoke tested method
        Iterable<AccountDto> result = instance.findAll();

        // Validate result
        assertEquals(2, ((ArrayList) result).size());
        result.forEach(res -> assertEquals(converterDto, res));

        verify(converter, never()).convertFrom(any());

        verify(crudRepository, only()).findAll();

        verify(converter, times(2)).convertTo(any());
    }

    @Test
    void findAllById() {
        // Parameter
        Iterable<AccountId> ids = Arrays.asList(createId(), createId());

        // Setup repository
        Iterable<AccountEntity> repositoryEntity = Arrays.asList(createEntity(), createEntity());
        when(crudRepository.findAllById(any())).thenReturn(repositoryEntity);

        // Invoke tested method
        Iterable<AccountDto> result = instance.findAllById(ids);

        // Validate result
        assertEquals(2, ((ArrayList) result).size());
        result.forEach(res -> assertEquals(converterDto, res));

        verify(converter, never()).convertFrom(any());

        verify(crudRepository, only()).findAllById(repositoryPksListCaptor.capture());
        repositoryPksListCaptor.getValue().forEach(
                captured -> {
                    assertEquals(0, captured.getOwnerId());
                    assertEquals(Currency.EUR.getCode(), captured.getCurrency());
                }
        );

        verify(converter, times(2)).convertTo(any());
    }

    @Test
    void count() {
        // Setup repository
        long expected = 13L;
        when(crudRepository.count()).thenReturn(expected);

        // Invoke tested method
        long result = instance.count();

        // Validate result
        assertEquals(expected, result);

        verify(converter, never()).convertFrom(any());

        verify(crudRepository, only()).count();

        verify(converter, never()).convertTo(any());
    }

    @Test
    void deleteById() {
        // Setup parameter
        int ownerID = 0;
        Currency currency = Currency.EUR;

        // Invoke tested method
        instance.deleteById(ownerID, currency);

        // Validate result
        verify(converter, never()).convertFrom(any());

        ArgumentCaptor<AccountEntityPK> pkCaptor = ArgumentCaptor.forClass(AccountEntityPK.class);
        verify(crudRepository, times(1)).deleteById(pkCaptor.capture());
        assertEquals(0, pkCaptor.getValue().getOwnerId());
        assertEquals(Currency.EUR.getCode(), pkCaptor.getValue().getCurrency());

        verify(converter, never()).convertTo(any());
    }

    @Test
    void delete() {
        // Setup parameter
        AccountDto parameter = createDto();

        // Invoke tested method
        instance.delete(parameter);

        // Validate result
        verify(converter, only()).convertFrom(converterFromCaptor.capture());
        assertSame(parameter, converterFromCaptor.getValue());

        verify(crudRepository, only()).delete(repositoryCaptor.capture());
        assertSame(converterEntity, repositoryCaptor.getValue());

        verify(converter, never()).convertTo(any());
    }

    @Test
    void deleteAll() {
        // Invoke tested method
        instance.deleteAll();

        // Validate result

        verify(converter, never()).convertFrom(any());

        verify(crudRepository, only()).deleteAll();

        verify(converter, never()).convertTo(any());
    }

    @Test
    void deleteAllGiven() {
        // Setup parameter
        List<AccountDto> parameter = Arrays.asList(createDto(), createDto());

        // Invoke tested method
        instance.deleteAll(parameter);

        // Validate result
        verify(converter, times(2)).convertFrom(any());

        verify(crudRepository, only()).deleteAll(repositoryListCaptor.capture());
        repositoryListCaptor.getValue().forEach(
                captured -> assertEquals(converterEntity, captured)
        );

        verify(converter, never()).convertTo(any());
    }

    private AccountDto createDto() {
        return new AccountDto(0, Currency.EUR, BigDecimal.TEN);
    }

    private AccountEntity createEntity() {
        AccountEntity entity = new AccountEntity();
        entity.setId(new AccountEntityPK());
        entity.getId().setOwnerId(0);
        entity.getId().setCurrency(Currency.EUR.getCode());
        entity.setBalance(1000);
        return entity;
    }

    private AccountId createId() {
        return new AccountId(0, Currency.EUR);
    }
}
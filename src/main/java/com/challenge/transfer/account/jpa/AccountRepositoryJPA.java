package com.challenge.transfer.account.jpa;

import com.challenge.transfer.account.AccountRepository;
import com.challenge.transfer.dto.AccountDto;
import com.challenge.transfer.util.Convertable;
import com.challenge.transfer.util.Currency;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
class AccountRepositoryJPA implements AccountRepository {

    /**
     * Repository converter from DTO object to database entity.
     */
    private final transient Convertable<AccountDto, AccountEntity> converter;

    /**
     * Database entity CRUD repository.
     */
    private final transient CrudRepository<AccountEntity, AccountEntityPK>
            crudRepository;

    AccountRepositoryJPA(
            Convertable<AccountDto, AccountEntity> converter,
            CrudRepository<AccountEntity, AccountEntityPK> crudRepository) {
        this.converter = converter;
        this.crudRepository = crudRepository;
    }

    @Override
    public AccountDto save(AccountDto dto) {
        AccountEntity entity = converter.convertFrom(dto);
        return converter.convertTo(crudRepository.save(entity));
    }

    @Override
    public Iterable<AccountDto> saveAll(Iterable<AccountDto> dtos) {
        List<AccountEntity> entities = new ArrayList<>();
        dtos.forEach(dto -> entities.add(converter.convertFrom(dto)));
        Iterable<AccountEntity> results = crudRepository.saveAll(entities);
        List<AccountDto> resultsDtos = new ArrayList<>();
        results.forEach(entity -> resultsDtos.add(converter.convertTo(entity)));
        return resultsDtos;
    }

    @Override
    public Optional<AccountDto> findById(Integer ownerId, Currency currency) {
        return crudRepository.findById(
                    new AccountEntityPK(ownerId, currency.getCode())
                )
                .map(converter::convertTo);
    }

    @Override
    public boolean existsById(Integer ownerId, Currency currency) {
        return crudRepository.existsById(
                new AccountEntityPK(ownerId, currency.getCode())
        );
    }

    @Override
    public Iterable<AccountDto> findAll() {
        List<AccountDto> dtos = new ArrayList<>();
        crudRepository.findAll()
                .forEach(entity -> dtos.add(converter.convertTo(entity)));
        return dtos;
    }

    @Override
    public Iterable<AccountDto> findAllById(
            Iterable<Map.Entry<Integer, Currency>> ids) {
        List<AccountEntityPK> pks = new ArrayList<>();
        ids.forEach(id -> pks.add(
                new AccountEntityPK(id.getKey(), id.getValue().getCode())
        ));
        List<AccountDto> dtos = new ArrayList<>();
        crudRepository.findAllById(pks)
                .forEach(entity -> dtos.add(converter.convertTo(entity)));
        return dtos;
    }

    @Override
    public long count() {
        return crudRepository.count();
    }

    @Override
    public void deleteById(Integer ownerId, Currency currency) {
        crudRepository.deleteById(
                new AccountEntityPK(ownerId, currency.getCode())
        );
    }

    @Override
    public void delete(AccountDto dto) {
        crudRepository.delete(converter.convertFrom(dto));
    }

    @Override
    public void deleteAll(Iterable<AccountDto> dtos) {
        List<AccountEntity> entities = new ArrayList<>();
        dtos.forEach(dto -> entities.add(converter.convertFrom(dto)));
        crudRepository.deleteAll(entities);
    }

    @Override
    public void deleteAll() {
        crudRepository.deleteAll();
    }
}

package com.challenge.transfer.account;

import com.challenge.transfer.util.Currency;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class AccountController {

    /**
     * Account repository for CRUD operation.
     */
    private final transient AccountRepository repository;

    /**
     * Account controller for CRUD operation.
     * @param repository Account repository for CRUD operation
     */
    public AccountController(AccountRepository repository) {
        this.repository = repository;
    }

    /**
     * Get all existing accounts.
     * @return All accounts
     */
    public Iterable<AccountDto> getAll() {
        return repository.findAll();
    }

    /**
     * Return account for given owner ID and currency.
     * @param ownerId Owner ID
     * @param currency Currency
     * @return Account for given owner ID and currency
     */
    public Optional<AccountDto> get(int ownerId, Currency currency) {
        return repository.findById(ownerId, currency);
    }

    /**
     * Return collection of account for given collection of account ID (owner ID and currency).
     * @param ids Collection of account ID
     * @return Collection of accounts
     */
    public Iterable<AccountDto> get(Iterable<AccountId> ids) {
        return repository.findAllById(ids);
    }

    /**
     * Creates new account.
     * @param dto Account DTO
     * @return Created account DTO
     * @throws IllegalArgumentException When the account already exist
     */
    public AccountDto create(AccountDto dto) throws IllegalArgumentException {
        if (repository.existsById(dto.getOwnerId(), dto.getCurrency())) {
            String message = String.format(
                    "Unable to create new account for owner: '%s' and currency: '%s'. Account already existed.",
                    dto.getOwnerId(), dto.getCurrency()
            );
            throw new IllegalArgumentException(message);
        }
        return repository.save(dto);
    }

    /**
     * Edit account.
     * @param dto Account DTO
     * @return edited DTO
     * @throws IllegalArgumentException When the account not exist
     */
    public AccountDto createOrEdit(AccountDto dto) throws IllegalArgumentException {
        if (!repository.existsById(dto.getOwnerId(), dto.getCurrency())) {
            String message = String.format(
                    "Unable to edit not existing account for owner: '%s' and currency: '%s'.",
                    dto.getOwnerId(), dto.getCurrency()
            );
            throw new IllegalArgumentException(message);
        }
        return repository.save(dto);
    }

    /**
     * Create or edit multiple accounts at once.
     * @param accountDtos Collection of accounts
     * @return Collection of edited accounts
     */
    public Iterable<AccountDto> createOrEdit(Iterable<AccountDto> accountDtos) {
        return repository.saveAll(accountDtos);
    }

    /**
     * Delete account for given ownerId and currency.
     * @param ownerId Owner ID
     * @param currency Currency
     */
    public void delete(Integer ownerId, Currency currency) {
        repository.deleteById(ownerId, currency);
    }
}

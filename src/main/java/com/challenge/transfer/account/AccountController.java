package com.challenge.transfer.account;

import com.challenge.transfer.dto.AccountDto;
import com.challenge.transfer.util.Currency;
import org.springframework.stereotype.Controller;

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
     * Creates new account.
     * @param dto Account DTO
     * @return Created account DTO
     * @throws IllegalArgumentException When the account already exist
     */
    public AccountDto create(AccountDto dto) throws IllegalArgumentException {
        if (repository.existsById(dto.getOwnerId(), dto.getCurrency())) {
            throw new IllegalArgumentException("Account already existed");
        }
        return repository.save(dto);
    }

    /**
     * Edit account.
     * @param dto Account DTO
     * @return edited DTO
     */
    public AccountDto edit(AccountDto dto) {
        return repository.save(dto);
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

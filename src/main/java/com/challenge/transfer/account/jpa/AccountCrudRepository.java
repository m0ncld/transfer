package com.challenge.transfer.account.jpa;

import org.springframework.data.repository.CrudRepository;

interface AccountCrudRepository
        extends CrudRepository<AccountEntity, AccountEntityPK> {
}

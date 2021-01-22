package com.challenge.transfer.account.jpa;

import com.challenge.transfer.dto.AccountDto;
import com.challenge.transfer.util.ConversionException;
import com.challenge.transfer.util.Convertable;
import com.challenge.transfer.util.Currency;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
class AccountConverter implements Convertable<AccountDto, AccountEntity> {

    @Override
    public AccountEntity convertFrom(AccountDto from) {
        AccountEntity entity = new AccountEntity();
        AccountEntityPK pk = new AccountEntityPK(from.getOwnerId(),
                from.getCurrency().getCode());
        entity.setId(pk);
        BigDecimal balance = from.getBalance()
                .movePointRight(from.getCurrency().getMinor());
        entity.setBalance(balance.intValue());
        return entity;
    }

    @Override
    public AccountDto convertTo(AccountEntity to) throws ConversionException {
        Integer ownerId = to.getId().getOwnerId();
        try {
            Currency currency = Currency.valueOf(to.getId().getCurrency());
            BigDecimal balance = new BigDecimal(to.getBalance())
                    .movePointLeft(currency.getMinor());
            return new AccountDto(
                    ownerId,
                    currency,
                    balance
            );
        } catch (IllegalArgumentException ex) {
            throw new ConversionException("Unable to find currency for code "
                    + to.getId().getCurrency(), ex);
        }
    }
}

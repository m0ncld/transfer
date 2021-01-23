package com.challenge.transfer.account.jpa;

import com.challenge.transfer.account.AccountDto;
import com.challenge.transfer.util.ConversionException;
import com.challenge.transfer.util.Currency;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountConverterTest {

    private AccountConverter instance;

    @BeforeEach
    void setUp() {
        instance = new AccountConverter();
    }

    @Test
    void convertFrom() {
        // Prepare parameter
        AccountDto from = new AccountDto(0, Currency.EUR, BigDecimal.TEN);

        // Invoke tested method
        AccountEntity result = instance.convertFrom(from);

        // Verify result
        assertEquals(from.getOwnerId(), result.getId().getOwnerId());
        assertEquals(from.getCurrency().getCode(), result.getId().getCurrency());
        int expBalance = from.getBalance().movePointRight(2).intValue();
        assertEquals(expBalance, result.getBalance());
    }

    @Test
    void convertTo() {
        // Prepare parameter
        AccountEntity from = new AccountEntity();
        from.setId(new AccountEntityPK());
        from.getId().setOwnerId(0);
        from.getId().setCurrency(Currency.EUR.getCode());
        from.setBalance(10000);

        // Invoke tested method
        AccountDto result = instance.convertTo(from);

        // Verify result
        assertEquals(from.getId().getOwnerId(), result.getOwnerId());
        assertEquals(Currency.EUR, result.getCurrency());
        assertEquals(0, new BigDecimal(100).compareTo(result.getBalance()));
    }

    @Test
    void convertToWrongCurrency() {
        // Prepare parameter
        AccountEntity from = new AccountEntity();
        from.setId(new AccountEntityPK());
        from.getId().setOwnerId(0);
        from.getId().setCurrency("WrongCurrency");
        from.setBalance(10000);

        // Invoke tested method
        Assertions.assertThrows(ConversionException.class, () -> {
            instance.convertTo(from);
        });
    }
}
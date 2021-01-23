package com.challenge.transfer.account;

import com.challenge.transfer.util.Currency;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AccountDtoTest {

    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeAll
    static void beforeAll() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterAll
    static void afterAll() {
        validatorFactory.close();
    }

    @Test
    public void accountDtoValid() {
        // Create valid object
        AccountDto dto = new AccountDto(0, Currency.EUR, BigDecimal.TEN);

        // Validate
        Set<ConstraintViolation<AccountDto>> violations = validator.validate(dto);

        // Check if valid
        assertTrue(violations.isEmpty());
    }

    @Test
    public void accountDtoNoOwnerID() {
        // Create valid object
        AccountDto dto = new AccountDto(null, Currency.EUR, BigDecimal.TEN);

        // Validate
        Set<ConstraintViolation<AccountDto>> violations = validator.validate(dto);

        // Check if valid
        assertFalse(violations.isEmpty());
    }

    @Test
    public void accountDtoNoCurrency() {
        // Create valid object
        AccountDto dto = new AccountDto(0, null, BigDecimal.TEN);

        // Validate
        Set<ConstraintViolation<AccountDto>> violations = validator.validate(dto);

        // Check if valid
        assertFalse(violations.isEmpty());
    }

    @Test
    public void accountDtoNoBalance() {
        // Create valid object
        AccountDto dto = new AccountDto(0, Currency.EUR, null);

        // Validate
        Set<ConstraintViolation<AccountDto>> violations = validator.validate(dto);

        // Check if valid
        assertFalse(violations.isEmpty());
    }

    @Test
    void testEqualsTheSameAndHashCode() {
        AccountDto dto1 = new AccountDto(1, Currency.EUR, BigDecimal.TEN);
        assertTrue(dto1.equals(dto1));
    }

    @Test
    void testEqualsAndHashCode() {
        AccountDto dto1 = new AccountDto(1, Currency.EUR, BigDecimal.TEN);
        AccountDto dto2 = new AccountDto(1, Currency.EUR, BigDecimal.TEN);
        assertTrue(dto1.equals(dto2));
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void testNotEqualsAndHashCode() {
        AccountDto dto1 = new AccountDto(1, Currency.EUR, BigDecimal.TEN);
        AccountDto dto2 = new AccountDto(1, Currency.EUR, BigDecimal.ONE);
        assertFalse(dto1.equals(dto2));
        assertNotEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void testNotEqualsAndHashCodeNull() {
        AccountDto dto1 = new AccountDto(1, Currency.EUR, BigDecimal.TEN);
        AccountDto dto2 = null;
        assertNotEquals(dto1, dto2);
    }

    @Test
    void testNotEqualsAndHashCodeObject() {
        AccountDto dto1 = new AccountDto(1, Currency.EUR, BigDecimal.TEN);
        Object dto2 = new Object();
        assertNotEquals(dto1, dto2);
    }
}
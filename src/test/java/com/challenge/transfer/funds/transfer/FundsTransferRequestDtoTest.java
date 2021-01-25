package com.challenge.transfer.funds.transfer;

import com.challenge.transfer.account.AccountId;
import com.challenge.transfer.util.Currency;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class FundsTransferRequestDtoTest {

    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    private AccountId source;
    private AccountId target;
    private BigDecimal amount;

    @BeforeAll
    static void beforeAll() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterAll
    static void afterAll() {
        validatorFactory.close();
    }

    @BeforeEach
    void setUp() {
        source = new AccountId(0, Currency.EUR);
        target = new AccountId(1, Currency.USD);
        amount = BigDecimal.TEN;
    }

    @Test
    public void objectValid() {

        // Create valid object
        FundsTransferRequestDto dto =
                new FundsTransferRequestDto(source, target, amount);

        // Validate
        Set<ConstraintViolation<FundsTransferRequestDto>> violations = validator.validate(dto);

        // Check if valid
        assertTrue(violations.isEmpty());
    }

    @Test
    public void objectInValidNoSource() {
        source = null;

        // Create valid object
        FundsTransferRequestDto dto =
                new FundsTransferRequestDto(source, target, amount);

        // Validate
        Set<ConstraintViolation<FundsTransferRequestDto>> violations = validator.validate(dto);

        // Check if valid
        assertFalse(violations.isEmpty());
    }

    @Test
    public void objectInValidNoTarget() {
        target = null;

        // Create valid object
        FundsTransferRequestDto dto =
                new FundsTransferRequestDto(source, target, amount);

        // Validate
        Set<ConstraintViolation<FundsTransferRequestDto>> violations = validator.validate(dto);

        // Check if valid
        assertFalse(violations.isEmpty());
    }

    @Test
    public void objectInValidAmount() {
        amount = null;

        // Create valid object
        FundsTransferRequestDto dto =
                new FundsTransferRequestDto(source, target, amount);

        // Validate
        Set<ConstraintViolation<FundsTransferRequestDto>> violations = validator.validate(dto);

        // Check if valid
        assertFalse(violations.isEmpty());
    }

    @Test
    public void objectInValidAmountNegative() {
        amount = BigDecimal.TEN.negate();

        // Create valid object
        FundsTransferRequestDto dto =
                new FundsTransferRequestDto(source, target, amount);

        // Validate
        Set<ConstraintViolation<FundsTransferRequestDto>> violations = validator.validate(dto);

        // Check if valid
        assertFalse(violations.isEmpty());
    }

    @Test
    public void objectValidAmountZero() {
        amount = BigDecimal.ZERO;

        // Create valid object
        FundsTransferRequestDto dto =
                new FundsTransferRequestDto(source, target, amount);

        // Validate
        Set<ConstraintViolation<FundsTransferRequestDto>> violations = validator.validate(dto);

        // Check if valid
        assertTrue(violations.isEmpty());
    }

    @Test
    void textEquals() {
        EqualsVerifier.forClass(FundsTransferRequestDto.class).usingGetClass().verify();
    }
}
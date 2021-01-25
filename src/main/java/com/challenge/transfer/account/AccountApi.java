package com.challenge.transfer.account;

import com.challenge.transfer.util.Currency;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("account")
class AccountApi {

    /**
     * Account controller.
     */
    private final transient AccountController controller;

    /**
     * RESTful API for CRUD operation on accounts.
     * @param controller Account controller
     */
    AccountApi(AccountController controller) {
        this.controller = controller;
    }

    @GetMapping
    Iterable<AccountDto> getAll() {
        return controller.getAll();
    }

    @PostMapping
    AccountDto create(@Valid @RequestBody AccountDto dto) {
        return controller.create(dto);
    }

    @PutMapping
    AccountDto edit(@Valid @RequestBody AccountDto dto) {
        return controller.createOrEdit(dto);
    }

    @DeleteMapping("/by")
    void delete(@RequestParam Integer ownerId,
                @RequestParam Currency currency) {
        controller.delete(ownerId, currency);
    }
}

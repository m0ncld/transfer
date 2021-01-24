package com.challenge.transfer.funds.transfer;

import com.challenge.transfer.funds.transfer.transaction.TransactionProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("funds/transfer")
class FundsTransferApi {

    /**
     * Funds transfer controller.
     */
    private final transient FundsTransferController controller;

    FundsTransferApi(FundsTransferController controller) {
        this.controller = controller;
    }

    @PutMapping
    ResponseEntity<FundsTransferResponseErrorDto> transferFunds(@Valid @RequestBody FundsTransferRequestDto request) {
        try {
            controller.transferFunds(request);
            return ResponseEntity.noContent().build();
        } catch (TransactionProcessingException ex) {
            FundsTransferResponseErrorDto errorResponse = new FundsTransferResponseErrorDto(
                    ex.getTransactionId(),
                    ex.getErrorCode(),
                    ex.getMessage()
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
}

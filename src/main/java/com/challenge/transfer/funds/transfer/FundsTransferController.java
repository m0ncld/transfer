package com.challenge.transfer.funds.transfer;

import com.challenge.transfer.funds.transfer.transaction.TransactionProcessingException;
import com.challenge.transfer.funds.transfer.transaction.TransactionProcessor;
import org.springframework.stereotype.Controller;

@Controller
public class FundsTransferController {

    /**
     * Funds transfer processor.
     */
    private final transient TransactionProcessor transactionProcessor;

    /**
     * Construct funds transfer controller.
     *
     * Funds transfer controller process funds transfer request by funds transfer processor.
     * @param transactionProcessor Funds transfer processor
     */
    public FundsTransferController(TransactionProcessor transactionProcessor) {
        this.transactionProcessor = transactionProcessor;
    }

    void transferFunds(FundsTransferRequestDto request) throws TransactionProcessingException {
        transactionProcessor.process(request);
    }
}

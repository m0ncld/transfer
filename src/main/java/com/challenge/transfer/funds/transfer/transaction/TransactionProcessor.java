package com.challenge.transfer.funds.transfer.transaction;

import com.challenge.transfer.funds.transfer.FundsTransferRequestDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;
import java.util.Stack;
import java.util.UUID;

@Component
@RequestScope
public class TransactionProcessor {

    /**
     * Class logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionProcessor.class);

    /**
     * Transaction ID.
     */
    private final transient String transactionID = UUID.randomUUID().toString();

    /**
     * List of available funds transfer transaction processors.
     */
    private final transient List<Processor> processors;


    TransactionProcessor(List<Processor> processors) {
        this.processors = processors;
    }

    /**
     * Process funds transfer transaction request.
     *
     * Processing funds transfer transaction request step by step by defined processors.
     * @param request Funds transfer transaction request
     * @throws TransactionProcessingException Exception occurred during processing request eg. not sufficient account
     * funds, not account data, exchange rate nor available
     */
    public void process(FundsTransferRequestDto request) throws TransactionProcessingException {
        LOGGER.info("Start processing transaction: " + transactionID);
        logTransactionDetails(request);
        ProcessingContext context = new ProcessingContext(transactionID, request);
        Stack<Processor> processed = new Stack<>();
        try {
            processProcessors(context, processed);
        } catch (ProcessingException ex) {
            LOGGER.warn("Transaction " + transactionID + " processing error", ex);
            processOnFail(processed, context);
            throw TransactionProcessingException.of(transactionID, ex);
        } catch (RuntimeException ex) {
            LOGGER.warn("Transaction " + transactionID + " processing error", ex);
            processOnFail(processed, context);
            String message = "Unknown exception occurred during processing transaction";
            throw TransactionProcessingException.of(transactionID, message, ex);
        }
    }

    @SuppressWarnings("PMD.DataflowAnomalyAnalysis") // PMD Bug on for loop
    private void processProcessors(ProcessingContext context,  Stack<Processor> processed)  throws ProcessingException {
        for (Processor processor : processors) {
            processor.process(context);
            processed.push(processor);
        }
    }

    private void processOnFail(Stack<Processor> processed, ProcessingContext context) {
        while (!processed.empty()) {
            try {
                Processor processor = processed.pop();
                processor.onProcessingError(context);
            } catch (RuntimeException exception) {
                String message = String.format("Erroc occured on processing error for transaction: %s on processor: "
                        + "%s", transactionID, processed.getClass());
                LOGGER.warn(message, exception);
            }
        }
    }

    private void logTransactionDetails(FundsTransferRequestDto request) {
        String info = new StringBuilder("Transaction details:").append('\n')
                .append("source:      ").append(request.getSource()).append('\n')
                .append("destination: ").append(request.getTarget()).append('\n')
                .append("amount:      ").append(request.getAmount()).toString();
        LOGGER.debug(info);
    }
}

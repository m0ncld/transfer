package com.challenge.transfer.funds.transfer.transaction;

interface Processor {

    void process(ProcessingContext context) throws ProcessingException;

    default void onProcessingError(ProcessingContext context) { };

}

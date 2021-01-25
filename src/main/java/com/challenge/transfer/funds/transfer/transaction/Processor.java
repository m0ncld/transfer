package com.challenge.transfer.funds.transfer.transaction;

interface Processor {

    String getName();

    void process(ProcessingContext context) throws ProcessingException;

    default void onProcessingError(ProcessingContext context) { };

}

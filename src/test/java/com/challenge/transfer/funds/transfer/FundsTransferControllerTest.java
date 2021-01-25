package com.challenge.transfer.funds.transfer;

import com.challenge.transfer.funds.transfer.transaction.TransactionProcessingException;
import com.challenge.transfer.funds.transfer.transaction.TransactionProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class FundsTransferControllerTest {

    private FundsTransferController instance;

    @Mock
    private TransactionProcessor processor;

    @Mock
    private FundsTransferRequestDto request;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        instance = new FundsTransferController(processor);
    }

    @Test
    void transferFundsSuccess() throws TransactionProcessingException {
        // Invoke tested method
        instance.transferFunds(request);

        // Verify result
        ArgumentCaptor<FundsTransferRequestDto> captor = ArgumentCaptor.forClass(FundsTransferRequestDto.class);
        verify(processor, times(1)).process(captor.capture());
        assertEquals(request, captor.getValue());
    }

    @Test
    void transferFundsException() throws TransactionProcessingException {
        doThrow(TransactionProcessingException.class).when(processor).process(request);

        // Invoke tested method
        assertThrows(TransactionProcessingException.class,
                () -> {
                    instance.transferFunds(request);
                }
        );
    }
}
package com.challenge.transfer.funds.transfer.transaction;

import com.challenge.transfer.funds.transfer.FundsTransferRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionProcessorTest {

    private TransactionProcessor instance;

    @Mock
    private Processor processor1;

    @Mock
    private Processor processor2;

    @Mock
    private Processor processor3;

    @Mock
    private Processor processor4;

    @Mock
    private FundsTransferRequestDto request;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        instance = new TransactionProcessor(Arrays.asList(processor1, processor2, processor3, processor4));
    }

    @Test
    void process() throws ProcessingException {
        // Prepare mocks
        doNothing().when(processor1).process(any());
        doNothing().when(processor2).process(any());
        doNothing().when(processor3).process(any());
        doNothing().when(processor4).process(any());
        InOrder inOrder = inOrder(processor1, processor2, processor3, processor4);

        // Invoke tested method
        instance.process(request);

        // Verify result
        // Preparing captors
        ArgumentCaptor<ProcessingContext> process1 = ArgumentCaptor.forClass(ProcessingContext.class);
        ArgumentCaptor<ProcessingContext> process2 = ArgumentCaptor.forClass(ProcessingContext.class);
        ArgumentCaptor<ProcessingContext> process3 = ArgumentCaptor.forClass(ProcessingContext.class);
        ArgumentCaptor<ProcessingContext> process4 = ArgumentCaptor.forClass(ProcessingContext.class);

        // Capture
        verify(processor1, times(1)).process(process1.capture());
        verify(processor2, times(1)).process(process2.capture());
        verify(processor3, times(1)).process(process3.capture());
        verify(processor4, times(1)).process(process4.capture());

        assertSame(process1.getValue(), process2.getValue());
        assertSame(process1.getValue(), process3.getValue());
        assertSame(process1.getValue(), process4.getValue());

        // Verify order
        inOrder.verify(processor1).process(any());
        inOrder.verify(processor2).process(any());
        inOrder.verify(processor3).process(any());
        inOrder.verify(processor4).process(any());
    }

    @Test
    void processFailed() throws ProcessingException {
        // Prepare exception
        ProcessingException processingException = new ProcessingException(13, "message", "details");

        // Prepare mocks
        doNothing().when(processor1).process(any());
        doNothing().when(processor2).process(any());
        doThrow(processingException).when(processor3).process(any());
        doNothing().when(processor4).process(any());
        InOrder inOrder = inOrder(processor1, processor2, processor3, processor4);

        // Invoke tested method
        TransactionProcessingException exception
                = assertThrows(TransactionProcessingException.class, () -> instance.process(request));

        // Verify result
        assertEquals(processingException.getErrorCode(), exception.getErrorCode());
        assertEquals(processingException.getMessage(), exception.getMessage());
        assertEquals(processingException.getDetails(), exception.getDetails());


        // Preparing captors process
        ArgumentCaptor<ProcessingContext> process1 = ArgumentCaptor.forClass(ProcessingContext.class);

        // Capture process
        verify(processor1, times(1)).process(process1.capture());
        verify(processor2, times(1)).process(any());
        verify(processor3, times(1)).process(any());
        verify(processor4, never()).process(any());

        assertEquals(process1.getValue().getTransactionID(), exception.getTransactionId());

        // Preparing captors fail
        ArgumentCaptor<ProcessingContext> processFail1 = ArgumentCaptor.forClass(ProcessingContext.class);

        // Capture fail
        verify(processor1, times(1)).onProcessingError(processFail1.capture());
        verify(processor2, times(1)).onProcessingError(any());
        verify(processor3, never()).onProcessingError(any());
        verify(processor4, never()).onProcessingError(any());

        assertSame(process1.getValue(), processFail1.getValue());

        // Verify order
        inOrder.verify(processor1).process(any());
        inOrder.verify(processor2).process(any());
        inOrder.verify(processor3).process(any());
        inOrder.verify(processor2).onProcessingError(any());
        inOrder.verify(processor1).onProcessingError(any());
    }

    @Test
    void processRuntimeException() throws ProcessingException {

        // Prepare mocks
        doNothing().when(processor1).process(any());
        doThrow(RuntimeException.class).when(processor2).process(any());
        doNothing().when(processor3).process(any());
        InOrder inOrder = inOrder(processor1, processor2, processor3);

        // Invoke tested method
        TransactionProcessingException exception
                = assertThrows(TransactionProcessingException.class, () -> instance.process(request));

        // Verify result
        assertEquals(ProcessingException.UNDEFINED_EC, exception.getErrorCode());


        // Preparing captors process
        ArgumentCaptor<ProcessingContext> process1 = ArgumentCaptor.forClass(ProcessingContext.class);
        ArgumentCaptor<ProcessingContext> process2 = ArgumentCaptor.forClass(ProcessingContext.class);

        // Capture process
        verify(processor1, times(1)).process(process1.capture());
        verify(processor2, times(1)).process(process2.capture());
        verify(processor3, never()).process(any());

        assertSame(process1.getValue(), process2.getValue());
        assertEquals(process1.getValue().getTransactionID(), exception.getTransactionId());

        // Preparing captors fail
        ArgumentCaptor<ProcessingContext> processFail1 = ArgumentCaptor.forClass(ProcessingContext.class);

        // Capture fail
        verify(processor1, times(1)).onProcessingError(processFail1.capture());
        verify(processor2, never()).onProcessingError(any());
        verify(processor3, never()).onProcessingError(any());

        assertSame(process1.getValue(), processFail1.getValue());

        // Verify order
        inOrder.verify(processor1).process(any());
        inOrder.verify(processor2).process(any());
        inOrder.verify(processor1).onProcessingError(any());
    }

    @Test
    void processExceptionOnFailed() throws ProcessingException {
        // Prepare mocks
        doNothing().when(processor1).process(any());
        doNothing().when(processor2).process(any());
        doThrow(ProcessingException.class).when(processor3).process(any());
        doNothing().when(processor4).process(any());

        doThrow(RuntimeException.class).when(processor2).onProcessingError(any());
        InOrder inOrder = inOrder(processor1, processor2, processor3, processor4);

        // Invoke tested method
        assertThrows(TransactionProcessingException.class, () -> instance.process(request));

        // Capture process
        verify(processor1, times(1)).process(any());
        verify(processor2, times(1)).process(any());
        verify(processor3, times(1)).process(any());
        verify(processor4, never()).process(any());

        // Capture fail
        verify(processor1, times(1)).onProcessingError(any());
        verify(processor2, times(1)).onProcessingError(any());
        verify(processor3, never()).onProcessingError(any());
        verify(processor4, never()).onProcessingError(any());

        // Verify order
        inOrder.verify(processor1).process(any());
        inOrder.verify(processor2).process(any());
        inOrder.verify(processor3).process(any());
        inOrder.verify(processor2).onProcessingError(any());
        inOrder.verify(processor1).onProcessingError(any());
    }
}
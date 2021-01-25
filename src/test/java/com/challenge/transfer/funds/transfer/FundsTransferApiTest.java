package com.challenge.transfer.funds.transfer;

import com.challenge.transfer.funds.transfer.transaction.TransactionProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FundsTransferApiTest {

    private FundsTransferApi instance;

    @Mock
    private FundsTransferController controller;

    @Mock
    private FundsTransferRequestDto request;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        instance = new FundsTransferApi(controller);
    }

    @Test
    void transferFundsSuccess() throws TransactionProcessingException {
        // Invoke tested method
        ResponseEntity<FundsTransferResponseErrorDto> result = instance.transferFunds(request);

        // Verify result
        ArgumentCaptor<FundsTransferRequestDto> captor = ArgumentCaptor.forClass(FundsTransferRequestDto.class);
        verify(controller).transferFunds(captor.capture());
        assertSame(request, captor.getValue());
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        assertFalse(result.hasBody());
    }

    @Test
    void transferFundsTransException() throws TransactionProcessingException {
        String transId = "trans-id";
        int errorCode = 13;
        String details = "Error details";
        String message = "Error message";

        TransactionProcessingException ex = mock(TransactionProcessingException.class);
        when(ex.getTransactionId()).thenReturn(transId);
        when(ex.getErrorCode()).thenReturn(errorCode);
        when(ex.getMessage()).thenReturn(message);
        when(ex.getDetails()).thenReturn(details);
        doThrow(ex).when(controller).transferFunds(any());

        // Invoke tested method
        ResponseEntity<FundsTransferResponseErrorDto> result = instance.transferFunds(request);

        // Verify result
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        FundsTransferResponseErrorDto body = result.getBody();
        assertNotNull(body);
        assertEquals(transId, body.getTransactionId());
        assertEquals(errorCode, body.getErrorCode());
        assertEquals(message, body.getMessage());
    }

    @Test
    void transferFundsRuntimeException() throws TransactionProcessingException {
        doThrow(RuntimeException.class).when(controller).transferFunds(any());

        // Invoke tested method
        ResponseEntity<FundsTransferResponseErrorDto> result = instance.transferFunds(request);

        // Verify result
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
        assertFalse(result.hasBody());
    }
}
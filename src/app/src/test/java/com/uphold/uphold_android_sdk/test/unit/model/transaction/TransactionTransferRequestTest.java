package com.uphold.uphold_android_sdk.test.unit.model.transaction;

import com.uphold.uphold_android_sdk.model.transaction.TransactionDenominationRequest;
import com.uphold.uphold_android_sdk.model.transaction.TransactionTransferRequest;

import junit.framework.Assert;

import org.apache.commons.lang3.SerializationUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * TransactionTransferRequestTest unit tests.
 */

@RunWith(JUnit4.class)
public class TransactionTransferRequestTest {

    @Test
    public void shouldBeSerializable() {
        TransactionDenominationRequest transactionDenominationRequest = new TransactionDenominationRequest("fiz", "bar");
        TransactionTransferRequest transactionTransferRequest = new TransactionTransferRequest(transactionDenominationRequest, "foobar");
        byte[] serializedTransactionDestinationRequestTest = SerializationUtils.serialize(transactionTransferRequest);
        TransactionTransferRequest deserializedTransactionTransferRequest = SerializationUtils.deserialize(serializedTransactionDestinationRequestTest);

        Assert.assertEquals(transactionTransferRequest.getDenomination().getAmount(), deserializedTransactionTransferRequest.getDenomination().getAmount());
        Assert.assertEquals(transactionTransferRequest.getDenomination().getCurrency(), deserializedTransactionTransferRequest.getDenomination().getCurrency());
        Assert.assertEquals(transactionTransferRequest.getDestination(), deserializedTransactionTransferRequest.getDestination());
    }

}

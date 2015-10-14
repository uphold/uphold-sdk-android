package com.uphold.uphold_android_sdk.test.unit.model.transaction;

import junit.framework.Assert;

import org.apache.commons.lang3.SerializationUtils;
import com.uphold.uphold_android_sdk.model.transaction.TransactionDenominationRequest;
import com.uphold.uphold_android_sdk.model.transaction.TransactionRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * TransactionRequest unit tests.
 */

@RunWith(JUnit4.class)
public class TransactionRequestTest {

    @Test
    public void shouldBeSerializable() {
        TransactionDenominationRequest transactionDenominationRequest = new TransactionDenominationRequest("fiz", "bar");
        TransactionRequest transactionRequest = new TransactionRequest(transactionDenominationRequest, "foobar");
        byte[] serializedTransactionRequestTest = SerializationUtils.serialize(transactionRequest);
        TransactionRequest deserializedTransactionRequest = SerializationUtils.deserialize(serializedTransactionRequestTest);

        Assert.assertEquals(transactionRequest.getDenomination().getAmount(), deserializedTransactionRequest.getDenomination().getAmount());
        Assert.assertEquals(transactionRequest.getDenomination().getCurrency(), deserializedTransactionRequest.getDenomination().getCurrency());
        Assert.assertEquals(transactionRequest.getDestination(), deserializedTransactionRequest.getDestination());
    }

}

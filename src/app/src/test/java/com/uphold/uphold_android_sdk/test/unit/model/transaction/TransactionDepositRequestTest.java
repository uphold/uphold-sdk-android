package com.uphold.uphold_android_sdk.test.unit.model.transaction;

import com.uphold.uphold_android_sdk.model.transaction.TransactionDenominationRequest;
import com.uphold.uphold_android_sdk.model.transaction.TransactionDepositRequest;

import junit.framework.Assert;

import org.apache.commons.lang3.SerializationUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * TransactionDepositRequestTest unit tests.
 */

@RunWith(JUnit4.class)
public class TransactionDepositRequestTest {

    @Test
    public void shouldBeSerializable() {
        TransactionDenominationRequest transactionDenominationRequest = new TransactionDenominationRequest("fiz", "bar");
        TransactionDepositRequest transactionDepositRequest = new TransactionDepositRequest(transactionDenominationRequest, "foobar");
        byte[] serializedTransactionOriginRequestTest = SerializationUtils.serialize(transactionDepositRequest);
        TransactionDepositRequest deserializedTransactionDepositRequest = SerializationUtils.deserialize(serializedTransactionOriginRequestTest);

        Assert.assertEquals(transactionDepositRequest.getDenomination().getAmount(), deserializedTransactionDepositRequest.getDenomination().getAmount());
        Assert.assertEquals(transactionDepositRequest.getDenomination().getCurrency(), deserializedTransactionDepositRequest.getDenomination().getCurrency());
        Assert.assertEquals(transactionDepositRequest.getOrigin(), deserializedTransactionDepositRequest.getOrigin());
    }

}

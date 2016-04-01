package com.uphold.uphold_android_sdk.test.unit.model.transaction;

import com.uphold.uphold_android_sdk.model.transaction.TransactionCardDepositRequest;
import com.uphold.uphold_android_sdk.model.transaction.TransactionDenominationRequest;

import junit.framework.Assert;

import org.apache.commons.lang3.SerializationUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * TransactionCardDepositRequestTest unit tests.
 */

@RunWith(JUnit4.class)
public class TransactionCardDepositRequestTest {

    @Test
    public void shouldBeSerializable() {
        TransactionDenominationRequest transactionDenominationRequest = new TransactionDenominationRequest("fiz", "bar");
        TransactionCardDepositRequest transactionCardDepositRequest = new TransactionCardDepositRequest(transactionDenominationRequest, "foobar", "1234");
        byte[] serializedTransactionCardOriginRequestTest = SerializationUtils.serialize(transactionCardDepositRequest);
        TransactionCardDepositRequest deserializedTransactionCardDepositRequest = SerializationUtils.deserialize(serializedTransactionCardOriginRequestTest);

        Assert.assertEquals(transactionCardDepositRequest.getDenomination().getAmount(), deserializedTransactionCardDepositRequest.getDenomination().getAmount());
        Assert.assertEquals(transactionCardDepositRequest.getDenomination().getCurrency(), deserializedTransactionCardDepositRequest.getDenomination().getCurrency());
        Assert.assertEquals(transactionCardDepositRequest.getOrigin(), deserializedTransactionCardDepositRequest.getOrigin());
        Assert.assertEquals(transactionCardDepositRequest.getSecurityCode(), deserializedTransactionCardDepositRequest.getSecurityCode());
    }

}

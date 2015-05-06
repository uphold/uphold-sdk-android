package org.bitreserve.bitreserve_android_sdk.test.unit.model.transaction;

import junit.framework.Assert;

import org.apache.commons.lang3.SerializationUtils;
import org.bitreserve.bitreserve_android_sdk.model.transaction.TransactionCommitRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * TransactionCommitRequest unit tests.
 */

@RunWith(JUnit4.class)
public class TransactionCommitRequestTest {

    @Test
    public void shouldBeSerializable() {
        TransactionCommitRequest transactionCommitRequest = new TransactionCommitRequest("foobar");
        byte[] serializedTransactionCommitRequestTest = SerializationUtils.serialize(transactionCommitRequest);
        TransactionCommitRequest deserializedTransactionCommitRequestTest = SerializationUtils.deserialize(serializedTransactionCommitRequestTest);

        Assert.assertEquals(transactionCommitRequest.getMessage(), deserializedTransactionCommitRequestTest.getMessage());
    }

}

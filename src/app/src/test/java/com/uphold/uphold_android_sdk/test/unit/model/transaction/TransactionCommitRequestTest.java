package com.uphold.uphold_android_sdk.test.unit.model.transaction;

import com.uphold.uphold_android_sdk.model.transaction.Beneficiary;
import com.uphold.uphold_android_sdk.model.transaction.BeneficiaryAddress;
import com.uphold.uphold_android_sdk.model.transaction.TransactionCommitRequest;

import junit.framework.Assert;

import org.apache.commons.lang3.SerializationUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * TransactionCommitRequest unit tests.
 */

@RunWith(JUnit4.class)
public class TransactionCommitRequestTest {

    @Test
    public void shouldBeSerializableTransactionCommitRequest() {
        TransactionCommitRequest transactionCommitRequest = new TransactionCommitRequest("foobar");
        byte[] serializedTransactionCommitRequestTest = SerializationUtils.serialize(transactionCommitRequest);
        TransactionCommitRequest deserializedTransactionCommitRequestTest = SerializationUtils.deserialize(serializedTransactionCommitRequestTest);

        Assert.assertEquals(transactionCommitRequest.getMessage(), deserializedTransactionCommitRequestTest.getMessage());
    }

    @Test
    public void shouldBeSerializableTransactionCommitRequestWithBeneficiary() {
        BeneficiaryAddress address = new BeneficiaryAddress("faz", "fez", "fiz", "foz", "fuz", "foobiz");
        Beneficiary beneficiary = new Beneficiary(address, "buz", "fez");
        TransactionCommitRequest transactionCommitRequest = new TransactionCommitRequest(beneficiary);
        byte[] serializedTransactionCommitRequestTest = SerializationUtils.serialize(transactionCommitRequest);
        TransactionCommitRequest deserializedTransactionCommitRequestTest = SerializationUtils.deserialize(serializedTransactionCommitRequestTest);

        Assert.assertEquals(transactionCommitRequest.getBeneficiary().getAddress().getCity(), deserializedTransactionCommitRequestTest.getBeneficiary().getAddress().getCity());
        Assert.assertEquals(transactionCommitRequest.getBeneficiary().getAddress().getCountry(), deserializedTransactionCommitRequestTest.getBeneficiary().getAddress().getCountry());
        Assert.assertEquals(transactionCommitRequest.getBeneficiary().getAddress().getLine1(), deserializedTransactionCommitRequestTest.getBeneficiary().getAddress().getLine1());
        Assert.assertEquals(transactionCommitRequest.getBeneficiary().getAddress().getLine2(), deserializedTransactionCommitRequestTest.getBeneficiary().getAddress().getLine2());
        Assert.assertEquals(transactionCommitRequest.getBeneficiary().getAddress().getState(), deserializedTransactionCommitRequestTest.getBeneficiary().getAddress().getState());
        Assert.assertEquals(transactionCommitRequest.getBeneficiary().getAddress().getZipCode(), deserializedTransactionCommitRequestTest.getBeneficiary().getAddress().getZipCode());
        Assert.assertEquals(transactionCommitRequest.getBeneficiary().getName(), deserializedTransactionCommitRequestTest.getBeneficiary().getName());
        Assert.assertEquals(transactionCommitRequest.getBeneficiary().getRelationship(), deserializedTransactionCommitRequestTest.getBeneficiary().getRelationship());
    }

    @Test
    public void shouldBeSerializableTransactionCommitRequestWithBeneficiaryAndMessage() {
        BeneficiaryAddress address = new BeneficiaryAddress("faz", "fez", "fiz", "foz", "fuz", "foobiz");
        Beneficiary beneficiary = new Beneficiary(address, "buz", "fez");
        TransactionCommitRequest transactionCommitRequest = new TransactionCommitRequest(beneficiary, "foo");
        byte[] serializedTransactionCommitRequestTest = SerializationUtils.serialize(transactionCommitRequest);
        TransactionCommitRequest deserializedTransactionCommitRequestTest = SerializationUtils.deserialize(serializedTransactionCommitRequestTest);

        Assert.assertEquals(transactionCommitRequest.getMessage(), deserializedTransactionCommitRequestTest.getMessage());
        Assert.assertEquals(transactionCommitRequest.getBeneficiary().getAddress().getCity(), deserializedTransactionCommitRequestTest.getBeneficiary().getAddress().getCity());
        Assert.assertEquals(transactionCommitRequest.getBeneficiary().getAddress().getCountry(), deserializedTransactionCommitRequestTest.getBeneficiary().getAddress().getCountry());
        Assert.assertEquals(transactionCommitRequest.getBeneficiary().getAddress().getLine1(), deserializedTransactionCommitRequestTest.getBeneficiary().getAddress().getLine1());
        Assert.assertEquals(transactionCommitRequest.getBeneficiary().getAddress().getLine2(), deserializedTransactionCommitRequestTest.getBeneficiary().getAddress().getLine2());
        Assert.assertEquals(transactionCommitRequest.getBeneficiary().getAddress().getState(), deserializedTransactionCommitRequestTest.getBeneficiary().getAddress().getState());
        Assert.assertEquals(transactionCommitRequest.getBeneficiary().getAddress().getZipCode(), deserializedTransactionCommitRequestTest.getBeneficiary().getAddress().getZipCode());
        Assert.assertEquals(transactionCommitRequest.getBeneficiary().getName(), deserializedTransactionCommitRequestTest.getBeneficiary().getName());
        Assert.assertEquals(transactionCommitRequest.getBeneficiary().getRelationship(), deserializedTransactionCommitRequestTest.getBeneficiary().getRelationship());
    }

}

package com.uphold.uphold_android_sdk.test.unit.model;

import junit.framework.Assert;

import org.apache.commons.lang3.SerializationUtils;
import com.uphold.uphold_android_sdk.model.Transaction;
import com.uphold.uphold_android_sdk.model.transaction.Denomination;
import com.uphold.uphold_android_sdk.model.transaction.Destination;
import com.uphold.uphold_android_sdk.model.transaction.Origin;
import com.uphold.uphold_android_sdk.model.transaction.Parameters;
import com.uphold.uphold_android_sdk.model.transaction.Source;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

/**
 * Transaction unit tests.
 */

@RunWith(JUnit4.class)
public class TransactionTest {

    @Test
    public void shouldBeSerializable() {
        Denomination denomination = new Denomination("foo", "bar", "fuz", "buz");
        Destination destination = new Destination("fizbiz", "foobar", "biz", "foobiz", "foobuz", "fizbuz", "fizbiz", "foo", "bar", "fiz", "biz", "buz");
        List<Source> sources = new ArrayList<>();
        Parameters parameters = new Parameters("foobar", "foobiz", "foobuz", "fizbiz", "fuz", "fiz", 1, "foo", "bar");
        Source source = new Source("FUZBUZ", "FIZBIZ");
        Origin origin = new Origin("biz", "foo", "fiz", "bar", "foobar", "foobiz", "fiz", "biz", "fuzbuz", "fuz", sources, "buz", "FOOBAR");
        Transaction transaction = new Transaction("foobar", "foobiz", denomination, destination, "fuzbuz", origin, parameters, "fizbiz", "foobuz", "foo");

        sources.add(source);

        byte[] serializedTransaction = SerializationUtils.serialize(transaction);
        Transaction deserializedTransaction = SerializationUtils.deserialize(serializedTransaction);

        Assert.assertEquals(transaction.getCreatedAt(), deserializedTransaction.getCreatedAt());
        Assert.assertEquals(transaction.getDenomination().getAmount(), deserializedTransaction.getDenomination().getAmount());
        Assert.assertEquals(transaction.getDenomination().getCurrency(), deserializedTransaction.getDenomination().getCurrency());
        Assert.assertEquals(transaction.getDenomination().getPair(), deserializedTransaction.getDenomination().getPair());
        Assert.assertEquals(transaction.getDenomination().getRate(), deserializedTransaction.getDenomination().getRate());
        Assert.assertEquals(transaction.getDestination().getAccountId(), deserializedTransaction.getDestination().getAccountId());
        Assert.assertEquals(transaction.getDestination().getAccountType(), deserializedTransaction.getDestination().getAccountType());
        Assert.assertEquals(transaction.getDestination().getAmount(), deserializedTransaction.getDestination().getAmount());
        Assert.assertEquals(transaction.getDestination().getBase(), deserializedTransaction.getDestination().getBase() );
        Assert.assertEquals(transaction.getDestination().getCardId(), deserializedTransaction.getDestination().getCardId());
        Assert.assertEquals(transaction.getDestination().getCommission(), deserializedTransaction.getDestination().getCommission());
        Assert.assertEquals(transaction.getDestination().getCurrency(), deserializedTransaction.getDestination().getCurrency() );
        Assert.assertEquals(transaction.getDestination().getDescription(), deserializedTransaction.getDestination().getDescription());
        Assert.assertEquals(transaction.getDestination().getFee(), deserializedTransaction.getDestination().getFee());
        Assert.assertEquals(transaction.getDestination().getRate(), deserializedTransaction.getDestination().getRate());
        Assert.assertEquals(transaction.getDestination().getType(), deserializedTransaction.getDestination().getType());
        Assert.assertEquals(transaction.getDestination().getUsername(), deserializedTransaction.getDestination().getUsername());
        Assert.assertEquals(transaction.getId(), deserializedTransaction.getId());
        Assert.assertEquals(transaction.getMessage(), deserializedTransaction.getMessage());
        Assert.assertEquals(transaction.getOrigin().getAccountId(), deserializedTransaction.getOrigin().getAccountId());
        Assert.assertEquals(transaction.getOrigin().getAccountType(), deserializedTransaction.getOrigin().getAccountType());
        Assert.assertEquals(transaction.getOrigin().getAmount(), deserializedTransaction.getOrigin().getAmount());
        Assert.assertEquals(transaction.getOrigin().getBase(), deserializedTransaction.getOrigin().getBase());
        Assert.assertEquals(transaction.getOrigin().getCardId(), deserializedTransaction.getOrigin().getCardId());
        Assert.assertEquals(transaction.getOrigin().getCommission(), deserializedTransaction.getOrigin().getCommission());
        Assert.assertEquals(transaction.getOrigin().getCurrency(), deserializedTransaction.getOrigin().getCurrency());
        Assert.assertEquals(transaction.getOrigin().getDescription(), deserializedTransaction.getOrigin().getDescription());
        Assert.assertEquals(transaction.getOrigin().getFee(), deserializedTransaction.getOrigin().getFee());
        Assert.assertEquals(transaction.getOrigin().getRate(), deserializedTransaction.getOrigin().getRate());
        Assert.assertEquals(transaction.getOrigin().getSources().size(), deserializedTransaction.getOrigin().getSources().size());
        Assert.assertEquals(transaction.getOrigin().getSources().get(0).getAmount(), deserializedTransaction.getOrigin().getSources().get(0).getAmount());
        Assert.assertEquals(transaction.getOrigin().getSources().get(0).getId(), deserializedTransaction.getOrigin().getSources().get(0).getId());
        Assert.assertEquals(transaction.getOrigin().getType(), deserializedTransaction.getOrigin().getType());
        Assert.assertEquals(transaction.getOrigin().getUsername(), deserializedTransaction.getOrigin().getUsername());
        Assert.assertEquals(transaction.getParams().getCurrency(), deserializedTransaction.getParams().getCurrency());
        Assert.assertEquals(transaction.getParams().getMargin(), deserializedTransaction.getParams().getMargin());
        Assert.assertEquals(transaction.getParams().getPair(), deserializedTransaction.getParams().getPair());
        Assert.assertEquals(transaction.getParams().getProgress(), deserializedTransaction.getParams().getProgress());
        Assert.assertEquals(transaction.getParams().getRate(), deserializedTransaction.getParams().getRate());
        Assert.assertEquals(transaction.getParams().getRefunds(), deserializedTransaction.getParams().getRefunds());
        Assert.assertEquals(transaction.getParams().getTtl(), deserializedTransaction.getParams().getTtl());
        Assert.assertEquals(transaction.getParams().getTxid(), deserializedTransaction.getParams().getTxid());
        Assert.assertEquals(transaction.getParams().getType(), deserializedTransaction.getParams().getType());
        Assert.assertEquals(transaction.getRefundedById(), deserializedTransaction.getRefundedById());
        Assert.assertEquals(transaction.getStatus(), deserializedTransaction.getStatus());
        Assert.assertEquals(transaction.getType(), deserializedTransaction.getType());
    }

}

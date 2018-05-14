package com.uphold.uphold_android_sdk.test.unit.model;

import com.uphold.uphold_android_sdk.model.Transaction;
import com.uphold.uphold_android_sdk.model.transaction.Denomination;
import com.uphold.uphold_android_sdk.model.transaction.Destination;
import com.uphold.uphold_android_sdk.model.transaction.Fee;
import com.uphold.uphold_android_sdk.model.transaction.Merchant;
import com.uphold.uphold_android_sdk.model.transaction.Node;
import com.uphold.uphold_android_sdk.model.transaction.Normalized;
import com.uphold.uphold_android_sdk.model.transaction.Origin;
import com.uphold.uphold_android_sdk.model.transaction.Parameters;
import com.uphold.uphold_android_sdk.model.transaction.Source;

import junit.framework.Assert;

import org.apache.commons.lang3.SerializationUtils;
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
        Merchant destinationMerchant = new Merchant("foo", "bar", "biz", "buz", "foobar");
        Node destinationNode = new Node("foo", "bar", "boz");
        Destination destination = new Destination("fizbiz", "foobar", "biz", "fizbuz", "foobiz", "foobuz", "fizbuz", "fizbiz", "foo", "bar", destinationMerchant, destinationNode, "fiz", "biz", "buz");
        Fee fee = new Fee("foo", "bar", "fuz", "buz", "biz");
        List<Fee> fees = new ArrayList<>();
        List<Normalized> normalizeds = new ArrayList<>();
        List<Source> sources = new ArrayList<>();
        Merchant originMerchant = new Merchant("fuz", "biz", "boz", "baz", "foobiz");
        Node originNode = new Node("fiz", "bor", "baz");
        Origin origin = new Origin("biz", "foo", "fiz", "bar", "foobar", "foobiz", "fiz", "biz", "fuzbuz", originMerchant, originNode, "fuz", sources, "buz", "FOOBAR");
        Parameters parameters = new Parameters("foobar", "foobiz", "foobuz", "fizbiz", "fuz", "fiz", 1, "foo", "bar");
        Normalized normalized = new Normalized("foo", "bar", "fiz", "biz", "fixbiz");
        Source source = new Source("FUZBUZ", "FIZBIZ");
        Transaction transaction = new Transaction("foobar", "foobiz", denomination, destination, fees, "fuzbuz", "qux", normalizeds, origin, parameters, "fizbiz", 12345,"foobuz", "foo");

        fees.add(fee);
        normalizeds.add(normalized);
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
        Assert.assertEquals(transaction.getDestination().getAddress(), deserializedTransaction.getDestination().getAddress());
        Assert.assertEquals(transaction.getDestination().getAmount(), deserializedTransaction.getDestination().getAmount());
        Assert.assertEquals(transaction.getDestination().getBase(), deserializedTransaction.getDestination().getBase() );
        Assert.assertEquals(transaction.getDestination().getCardId(), deserializedTransaction.getDestination().getCardId());
        Assert.assertEquals(transaction.getDestination().getCommission(), deserializedTransaction.getDestination().getCommission());
        Assert.assertEquals(transaction.getDestination().getCurrency(), deserializedTransaction.getDestination().getCurrency() );
        Assert.assertEquals(transaction.getDestination().getDescription(), deserializedTransaction.getDestination().getDescription());
        Assert.assertEquals(transaction.getDestination().getFee(), deserializedTransaction.getDestination().getFee());
        Assert.assertEquals(transaction.getDestination().getMerchant().getCity(), deserializedTransaction.getDestination().getMerchant().getCity());
        Assert.assertEquals(transaction.getDestination().getMerchant().getCountry(), deserializedTransaction.getDestination().getMerchant().getCountry());
        Assert.assertEquals(transaction.getDestination().getMerchant().getName(), deserializedTransaction.getDestination().getMerchant().getName());
        Assert.assertEquals(transaction.getDestination().getMerchant().getState(), deserializedTransaction.getDestination().getMerchant().getState());
        Assert.assertEquals(transaction.getDestination().getMerchant().getZipCode(), deserializedTransaction.getDestination().getMerchant().getZipCode());
        Assert.assertEquals(transaction.getDestination().getNode().getBrand(), deserializedTransaction.getDestination().getNode().getBrand());
        Assert.assertEquals(transaction.getDestination().getNode().getId(), deserializedTransaction.getDestination().getNode().getId());
        Assert.assertEquals(transaction.getDestination().getNode().getType(), deserializedTransaction.getDestination().getNode().getType());
        Assert.assertEquals(transaction.getDestination().getRate(), deserializedTransaction.getDestination().getRate());
        Assert.assertEquals(transaction.getDestination().getType(), deserializedTransaction.getDestination().getType());
        Assert.assertEquals(transaction.getDestination().getUsername(), deserializedTransaction.getDestination().getUsername());
        Assert.assertEquals(transaction.getFees().get(0).getAmount(), deserializedTransaction.getFees().get(0).getAmount());
        Assert.assertEquals(transaction.getFees().get(0).getCurrency(), deserializedTransaction.getFees().get(0).getCurrency());
        Assert.assertEquals(transaction.getFees().get(0).getPercentage(), deserializedTransaction.getFees().get(0).getPercentage());
        Assert.assertEquals(transaction.getFees().get(0).getTarget(), deserializedTransaction.getFees().get(0).getTarget());
        Assert.assertEquals(transaction.getFees().get(0).getType(), deserializedTransaction.getFees().get(0).getType());
        Assert.assertEquals(transaction.getId(), deserializedTransaction.getId());
        Assert.assertEquals(transaction.getMessage(), deserializedTransaction.getMessage());
        Assert.assertEquals(transaction.getNetwork(), deserializedTransaction.getNetwork());
        Assert.assertEquals(transaction.getNormalized().size(), 1);
        Assert.assertEquals(transaction.getNormalized().get(0).getAmount(), deserializedTransaction.getNormalized().get(0).getAmount());
        Assert.assertEquals(transaction.getNormalized().get(0).getCommission(), deserializedTransaction.getNormalized().get(0).getCommission());
        Assert.assertEquals(transaction.getNormalized().get(0).getCurrency(), deserializedTransaction.getNormalized().get(0).getCurrency());
        Assert.assertEquals(transaction.getNormalized().get(0).getFee(), deserializedTransaction.getNormalized().get(0).getFee());
        Assert.assertEquals(transaction.getNormalized().get(0).getRate(), deserializedTransaction.getNormalized().get(0).getRate());
        Assert.assertEquals(transaction.getOrigin().getAccountId(), deserializedTransaction.getOrigin().getAccountId());
        Assert.assertEquals(transaction.getOrigin().getAccountType(), deserializedTransaction.getOrigin().getAccountType());
        Assert.assertEquals(transaction.getOrigin().getAmount(), deserializedTransaction.getOrigin().getAmount());
        Assert.assertEquals(transaction.getOrigin().getBase(), deserializedTransaction.getOrigin().getBase());
        Assert.assertEquals(transaction.getOrigin().getCardId(), deserializedTransaction.getOrigin().getCardId());
        Assert.assertEquals(transaction.getOrigin().getCommission(), deserializedTransaction.getOrigin().getCommission());
        Assert.assertEquals(transaction.getOrigin().getCurrency(), deserializedTransaction.getOrigin().getCurrency());
        Assert.assertEquals(transaction.getOrigin().getDescription(), deserializedTransaction.getOrigin().getDescription());
        Assert.assertEquals(transaction.getOrigin().getFee(), deserializedTransaction.getOrigin().getFee());
        Assert.assertEquals(transaction.getOrigin().getMerchant().getCity(), deserializedTransaction.getOrigin().getMerchant().getCity());
        Assert.assertEquals(transaction.getOrigin().getMerchant().getCountry(), deserializedTransaction.getOrigin().getMerchant().getCountry());
        Assert.assertEquals(transaction.getOrigin().getMerchant().getName(), deserializedTransaction.getOrigin().getMerchant().getName());
        Assert.assertEquals(transaction.getOrigin().getMerchant().getState(), deserializedTransaction.getOrigin().getMerchant().getState());
        Assert.assertEquals(transaction.getOrigin().getMerchant().getZipCode(), deserializedTransaction.getOrigin().getMerchant().getZipCode());
        Assert.assertEquals(transaction.getOrigin().getNode().getBrand(), deserializedTransaction.getOrigin().getNode().getBrand());
        Assert.assertEquals(transaction.getOrigin().getNode().getId(), deserializedTransaction.getOrigin().getNode().getId());
        Assert.assertEquals(transaction.getOrigin().getNode().getType(), deserializedTransaction.getOrigin().getNode().getType());
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
        Assert.assertEquals(transaction.getReference(), deserializedTransaction.getReference());
        Assert.assertEquals(transaction.getRefundedById(), deserializedTransaction.getRefundedById());
        Assert.assertEquals(transaction.getStatus(), deserializedTransaction.getStatus());
        Assert.assertEquals(transaction.getType(), deserializedTransaction.getType());
    }

}

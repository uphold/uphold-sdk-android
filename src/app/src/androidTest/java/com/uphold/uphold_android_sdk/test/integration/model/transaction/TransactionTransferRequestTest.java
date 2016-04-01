package com.uphold.uphold_android_sdk.test.integration.model.transaction;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import com.uphold.uphold_android_sdk.model.transaction.TransactionTransferRequest;
import com.uphold.uphold_android_sdk.test.util.Fixtures;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

/**
 * TransactionTransferRequestTest integration tests.
 */

@RunWith(AndroidJUnit4.class)
@SmallTest
public class TransactionTransferRequestTest {

    @Test
    public void getDestinationShouldReturnTheDestination() {
        TransactionTransferRequest transactionTransferRequest = Fixtures.loadTransactionTransferRequest(new HashMap<String, String>() {{
            put("destination", "foobar");
        }});

        Assert.assertEquals(transactionTransferRequest.getDestination(), "foobar");
    }

    @Test
    public void getDenominationShouldReturnTheDenomination() {
        TransactionTransferRequest transactionTransferRequest = Fixtures.loadTransactionTransferRequest(new HashMap<String, String>() {{
            put("amount", "0.01");
            put("currency", "foobar");
        }});

        Assert.assertEquals(transactionTransferRequest.getDenomination().getAmount(), "0.01");
        Assert.assertEquals(transactionTransferRequest.getDenomination().getCurrency(), "foobar");
    }

}

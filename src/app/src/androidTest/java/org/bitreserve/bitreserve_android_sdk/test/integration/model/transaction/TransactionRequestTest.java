package org.bitreserve.bitreserve_android_sdk.test.integration.model.transaction;

import junit.framework.Assert;

import org.bitreserve.bitreserve_android_sdk.model.transaction.TransactionRequest;
import org.bitreserve.bitreserve_android_sdk.test.util.Fixtures;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import java.util.HashMap;

/**
 * TransactionRequest integration tests.
 */

@RunWith(AndroidJUnit4.class)
@SmallTest
public class TransactionRequestTest {

    @Test
    public void getDestinationShouldReturnTheDestination() {
        TransactionRequest transactionRequest = Fixtures.loadTransactionRequest(new HashMap<String, String>() {{
            put("destination", "foobar");
        }});

        Assert.assertEquals(transactionRequest.getDestination(), "foobar");
    }

    @Test
    public void getDenominationShouldReturnTheDenomination() {
        TransactionRequest transactionRequest = Fixtures.loadTransactionRequest(new HashMap<String, String>() {{
            put("amount", "0.01");
            put("currency", "foobar");
        }});

        Assert.assertEquals(transactionRequest.getDenomination().getAmount(), "0.01");
        Assert.assertEquals(transactionRequest.getDenomination().getCurrency(), "foobar");
    }

}

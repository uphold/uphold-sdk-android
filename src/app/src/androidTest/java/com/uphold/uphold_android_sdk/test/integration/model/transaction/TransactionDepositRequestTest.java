package com.uphold.uphold_android_sdk.test.integration.model.transaction;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import com.uphold.uphold_android_sdk.model.transaction.TransactionDepositRequest;
import com.uphold.uphold_android_sdk.test.util.Fixtures;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

/**
 * TransactionDepositRequestTest integration tests.
 */

@RunWith(AndroidJUnit4.class)
@SmallTest
public class TransactionDepositRequestTest {

    @Test
    public void getOriginShouldReturnTheOrigin() {
        TransactionDepositRequest transactionDepositRequest = Fixtures.loadTransactionDepositRequest(new HashMap<String, String>() {{
            put("origin", "foobar");
        }});

        Assert.assertEquals(transactionDepositRequest.getOrigin(), "foobar");
    }

    @Test
    public void getDenominationShouldReturnTheDenomination() {
        TransactionDepositRequest transactionDepositRequest = Fixtures.loadTransactionDepositRequest(new HashMap<String, String>() {{
            put("amount", "0.01");
            put("currency", "foo");
            put("origin", "bar");
        }});

        Assert.assertEquals(transactionDepositRequest.getDenomination().getAmount(), "0.01");
        Assert.assertEquals(transactionDepositRequest.getDenomination().getCurrency(), "foo");
    }

}

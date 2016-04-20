package com.uphold.uphold_android_sdk.test.integration.model;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import com.uphold.uphold_android_sdk.model.Account;
import com.uphold.uphold_android_sdk.test.util.Fixtures;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

/**
 * Account integration tests.
 */

@RunWith(AndroidJUnit4.class)
@SmallTest
public class AccountTest {

    @Test
    public void getCurrencyShouldReturnCurrency() {
        Account account = Fixtures.loadAccount(new HashMap<String, String>() {{
            put("currency", "foo bar");
        }});

        Assert.assertEquals(account.getCurrency(), "foo bar");
    }

    @Test
    public void getIdShouldReturnId() {
        Account account = Fixtures.loadAccount(new HashMap<String, String>() {{
            put("id", "foobar");
        }});

        Assert.assertEquals(account.getId(), "foobar");
    }

    @Test
    public void getLabelShouldReturnLabel() {
        Account account = Fixtures.loadAccount(new HashMap<String, String>() {{
            put("label", "foo");
        }});

        Assert.assertEquals(account.getLabel(), "foo");
    }

    @Test
    public void getStatusShouldReturnStatus() {
        Account account = Fixtures.loadAccount(new HashMap<String, String>() {{
            put("status", "bar");
        }});

        Assert.assertEquals(account.getStatus(), "bar");
    }

    @Test
    public void getTypeShouldReturnType() {
        Account account = Fixtures.loadAccount(new HashMap<String, String>() {{
            put("type", "foo b");
        }});

        Assert.assertEquals(account.getType(), "foo b");
    }

}

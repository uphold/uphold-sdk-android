package com.uphold.uphold_android_sdk.test.unit.model;

import com.uphold.uphold_android_sdk.model.Account;

import junit.framework.Assert;

import org.apache.commons.lang3.SerializationUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Account unit tests.
 */

@RunWith(JUnit4.class)
public class AccountTest {

    @Test
    public void shouldBeSerializable() {
        Account account = new Account("foobar", "foo", "bar", "foobar", "foo bar");

        byte[] serializedAccount = SerializationUtils.serialize(account);
        Account deserializedAccount = SerializationUtils.deserialize(serializedAccount);

        Assert.assertEquals(account.getCurrency(), deserializedAccount.getCurrency());
        Assert.assertEquals(account.getId(), deserializedAccount.getId());
        Assert.assertEquals(account.getLabel(), deserializedAccount.getLabel());
        Assert.assertEquals(account.getStatus(), deserializedAccount.getStatus());
        Assert.assertEquals(account.getType(), deserializedAccount.getType());
    }

}

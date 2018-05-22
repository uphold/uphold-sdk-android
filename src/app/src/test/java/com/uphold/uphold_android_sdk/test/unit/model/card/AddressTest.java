package com.uphold.uphold_android_sdk.test.unit.model.card;

import com.uphold.uphold_android_sdk.model.card.Address;

import junit.framework.Assert;

import org.apache.commons.lang3.SerializationUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Address unit tests.
 */

@RunWith(JUnit4.class)
public class AddressTest {

    @Test
    public void addressShouldBeSerializable() {
        Address address = new Address("foo", "bar", "foobar");
        byte[] serializedAddress = SerializationUtils.serialize(address);
        Address deserializedAddress = SerializationUtils.deserialize(serializedAddress);

        Assert.assertEquals(address.getId(), deserializedAddress.getId());
        Assert.assertEquals(address.getNetwork(), deserializedAddress.getNetwork());
        Assert.assertEquals(address.getTag(), deserializedAddress.getTag());
    }

}

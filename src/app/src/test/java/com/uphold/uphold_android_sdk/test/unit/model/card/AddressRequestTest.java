package com.uphold.uphold_android_sdk.test.unit.model.card;

import com.uphold.uphold_android_sdk.model.card.AddressRequest;

import junit.framework.Assert;

import org.apache.commons.lang3.SerializationUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * AddressRequest unit tests.
 */

@RunWith(JUnit4.class)
public class AddressRequestTest {

    @Test
    public void addressRequestShouldBeSerializable() {
        AddressRequest addressRequest = new AddressRequest("foo");
        byte[] serializedAddressRequest = SerializationUtils.serialize(addressRequest);
        AddressRequest deserializedAddressRequest = SerializationUtils.deserialize(serializedAddressRequest);

        Assert.assertEquals(addressRequest.getNetwork(), deserializedAddressRequest.getNetwork());
    }

}

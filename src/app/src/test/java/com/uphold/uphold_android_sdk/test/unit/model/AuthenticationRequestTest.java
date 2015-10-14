package com.uphold.uphold_android_sdk.test.unit.model;

import junit.framework.Assert;

import org.apache.commons.lang3.SerializationUtils;
import com.uphold.uphold_android_sdk.model.AuthenticationRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * AuthenticationRequest unit tests.
 */

@RunWith(JUnit4.class)
public class AuthenticationRequestTest {

    @Test
    public void shouldBeSerializable() {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("foobar");
        byte[] serializedAuthenticationRequest = SerializationUtils.serialize(authenticationRequest);
        AuthenticationRequest deserializedAuthenticationRequest = SerializationUtils.deserialize(serializedAuthenticationRequest);

        Assert.assertEquals(authenticationRequest.getDescription(), deserializedAuthenticationRequest.getDescription());
    }

}

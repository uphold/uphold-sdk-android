package org.bitreserve.bitreserve_android_sdk.test.unit.model;

import junit.framework.Assert;

import org.apache.commons.lang3.SerializationUtils;
import org.bitreserve.bitreserve_android_sdk.model.AuthenticationResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * AuthenticationResponse unit tests.
 */

@RunWith(JUnit4.class)
public class AuthenticationResponseTest {

    @Test
    public void shouldBeSerializable() {
        AuthenticationResponse authenticationResponse = new AuthenticationResponse("foobar", 100, "foo", "bar");
        byte[] serializedAuthenticationResponse = SerializationUtils.serialize(authenticationResponse);
        AuthenticationResponse deserializedAuthenticationResponse = SerializationUtils.deserialize(serializedAuthenticationResponse);

        Assert.assertEquals(authenticationResponse.getAccessToken(), deserializedAuthenticationResponse.getAccessToken());
        Assert.assertEquals(authenticationResponse.getExpiresIn(), deserializedAuthenticationResponse.getExpiresIn());
        Assert.assertEquals(authenticationResponse.getScope(), deserializedAuthenticationResponse.getScope());
        Assert.assertEquals(authenticationResponse.getTokenType(), deserializedAuthenticationResponse.getTokenType());
    }

}

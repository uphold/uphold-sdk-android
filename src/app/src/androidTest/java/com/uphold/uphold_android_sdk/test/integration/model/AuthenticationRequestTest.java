package com.uphold.uphold_android_sdk.test.integration.model;

import junit.framework.Assert;

import com.uphold.uphold_android_sdk.model.AuthenticationRequest;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

/**
 * AuthenticationRequest integration tests.
 */

@RunWith(AndroidJUnit4.class)
@SmallTest
public class AuthenticationRequestTest {

    @Test
    public void getDescriptionShouldReturnTheDescription() {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("foobar");

        Assert.assertEquals(authenticationRequest.getDescription(), "foobar");
    }

}

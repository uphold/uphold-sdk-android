package com.uphold.uphold_android_sdk.test.integration.model.card;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import com.uphold.uphold_android_sdk.model.card.AddressRequest;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * AddressRequest integration tests.
 */

@RunWith(AndroidJUnit4.class)
@SmallTest
public class AddressRequestTest {

    @Test
    public void getNetworkShouldReturnTheNetwork() {
        AddressRequest addressRequest = new AddressRequest("foo");

        Assert.assertEquals(addressRequest.getNetwork(), "foo");
    }

}

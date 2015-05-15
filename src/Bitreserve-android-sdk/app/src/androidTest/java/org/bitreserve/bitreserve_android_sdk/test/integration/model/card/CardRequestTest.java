package org.bitreserve.bitreserve_android_sdk.test.integration.model.card;

import junit.framework.Assert;

import org.bitreserve.bitreserve_android_sdk.model.card.CardRequest;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

/**
 * CardRequest integration tests.
 */

@RunWith(AndroidJUnit4.class)
@SmallTest
public class CardRequestTest {

    @Test
    public void getLabelShouldReturnTheLabel() {
        CardRequest cardRequest = new CardRequest("foo", "bar");

        Assert.assertEquals(cardRequest.getLabel(), "foo");
    }

    @Test
    public void getCurrencyShouldReturnTheCurrency() {
        CardRequest cardRequest = new CardRequest("foo", "bar");

        Assert.assertEquals(cardRequest.getCurrency(), "bar");
    }

}

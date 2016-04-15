package com.uphold.uphold_android_sdk.test.integration.model.card;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import com.uphold.uphold_android_sdk.model.card.CardRequest;
import com.uphold.uphold_android_sdk.model.card.Settings;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

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

    @Test
    public void getSettingsPositionShouldReturnTheSettingsPosition() {
        CardRequest cardRequest = new CardRequest("foo", "bar", new Settings(0, true));

        Assert.assertEquals((int) cardRequest.getSettings().getPosition(), 0);
    }

    @Test
    public void getSettingsStarredShouldReturnTheSettingsStarred() {
        CardRequest cardRequest = new CardRequest("foo", "bar", new Settings(0, true));

        Assert.assertEquals((boolean) cardRequest.getSettings().getStarred(), true);
    }

}

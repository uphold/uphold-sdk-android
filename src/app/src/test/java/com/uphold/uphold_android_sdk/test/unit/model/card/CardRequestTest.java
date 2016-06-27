package com.uphold.uphold_android_sdk.test.unit.model.card;

import com.uphold.uphold_android_sdk.model.card.CardRequest;
import com.uphold.uphold_android_sdk.model.card.Settings;

import junit.framework.Assert;

import org.apache.commons.lang3.SerializationUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * CardRequest unit tests.
 */

@RunWith(JUnit4.class)
public class CardRequestTest {

    @Test
    public void cardRequestShouldBeSerializable() {
        CardRequest cardRequest = new CardRequest("foo", "bar");
        byte[] serializedCardRequest = SerializationUtils.serialize(cardRequest);
        CardRequest deserializedCardRequest = SerializationUtils.deserialize(serializedCardRequest);

        Assert.assertEquals(cardRequest.getCurrency(), deserializedCardRequest.getCurrency());
        Assert.assertEquals(cardRequest.getLabel(), deserializedCardRequest.getLabel());
    }

    @Test
    public void cardRequestWithSettingsShouldBeSerializable() {
        CardRequest cardRequest = new CardRequest("foo", "bar", new Settings(0, true));
        byte[] serializedCardRequest = SerializationUtils.serialize(cardRequest);
        CardRequest deserializedCardRequest = SerializationUtils.deserialize(serializedCardRequest);

        Assert.assertEquals(cardRequest.getCurrency(), deserializedCardRequest.getCurrency());
        Assert.assertEquals(cardRequest.getLabel(), deserializedCardRequest.getLabel());
        Assert.assertEquals(cardRequest.getSettings().getPosition(), deserializedCardRequest.getSettings().getPosition());
        Assert.assertEquals(cardRequest.getSettings().getStarred(), deserializedCardRequest.getSettings().getStarred());
    }

}

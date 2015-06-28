package org.bitreserve.bitreserve_android_sdk.test.unit.model.card;

import junit.framework.Assert;

import org.apache.commons.lang3.SerializationUtils;
import org.bitreserve.bitreserve_android_sdk.model.card.CardRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * CardRequest unit tests.
 */

@RunWith(JUnit4.class)
public class CardRequestTest {

    @Test
    public void shouldBeSerializable() {
        CardRequest cardRequest = new CardRequest("foo", "bar");
        byte[] serializedCardRequest = SerializationUtils.serialize(cardRequest);
        CardRequest deserializedCardRequest = SerializationUtils.deserialize(serializedCardRequest);

        Assert.assertEquals(cardRequest.getCurrency(), deserializedCardRequest.getCurrency());
        Assert.assertEquals(cardRequest.getLabel(), deserializedCardRequest.getLabel());
    }

}

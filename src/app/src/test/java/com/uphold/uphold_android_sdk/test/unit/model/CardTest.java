package com.uphold.uphold_android_sdk.test.unit.model;

import junit.framework.Assert;

import org.apache.commons.lang3.SerializationUtils;
import com.uphold.uphold_android_sdk.model.Card;
import com.uphold.uphold_android_sdk.model.card.Normalized;
import com.uphold.uphold_android_sdk.model.card.Settings;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Card unit tests.
 */

@RunWith(JUnit4.class)
public class CardTest {

    @Test
    public void shouldBeSerializable() {
        HashMap<String, String> addressMap = new HashMap<>();
        List<Normalized> normalizedList = new ArrayList<>();

        addressMap.put("FOO", "BAR");
        normalizedList.add(new Normalized("foo", "bar", "foobar"));

        Settings settings = new Settings(1, true);
        Card card = new Card("foobar", addressMap, "foo", "bar", "foobar", "foobuz", "fiz", normalizedList, settings);

        byte[] serializedCard = SerializationUtils.serialize(card);
        Card deserializedCard = SerializationUtils.deserialize(serializedCard);

        Assert.assertEquals(card.getAddress().size(), deserializedCard.getAddress().size());
        Assert.assertEquals(card.getAddress().get("FOO"), deserializedCard.getAddress().get("FOO"));
        Assert.assertEquals(card.getAvailable(), deserializedCard.getAvailable());
        Assert.assertEquals(card.getBalance(), deserializedCard.getBalance());
        Assert.assertEquals(card.getCurrency(), deserializedCard.getCurrency());
        Assert.assertEquals(card.getId(), deserializedCard.getId());
        Assert.assertEquals(card.getLabel(), deserializedCard.getLabel());
        Assert.assertEquals(card.getLastTransactionAt(), deserializedCard.getLastTransactionAt());
        Assert.assertEquals(card.getNormalized().size(), deserializedCard.getNormalized().size());
        Assert.assertEquals(card.getNormalized().get(0).getAvailable(), deserializedCard.getNormalized().get(0).getAvailable());
        Assert.assertEquals(card.getNormalized().get(0).getBalance(), deserializedCard.getNormalized().get(0).getBalance());
        Assert.assertEquals(card.getNormalized().get(0).getCurrency(), deserializedCard.getNormalized().get(0).getCurrency());
        Assert.assertEquals(card.getSettings().getPosition(), deserializedCard.getSettings().getPosition());
        Assert.assertEquals(card.getSettings().getStarred(), deserializedCard.getSettings().getStarred());
    }

}

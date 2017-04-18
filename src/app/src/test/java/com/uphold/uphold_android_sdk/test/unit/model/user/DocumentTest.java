package com.uphold.uphold_android_sdk.test.unit.model.user;

import com.uphold.uphold_android_sdk.model.user.Document;

import junit.framework.Assert;

import org.apache.commons.lang3.SerializationUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Document unit tests.
 */

@RunWith(JUnit4.class)
public class DocumentTest {

    @Test
    public void shouldBeSerializable() {
        Document document = new Document("foo", "bar");
        byte[] serializedDocument = SerializationUtils.serialize(document);
        Document deserializedDocument = SerializationUtils.deserialize(serializedDocument);

        Assert.assertEquals(document.getType(), deserializedDocument.getType());
        Assert.assertEquals(document.getValue(), deserializedDocument.getValue());
    }

}

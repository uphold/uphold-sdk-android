package com.uphold.uphold_android_sdk.test.unit.model.user;

import com.uphold.uphold_android_sdk.model.user.DocumentRequest;

import junit.framework.Assert;

import org.apache.commons.lang3.SerializationUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * DocumentRequest unit tests.
 */

@RunWith(JUnit4.class)
public class DocumentRequestTest {

    @Test
    public void shouldBeSerializable() {
        DocumentRequest documentRequest = new DocumentRequest("foo", "bar");
        byte[] serializedDocumentRequest = SerializationUtils.serialize(documentRequest);
        DocumentRequest deserializedDocumentRequest = SerializationUtils.deserialize(serializedDocumentRequest);

        Assert.assertEquals(documentRequest.getType(), deserializedDocumentRequest.getType());
        Assert.assertEquals(documentRequest.getValue(), deserializedDocumentRequest.getValue());
    }

}
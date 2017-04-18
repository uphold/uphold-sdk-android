package com.uphold.uphold_android_sdk.test.integration.model.user;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import com.uphold.uphold_android_sdk.model.user.DocumentRequest;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * DocumentRequest integration tests.
 */

@RunWith(AndroidJUnit4.class)
@SmallTest
public class DocumentRequestTest {

    @Test
    public void getTypeShouldReturnTheType() {
        DocumentRequest documentRequest = new DocumentRequest("foo", "bar");

        Assert.assertEquals(documentRequest.getType(), "foo");
    }

    @Test
    public void getValueShouldReturnTheValue() {
        DocumentRequest documentRequest = new DocumentRequest("foo", "bar");

        Assert.assertEquals(documentRequest.getValue(), "bar");
    }

}

package com.uphold.uphold_android_sdk.test.integration.model.user;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import com.uphold.uphold_android_sdk.model.user.Document;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Document integration tests.
 */

@RunWith(AndroidJUnit4.class)
@SmallTest
public class DocumentTest {

    @Test
    public void getTypeShouldReturnTheType() {
        Document document = new Document("foo", "bar");

        Assert.assertEquals(document.getType(), "foo");
    }

    @Test
    public void getValueShouldReturnTheValue() {
        Document document = new Document("foo", "bar");

        Assert.assertEquals(document.getValue(), "bar");
    }

}

package com.uphold.uphold_android_sdk.test.unit.model;

import junit.framework.Assert;

import org.apache.commons.lang3.SerializationUtils;
import com.uphold.uphold_android_sdk.model.Reserve;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Reserve unit tests.
 */

@RunWith(JUnit4.class)
public class ReserveTest {

    @Test
    public void shouldBeSerializable() {
        Reserve reserve = new Reserve();

        byte[] serializedReserve = SerializationUtils.serialize(reserve);
        Reserve deserializedReserve = SerializationUtils.deserialize(serializedReserve);

        Assert.assertTrue(reserve.getClass().equals(deserializedReserve.getClass()));
    }

}

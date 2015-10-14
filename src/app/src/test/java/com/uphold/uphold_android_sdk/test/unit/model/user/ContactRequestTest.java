package com.uphold.uphold_android_sdk.test.unit.model.user;

import junit.framework.Assert;

import org.apache.commons.lang3.SerializationUtils;
import com.uphold.uphold_android_sdk.model.user.ContactRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

/**
 * ContactRequest unit tests.
 */

@RunWith(JUnit4.class)
public class ContactRequestTest {

    @Test
    public void shouldBeSerializable() {
        List<String> addresses = new ArrayList<>();
        List<String> emails = new ArrayList<>();

        addresses.add("foobar");
        emails.add("foobar@email.com");

        ContactRequest contactRequest = new ContactRequest(addresses, "foo", emails, "fuz", "buz");
        byte[] serializedContactRequest = SerializationUtils.serialize(contactRequest);
        ContactRequest deserializedContactRequest = SerializationUtils.deserialize(serializedContactRequest);

        Assert.assertEquals(contactRequest.getAddresses().size(), deserializedContactRequest.getAddresses().size());
        Assert.assertEquals(contactRequest.getAddresses().get(0), deserializedContactRequest.getAddresses().get(0));
        Assert.assertEquals(contactRequest.getCompany(), deserializedContactRequest.getCompany());
        Assert.assertEquals(contactRequest.getEmails().size(), deserializedContactRequest.getEmails().size());
        Assert.assertEquals(contactRequest.getEmails().get(0), deserializedContactRequest.getEmails().get(0));
        Assert.assertEquals(contactRequest.getFirstName(), deserializedContactRequest.getFirstName());
        Assert.assertEquals(contactRequest.getLastName(), deserializedContactRequest.getLastName());
    }

}

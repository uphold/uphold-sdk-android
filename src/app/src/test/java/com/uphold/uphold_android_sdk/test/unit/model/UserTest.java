package com.uphold.uphold_android_sdk.test.unit.model;

import junit.framework.Assert;

import org.apache.commons.lang3.SerializationUtils;
import com.uphold.uphold_android_sdk.model.User;
import com.uphold.uphold_android_sdk.model.user.InternationalizationUserSetting;
import com.uphold.uphold_android_sdk.model.user.InternationalizationUserSettings;
import com.uphold.uphold_android_sdk.model.user.Settings;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;

/**
 * User unit tests.
 */

@RunWith(JUnit4.class)
public class UserTest {

    @Test
    public void shouldBeSerializable() {
        InternationalizationUserSetting internationalizationUserSettingDateTimeFormat = new InternationalizationUserSetting("FOO");
        InternationalizationUserSetting internationalizationUserSettingLanguage = new InternationalizationUserSetting("BAR");
        InternationalizationUserSetting internationalizationUserSettingNumberFormat = new InternationalizationUserSetting("FOOBAR");
        InternationalizationUserSettings internationalizationUserSettings = new InternationalizationUserSettings(internationalizationUserSettingLanguage, internationalizationUserSettingDateTimeFormat, internationalizationUserSettingNumberFormat);
        Settings settings = new Settings("FIZ", true, false, internationalizationUserSettings, "FUZ");
        ArrayList<String> currencies = new ArrayList<>();
        User user = new User("foobar", currencies, "foobar@foo.com", "foo", "bar", "Foo Bar", settings, "foobiz", "foobar", "fizbiz");

        currencies.add("EUR");
        currencies.add("USD");

        byte[] serializedUser = SerializationUtils.serialize(user);
        User deserializedUser = SerializationUtils.deserialize(serializedUser);

        Assert.assertEquals(user.getCountry(), deserializedUser.getCountry());
        Assert.assertEquals(user.getCurrencies().size(), deserializedUser.getCurrencies().size());
        Assert.assertEquals(user.getCurrencies().get(0), deserializedUser.getCurrencies().get(0));
        Assert.assertEquals(user.getCurrencies().get(1), deserializedUser.getCurrencies().get(1));
        Assert.assertEquals(user.getEmail(), deserializedUser.getEmail());
        Assert.assertEquals(user.getFirstName(), deserializedUser.getFirstName());
        Assert.assertEquals(user.getLastName(), deserializedUser.getLastName());
        Assert.assertEquals(user.getName(), deserializedUser.getName());
        Assert.assertEquals(user.getSettings().getCurrency(), deserializedUser.getSettings().getCurrency());
        Assert.assertEquals(user.getSettings().getHasNewsSubscription(), deserializedUser.getSettings().getHasNewsSubscription());
        Assert.assertEquals(user.getSettings().getHasOtpEnabled(), deserializedUser.getSettings().getHasOtpEnabled());
        Assert.assertEquals(user.getSettings().getIntl().getDateTimeFormat().getLocale(), deserializedUser.getSettings().getIntl().getDateTimeFormat().getLocale());
        Assert.assertEquals(user.getSettings().getIntl().getLanguage().getLocale(), deserializedUser.getSettings().getIntl().getLanguage().getLocale());
        Assert.assertEquals(user.getSettings().getIntl().getNumberFormat().getLocale(), deserializedUser.getSettings().getIntl().getNumberFormat().getLocale());
        Assert.assertEquals(user.getSettings().getTheme(), deserializedUser.getSettings().getTheme());
        Assert.assertEquals(user.getState(), deserializedUser.getState());
        Assert.assertEquals(user.getStatus(), deserializedUser.getStatus());
        Assert.assertEquals(user.getUsername(), deserializedUser.getUsername());
    }

}

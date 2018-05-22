package com.uphold.uphold_android_sdk.test.unit.model;

import com.uphold.uphold_android_sdk.model.User;
import com.uphold.uphold_android_sdk.model.user.VerificationParameter;
import com.uphold.uphold_android_sdk.model.user.Verifications;
import com.uphold.uphold_android_sdk.model.user.settings.internationalizationusersettings.InternationalizationUserSetting;
import com.uphold.uphold_android_sdk.model.user.settings.InternationalizationUserSettings;
import com.uphold.uphold_android_sdk.model.user.settings.otp.Login;
import com.uphold.uphold_android_sdk.model.user.settings.Otp;
import com.uphold.uphold_android_sdk.model.user.Settings;
import com.uphold.uphold_android_sdk.model.user.settings.otp.Transactions;
import com.uphold.uphold_android_sdk.model.user.settings.otp.transactions.Send;
import com.uphold.uphold_android_sdk.model.user.settings.otp.transactions.Transfer;
import com.uphold.uphold_android_sdk.model.user.settings.otp.transactions.Withdraw;
import com.uphold.uphold_android_sdk.model.user.settings.otp.transactions.withdraw.Crypto;

import junit.framework.Assert;

import org.apache.commons.lang3.SerializationUtils;
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
        Otp otp = new Otp(new Login(false), new Transactions(new Send(false), new Transfer(false), new Withdraw(new Crypto(true))));
        Settings settings = new Settings("FIZ", true, true, false, internationalizationUserSettings, otp, "FUZ");
        ArrayList<String> currencies = new ArrayList<>();
        VerificationParameter addressVerification = new VerificationParameter("address", "required");
        VerificationParameter birthdateVerification = new VerificationParameter("birthdate", "required");
        VerificationParameter documentsVerification = new VerificationParameter("documents", "required");
        VerificationParameter emailVerification = new VerificationParameter("email", "unconfirmed");
        VerificationParameter identityVerification = new VerificationParameter("identity", "required");
        VerificationParameter locationVerification = new VerificationParameter("location", "required");
        VerificationParameter marketingVerification = new VerificationParameter("marketing", "required");
        VerificationParameter phoneVerification = new VerificationParameter("phone", "required");
        VerificationParameter termsVerification = new VerificationParameter("terms", "required");
        Verifications verifications = new Verifications(addressVerification, birthdateVerification, documentsVerification, emailVerification, identityVerification, locationVerification, marketingVerification, phoneVerification, termsVerification);
        User user = new User("foobar", currencies, "foobar@foo.com", "foo", "bar", "foobar", "Foo Bar", settings, "foobiz", "foobar", "fizbiz", verifications);

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
        Assert.assertEquals(user.getMemberAt(), deserializedUser.getMemberAt());
        Assert.assertEquals(user.getName(), deserializedUser.getName());
        Assert.assertEquals(user.getSettings().getCurrency(), deserializedUser.getSettings().getCurrency());
        Assert.assertEquals(user.getSettings().getHasNewsSubscription(), deserializedUser.getSettings().getHasNewsSubscription());
        Assert.assertEquals(user.getSettings().getIntl().getDateTimeFormat().getLocale(), deserializedUser.getSettings().getIntl().getDateTimeFormat().getLocale());
        Assert.assertEquals(user.getSettings().getIntl().getLanguage().getLocale(), deserializedUser.getSettings().getIntl().getLanguage().getLocale());
        Assert.assertEquals(user.getSettings().getIntl().getNumberFormat().getLocale(), deserializedUser.getSettings().getIntl().getNumberFormat().getLocale());
        Assert.assertEquals(user.getSettings().getOtp().getLogin().getEnabled(), deserializedUser.getSettings().getOtp().getLogin().getEnabled());
        Assert.assertEquals(user.getSettings().getOtp().getTransactions().getSend().getEnabled(), deserializedUser.getSettings().getOtp().getTransactions().getSend().getEnabled());
        Assert.assertEquals(user.getSettings().getOtp().getTransactions().getTransfer().getEnabled(), deserializedUser.getSettings().getOtp().getTransactions().getTransfer().getEnabled());
        Assert.assertEquals(user.getSettings().getOtp().getTransactions().getWithdraw().getCrypto().getEnabled(), deserializedUser.getSettings().getOtp().getTransactions().getWithdraw().getCrypto().getEnabled());
        Assert.assertEquals(user.getSettings().getTheme(), deserializedUser.getSettings().getTheme());
        Assert.assertEquals(user.getState(), deserializedUser.getState());
        Assert.assertEquals(user.getStatus(), deserializedUser.getStatus());
        Assert.assertEquals(user.getUsername(), deserializedUser.getUsername());
        Assert.assertEquals(user.getVerifications().getAddress().getReason(), deserializedUser.getVerifications().getAddress().getReason());
        Assert.assertEquals(user.getVerifications().getAddress().getStatus(), deserializedUser.getVerifications().getAddress().getStatus());
        Assert.assertEquals(user.getVerifications().getBirthdate().getReason(), deserializedUser.getVerifications().getBirthdate().getReason());
        Assert.assertEquals(user.getVerifications().getBirthdate().getStatus(), deserializedUser.getVerifications().getBirthdate().getStatus());
        Assert.assertEquals(user.getVerifications().getDocuments().getReason(), deserializedUser.getVerifications().getDocuments().getReason());
        Assert.assertEquals(user.getVerifications().getDocuments().getStatus(), deserializedUser.getVerifications().getDocuments().getStatus());
        Assert.assertEquals(user.getVerifications().getEmail().getReason(), deserializedUser.getVerifications().getEmail().getReason());
        Assert.assertEquals(user.getVerifications().getEmail().getStatus(), deserializedUser.getVerifications().getEmail().getStatus());
        Assert.assertEquals(user.getVerifications().getIdentity().getReason(), deserializedUser.getVerifications().getIdentity().getReason());
        Assert.assertEquals(user.getVerifications().getIdentity().getStatus(), deserializedUser.getVerifications().getIdentity().getStatus());
        Assert.assertEquals(user.getVerifications().getLocation().getReason(), deserializedUser.getVerifications().getLocation().getReason());
        Assert.assertEquals(user.getVerifications().getLocation().getStatus(), deserializedUser.getVerifications().getLocation().getStatus());
        Assert.assertEquals(user.getVerifications().getMarketing().getReason(), deserializedUser.getVerifications().getMarketing().getReason());
        Assert.assertEquals(user.getVerifications().getMarketing().getStatus(), deserializedUser.getVerifications().getMarketing().getStatus());
        Assert.assertEquals(user.getVerifications().getPhone().getReason(), deserializedUser.getVerifications().getPhone().getReason());
        Assert.assertEquals(user.getVerifications().getPhone().getStatus(), deserializedUser.getVerifications().getPhone().getStatus());
        Assert.assertEquals(user.getVerifications().getTerms().getReason(), deserializedUser.getVerifications().getTerms().getReason());
        Assert.assertEquals(user.getVerifications().getTerms().getStatus(), deserializedUser.getVerifications().getTerms().getStatus());
    }

}

package org.bitreserve.bitreserve_android_sdk.test.util;

import com.github.javafaker.Faker;

import org.bitreserve.bitreserve_android_sdk.model.User;
import org.bitreserve.bitreserve_android_sdk.model.user.InternationalizationUserSetting;
import org.bitreserve.bitreserve_android_sdk.model.user.InternationalizationUserSettings;
import org.bitreserve.bitreserve_android_sdk.model.user.Settings;
import org.bitreserve.bitreserve_android_sdk.model.user.Status;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Fixtures for generating users, cards, etc.
 */

public class Fixtures {

    public static User loadUser() {
        return loadUser(null);
    }

    public static User loadUser(HashMap<String, String> fields) {
        final Faker faker = new Faker();
        final HashMap<String, String> fakerFields = new HashMap<String, String>() {{
            put("country", faker.address().country());
            put("currencies", String.format("%s,%s,%s", faker.lorem().fixedString(3), faker.lorem().fixedString(3), faker.lorem().fixedString(3)));
            put("currency", faker.lorem().fixedString(3));
            put("email", faker.internet().emailAddress());
            put("emailStatus", faker.lorem().fixedString(10));
            put("firstName", faker.name().firstName());
            put("hasNewsSubscription", "true");
            put("hasOtpEnabled", "true");
            put("identityStatus", faker.lorem().fixedString(10));
            put("internationalizationUserSettingDateTimeFormat", faker.lorem().fixedString(5));
            put("internationalizationUserSettingLanguage", faker.lorem().fixedString(5));
            put("internationalizationUserSettingNumberFormat", faker.lorem().fixedString(5));
            put("lastName", faker.name().lastName());
            put("name", faker.name().name());
            put("overviewStatus", faker.lorem().fixedString(10));
            put("phoneStatus", faker.lorem().fixedString(10));
            put("registrationStatus", faker.lorem().fixedString(10));
            put("reviewStatus", faker.lorem().fixedString(10));
            put("screeningStatus", faker.lorem().fixedString(10));
            put("state", faker.address().stateAbbr());
            put("theme", faker.lorem().fixedString(10));
            put("username", faker.lorem().fixedString(10));
            put("volumeStatus", faker.lorem().fixedString(10));
        }};

        if (fields != null) {
            fakerFields.putAll(fields);
        }

        ArrayList<String> currencies = new ArrayList<String>(){{
            for (String currency : fakerFields.get("currencies").split(",")) {
                add(currency);
            }
        }};
        InternationalizationUserSetting internationalizationUserSettingDateTimeFormat = new InternationalizationUserSetting(fakerFields.get("internationalizationUserSettingDateTimeFormat"));
        InternationalizationUserSetting internationalizationUserSettingLanguage = new InternationalizationUserSetting(fakerFields.get("internationalizationUserSettingLanguage"));
        InternationalizationUserSetting internationalizationUserSettingNumberFormat = new InternationalizationUserSetting(fakerFields.get("internationalizationUserSettingNumberFormat"));
        InternationalizationUserSettings internationalizationUserSettings = new InternationalizationUserSettings(internationalizationUserSettingLanguage, internationalizationUserSettingDateTimeFormat, internationalizationUserSettingNumberFormat);
        Settings settings = new Settings(fakerFields.get("currency"), Boolean.valueOf(fakerFields.get("hasNewsSubscription")), Boolean.valueOf(fakerFields.get("hasOtpEnabled")), internationalizationUserSettings, fakerFields.get("theme"));
        Status status = new Status(fakerFields.get("emailStatus"), fakerFields.get("identityStatus"), fakerFields.get("overviewStatus"), fakerFields.get("phoneStatus"), fakerFields.get("registrationStatus"), fakerFields.get("reviewStatus"), fakerFields.get("screeningStatus"), fakerFields.get("volumeStatus"));

        return new User(fakerFields.get("country"), currencies, fakerFields.get("email"), fakerFields.get("firstName"), fakerFields.get("lastName"), fakerFields.get("name"), settings, fakerFields.get("state"), status, fakerFields.get("username"));
    }

}

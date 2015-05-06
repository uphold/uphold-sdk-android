package org.bitreserve.bitreserve_android_sdk.test.util;

import com.github.javafaker.Faker;

import org.bitreserve.bitreserve_android_sdk.model.Card;
import org.bitreserve.bitreserve_android_sdk.model.User;
import org.bitreserve.bitreserve_android_sdk.model.card.Address;
import org.bitreserve.bitreserve_android_sdk.model.Transaction;
import org.bitreserve.bitreserve_android_sdk.model.transaction.Denomination;
import org.bitreserve.bitreserve_android_sdk.model.transaction.Destination;
import org.bitreserve.bitreserve_android_sdk.model.transaction.Origin;
import org.bitreserve.bitreserve_android_sdk.model.transaction.Parameters;
import org.bitreserve.bitreserve_android_sdk.model.transaction.Source;
import org.bitreserve.bitreserve_android_sdk.model.transaction.TransactionDenominationRequest;
import org.bitreserve.bitreserve_android_sdk.model.transaction.TransactionRequest;
import org.bitreserve.bitreserve_android_sdk.model.user.ContactRequest;
import org.bitreserve.bitreserve_android_sdk.model.user.InternationalizationUserSetting;
import org.bitreserve.bitreserve_android_sdk.model.user.InternationalizationUserSettings;
import org.bitreserve.bitreserve_android_sdk.model.user.Settings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Fixtures for generating users, cards, etc.
 */

public class Fixtures {

    public static Card loadCard() {
        return loadCard(null);
    }

    public static Card loadCard(HashMap<String, String> fields) {
        final Faker faker = new Faker();
        final HashMap<String, String> fakerFields = new HashMap<String, String>() {{
            put("addressKeys", String.format("%s,%s,%s", faker.lorem().fixedString(24), faker.lorem().fixedString(24), faker.lorem().fixedString(24)));
            put("addressValues", String.format("%s,%s,%s", faker.lorem().fixedString(24), faker.lorem().fixedString(24), faker.lorem().fixedString(24)));
            put("addressesKeys", String.format("%s,%s,%s", faker.lorem().fixedString(24), faker.lorem().fixedString(24), faker.lorem().fixedString(24)));
            put("addressesValues", String.format("%s,%s,%s", faker.lorem().fixedString(24), faker.lorem().fixedString(24), faker.lorem().fixedString(24)));
            put("available", faker.numerify("123456789"));
            put("balance", faker.numerify("123456789"));
            put("currency", faker.lorem().fixedString(3));
            put("id", faker.lorem().fixedString(20));
            put("label", faker.lorem().fixedString(20));
            put("lastTransactionAt", faker.lorem().fixedString(24));
            put("settingsPosition", "1");
            put("settingsStarred", "true");
        }};

        if (fields != null) {
            fakerFields.putAll(fields);
        }

        HashMap<String, String> address = new HashMap<String, String>() {{
            ArrayList<String> addressKeys = new ArrayList<>(Arrays.asList(fakerFields.get("addressKeys").split(",")));
            ArrayList<String> addressValues = new ArrayList<>(Arrays.asList(fakerFields.get("addressValues").split(",")));

            for (int position = 0; position < addressKeys.size(); position++) {
                put(addressKeys.get(position), addressValues.get(position));
            }
        }};
        ArrayList<Address> addresses = new ArrayList<Address>() {{
            ArrayList<String> addressesKeys = new ArrayList<>(Arrays.asList(fakerFields.get("addressesKeys").split(",")));
            ArrayList<String> addressesValues = new ArrayList<>(Arrays.asList(fakerFields.get("addressesValues").split(",")));

            for (int position = 0; position < addressesKeys.size(); position++) {
                add(new Address(addressesKeys.get(position), addressesValues.get(position)));
            }
        }};

        return new Card(fakerFields.get("id"), address, addresses, fakerFields.get("available"), fakerFields.get("balance"), fakerFields.get("currency"), fakerFields.get("label"), fakerFields.get("lastTransactionAt"), new org.bitreserve.bitreserve_android_sdk.model.card.Settings(Integer.parseInt(fakerFields.get("settingsPosition")), Boolean.valueOf(fakerFields.get("settingsStarred"))));
    }

    public static ContactRequest loadContactRequest() {
        return loadContactRequest(null);
    }

    public static ContactRequest loadContactRequest(HashMap<String, String> fields) {
        final Faker faker = new Faker();
        final HashMap<String, String> fakerFields = new HashMap<String, String>() {{
            put("addresses", String.format("%s,%s,%s", faker.numerify("123456789"), faker.numerify("123456789"), faker.numerify("123456789")));
            put("company", faker.name().name());
            put("emails", String.format("%s,%s,%s", faker.internet().emailAddress(), faker.internet().emailAddress(), faker.internet().emailAddress()));
            put("firstName", faker.name().firstName());
            put("lastName", faker.name().lastName());
        }};

        if (fields != null) {
            fakerFields.putAll(fields);
        }

        ArrayList<String> addresses = new ArrayList<>(Arrays.asList(fakerFields.get("addresses").split(",")));
        ArrayList<String> emails = new ArrayList<>(Arrays.asList(fakerFields.get("emails").split(",")));

        return new ContactRequest(addresses, fakerFields.get("company"), emails, fakerFields.get("firstName"), fakerFields.get("lastName"));
    }

    public static Transaction loadTransaction() {
        return loadTransaction(null);
    }

    public static Transaction loadTransaction(HashMap<String, String> fields) {
        final Faker faker = new Faker();
        final HashMap<String, String> fakerFields = new HashMap<String, String>() {{
            put("denominationAmount", faker.numerify("123456789"));
            put("denominationCurrency", faker.lorem().fixedString(3));
            put("denominationPair", faker.lorem().fixedString(6));
            put("denominationRate", faker.numerify("123456789"));
            put("destinationAmount", faker.numerify("123456789"));
            put("destinationBase", faker.numerify("123456789"));
            put("destinationCardId", faker.lorem().fixedString(24));
            put("destinationCommission", faker.numerify("123456789"));
            put("destinationCurrency", faker.lorem().fixedString(3));
            put("destinationDescription", faker.name().name());
            put("destinationFee", faker.lorem().fixedString(3));
            put("destinationRate", faker.lorem().fixedString(3));
            put("destinationType", faker.lorem().fixedString(6));
            put("destinationUsername", faker.lorem().fixedString(10));
            put("originAmount", faker.numerify("123456789"));
            put("originBase", faker.numerify("123456789"));
            put("originCardId", faker.lorem().fixedString(24));
            put("originCommission", faker.numerify("123456789"));
            put("originCurrency", faker.lorem().fixedString(3));
            put("originDescription", faker.name().fullName());
            put("originFee", faker.numerify("123456789"));
            put("originRate", faker.numerify("123456789"));
            put("originSourcesAmount", String.format("%s,%s,%s", faker.numerify("123456789"), faker.numerify("123456789"), faker.numerify("123456789")));
            put("originSourcesId", String.format("%s,%s,%s", faker.lorem().fixedString(24), faker.lorem().fixedString(24), faker.lorem().fixedString(24)));
            put("originType", faker.lorem().fixedString(10));
            put("originUsername", faker.name().name());
            put("parametersCurrency", faker.lorem().fixedString(3));
            put("parametersMargin", faker.numerify("123456789"));
            put("parametersPair", faker.lorem().fixedString(6));
            put("parametersProgress", faker.numerify("123456789"));
            put("parametersRate", faker.numerify("123456789"));
            put("parametersTtl", faker.numerify("123456789"));
            put("parametersTxid", faker.numerify("123456789"));
            put("parametersType", faker.lorem().fixedString(10));
            put("transactionCreatedAt", faker.lorem().fixedString(24));
            put("transactionId", faker.lorem().fixedString(24));
            put("transactionMessage", faker.lorem().fixedString(24));
            put("transactionRefundedById", faker.lorem().fixedString(24));
            put("transactionStatus", faker.lorem().fixedString(24));
            put("transactionType", faker.lorem().fixedString(24));
        }};

        if (fields != null) {
            fakerFields.putAll(fields);
        }

        Denomination denomination = new Denomination(fakerFields.get("denominationAmount"), fakerFields.get("denominationCurrency"), fakerFields.get("denominationPair"), fakerFields.get("denominationRate"));
        Destination destination = new Destination(fakerFields.get("destinationCardId"), fakerFields.get("destinationAmount"), fakerFields.get("destinationBase"), fakerFields.get("destinationCommission"), fakerFields.get("destinationCurrency"), fakerFields.get("destinationDescription"), fakerFields.get("destinationFee"), fakerFields.get("destinationRate"), fakerFields.get("destinationType"), fakerFields.get("destinationUsername"));
        ArrayList<Source> sources = new ArrayList<Source>() {{
            ArrayList<String> ids = new ArrayList<>(Arrays.asList(fakerFields.get("originSourcesId").split(",")));
            ArrayList<String> amount = new ArrayList<>(Arrays.asList(fakerFields.get("originSourcesAmount").split(",")));

            for (int counter = 0; counter < ids.size(); counter++) {
                add(new Source(ids.get(counter), amount.get(counter)));
            }
        }};
        Origin origin = new Origin(fakerFields.get("originCardId"), fakerFields.get("originAmount"), fakerFields.get("originBase"), fakerFields.get("originCommission"), fakerFields.get("originCurrency"), fakerFields.get("originDescription"), fakerFields.get("originFee"), fakerFields.get("originRate"), sources, fakerFields.get("originType"), fakerFields.get("originUsername"));
        Parameters parameters = new Parameters(fakerFields.get("parametersCurrency"), fakerFields.get("parametersMargin"), fakerFields.get("parametersPair"), fakerFields.get("parametersProgress"), fakerFields.get("parametersRate"), Integer.parseInt(fakerFields.get("parametersTtl")), fakerFields.get("parametersTxid"), fakerFields.get("parametersType"));

        return new Transaction(fakerFields.get("transactionId"), fakerFields.get("transactionCreatedAt"), denomination, destination, fakerFields.get("transactionMessage"), origin, parameters, fakerFields.get("transactionRefundedById"), fakerFields.get("transactionStatus"), fakerFields.get("transactionType"));
    }

    public static TransactionRequest loadTransactionRequest(){
        return loadTransactionRequest(null);
    }

    public static TransactionRequest loadTransactionRequest(HashMap<String, String> fields){
        final Faker faker = new Faker();
        final HashMap<String, String> fakerFields = new HashMap<String, String>() {{
            put("amount", faker.numerify("123456789"));
            put("currency", faker.lorem().fixedString(3));
            put("destination", faker.internet().emailAddress());
        }};

        if (fields != null) {
            fakerFields.putAll(fields);
        }

        TransactionDenominationRequest transactionDenominationRequest = new TransactionDenominationRequest(fakerFields.get("amount"), fakerFields.get("currency"));

        return new TransactionRequest(transactionDenominationRequest, fakerFields.get("destination"));
    }

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
            put("firstName", faker.name().firstName());
            put("hasNewsSubscription", "true");
            put("hasOtpEnabled", "true");
            put("internationalizationUserSettingDateTimeFormat", faker.lorem().fixedString(5));
            put("internationalizationUserSettingLanguage", faker.lorem().fixedString(5));
            put("internationalizationUserSettingNumberFormat", faker.lorem().fixedString(5));
            put("lastName", faker.name().lastName());
            put("name", faker.name().name());
            put("state", faker.address().stateAbbr());
            put("status", faker.lorem().fixedString(10));
            put("theme", faker.lorem().fixedString(10));
            put("username", faker.lorem().fixedString(10));
        }};

        if (fields != null) {
            fakerFields.putAll(fields);
        }

        ArrayList<String> currencies = new ArrayList<String>() {{
            for (String currency : fakerFields.get("currencies").split(",")) {
                add(currency);
            }
        }};
        InternationalizationUserSetting internationalizationUserSettingDateTimeFormat = new InternationalizationUserSetting(fakerFields.get("internationalizationUserSettingDateTimeFormat"));
        InternationalizationUserSetting internationalizationUserSettingLanguage = new InternationalizationUserSetting(fakerFields.get("internationalizationUserSettingLanguage"));
        InternationalizationUserSetting internationalizationUserSettingNumberFormat = new InternationalizationUserSetting(fakerFields.get("internationalizationUserSettingNumberFormat"));
        InternationalizationUserSettings internationalizationUserSettings = new InternationalizationUserSettings(internationalizationUserSettingLanguage, internationalizationUserSettingDateTimeFormat, internationalizationUserSettingNumberFormat);
        Settings settings = new Settings(fakerFields.get("currency"), Boolean.valueOf(fakerFields.get("hasNewsSubscription")), Boolean.valueOf(fakerFields.get("hasOtpEnabled")), internationalizationUserSettings, fakerFields.get("theme"));

        return new User(fakerFields.get("country"), currencies, fakerFields.get("email"), fakerFields.get("firstName"), fakerFields.get("lastName"), fakerFields.get("name"), settings, fakerFields.get("state"), fakerFields.get("status"), fakerFields.get("username"));
    }

}

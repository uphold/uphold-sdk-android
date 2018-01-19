package com.uphold.uphold_android_sdk.test.util;

import com.github.javafaker.Faker;
import com.uphold.uphold_android_sdk.model.Account;
import com.uphold.uphold_android_sdk.model.Card;
import com.uphold.uphold_android_sdk.model.Transaction;
import com.uphold.uphold_android_sdk.model.User;
import com.uphold.uphold_android_sdk.model.card.Normalized;
import com.uphold.uphold_android_sdk.model.transaction.Denomination;
import com.uphold.uphold_android_sdk.model.transaction.Destination;
import com.uphold.uphold_android_sdk.model.transaction.Fee;
import com.uphold.uphold_android_sdk.model.transaction.Merchant;
import com.uphold.uphold_android_sdk.model.transaction.Node;
import com.uphold.uphold_android_sdk.model.transaction.Origin;
import com.uphold.uphold_android_sdk.model.transaction.Parameters;
import com.uphold.uphold_android_sdk.model.transaction.Source;
import com.uphold.uphold_android_sdk.model.transaction.TransactionCardDepositRequest;
import com.uphold.uphold_android_sdk.model.transaction.TransactionDenominationRequest;
import com.uphold.uphold_android_sdk.model.transaction.TransactionDepositRequest;
import com.uphold.uphold_android_sdk.model.transaction.TransactionTransferRequest;
import com.uphold.uphold_android_sdk.model.user.ContactRequest;
import com.uphold.uphold_android_sdk.model.user.DocumentRequest;
import com.uphold.uphold_android_sdk.model.user.Settings;
import com.uphold.uphold_android_sdk.model.user.VerificationParameter;
import com.uphold.uphold_android_sdk.model.user.Verifications;
import com.uphold.uphold_android_sdk.model.user.settings.InternationalizationUserSettings;
import com.uphold.uphold_android_sdk.model.user.settings.Otp;
import com.uphold.uphold_android_sdk.model.user.settings.internationalizationusersettings.InternationalizationUserSetting;
import com.uphold.uphold_android_sdk.model.user.settings.otp.Login;
import com.uphold.uphold_android_sdk.model.user.settings.otp.Transactions;
import com.uphold.uphold_android_sdk.model.user.settings.otp.transactions.Send;
import com.uphold.uphold_android_sdk.model.user.settings.otp.transactions.Transfer;
import com.uphold.uphold_android_sdk.model.user.settings.otp.transactions.Withdraw;
import com.uphold.uphold_android_sdk.model.user.settings.otp.transactions.withdraw.Crypto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Fixtures for generating users, cards, etc.
 */

public class Fixtures {

    public static Account loadAccount(HashMap<String, String> fields) {
        final Faker faker = new Faker();
        final HashMap<String, String> fakerFields = new HashMap<String, String>() {{
            put("currency", faker.lorem().fixedString(3));
            put("id", faker.lorem().fixedString(20));
            put("label", faker.lorem().fixedString(10));
            put("status", faker.lorem().fixedString(8));
            put("type", faker.lorem().fixedString(8));
        }};

        if (fields != null) {
            fakerFields.putAll(fields);
        }

        return new Account(fakerFields.get("currency"), fakerFields.get("id"), fakerFields.get("label"), fakerFields.get("status"), fakerFields.get("type"));
    }

    public static Card loadCard() {
        return loadCard(null);
    }

    public static Card loadCard(HashMap<String, String> fields) {
        final Faker faker = new Faker();
        final HashMap<String, String> fakerFields = new HashMap<String, String>() {{
            put("addressKeys", String.format("%s,%s,%s", faker.lorem().fixedString(24), faker.lorem().fixedString(24), faker.lorem().fixedString(24)));
            put("addressValues", String.format("%s,%s,%s", faker.lorem().fixedString(24), faker.lorem().fixedString(24), faker.lorem().fixedString(24)));
            put("available", faker.numerify("123456789"));
            put("balance", faker.numerify("123456789"));
            put("currency", faker.lorem().fixedString(3));
            put("id", faker.lorem().fixedString(20));
            put("label", faker.lorem().fixedString(20));
            put("lastTransactionAt", faker.lorem().fixedString(24));
            put("normalizedAvailable", String.format("%s,%s,%s", faker.numerify("123456789"), faker.numerify("123456789"), faker.numerify("123456789")));
            put("normalizedBalance", String.format("%s,%s,%s", faker.numerify("123456789"), faker.numerify("123456789"), faker.numerify("123456789")));
            put("normalizedCurrencies", String.format("%s,%s,%s", faker.lorem().fixedString(3), faker.lorem().fixedString(3), faker.lorem().fixedString(3)));
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
        ArrayList<Normalized> normalized = new ArrayList<Normalized>() {{
            ArrayList<String> normalizedAvailable = new ArrayList<>(Arrays.asList(fakerFields.get("normalizedAvailable").split(",")));
            ArrayList<String> normalizedBalance = new ArrayList<>(Arrays.asList(fakerFields.get("normalizedBalance").split(",")));
            ArrayList<String> normalizedCurrencies = new ArrayList<>(Arrays.asList(fakerFields.get("normalizedCurrencies").split(",")));

            for (int position = 0; position < normalizedAvailable.size(); position++) {
                add(new Normalized(normalizedAvailable.get(position), normalizedBalance.get(position), normalizedCurrencies.get(position)));
            }
        }};

        return new Card(fakerFields.get("id"), address, fakerFields.get("available"), fakerFields.get("balance"), fakerFields.get("currency"), fakerFields.get("label"), fakerFields.get("lastTransactionAt"), normalized, new com.uphold.uphold_android_sdk.model.card.Settings(Integer.parseInt(fakerFields.get("settingsPosition")), Boolean.valueOf(fakerFields.get("settingsStarred"))));
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

    public static DocumentRequest loadDocumentRequest() {
        return loadDocumentRequest(null);
    }

    public static DocumentRequest loadDocumentRequest(HashMap<String, String> fields) {
        final Faker faker = new Faker();
        final HashMap<String, String> fakerFields = new HashMap<String, String>() {{
            put("type", faker.lorem().fixedString(5));
            put("value", faker.lorem().fixedString(5));
        }};

        if (fields != null) {
            fakerFields.putAll(fields);
        }

        return new DocumentRequest(fakerFields.get("type"), fakerFields.get("value"));
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
            put("destinationAccountId", faker.numerify("123456789"));
            put("destinationAccountType", faker.lorem().fixedString(7));
            put("destinationAmount", faker.numerify("123456789"));
            put("destinationBase", faker.numerify("123456789"));
            put("destinationCardId", faker.lorem().fixedString(24));
            put("destinationCommission", faker.numerify("123456789"));
            put("destinationCurrency", faker.lorem().fixedString(3));
            put("destinationDescription", faker.name().name());
            put("destinationFee", faker.lorem().fixedString(3));
            put("destinationMerchantCity", faker.address().cityPrefix());
            put("destinationMerchantCountry", faker.address().country());
            put("destinationMerchantName", faker.name().fullName());
            put("destinationMerchantState", faker.address().stateAbbr());
            put("destinationMerchantZipCode", faker.address().zipCode());
            put("destinationNodeBrand", faker.lorem().fixedString(5));
            put("destinationNodeId", faker.lorem().fixedString(20));
            put("destinationNodeType", faker.lorem().fixedString(5));
            put("destinationRate", faker.lorem().fixedString(3));
            put("destinationType", faker.lorem().fixedString(6));
            put("destinationUsername", faker.lorem().fixedString(10));
            put("feeAmount", faker.numerify("123456789"));
            put("feeCurrency", faker.lorem().fixedString(3));
            put("feePercentage", faker.numerify("123456789"));
            put("feeTarget", faker.lorem().fixedString(10));
            put("feeType", faker.lorem().fixedString(10));
            put("normalizedAmount", faker.numerify("123456789"));
            put("normalizedCommission", faker.numerify("123456789"));
            put("normalizedCurrency", faker.lorem().fixedString(3));
            put("normalizedFee", faker.numerify("123456789"));
            put("normalizedRate", faker.numerify("123456789"));
            put("originAccountId", faker.numerify("123456789"));
            put("originAccountType", faker.lorem().fixedString(7));
            put("originAmount", faker.numerify("123456789"));
            put("originBase", faker.numerify("123456789"));
            put("originCardId", faker.lorem().fixedString(24));
            put("originCommission", faker.numerify("123456789"));
            put("originCurrency", faker.lorem().fixedString(3));
            put("originDescription", faker.name().fullName());
            put("originFee", faker.numerify("123456789"));
            put("originMerchantCity", faker.address().cityPrefix());
            put("originMerchantCountry", faker.address().country());
            put("originMerchantName", faker.name().fullName());
            put("originMerchantState", faker.address().stateAbbr());
            put("originMerchantZipCode", faker.address().zipCode());
            put("originNodeBrand", faker.lorem().fixedString(5));
            put("originNodeId", faker.lorem().fixedString(20));
            put("originNodeType", faker.lorem().fixedString(5));
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
            put("transactionNetwork", faker.lorem().fixedString(24));
            put("transactionRefundedById", faker.lorem().fixedString(24));
            put("transactionRefunds", faker.lorem().fixedString(24));
            put("transactionStatus", faker.lorem().fixedString(24));
            put("transactionType", faker.lorem().fixedString(24));
        }};

        if (fields != null) {
            fakerFields.putAll(fields);
        }

        Denomination denomination = new Denomination(fakerFields.get("denominationAmount"), fakerFields.get("denominationCurrency"), fakerFields.get("denominationPair"), fakerFields.get("denominationRate"));
        Merchant destinationMerchant = new Merchant(fakerFields.get("destinationMerchantCity"), fakerFields.get("destinationMerchantCountry"), fakerFields.get("destinationMerchantName"), fakerFields.get("destinationMerchantState"),
            fakerFields.get("destinationMerchantZipCode"));
        Node destinationNode = new Node(fakerFields.get("destinationNodeBrand"), fakerFields.get("destinationNodeId"), fakerFields.get("destinationNodeType"));
        Destination destination = new Destination(fakerFields.get("destinationAccountId"), fakerFields.get("destinationCardId"), fakerFields.get("destinationAccountType"), fakerFields.get("destinationAmount"), fakerFields.get("destinationBase"),
            fakerFields.get("destinationCommission"), fakerFields.get("destinationCurrency"), fakerFields.get("destinationDescription"), fakerFields.get("destinationFee"), destinationMerchant, destinationNode, fakerFields.get("destinationRate"),
            fakerFields.get("destinationType"), fakerFields.get("destinationUsername"));
        ArrayList<Fee> fees = new ArrayList<Fee>() {{
            add(new Fee(fakerFields.get("feeAmount"), fakerFields.get("feeCurrency"), fakerFields.get("feePercentage"), fakerFields.get("feeTarget"), fakerFields.get("feeType")));
        }};
        ArrayList<Source> sources = new ArrayList<Source>() {{
            ArrayList<String> ids = new ArrayList<>(Arrays.asList(fakerFields.get("originSourcesId").split(",")));
            ArrayList<String> amount = new ArrayList<>(Arrays.asList(fakerFields.get("originSourcesAmount").split(",")));

            for (int counter = 0; counter < ids.size(); counter++) {
                add(new Source(ids.get(counter), amount.get(counter)));
            }
        }};
        ArrayList<com.uphold.uphold_android_sdk.model.transaction.Normalized> normalized = new ArrayList<com.uphold.uphold_android_sdk.model.transaction.Normalized>() {{
            add(new com.uphold.uphold_android_sdk.model.transaction.Normalized(fakerFields.get("normalizedAmount"), fakerFields.get("normalizedCommission"), fakerFields.get("normalizedCurrency"), fakerFields.get("normalizedFee"), fakerFields.get("normalizedRate")));
        }};
        Merchant originMerchant = new Merchant(fakerFields.get("originMerchantCity"), fakerFields.get("originMerchantCountry"), fakerFields.get("originMerchantName"), fakerFields.get("originMerchantState"), fakerFields.get("originMerchantZipCode"));
        Node originNode = new Node(fakerFields.get("originNodeBrand"), fakerFields.get("originNodeId"), fakerFields.get("originNodeType"));
        Origin origin = new Origin(fakerFields.get("originAccountId"), fakerFields.get("originCardId"), fakerFields.get("originAccountType"), fakerFields.get("originAmount"), fakerFields.get("originBase"), fakerFields.get("originCommission"),
            fakerFields.get("originCurrency"), fakerFields.get("originDescription"), fakerFields.get("originFee"), originMerchant, originNode, fakerFields.get("originRate"), sources, fakerFields.get("originType"), fakerFields.get("originUsername"));
        Parameters parameters = new Parameters(fakerFields.get("parametersCurrency"), fakerFields.get("parametersMargin"), fakerFields.get("parametersPair"), fakerFields.get("parametersProgress"), fakerFields.get("parametersRate"),
            fakerFields.get("parametersRefunds"), Integer.parseInt(fakerFields.get("parametersTtl")), fakerFields.get("parametersTxid"), fakerFields.get("parametersType"));

        return new Transaction(fakerFields.get("transactionId"), fakerFields.get("transactionCreatedAt"), denomination, destination, fees, fakerFields.get("transactionMessage"), fakerFields.get("transactionNetwork"), normalized, origin, parameters, fakerFields.get("transactionRefundedById"), fakerFields.get("transactionStatus"), fakerFields.get("transactionType"));
    }

    public static TransactionCardDepositRequest loadTransactionCardDepositRequest(HashMap<String, String> fields){
        final Faker faker = new Faker();
        final HashMap<String, String> fakerFields = new HashMap<String, String>() {{
            put("amount", faker.numerify("123456789"));
            put("currency", faker.lorem().fixedString(3));
            put("origin", faker.lorem().fixedString(8));
            put("securityCode", String.valueOf(faker.numerify("1234")));
        }};

        if (fields != null) {
            fakerFields.putAll(fields);
        }

        TransactionDenominationRequest transactionDenominationRequest = new TransactionDenominationRequest(fakerFields.get("amount"), fakerFields.get("currency"));

        return new TransactionCardDepositRequest(transactionDenominationRequest, fakerFields.get("origin"), fakerFields.get("securityCode"));
    }

    public static TransactionDepositRequest loadTransactionDepositRequest(HashMap<String, String> fields){
        final Faker faker = new Faker();
        final HashMap<String, String> fakerFields = new HashMap<String, String>() {{
            put("amount", faker.numerify("123456789"));
            put("currency", faker.lorem().fixedString(3));
            put("origin", faker.lorem().fixedString(8));
        }};

        if (fields != null) {
            fakerFields.putAll(fields);
        }

        TransactionDenominationRequest transactionDenominationRequest = new TransactionDenominationRequest(fakerFields.get("amount"), fakerFields.get("currency"));

        return new TransactionDepositRequest(transactionDenominationRequest, fakerFields.get("origin"));
    }

    public static TransactionTransferRequest loadTransactionTransferRequest(HashMap<String, String> fields){
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

        return new TransactionTransferRequest(transactionDenominationRequest, fakerFields.get("destination"));
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
            put("internationalizationUserSettingDateTimeFormat", faker.lorem().fixedString(5));
            put("internationalizationUserSettingLanguage", faker.lorem().fixedString(5));
            put("internationalizationUserSettingNumberFormat", faker.lorem().fixedString(5));
            put("lastName", faker.name().lastName());
            put("memberAt", faker.lorem().fixedString(8));
            put("name", faker.name().name());
            put("settingsOTPLogin", "true");
            put("settingsOTPTransactionsSend", "true");
            put("settingsOTPTransactionsTransfer", "true");
            put("settingsOTPTransactionsWithdrawCrypto", "true");
            put("state", faker.address().stateAbbr());
            put("status", faker.lorem().fixedString(10));
            put("theme", faker.lorem().fixedString(10));
            put("username", faker.lorem().fixedString(10));
            put("verificationsReasonAddress", faker.lorem().fixedString(10));
            put("verificationsReasonBirthdate", faker.lorem().fixedString(10));
            put("verificationsReasonDocuments", faker.lorem().fixedString(10));
            put("verificationsReasonEmail", faker.lorem().fixedString(10));
            put("verificationsReasonIdentity", faker.lorem().fixedString(10));
            put("verificationsReasonLocation", faker.lorem().fixedString(10));
            put("verificationsReasonPhone", faker.lorem().fixedString(10));
            put("verificationsReasonTerms", faker.lorem().fixedString(10));
            put("verificationsStatusAddress", faker.lorem().fixedString(10));
            put("verificationsStatusBirthdate", faker.lorem().fixedString(10));
            put("verificationsStatusDocuments", faker.lorem().fixedString(10));
            put("verificationsStatusEmail", faker.lorem().fixedString(10));
            put("verificationsStatusIdentity", faker.lorem().fixedString(10));
            put("verificationsStatusLocation", faker.lorem().fixedString(10));
            put("verificationsStatusPhone", faker.lorem().fixedString(10));
            put("verificationsStatusTerms", faker.lorem().fixedString(10));
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
        Otp otp = new Otp(new Login(Boolean.valueOf(fakerFields.get("settingsOTPLogin"))), new Transactions(new Send(Boolean.valueOf(fakerFields.get("settingsOTPTransactionsSend"))), new Transfer(Boolean.valueOf(fakerFields.get("settingsOTPTransactionsTransfer"))), new Withdraw(new Crypto(Boolean.valueOf(fakerFields.get("settingsOTPTransactionsWithdrawCrypto"))))));
        Settings settings = new Settings(fakerFields.get("currency"), Boolean.valueOf(fakerFields.get("hasNewsSubscription")), Boolean.valueOf(fakerFields.get("hasOtpEnabled")), internationalizationUserSettings, otp, fakerFields.get("theme"));
        VerificationParameter verificationsAddress = new VerificationParameter(fakerFields.get("verificationsReasonAddress"), fakerFields.get("verificationsStatusAddress"));
        VerificationParameter verificationsBirthdate = new VerificationParameter(fakerFields.get("verificationsReasonBirthdate"), fakerFields.get("verificationsStatusBirthdate"));
        VerificationParameter verificationsDocuments = new VerificationParameter(fakerFields.get("verificationsReasonDocuments"), fakerFields.get("verificationsStatusDocuments"));
        VerificationParameter verificationsEmail = new VerificationParameter(fakerFields.get("verificationsReasonEmail"), fakerFields.get("verificationsStatusEmail"));
        VerificationParameter verificationsIdentity = new VerificationParameter(fakerFields.get("verificationsReasonIdentity"), fakerFields.get("verificationsStatusIdentity"));
        VerificationParameter verificationsLocation = new VerificationParameter(fakerFields.get("verificationsReasonLocation"), fakerFields.get("verificationsStatusLocation"));
        VerificationParameter verificationsPhone = new VerificationParameter(fakerFields.get("verificationsReasonPhone"), fakerFields.get("verificationsStatusPhone"));
        VerificationParameter verificationsTerms = new VerificationParameter(fakerFields.get("verificationsReasonTerms"), fakerFields.get("verificationsStatusTerms"));
        Verifications verifications = new Verifications(verificationsAddress, verificationsBirthdate, verificationsDocuments, verificationsEmail, verificationsIdentity, verificationsLocation, verificationsPhone, verificationsTerms);

        return new User(fakerFields.get("country"), currencies, fakerFields.get("email"), fakerFields.get("firstName"), fakerFields.get("lastName"), fakerFields.get("memberAt"), fakerFields.get("name"), settings, fakerFields.get("state"), fakerFields.get("status"), fakerFields.get("username"), verifications);
    }

}

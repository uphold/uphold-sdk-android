package com.uphold.uphold_android_sdk.test.integration.model;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import com.darylteo.rx.promises.java.Promise;
import com.darylteo.rx.promises.java.functions.RepromiseFunction;
import com.uphold.uphold_android_sdk.client.restadapter.UpholdRestAdapter;
import com.uphold.uphold_android_sdk.exception.LogicException;
import com.uphold.uphold_android_sdk.model.Transaction;
import com.uphold.uphold_android_sdk.model.transaction.Beneficiary;
import com.uphold.uphold_android_sdk.model.transaction.BeneficiaryAddress;
import com.uphold.uphold_android_sdk.model.transaction.TransactionCommitRequest;
import com.uphold.uphold_android_sdk.test.BuildConfig;
import com.uphold.uphold_android_sdk.test.util.Fixtures;
import com.uphold.uphold_android_sdk.test.util.MockRestAdapter;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

import retrofit.client.Header;
import retrofit.client.Request;

/**
 * Transaction integration tests.
 */

@RunWith(AndroidJUnit4.class)
@SmallTest
public class TransactionTest {

    @Test
    public void cancelShouldReturnALogicExceptionIfCardIdIsMissing() throws Exception {
        MockRestAdapter<Transaction> adapter = new MockRestAdapter<>(null, null);

        adapter.request(new RepromiseFunction<UpholdRestAdapter, Transaction>() {
            @Override
            public Promise<Transaction> call(UpholdRestAdapter adapter) {
                Transaction transaction = Fixtures.loadTransaction(new HashMap<String, String>() {{
                    put("destinationCardId", null);
                    put("originAccountId", null);
                    put("originCardId", null);
                }});

                transaction.setUpholdRestAdapter(adapter);

                return transaction.cancel();
            }
        });

        Exception exception = adapter.getException();

        Assert.assertEquals(exception.getClass().getName(), LogicException.class.getName());
        Assert.assertEquals(exception.getMessage(), "Origin CardId is missing from this transaction");
    }

    @Test
    public void cancelShouldReturnALogicExceptionIfDestinationCardIdIsMissingForDeposits() throws Exception {
        MockRestAdapter<Transaction> adapter = new MockRestAdapter<>(null, null);

        adapter.request(new RepromiseFunction<UpholdRestAdapter, Transaction>() {
            @Override
            public Promise<Transaction> call(UpholdRestAdapter adapter) {
                Transaction transaction = Fixtures.loadTransaction(new HashMap<String, String>() {{
                    put("destinationCardId", null);
                    put("transactionType", "deposit");
                }});

                transaction.setUpholdRestAdapter(adapter);

                return transaction.cancel();
            }
        });

        Exception exception = adapter.getException();

        Assert.assertEquals(exception.getClass().getName(), LogicException.class.getName());
        Assert.assertEquals(exception.getMessage(), "Destination CardId is missing from this deposit transaction");
    }

    @Test
    public void cancelShouldReturnALogicExceptionIfOriginAccountIdIsMissingForDeposits() throws Exception {
        MockRestAdapter<Transaction> adapter = new MockRestAdapter<>(null, null);

        adapter.request(new RepromiseFunction<UpholdRestAdapter, Transaction>() {
            @Override
            public Promise<Transaction> call(UpholdRestAdapter adapter) {
                Transaction transaction = Fixtures.loadTransaction(new HashMap<String, String>() {{
                    put("originAccountId", null);
                    put("transactionType", "deposit");
                }});

                transaction.setUpholdRestAdapter(adapter);

                return transaction.cancel();
            }
        });

        Exception exception = adapter.getException();

        Assert.assertEquals(exception.getClass().getName(), LogicException.class.getName());
        Assert.assertEquals(exception.getMessage(), "Origin AccountId is missing from this deposit transaction");
    }

    @Test
    public void cancelShouldReturnTheLogicExceptionUncommitedTransaction() throws Exception {
        MockRestAdapter<Transaction> adapter = new MockRestAdapter<>(null, null);

        adapter.request(new RepromiseFunction<UpholdRestAdapter, Transaction>() {
            @Override
            public Promise<Transaction> call(UpholdRestAdapter adapter) {
                Transaction transaction = Fixtures.loadTransaction(new HashMap<String, String>() {{
                    put("transactionStatus", "pending");
                }});

                transaction.setUpholdRestAdapter(adapter);

                return transaction.cancel();
            }
        });

        Exception exception = adapter.getException();

        Assert.assertEquals(exception.getClass().getName(), LogicException.class.getName());
        Assert.assertEquals(exception.getMessage(), "Unable to cancel uncommited transaction");
    }

    @Test
    public void cancelShouldReturnTheLogicExceptionWhenTransactionStatusIsNotWaiting() throws Exception {
        MockRestAdapter<Transaction> adapter = new MockRestAdapter<>(null, null);

        adapter.request(new RepromiseFunction<UpholdRestAdapter, Transaction>() {
            @Override
            public Promise<Transaction> call(UpholdRestAdapter adapter) {
                Transaction transaction = Fixtures.loadTransaction(new HashMap<String, String>() {{
                    put("transactionStatus", "complete");
                }});

                transaction.setUpholdRestAdapter(adapter);

                return transaction.cancel();
            }
        });

        Exception exception = adapter.getException();

        Assert.assertEquals(exception.getClass().getName(), LogicException.class.getName());
        Assert.assertEquals(exception.getMessage(), "This transaction cannot be cancelled, because the current status is complete");
    }

    @Test
    public void cancelShouldReturnTheTransaction() throws Exception{
        String responseString = "{ \"id\": \"foobar\" }";
        MockRestAdapter<Transaction> adapter = new MockRestAdapter<>(responseString, null);

        adapter.request(new RepromiseFunction<UpholdRestAdapter, Transaction>() {
            @Override
            public Promise<Transaction> call(UpholdRestAdapter adapter) {
                Transaction transaction = Fixtures.loadTransaction(new HashMap<String, String>() {{
                    put("transactionStatus", "waiting");
                    put("originCardId", "foo");
                    put("transactionId", "bar");
                }});

                transaction.setUpholdRestAdapter(adapter);

                return transaction.cancel();
            }
        });

        Transaction transaction = adapter.getResult();
        Request request = adapter.getRequest();

        Assert.assertEquals(request.getMethod(), "POST");
        Assert.assertEquals(request.getUrl(), String.format("%s/v0/me/cards/foo/transactions/bar/cancel", BuildConfig.API_SERVER_URL));
        Assert.assertEquals(transaction.getId(), "foobar");
    }

    @Test
    public void commitShouldReturnTheLogicExceptionCardIdIsMissing() throws Exception {
        MockRestAdapter<Transaction> adapter = new MockRestAdapter<>(null, null);

        adapter.request(new RepromiseFunction<UpholdRestAdapter, Transaction>() {
            @Override
            public Promise<Transaction> call(UpholdRestAdapter adapter) {
                Transaction transaction = Fixtures.loadTransaction(new HashMap<String, String>() {{
                    put("destinationCardId", null);
                    put("originAccountId", null);
                    put("originCardId", null);
                }});

                transaction.setUpholdRestAdapter(adapter);

                return transaction.commit(new TransactionCommitRequest("foobar"));
            }
        });

        Exception exception = adapter.getException();

        Assert.assertEquals(exception.getClass().getName(), LogicException.class.getName());
        Assert.assertEquals(exception.getMessage(), "Origin CardId is missing from this transaction");
    }

    @Test
    public void commitShouldReturnTheLogicExceptionIfDestinationCardIdIsMissingForDeposits() throws Exception {
        MockRestAdapter<Transaction> adapter = new MockRestAdapter<>(null, null);

        adapter.request(new RepromiseFunction<UpholdRestAdapter, Transaction>() {
            @Override
            public Promise<Transaction> call(UpholdRestAdapter adapter) {
                Transaction transaction = Fixtures.loadTransaction(new HashMap<String, String>() {{
                    put("destinationCardId", null);
                    put("transactionType", "deposit");
                }});

                transaction.setUpholdRestAdapter(adapter);

                return transaction.commit(new TransactionCommitRequest("foobar"));
            }
        });

        Exception exception = adapter.getException();

        Assert.assertEquals(exception.getClass().getName(), LogicException.class.getName());
        Assert.assertEquals(exception.getMessage(), "Destination CardId is missing from this deposit transaction");
    }

    @Test
    public void commitShouldReturnTheLogicExceptionIfOriginAccountIdIsMissingForDeposits() throws Exception {
        MockRestAdapter<Transaction> adapter = new MockRestAdapter<>(null, null);

        adapter.request(new RepromiseFunction<UpholdRestAdapter, Transaction>() {
            @Override
            public Promise<Transaction> call(UpholdRestAdapter adapter) {
                Transaction transaction = Fixtures.loadTransaction(new HashMap<String, String>() {{
                    put("originAccountId", null);
                    put("transactionType", "deposit");
                }});

                transaction.setUpholdRestAdapter(adapter);

                return transaction.commit(new TransactionCommitRequest("foobar"));
            }
        });

        Exception exception = adapter.getException();

        Assert.assertEquals(exception.getClass().getName(), LogicException.class.getName());
        Assert.assertEquals(exception.getMessage(), "Origin AccountId is missing from this deposit transaction");
    }

    @Test
    public void commitShouldReturnTheLogicExceptionTransactionCouldNotBeCommited() throws Exception {
        MockRestAdapter<Transaction> adapter = new MockRestAdapter<>(null, null);

        adapter.request(new RepromiseFunction<UpholdRestAdapter, Transaction>() {
            @Override
            public Promise<Transaction> call(UpholdRestAdapter adapter) {
                Transaction transaction = Fixtures.loadTransaction(new HashMap<String, String>() {{
                    put("transactionStatus", "completed");
                }});

                transaction.setUpholdRestAdapter(adapter);

                return transaction.commit(new TransactionCommitRequest("foobar"));
            }
        });

        Exception exception = adapter.getException();

        Assert.assertEquals(exception.getClass().getName(), LogicException.class.getName());
        Assert.assertEquals(exception.getMessage(), "This transaction cannot be committed, because the current status is completed");
    }

    @Test
    public void commitWithMessageShouldReturnTheTransaction() throws Exception {
        ByteArrayOutputStream bodyOutput = new ByteArrayOutputStream();
        String responseString = "{ \"id\": \"foobar\" }";

        MockRestAdapter<Transaction> adapter = new MockRestAdapter<>(responseString, null);

        adapter.request(new RepromiseFunction<UpholdRestAdapter, Transaction>() {
            @Override
            public Promise<Transaction> call(UpholdRestAdapter adapter) {
                Transaction transaction = Fixtures.loadTransaction(new HashMap<String, String>() {{
                    put("transactionStatus", "pending");
                    put("originCardId", "foo");
                    put("transactionId", "bar");
                }});

                transaction.setUpholdRestAdapter(adapter);

                return transaction.commit(new TransactionCommitRequest("foobar"));
            }
        });

        Header otpHeader = null;
        Request request = adapter.getRequest();
        Transaction transaction = adapter.getResult();

        for (Header header : request.getHeaders()) {
            if ("OTP-Token".equalsIgnoreCase(header.getName())) {
                otpHeader = header;

                break;
            }
        }

        request.getBody().writeTo(bodyOutput);

        Assert.assertEquals(request.getUrl(), String.format("%s/v0/me/cards/foo/transactions/bar/commit", BuildConfig.API_SERVER_URL));
        Assert.assertEquals(request.getMethod(), "POST");
        Assert.assertNull(otpHeader);
        Assert.assertEquals(transaction.getId(), "foobar");
        Assert.assertEquals(bodyOutput.toString(), "{\"message\":\"foobar\"}");
    }

    @Test
    public void commitWithMessageAndOTPShouldReturnTheTransaction() throws Exception {
        ByteArrayOutputStream bodyOutput = new ByteArrayOutputStream();
        String responseString = "{ \"id\": \"foobar\" }";

        MockRestAdapter<Transaction> adapter = new MockRestAdapter<>(responseString, null);

        adapter.request(new RepromiseFunction<UpholdRestAdapter, Transaction>() {
            @Override
            public Promise<Transaction> call(UpholdRestAdapter adapter) {
                Transaction transaction = Fixtures.loadTransaction(new HashMap<String, String>() {{
                    put("transactionStatus", "pending");
                    put("originCardId", "foo");
                    put("transactionId", "bar");
                }});

                transaction.setUpholdRestAdapter(adapter);

                return transaction.commit("otp", new TransactionCommitRequest("foobar"));
            }
        });

        Header otpHeader = null;
        Request request = adapter.getRequest();
        Transaction transaction = adapter.getResult();

        for (Header header : request.getHeaders()) {
            if ("OTP-Token".equalsIgnoreCase(header.getName())) {
                otpHeader = header;

                break;
            }
        }

        request.getBody().writeTo(bodyOutput);

        Assert.assertEquals(request.getUrl(), String.format("%s/v0/me/cards/foo/transactions/bar/commit", BuildConfig.API_SERVER_URL));
        Assert.assertEquals(request.getMethod(), "POST");
        Assert.assertEquals(otpHeader.getValue(), "otp");
        Assert.assertEquals(transaction.getId(), "foobar");
        Assert.assertEquals(bodyOutput.toString(), "{\"message\":\"foobar\"}");
    }

    @Test
    public void commitWithBeneficiaryShouldReturnTheTransaction() throws Exception {
        ByteArrayOutputStream bodyOutput = new ByteArrayOutputStream();
        String responseString = "{ \"id\": \"foobar\" }";

        MockRestAdapter<Transaction> adapter = new MockRestAdapter<>(responseString, null);

        adapter.request(new RepromiseFunction<UpholdRestAdapter, Transaction>() {
            @Override
            public Promise<Transaction> call(UpholdRestAdapter adapter) {
                Transaction transaction = Fixtures.loadTransaction(new HashMap<String, String>() {{
                    put("transactionStatus", "pending");
                    put("originCardId", "foo");
                    put("transactionId", "bar");
                }});

                transaction.setUpholdRestAdapter(adapter);

                BeneficiaryAddress address = new BeneficiaryAddress("faz", "fez", "fiz", "foz", "fuz", "foobiz");
                Beneficiary beneficiary = new Beneficiary(address, "buz", "fez");

                return transaction.commit(new TransactionCommitRequest(beneficiary));
            }
        });

        Header otpHeader = null;
        Request request = adapter.getRequest();
        Transaction transaction = adapter.getResult();

        for (Header header : request.getHeaders()) {
            if ("OTP-Token".equalsIgnoreCase(header.getName())) {
                otpHeader = header;

                break;
            }
        }

        request.getBody().writeTo(bodyOutput);

        Assert.assertEquals(request.getUrl(), String.format("%s/v0/me/cards/foo/transactions/bar/commit", BuildConfig.API_SERVER_URL));
        Assert.assertEquals(request.getMethod(), "POST");
        Assert.assertNull(otpHeader);
        Assert.assertEquals(transaction.getId(), "foobar");
        Assert.assertEquals(bodyOutput.toString(), "{\"beneficiary\":{\"address\":{\"city\":\"faz\",\"country\":\"fez\",\"line1\":\"fiz\",\"line2\":\"foz\",\"state\":\"fuz\",\"zipCode\":\"foobiz\"},\"name\":\"buz\",\"relationship\":\"fez\"}}");
    }

    @Test
    public void commitWithMessageAndBeneficiaryShouldReturnTheTransaction() throws Exception {
        ByteArrayOutputStream bodyOutput = new ByteArrayOutputStream();
        String responseString = "{ \"id\": \"foobar\" }";

        MockRestAdapter<Transaction> adapter = new MockRestAdapter<>(responseString, null);

        adapter.request(new RepromiseFunction<UpholdRestAdapter, Transaction>() {
            @Override
            public Promise<Transaction> call(UpholdRestAdapter adapter) {
                Transaction transaction = Fixtures.loadTransaction(new HashMap<String, String>() {{
                    put("transactionStatus", "pending");
                    put("originCardId", "foo");
                    put("transactionId", "bar");
                }});

                transaction.setUpholdRestAdapter(adapter);

                BeneficiaryAddress address = new BeneficiaryAddress("faz", "fez", "fiz", "foz", "fuz", "foobiz");
                Beneficiary beneficiary = new Beneficiary(address, "buz", "fez");

                return transaction.commit(new TransactionCommitRequest(beneficiary, "foobar"));
            }
        });

        Header otpHeader = null;
        Request request = adapter.getRequest();
        Transaction transaction = adapter.getResult();

        for (Header header : request.getHeaders()) {
            if ("OTP-Token".equalsIgnoreCase(header.getName())) {
                otpHeader = header;

                break;
            }
        }

        request.getBody().writeTo(bodyOutput);

        Assert.assertEquals(request.getUrl(), String.format("%s/v0/me/cards/foo/transactions/bar/commit", BuildConfig.API_SERVER_URL));
        Assert.assertEquals(request.getMethod(), "POST");
        Assert.assertNull(otpHeader);
        Assert.assertEquals(transaction.getId(), "foobar");
        Assert.assertEquals(bodyOutput.toString(), "{\"beneficiary\":{\"address\":{\"city\":\"faz\",\"country\":\"fez\",\"line1\":\"fiz\",\"line2\":\"foz\",\"state\":\"fuz\",\"zipCode\":\"foobiz\"},\"name\":\"buz\",\"relationship\":\"fez\"},\"message\":\"foobar\"}");
    }

    @Test
    public void commitWithMessageAndSecurityCodeShouldReturnTheTransaction() throws Exception {
        ByteArrayOutputStream bodyOutput = new ByteArrayOutputStream();
        String responseString = "{ \"id\": \"foobar\" }";

        MockRestAdapter<Transaction> adapter = new MockRestAdapter<>(responseString, null);

        adapter.request(new RepromiseFunction<UpholdRestAdapter, Transaction>() {
            @Override
            public Promise<Transaction> call(UpholdRestAdapter adapter) {
                Transaction transaction = Fixtures.loadTransaction(new HashMap<String, String>() {{
                    put("transactionStatus", "pending");
                    put("originCardId", "foo");
                    put("transactionId", "bar");
                }});

                transaction.setUpholdRestAdapter(adapter);

                return transaction.commit(new TransactionCommitRequest("foobar", "foo"));
            }
        });

        Header otpHeader = null;
        Request request = adapter.getRequest();
        Transaction transaction = adapter.getResult();

        for (Header header : request.getHeaders()) {
            if ("OTP-Token".equalsIgnoreCase(header.getName())) {
                otpHeader = header;

                break;
            }
        }

        request.getBody().writeTo(bodyOutput);

        Assert.assertEquals(request.getUrl(), String.format("%s/v0/me/cards/foo/transactions/bar/commit", BuildConfig.API_SERVER_URL));
        Assert.assertEquals(request.getMethod(), "POST");
        Assert.assertNull(otpHeader);
        Assert.assertEquals(transaction.getId(), "foobar");
        Assert.assertEquals(bodyOutput.toString(), "{\"message\":\"foobar\",\"securityCode\":\"foo\"}");
    }

    @Test
    public void commitWithOTPShouldReturnTheTransaction() throws Exception {
        ByteArrayOutputStream bodyOutput = new ByteArrayOutputStream();
        String responseString = "{ \"id\": \"foobar\" }";

        MockRestAdapter<Transaction> adapter = new MockRestAdapter<>(responseString, null);

        adapter.request(new RepromiseFunction<UpholdRestAdapter, Transaction>() {
            @Override
            public Promise<Transaction> call(UpholdRestAdapter adapter) {
                Transaction transaction = Fixtures.loadTransaction(new HashMap<String, String>() {{
                    put("transactionStatus", "pending");
                    put("originCardId", "foo");
                    put("transactionId", "bar");
                }});

                transaction.setUpholdRestAdapter(adapter);

                return transaction.commit("otp");
            }
        });

        Header otpHeader = null;
        Request request = adapter.getRequest();
        Transaction transaction = adapter.getResult();

        for (Header header : request.getHeaders()) {
            if ("OTP-Token".equalsIgnoreCase(header.getName())) {
                otpHeader = header;

                break;
            }
        }

        request.getBody().writeTo(bodyOutput);

        Assert.assertEquals(request.getUrl(), String.format("%s/v0/me/cards/foo/transactions/bar/commit", BuildConfig.API_SERVER_URL));
        Assert.assertEquals(request.getMethod(), "POST");
        Assert.assertEquals(otpHeader.getValue(), "otp");
        Assert.assertEquals(transaction.getId(), "foobar");
        Assert.assertEquals(bodyOutput.toString(), "{}");
    }

    @Test
    public void commitShouldReturnTheTransaction() throws Exception {
        ByteArrayOutputStream bodyOutput = new ByteArrayOutputStream();
        String responseString = "{ \"id\": \"foobar\" }";

        MockRestAdapter<Transaction> adapter = new MockRestAdapter<>(responseString, null);

        adapter.request(new RepromiseFunction<UpholdRestAdapter, Transaction>() {
            @Override
            public Promise<Transaction> call(UpholdRestAdapter adapter) {
                Transaction transaction = Fixtures.loadTransaction(new HashMap<String, String>() {{
                    put("originCardId", "foo");
                    put("transactionId", "bar");
                    put("transactionStatus", "pending");
                }});

                transaction.setUpholdRestAdapter(adapter);

                return transaction.commit();
            }
        });

        Header otpHeader = null;
        Request request = adapter.getRequest();
        Transaction transaction = adapter.getResult();

        for (Header header : request.getHeaders()) {
            if ("OTP-Token".equalsIgnoreCase(header.getName())) {
                otpHeader = header;

                break;
            }
        }

        request.getBody().writeTo(bodyOutput);

        Assert.assertEquals(request.getUrl(), String.format("%s/v0/me/cards/foo/transactions/bar/commit", BuildConfig.API_SERVER_URL));
        Assert.assertEquals(request.getMethod(), "POST");
        Assert.assertNull(otpHeader);
        Assert.assertEquals(transaction.getId(), "foobar");
        Assert.assertEquals(bodyOutput.toString(), "{}");
    }

    @Test
    public void getIdShouldReturnTheId() {
        Transaction transaction = Fixtures.loadTransaction(new HashMap<String, String>() {{
            put("transactionId", "foobar");
        }});

        Assert.assertEquals(transaction.getId(), "foobar");
    }

    @Test
    public void getCreatedAtShouldReturnTheCreatedAt() {
        Transaction transaction = Fixtures.loadTransaction(new HashMap<String, String>() {{
            put("transactionCreatedAt", "foobar");
        }});

        Assert.assertEquals(transaction.getCreatedAt(), "foobar");
    }

    @Test
    public void getDenominationShouldReturnTheDenomination() {
        Transaction transaction = Fixtures.loadTransaction(new HashMap<String, String>() {{
            put("denominationAmount", "foo");
            put("denominationCurrency", "bar");
            put("denominationPair", "fuz");
            put("denominationRate", "buz");
        }});

        Assert.assertEquals(transaction.getDenomination().getAmount(), "foo");
        Assert.assertEquals(transaction.getDenomination().getCurrency(), "bar");
        Assert.assertEquals(transaction.getDenomination().getPair(), "fuz");
        Assert.assertEquals(transaction.getDenomination().getRate(), "buz");
    }

    @Test
    public void getDestinationShouldReturnTheDestination() {
        Transaction transaction = Fixtures.loadTransaction(new HashMap<String, String>() {{
            put("destinationCardId", "foobar");
            put("destinationAmount", "foo");
            put("destinationBase", "fuz");
            put("destinationCommission", "fiz");
            put("destinationCurrency", "bar");
            put("destinationDescription", "biz");
            put("destinationFee", "foobuz");
            put("destinationMerchantCity", "bar");
            put("destinationMerchantCountry", "foo");
            put("destinationMerchantName", "buz");
            put("destinationMerchantState", "fiz");
            put("destinationMerchantZipCode", "biz");
            put("destinationNodeBrand", "foz");
            put("destinationNodeId", "fez");
            put("destinationNodeType", "faz");
            put("destinationRate", "buz");
            put("destinationType", "fuzbiz");
            put("destinationUsername", "fizbuz");
        }});

        Assert.assertEquals(transaction.getDestination().getCardId(), "foobar");
        Assert.assertEquals(transaction.getDestination().getAmount(), "foo");
        Assert.assertEquals(transaction.getDestination().getBase(), "fuz");
        Assert.assertEquals(transaction.getDestination().getCommission(), "fiz");
        Assert.assertEquals(transaction.getDestination().getCurrency(), "bar");
        Assert.assertEquals(transaction.getDestination().getDescription(), "biz");
        Assert.assertEquals(transaction.getDestination().getFee(), "foobuz");
        Assert.assertEquals(transaction.getDestination().getMerchant().getCity(), "bar");
        Assert.assertEquals(transaction.getDestination().getMerchant().getCountry(), "foo");
        Assert.assertEquals(transaction.getDestination().getMerchant().getName(), "buz");
        Assert.assertEquals(transaction.getDestination().getMerchant().getState(), "fiz");
        Assert.assertEquals(transaction.getDestination().getMerchant().getZipCode(), "biz");
        Assert.assertEquals(transaction.getDestination().getNode().getBrand(), "foz");
        Assert.assertEquals(transaction.getDestination().getNode().getId(), "fez");
        Assert.assertEquals(transaction.getDestination().getNode().getType(), "faz");
        Assert.assertEquals(transaction.getDestination().getRate(), "buz");
        Assert.assertEquals(transaction.getDestination().getType(), "fuzbiz");
        Assert.assertEquals(transaction.getDestination().getUsername(), "fizbuz");
    }

    @Test
    public void getMessageShouldReturnTheMessage() {
        Transaction transaction = Fixtures.loadTransaction(new HashMap<String, String>() {{
            put("transactionMessage", "foobar");
        }});

        Assert.assertEquals(transaction.getMessage(), "foobar");
    }

    @Test
    public void getNormalizedShouldReturnTheNormalized() {
        Transaction transaction = Fixtures.loadTransaction(new HashMap<String, String>() {{
            put("normalizedAmount", "foo");
            put("normalizedCommission", "bar");
            put("normalizedCurrency", "foobar");
            put("normalizedFee", "foobiz");
            put("normalizedRate", "buz");
        }});

        Assert.assertEquals(transaction.getNormalized().get(0).getAmount(), "foo");
        Assert.assertEquals(transaction.getNormalized().get(0).getCommission(), "bar");
        Assert.assertEquals(transaction.getNormalized().get(0).getCurrency(), "foobar");
        Assert.assertEquals(transaction.getNormalized().get(0).getFee(), "foobiz");
        Assert.assertEquals(transaction.getNormalized().get(0).getRate(), "buz");
    }

    @Test
    public void getOriginShouldReturnTheOrigin() {
        Transaction transaction = Fixtures.loadTransaction(new HashMap<String, String>() {{
            put("originCardId", "foobar");
            put("originAmount", "foo");
            put("originBase", "bar");
            put("originCommission", "fiz");
            put("originCurrency", "bar");
            put("originDescription", "biz");
            put("originFee", "foobuz");
            put("originMerchantCity", "bar");
            put("originMerchantCountry", "foo");
            put("originMerchantName", "buz");
            put("originMerchantState", "fiz");
            put("originMerchantZipCode", "biz");
            put("originNodeBrand", "foz");
            put("originNodeId", "fez");
            put("originNodeType", "faz");
            put("originRate", "buz");
            put("originSourcesAmount", "fuzbiz,fizbuz");
            put("originSourcesId", "foo,bar");
            put("originType", "fuzbiz");
            put("originUsername", "fizbuz");
        }});

        Assert.assertEquals(transaction.getOrigin().getAmount(), "foo");
        Assert.assertEquals(transaction.getOrigin().getBase(), "bar");
        Assert.assertEquals(transaction.getOrigin().getCardId(), "foobar");
        Assert.assertEquals(transaction.getOrigin().getCommission(), "fiz");
        Assert.assertEquals(transaction.getOrigin().getCurrency(), "bar");
        Assert.assertEquals(transaction.getOrigin().getDescription(), "biz");
        Assert.assertEquals(transaction.getOrigin().getFee(), "foobuz");
        Assert.assertEquals(transaction.getOrigin().getMerchant().getCity(), "bar");
        Assert.assertEquals(transaction.getOrigin().getMerchant().getCountry(), "foo");
        Assert.assertEquals(transaction.getOrigin().getMerchant().getName(), "buz");
        Assert.assertEquals(transaction.getOrigin().getMerchant().getState(), "fiz");
        Assert.assertEquals(transaction.getOrigin().getMerchant().getZipCode(), "biz");
        Assert.assertEquals(transaction.getOrigin().getNode().getBrand(), "foz");
        Assert.assertEquals(transaction.getOrigin().getNode().getId(), "fez");
        Assert.assertEquals(transaction.getOrigin().getNode().getType(), "faz");
        Assert.assertEquals(transaction.getOrigin().getRate(), "buz");
        Assert.assertEquals(transaction.getOrigin().getSources().size(), 2);
        Assert.assertEquals(transaction.getOrigin().getSources().get(0).getAmount(), "fuzbiz");
        Assert.assertEquals(transaction.getOrigin().getSources().get(0).getId(), "foo");
        Assert.assertEquals(transaction.getOrigin().getSources().get(1).getAmount(), "fizbuz");
        Assert.assertEquals(transaction.getOrigin().getSources().get(1).getId(), "bar");
        Assert.assertEquals(transaction.getOrigin().getType(), "fuzbiz");
        Assert.assertEquals(transaction.getOrigin().getUsername(), "fizbuz");
    }

    @Test
    public void getParamsShouldReturnTheParameters() {
        Transaction transaction = Fixtures.loadTransaction(new HashMap<String, String>() {{
            put("parametersCurrency", "bar");
            put("parametersMargin", "foobiz");
            put("parametersPair", "fuz");
            put("parametersProgress", "fiz");
            put("parametersRate", "bar");
            put("parametersRefunds", "biz");
            put("parametersTtl", "1");
            put("parametersTxid", "foobuz");
            put("parametersType", "buz");
        }});

        Assert.assertEquals(transaction.getParams().getCurrency(), "bar");
        Assert.assertEquals(transaction.getParams().getMargin(), "foobiz");
        Assert.assertEquals(transaction.getParams().getPair(), "fuz");
        Assert.assertEquals(transaction.getParams().getProgress(), "fiz");
        Assert.assertEquals(transaction.getParams().getRate(), "bar");
        Assert.assertEquals(transaction.getParams().getRefunds(), "biz");
        Assert.assertEquals(transaction.getParams().getTtl(), Integer.valueOf(1));
        Assert.assertEquals(transaction.getParams().getTxid(), "foobuz");
        Assert.assertEquals(transaction.getParams().getType(), "buz");
    }

    @Test
    public void getRefundedByIdShouldReturnTheRefundedById() {
        Transaction transaction = Fixtures.loadTransaction(new HashMap<String, String>() {{
            put("transactionRefundedById", "foobuz");
        }});

        Assert.assertEquals(transaction.getRefundedById(), "foobuz");
    }

    @Test
    public void getStatusShouldReturnTheStatus() {
        Transaction transaction = Fixtures.loadTransaction(new HashMap<String, String>() {{
            put("transactionStatus", "foobar");
        }});

        Assert.assertEquals(transaction.getStatus(), "foobar");
    }

    @Test
    public void getTypeShouldReturnTheType() {
        Transaction transaction = Fixtures.loadTransaction(new HashMap<String, String>() {{
            put("transactionType", "foo");
        }});

        Assert.assertEquals(transaction.getType(), "foo");
    }

}

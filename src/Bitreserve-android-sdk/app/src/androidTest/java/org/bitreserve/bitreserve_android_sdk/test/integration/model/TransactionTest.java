package org.bitreserve.bitreserve_android_sdk.test.integration.model;


import com.darylteo.rx.promises.java.Promise;
import com.darylteo.rx.promises.java.functions.RepromiseFunction;

import junit.framework.Assert;

import org.bitreserve.bitreserve_android_sdk.client.restadapter.BitreserveRestAdapter;
import org.bitreserve.bitreserve_android_sdk.exception.LogicException;
import org.bitreserve.bitreserve_android_sdk.model.Transaction;
import org.bitreserve.bitreserve_android_sdk.model.transaction.TransactionCommitRequest;
import org.bitreserve.bitreserve_android_sdk.test.util.Fixtures;
import org.bitreserve.bitreserve_android_sdk.test.util.MockRestAdapter;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

import retrofit.client.Request;

/**
 * Transaction integration tests.
 */

@RunWith(AndroidJUnit4.class)
@SmallTest
public class TransactionTest {

    @Test
    public void cancelShouldReturnALogicExceptionIfCardIdIsMissing() throws Exception {
        MockRestAdapter<Transaction> adapter = new MockRestAdapter<>("foobar", null, null);

        adapter.request(new RepromiseFunction<BitreserveRestAdapter, Transaction>() {
            @Override
            public Promise<Transaction> call(BitreserveRestAdapter adapter) {
                Transaction transaction = Fixtures.loadTransaction(new HashMap<String, String>() {{
                    put("originCardId", null);
                }});

                transaction.setBitreserveRestAdapter(adapter);

                return transaction.cancel();
            }
        });

        Exception exception = adapter.getException();

        Assert.assertEquals(exception.getClass().getName(), LogicException.class.getName());
        Assert.assertEquals(exception.getMessage(), "Origin CardId is missing from this transaction");
    }

    @Test
    public void cancelShouldReturnTheLogicExceptionUncommitedTransaction() throws Exception {
        MockRestAdapter<Transaction> adapter = new MockRestAdapter<>("foobar", null, null);

        adapter.request(new RepromiseFunction<BitreserveRestAdapter, Transaction>() {
            @Override
            public Promise<Transaction> call(BitreserveRestAdapter adapter) {
                Transaction transaction = Fixtures.loadTransaction(new HashMap<String, String>() {{
                    put("transactionStatus", "pending");
                }});

                transaction.setBitreserveRestAdapter(adapter);

                return transaction.cancel();
            }
        });

        Exception exception = adapter.getException();

        Assert.assertEquals(exception.getClass().getName(), LogicException.class.getName());
        Assert.assertEquals(exception.getMessage(), "Unable to cancel uncommited transaction");
    }

    @Test
    public void cancelShouldReturnTheLogicExceptionWhenTransactionStatusIsNotWaiting() throws Exception {
        MockRestAdapter<Transaction> adapter = new MockRestAdapter<>("foobar", null, null);

        adapter.request(new RepromiseFunction<BitreserveRestAdapter, Transaction>() {
            @Override
            public Promise<Transaction> call(BitreserveRestAdapter adapter) {
                Transaction transaction = Fixtures.loadTransaction(new HashMap<String, String>() {{
                    put("transactionStatus", "complete");
                }});

                transaction.setBitreserveRestAdapter(adapter);

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
        MockRestAdapter<Transaction> adapter = new MockRestAdapter<>("foobar", responseString, null);

        adapter.request(new RepromiseFunction<BitreserveRestAdapter, Transaction>() {
            @Override
            public Promise<Transaction> call(BitreserveRestAdapter adapter) {
                Transaction transaction = Fixtures.loadTransaction(new HashMap<String, String>() {{
                    put("transactionStatus", "waiting");
                    put("originCardId", "foo");
                    put("transactionId", "bar");
                }});

                transaction.setBitreserveRestAdapter(adapter);

                return transaction.cancel();
            }
        });

        Transaction transaction = adapter.getResult();
        Request request = adapter.getRequest();

        Assert.assertEquals(request.getMethod(), "POST");
        Assert.assertEquals(request.getUrl(), "https://api.bitreserve.org/v0/me/cards/foo/transactions/bar/cancel");
        Assert.assertEquals(transaction.getId(), "foobar");
    }

    @Test
    public void commitShouldReturnTheLogicExceptionCardIdIsMissing() throws Exception {
        MockRestAdapter<Transaction> adapter = new MockRestAdapter<>("foobar", null, null);

        adapter.request(new RepromiseFunction<BitreserveRestAdapter, Transaction>() {
            @Override
            public Promise<Transaction> call(BitreserveRestAdapter adapter) {
                Transaction transaction = Fixtures.loadTransaction(new HashMap<String, String>() {{
                    put("originCardId", null);
                }});

                transaction.setBitreserveRestAdapter(adapter);

                return transaction.commit(new TransactionCommitRequest("foobar"));
            }
        });

        Exception exception = adapter.getException();

        Assert.assertEquals(exception.getClass().getName(), LogicException.class.getName());
        Assert.assertEquals(exception.getMessage(), "Origin CardId is missing from this transaction");
    }

    @Test
    public void commitShouldReturnTheLogicExceptionTransactionCouldNotBeCommited() throws Exception {
        MockRestAdapter<Transaction> adapter = new MockRestAdapter<>("foobar", null, null);

        adapter.request(new RepromiseFunction<BitreserveRestAdapter, Transaction>() {
            @Override
            public Promise<Transaction> call(BitreserveRestAdapter adapter) {
                Transaction transaction = Fixtures.loadTransaction(new HashMap<String, String>() {{
                    put("transactionStatus", "completed");
                }});

                transaction.setBitreserveRestAdapter(adapter);

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

        MockRestAdapter<Transaction> adapter = new MockRestAdapter<>("foobar", responseString, null);

        adapter.request(new RepromiseFunction<BitreserveRestAdapter, Transaction>() {
            @Override
            public Promise<Transaction> call(BitreserveRestAdapter adapter) {
                Transaction transaction = Fixtures.loadTransaction(new HashMap<String, String>() {{
                    put("transactionStatus", "pending");
                    put("originCardId", "foo");
                    put("transactionId", "bar");
                }});

                transaction.setBitreserveRestAdapter(adapter);

                return transaction.commit(new TransactionCommitRequest("foobar"));
            }
        });

        Request request = adapter.getRequest();
        Transaction transaction = adapter.getResult();

        request.getBody().writeTo(bodyOutput);

        Assert.assertEquals(request.getUrl(), "https://api.bitreserve.org/v0/me/cards/foo/transactions/bar/commit");
        Assert.assertEquals(request.getMethod(), "POST");
        Assert.assertEquals(transaction.getId(), "foobar");
        Assert.assertEquals(bodyOutput.toString(), "{\"message\":\"foobar\"}");
    }

    @Test
    public void commitShouldReturnTheTransaction() throws Exception {
        ByteArrayOutputStream bodyOutput = new ByteArrayOutputStream();
        String responseString = "{ \"id\": \"foobar\" }";

        MockRestAdapter<Transaction> adapter = new MockRestAdapter<>("foobar", responseString, null);

        adapter.request(new RepromiseFunction<BitreserveRestAdapter, Transaction>() {
            @Override
            public Promise<Transaction> call(BitreserveRestAdapter adapter) {
                Transaction transaction = Fixtures.loadTransaction(new HashMap<String, String>() {{
                    put("originCardId", "foo");
                    put("transactionId", "bar");
                    put("transactionStatus", "pending");
                }});

                transaction.setBitreserveRestAdapter(adapter);

                return transaction.commit();
            }
        });

        Request request = adapter.getRequest();
        Transaction transaction = adapter.getResult();

        request.getBody().writeTo(bodyOutput);

        Assert.assertEquals(request.getUrl(), "https://api.bitreserve.org/v0/me/cards/foo/transactions/bar/commit");
        Assert.assertEquals(request.getMethod(), "POST");
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
    public void getOriginShouldReturnTheOrigin() {
        Transaction transaction = Fixtures.loadTransaction(new HashMap<String, String>() {{
            put("originCardId", "foobar");
            put("originAmount", "foo");
            put("originBase", "bar");
            put("originCommission", "fiz");
            put("originCurrency", "bar");
            put("originDescription", "biz");
            put("originFee", "foobuz");
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
            put("parametersTtl", "1");
            put("parametersTxid", "foobuz");
            put("parametersType", "buz");
        }});

        Assert.assertEquals(transaction.getParams().getCurrency(), "bar");
        Assert.assertEquals(transaction.getParams().getMargin(), "foobiz");
        Assert.assertEquals(transaction.getParams().getPair(), "fuz");
        Assert.assertEquals(transaction.getParams().getProgress(), "fiz");
        Assert.assertEquals(transaction.getParams().getRate(), "bar");
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

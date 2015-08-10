package org.bitreserve.bitreserve_android_sdk.test.integration.model;

import com.darylteo.rx.promises.java.Promise;
import com.darylteo.rx.promises.java.functions.RepromiseFunction;

import junit.framework.Assert;

import org.bitreserve.bitreserve_android_sdk.client.restadapter.BitreserveRestAdapter;
import org.bitreserve.bitreserve_android_sdk.model.Reserve;
import org.bitreserve.bitreserve_android_sdk.model.Transaction;
import org.bitreserve.bitreserve_android_sdk.model.reserve.Deposit;
import org.bitreserve.bitreserve_android_sdk.model.reserve.ReserveStatistics;
import org.bitreserve.bitreserve_android_sdk.paginator.Paginator;
import org.bitreserve.bitreserve_android_sdk.test.BuildConfig;
import org.bitreserve.bitreserve_android_sdk.test.util.MockRestAdapter;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import java.lang.String;
import java.util.HashMap;
import java.util.List;

import retrofit.client.Request;

/**
 * Reserve integration tests.
 */

@RunWith(AndroidJUnit4.class)
@SmallTest
public class ReserveTest {

    @Test
    public void getLedgerShouldReturnThePaginatorCount() throws Exception {
        String responseString = "[ { \"type\": \"asset\" }, { \"type\": \"liability\" } ]";
        MockRestAdapter<Integer> adapter = new MockRestAdapter<>("foobar", responseString, new HashMap<String, String>() {{
            put("Content-Range", "0-2/60");
        }});

        adapter.request(new RepromiseFunction<BitreserveRestAdapter, Integer>() {
            @Override
            public Promise<Integer> call(BitreserveRestAdapter adapter) {
                Reserve reserve = new Reserve();

                reserve.setBitreserveRestAdapter(adapter);

                final Paginator<Deposit> paginator = reserve.getLedger();

                return paginator.getElements().then(new RepromiseFunction<List<Deposit>, Integer>() {
                    @Override
                    public Promise<Integer> call(List<Deposit> transactions) {
                        return paginator.count();
                    }
                });
            }
        });

        Integer count = adapter.getResult();
        Request request = adapter.getRequest();

        Assert.assertEquals(request.getMethod(), "GET");
        Assert.assertEquals(request.getUrl(), String.format("%s/v0/reserve/ledger", BuildConfig.API_SERVER_URL));
        Assert.assertEquals(request.getHeaders().get(0).getName(), "Range");
        Assert.assertEquals(request.getHeaders().get(0).getValue(), "items=0-1");
        Assert.assertEquals(count, Integer.valueOf(60));
    }

    @Test
    public void getLedgerShouldReturnThePaginatorHasNext() throws Exception {
        String responseString = "[ { \"type\": \"asset\" }, { \"type\": \"liability\" } ]";
        MockRestAdapter<Boolean> adapter = new MockRestAdapter<>("foobar", responseString, new HashMap<String, String>() {{
            put("Content-Range", "0-49/51");
        }});

        adapter.request(new RepromiseFunction<BitreserveRestAdapter, Boolean>() {
            @Override
            public Promise<Boolean> call(BitreserveRestAdapter adapter) {
                Reserve reserve = new Reserve();

                reserve.setBitreserveRestAdapter(adapter);

                final Paginator<Deposit> paginator = reserve.getLedger();

                return paginator.getElements().then(new RepromiseFunction<List<Deposit>, Boolean>() {
                    @Override
                    public Promise<Boolean> call(List<Deposit> transactions) {
                        return paginator.hasNext();
                    }
                });
            }
        });

        Boolean hasNext = adapter.getResult();
        Request request = adapter.getRequest();

        Assert.assertEquals(request.getMethod(), "GET");
        Assert.assertEquals(request.getUrl(), String.format("%s/v0/reserve/ledger", BuildConfig.API_SERVER_URL));
        Assert.assertEquals(request.getHeaders().get(0).getName(), "Range");
        Assert.assertEquals(request.getHeaders().get(0).getValue(), "items=0-1");
        Assert.assertTrue(hasNext);
    }

    @Test
    public void getLedgerShouldReturnTheListWithDeposits() throws Exception {
        String responseString = "[{" +
            "\"type\": \"foo\"," +
            "\"out\": {" +
                "\"amount\": \"foobar\"," +
                "\"currency\": \"fuz\"" +
            "}," +
            "\"in\": {" +
                "\"amount\": \"foobiz\"," +
                "\"currency\": \"fiz\"" +
            "}," +
            "\"createdAt\": \"2015-04-20T14:57:12.398Z\"" +
        "},{" +
            "\"type\": \"bar\"," +
            "\"out\": {" +
                "\"amount\": \"foobiz\"," +
                "\"currency\": \"buz\"" +
            "}," +
            "\"in\": {" +
                "\"amount\": \"foobar\"," +
                "\"currency\": \"biz\"" +
            "}," +
            "\"TransactionId\": \"foobar\"," +
            "\"createdAt\": \"2015-04-20T14:57:12.398Z\"" +
        "}]";
        MockRestAdapter<List<Deposit>> adapter = new MockRestAdapter<>("foobar", responseString, null);

        adapter.request(new RepromiseFunction<BitreserveRestAdapter, List<Deposit>>() {
            @Override
            public Promise<List<Deposit>> call(BitreserveRestAdapter adapter) {
                Reserve reserve = new Reserve();

                reserve.setBitreserveRestAdapter(adapter);

                final Paginator<Deposit> paginator = reserve.getLedger();

                return paginator.getElements().then(new RepromiseFunction<List<Deposit>, List<Deposit>>() {
                    @Override
                    public Promise<List<Deposit>> call(List<Deposit> transactions) {
                        return paginator.getElements();
                    }
                });
            }
        });

        List<Deposit> deposits = adapter.getResult();
        Request request = adapter.getRequest();

        Assert.assertEquals(request.getMethod(), "GET");
        Assert.assertEquals(request.getUrl(), String.format("%s/v0/reserve/ledger", BuildConfig.API_SERVER_URL));
        Assert.assertEquals(request.getHeaders().get(0).getName(), "Range");
        Assert.assertEquals(request.getHeaders().get(0).getValue(), "items=0-49");
        Assert.assertEquals(deposits.size(), 2);
        Assert.assertEquals(deposits.get(0).getCreatedAt(), "2015-04-20T14:57:12.398Z");
        Assert.assertEquals(deposits.get(0).getIn().getAmount(), "foobiz");
        Assert.assertEquals(deposits.get(0).getIn().getCurrency(), "fiz");
        Assert.assertEquals(deposits.get(0).getOut().getAmount(), "foobar");
        Assert.assertEquals(deposits.get(0).getOut().getCurrency(), "fuz");
        Assert.assertNull(deposits.get(0).getTransactionId());
        Assert.assertEquals(deposits.get(0).getType(), "foo");
        Assert.assertEquals(deposits.get(1).getCreatedAt(), "2015-04-20T14:57:12.398Z");
        Assert.assertEquals(deposits.get(1).getIn().getAmount(), "foobar");
        Assert.assertEquals(deposits.get(1).getIn().getCurrency(), "biz");
        Assert.assertEquals(deposits.get(1).getOut().getAmount(), "foobiz");
        Assert.assertEquals(deposits.get(1).getOut().getCurrency(), "buz");
        Assert.assertEquals(deposits.get(1).getTransactionId(), "foobar");
        Assert.assertEquals(deposits.get(1).getType(), "bar");
    }

    @Test
    public void getLedgerShouldReturnThePaginatorNextPage() throws Exception {
        String responseString = "[ { \"type\": \"asset\" }, { \"type\": \"liability\" } ]";
        MockRestAdapter<List<Deposit>> adapter = new MockRestAdapter<>("foobar", responseString, null);

        adapter.request(new RepromiseFunction<BitreserveRestAdapter, List<Deposit>>() {
            @Override
            public Promise<List<Deposit>> call(BitreserveRestAdapter adapter) {
                Reserve reserve = new Reserve();

                reserve.setBitreserveRestAdapter(adapter);

                final Paginator<Deposit> paginator = reserve.getLedger();

                return paginator.getElements().then(new RepromiseFunction<List<Deposit>, List<Deposit>>() {
                    @Override
                    public Promise<List<Deposit>> call(List<Deposit> transactions) {
                        return paginator.getNext();
                    }
                });
            }
        });

        Request request = adapter.getRequest();

        Assert.assertEquals(request.getMethod(), "GET");
        Assert.assertEquals(request.getUrl(), String.format("%s/v0/reserve/ledger", BuildConfig.API_SERVER_URL));
        Assert.assertEquals(request.getHeaders().get(0).getName(), "Range");
        Assert.assertEquals(request.getHeaders().get(0).getValue(), "items=50-99");
    }

    @Test
    public void getStatisticsShouldReturnTheListWithReserveStatistics() throws Exception {
        String responseString = "[{" +
            "\"currency\": \"FOO\"," +
            "\"values\": [{" +
                "\"assets\": \"foobar\"," +
                "\"currency\": \"foo\"," +
                "\"liabilities\": \"bar\"," +
                "\"rate\": \"biz\"" +
            "}]," +
            "\"totals\": {" +
                "\"commissions\": \"foo\"," +
                "\"transactions\": \"bar\"," +
                "\"assets\": \"foobar\"," +
                "\"liabilities\": \"foobiz\"" +
            "}" +
        "}, {" +
            "\"currency\": \"BAR\"," +
            "\"values\": [{" +
                "\"assets\": \"foobiz\"," +
                "\"currency\": \"biz\"," +
                "\"liabilities\": \"buz\"," +
                "\"rate\": \"foo\"" +
            "}]," +
            "\"totals\": {" +
                "\"commissions\": \"fiz\"," +
                "\"transactions\": \"biz\"," +
                "\"assets\": \"fuz\"," +
                "\"liabilities\": \"buz\"" +
            "}" +
        "}]";
        MockRestAdapter<List<ReserveStatistics>> adapter = new MockRestAdapter<>("foobar", responseString, null);

        adapter.request(new RepromiseFunction<BitreserveRestAdapter, List<ReserveStatistics>>() {
            @Override
            public Promise<List<ReserveStatistics>> call(BitreserveRestAdapter adapter) {
                Reserve reserve = new Reserve();

                reserve.setBitreserveRestAdapter(adapter);

                return reserve.getStatistics();
            }
        });

        Request request = adapter.getRequest();
        List<ReserveStatistics> statistics = adapter.getResult();

        Assert.assertEquals(request.getMethod(), "GET");
        Assert.assertEquals(request.getUrl(), String.format("%s/v0/reserve/statistics", BuildConfig.API_SERVER_URL));
        Assert.assertEquals(statistics.size(), 2);
        Assert.assertEquals(statistics.get(0).getCurrency(), "FOO");
        Assert.assertEquals(statistics.get(1).getCurrency(), "BAR");
        Assert.assertEquals(statistics.get(0).getValues().size(), 1);
        Assert.assertEquals(statistics.get(1).getValues().size(), 1);
        Assert.assertEquals(statistics.get(0).getValues().get(0).getAssets(), "foobar");
        Assert.assertEquals(statistics.get(0).getValues().get(0).getCurrency(), "foo");
        Assert.assertEquals(statistics.get(0).getValues().get(0).getLiabilities(), "bar");
        Assert.assertEquals(statistics.get(0).getValues().get(0).getRate(), "biz");
        Assert.assertEquals(statistics.get(1).getValues().get(0).getAssets(), "foobiz");
        Assert.assertEquals(statistics.get(1).getValues().get(0).getCurrency(), "biz");
        Assert.assertEquals(statistics.get(1).getValues().get(0).getLiabilities(), "buz");
        Assert.assertEquals(statistics.get(1).getValues().get(0).getRate(), "foo");
        Assert.assertEquals(statistics.get(0).getTotals().getAssets(), "foobar");
        Assert.assertEquals(statistics.get(0).getTotals().getCommissions(), "foo");
        Assert.assertEquals(statistics.get(0).getTotals().getLiabilities(), "foobiz");
        Assert.assertEquals(statistics.get(0).getTotals().getTransactions(), "bar");
        Assert.assertEquals(statistics.get(1).getTotals().getAssets(), "fuz");
        Assert.assertEquals(statistics.get(1).getTotals().getCommissions(), "fiz");
        Assert.assertEquals(statistics.get(1).getTotals().getLiabilities(), "buz");
        Assert.assertEquals(statistics.get(1).getTotals().getTransactions(), "biz");
    }

    @Test
    public void getTransactionsByIdShouldReturnTheTransaction() throws Exception {
        String responseString = "{ \"id\": \"FOOBAR\" }";
        MockRestAdapter<Transaction> adapter = new MockRestAdapter<>("foobar", responseString, null);

        adapter.request(new RepromiseFunction<BitreserveRestAdapter, Transaction>() {
            @Override
            public Promise<Transaction> call(BitreserveRestAdapter adapter) {
                Reserve reserve = new Reserve();

                reserve.setBitreserveRestAdapter(adapter);

                return reserve.getTransactionById("foobiz");
            }
        });

        Request request = adapter.getRequest();
        Transaction transaction = adapter.getResult();

        Assert.assertEquals(request.getMethod(), "GET");
        Assert.assertEquals(request.getUrl(), String.format("%s/v0/reserve/transactions/foobiz", BuildConfig.API_SERVER_URL));
        Assert.assertEquals(transaction.getId(), "FOOBAR");
    }

    @Test
    public void getTransactionsShouldReturnThePaginatorCount() throws Exception {
        String responseString = "[ { \"id\": \"FOOBAR\" }, { \"id\": \"FOOBIZ\" } ]";
        MockRestAdapter<Integer> adapter = new MockRestAdapter<>("foobar", responseString, new HashMap<String, String>() {{
            put("Content-Range", "0-2/60");
        }});

        adapter.request(new RepromiseFunction<BitreserveRestAdapter, Integer>() {
            @Override
            public Promise<Integer> call(BitreserveRestAdapter adapter) {
                Reserve reserve = new Reserve();

                reserve.setBitreserveRestAdapter(adapter);

                final Paginator<Transaction> paginator = reserve.getTransactions();

                return paginator.getElements().then(new RepromiseFunction<List<Transaction>, Integer>() {
                    @Override
                    public Promise<Integer> call(List<Transaction> transactions) {
                        return paginator.count();
                    }
                });
            }
        });

        Integer count = adapter.getResult();
        Request request = adapter.getRequest();

        Assert.assertEquals(request.getMethod(), "GET");
        Assert.assertEquals(request.getUrl(), String.format("%s/v0/reserve/transactions", BuildConfig.API_SERVER_URL));
        Assert.assertEquals(request.getHeaders().get(0).getName(), "Range");
        Assert.assertEquals(request.getHeaders().get(0).getValue(), "items=0-1");
        Assert.assertEquals(count, Integer.valueOf(60));
    }

    @Test
    public void getTransactionsShouldReturnThePaginatorHasNext() throws Exception {
        String responseString = "[ { \"id\": \"FOOBAR\" }, { \"id\": \"FOOBIZ\" } ]";
        MockRestAdapter<Boolean> adapter = new MockRestAdapter<>("foobar", responseString, new HashMap<String, String>() {{
            put("Content-Range", "0-49/51");
        }});

        adapter.request(new RepromiseFunction<BitreserveRestAdapter, Boolean>() {
            @Override
            public Promise<Boolean> call(BitreserveRestAdapter adapter) {
                Reserve reserve = new Reserve();

                reserve.setBitreserveRestAdapter(adapter);

                final Paginator<Transaction> paginator = reserve.getTransactions();

                return paginator.getElements().then(new RepromiseFunction<List<Transaction>, Boolean>() {
                    @Override
                    public Promise<Boolean> call(List<Transaction> transactions) {
                        return paginator.hasNext();
                    }
                });
            }
        });

        Boolean hasNext = adapter.getResult();
        Request request = adapter.getRequest();

        Assert.assertEquals(request.getMethod(), "GET");
        Assert.assertEquals(request.getUrl(), String.format("%s/v0/reserve/transactions", BuildConfig.API_SERVER_URL));
        Assert.assertEquals(request.getHeaders().get(0).getName(), "Range");
        Assert.assertEquals(request.getHeaders().get(0).getValue(), "items=0-1");
        Assert.assertTrue(hasNext);
    }

    @Test
    public void getTransactionsShouldReturnTheListOfTransactions() throws Exception {
        String responseString = "[ { \"id\": \"FOOBAR\" }, { \"id\": \"FOOBIZ\" } ]";
        MockRestAdapter<List<Transaction>> adapter = new MockRestAdapter<>("foobar", responseString, null);

        adapter.request(new RepromiseFunction<BitreserveRestAdapter, List<Transaction>>() {
            @Override
            public Promise<List<Transaction>> call(BitreserveRestAdapter adapter) {
                Reserve reserve = new Reserve();

                reserve.setBitreserveRestAdapter(adapter);

                final Paginator<Transaction> paginator = reserve.getTransactions();

                return paginator.getElements().then(new RepromiseFunction<List<Transaction>, List<Transaction>>() {
                    @Override
                    public Promise<List<Transaction>> call(List<Transaction> transactions) {
                        return paginator.getElements();
                    }
                });
            }
        });

        Request request = adapter.getRequest();
        List<Transaction> transactions = adapter.getResult();

        Assert.assertEquals(request.getMethod(), "GET");
        Assert.assertEquals(request.getUrl(), String.format("%s/v0/reserve/transactions", BuildConfig.API_SERVER_URL));
        Assert.assertEquals(request.getHeaders().get(0).getName(), "Range");
        Assert.assertEquals(request.getHeaders().get(0).getValue(), "items=0-49");
        Assert.assertEquals(transactions.size(), 2);
        Assert.assertEquals(transactions.get(0).getId(), "FOOBAR");
        Assert.assertEquals(transactions.get(1).getId(), "FOOBIZ");
    }

    @Test
    public void getTransactionsShouldReturnThePaginatorNextPage() throws Exception {
        String responseString = "[ { \"id\": \"FOOBAR\" }, { \"id\": \"FOOBIZ\" } ]";
        MockRestAdapter<List<Transaction>> adapter = new MockRestAdapter<>("foobar", responseString, null);

        adapter.request(new RepromiseFunction<BitreserveRestAdapter, List<Transaction>>() {
            @Override
            public Promise<List<Transaction>> call(BitreserveRestAdapter adapter) {
                Reserve reserve = new Reserve();

                reserve.setBitreserveRestAdapter(adapter);

                final Paginator<Transaction> paginator = reserve.getTransactions();

                return paginator.getElements().then(new RepromiseFunction<List<Transaction>, List<Transaction>>() {
                    @Override
                    public Promise<List<Transaction>> call(List<Transaction> transactions) {
                        return paginator.getNext();
                    }
                });
            }
        });

        Request request = adapter.getRequest();

        Assert.assertEquals(request.getMethod(), "GET");
        Assert.assertEquals(request.getUrl(), String.format("%s/v0/reserve/transactions", BuildConfig.API_SERVER_URL));
        Assert.assertEquals(request.getHeaders().get(0).getName(), "Range");
        Assert.assertEquals(request.getHeaders().get(0).getValue(), "items=50-99");
    }

}

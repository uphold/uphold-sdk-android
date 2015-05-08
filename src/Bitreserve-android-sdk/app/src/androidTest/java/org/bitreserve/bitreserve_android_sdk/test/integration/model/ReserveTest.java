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
import org.bitreserve.bitreserve_android_sdk.test.util.MockRestAdapter;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import java.util.List;

import retrofit.client.Request;

/**
 * Reserve integration tests.
 */

@RunWith(AndroidJUnit4.class)
@SmallTest
public class ReserveTest {

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
        Assert.assertEquals(request.getUrl(), "https://api.bitreserve.org/v0/reserve/ledger");
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
    public void getLedgerShouldReturnTheNextPage() throws Exception {
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

        List<Deposit> deposits = adapter.getResult();
        Request request = adapter.getRequest();

        Assert.assertEquals(request.getMethod(), "GET");
        Assert.assertEquals(request.getUrl(), "https://api.bitreserve.org/v0/reserve/ledger");
        Assert.assertEquals(request.getHeaders().get(0).getName(), "Range");
        Assert.assertEquals(request.getHeaders().get(0).getValue(), "items=50-99");
        Assert.assertEquals(deposits.size(), 2);
        Assert.assertEquals(deposits.get(0).getType(), "asset");
        Assert.assertEquals(deposits.get(1).getType(), "liability");
    }

    @Test
    public void getStatisticsShouldTheListWithReserveStatistics() throws Exception {
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
        Assert.assertEquals(request.getUrl(), "https://api.bitreserve.org/v0/reserve/statistics");
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
        Assert.assertEquals(request.getUrl(), "https://api.bitreserve.org/v0/reserve/transactions/foobiz");
        Assert.assertEquals(transaction.getId(), "FOOBAR");
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
        Assert.assertEquals(request.getUrl(), "https://api.bitreserve.org/v0/reserve/transactions");
        Assert.assertEquals(request.getHeaders().get(0).getName(), "Range");
        Assert.assertEquals(request.getHeaders().get(0).getValue(), "items=0-49");
        Assert.assertEquals(transactions.size(), 2);
        Assert.assertEquals(transactions.get(0).getId(), "FOOBAR");
        Assert.assertEquals(transactions.get(1).getId(), "FOOBIZ");
    }

    @Test
    public void getTransactionsShouldReturnTheNextPage() throws Exception {
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
        List<Transaction> transactions = adapter.getResult();

        Assert.assertEquals(request.getMethod(), "GET");
        Assert.assertEquals(request.getUrl(), "https://api.bitreserve.org/v0/reserve/transactions");
        Assert.assertEquals(request.getHeaders().get(0).getName(), "Range");
        Assert.assertEquals(request.getHeaders().get(0).getValue(), "items=50-99");
        Assert.assertEquals(transactions.size(), 2);
        Assert.assertEquals(transactions.get(0).getId(), "FOOBAR");
        Assert.assertEquals(transactions.get(1).getId(), "FOOBIZ");
    }

}

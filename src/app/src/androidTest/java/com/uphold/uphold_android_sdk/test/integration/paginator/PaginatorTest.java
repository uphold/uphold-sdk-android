package com.uphold.uphold_android_sdk.test.integration.paginator;

import com.uphold.uphold_android_sdk.paginator.PaginatorInterface;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import com.darylteo.rx.promises.java.Promise;
import com.darylteo.rx.promises.java.functions.PromiseAction;

import junit.framework.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Paginator integration tests.
 */

@RunWith(AndroidJUnit4.class)
@SmallTest
public class PaginatorTest {

    private PaginatorInterface<Object> paginator = new PaginatorInterface<Object>() {

        @Override
        public Promise<List<Object>> getNext(String range) {
            Promise<List<Object>> promise = new Promise<>();

            promise.fulfill(objects);

            return promise;
        }

        @Override
        public Promise<Integer> count() {
            Promise<Integer> promise = new Promise<>();

            promise.fulfill(10);

            return promise;
        }

        @Override
        public Promise<Boolean> hasNext(Integer currentPage) {
            Promise<Boolean> promise = new Promise<>();

            promise.fulfill(true);

            return promise;
        }

    };
    private List<Object> objects = new ArrayList<>();

    @Test
    public void getNextShouldReturnTheList() {
        final AtomicReference<List<Object>> resultReference = new AtomicReference<>();

        paginator.getNext("foobar").then(new PromiseAction<List<Object>>() {
            @Override
            public void call(List<Object> result) {
                resultReference.set(result);
            }
        });

        Assert.assertEquals(resultReference.get(), objects);
    }

    @Test
    public void getCountShouldReturnTheCount() {
        final AtomicReference<Integer> resultReference = new AtomicReference<>();

        paginator.count().then(new PromiseAction<Integer>() {
            @Override
            public void call(Integer result) {
                resultReference.set(result);
            }
        });

        Assert.assertEquals(resultReference.get(), Integer.valueOf(10));
    }

    @Test
    public void hasNextShouldReturnTrue () {
        final AtomicReference<Boolean> resultReference = new AtomicReference<>();

        paginator.hasNext(100).then(new PromiseAction<Boolean>() {
            @Override
            public void call(Boolean result) {
                resultReference.set(result);
            }
        });

        Assert.assertTrue(resultReference.get());
    }

}

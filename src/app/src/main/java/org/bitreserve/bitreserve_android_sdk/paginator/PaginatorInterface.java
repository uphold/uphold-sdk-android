package org.bitreserve.bitreserve_android_sdk.paginator;

import com.darylteo.rx.promises.java.Promise;

import java.util.List;

/**
 * Paginator interface.
 */

public interface PaginatorInterface<T> {

    /**
     * Gets the next page of results.
     *
     * @param range The range header.
     *
     * @return a promise with the results.
     */

    public Promise<List<T>> getNext(String range);

    /**
     * Gets the total number of results available.
     *
     * @return a promise with total number of results.
     */

    public Promise<Integer> count();

    /**
     * Gets a boolean indicating if exists a next page of results.
     *
     * @param currentPage The current page.
     *
     * @return a promise with a boolean indicating if exists a next page of results.
     */

    public Promise<Boolean> hasNext(final Integer currentPage);

}

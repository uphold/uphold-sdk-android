package org.bitreserve.bitreserve_android_sdk.paginator;

import com.darylteo.rx.promises.java.Promise;

import org.bitreserve.bitreserve_android_sdk.util.Header;

import java.util.List;

/**
 * Paginator.
 */

public class Paginator<T> {

    public static final int DEFAULT_START = 0;
    public static final int DEFAULT_OFFSET = 50;

    private int currentPage;
    private final Promise<List<T>> elements;
    private final PaginatorInterface<T> paginatorInterface;

    /**
     * Constructor.
     *
     * @param elements A promise with the current elements.
     * @param paginatorInterface A paginator interface.
     */

    public Paginator(Promise<List<T>> elements, PaginatorInterface<T> paginatorInterface) {
        this.elements = elements;
        this.currentPage = 1;
        this.paginatorInterface = paginatorInterface;
    }

    /**
     * Gets the total number of elements.
     *
     * @return a {@link Promise<Integer>}
     */

    public Promise<Integer> count() {
        return paginatorInterface.count();
    }

    /**
     * Gets a promise with the elements.
     *
     * @return a {@link Promise<List<T>>}
     */

    public Promise<List<T>> getElements() {
        return elements;
    }

    /**
     * Gets the next page of elements.
     *
     * @return a {@link Promise<List<T>>}.
     */

    public synchronized Promise<List<T>> getNext() {
        Promise<List<T>> promise = paginatorInterface.getNext(Header.buildRangeHeader((DEFAULT_OFFSET * currentPage), ((DEFAULT_OFFSET * currentPage) + DEFAULT_OFFSET) - 1));

        currentPage++;

        return promise;
    }

    /**
     * Gets a promise with a bolean indicating if exists a next page of elements.
     *
     * @return a {@link Promise<Boolean>}.
     */

    public Promise<Boolean> hasNext() {
        return paginatorInterface.hasNext(currentPage);
    }

}

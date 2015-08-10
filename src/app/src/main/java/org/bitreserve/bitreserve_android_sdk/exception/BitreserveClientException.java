package org.bitreserve.bitreserve_android_sdk.exception;

import retrofit.RetrofitError;

/**
 * BitreserveClientException exception.
 */

public class BitreserveClientException extends Exception {

    private final RetrofitError error;
    private final Integer httpCode;

    /**
     * Constructor.
     */

    public BitreserveClientException() {
        super();

        this.error = null;
        this.httpCode = null;
    }

    /**
     * Constructor.
     *
     * @param detailMessage The exception message.
     */

    public BitreserveClientException(String detailMessage) {
        super(detailMessage);

        this.error = null;
        this.httpCode = null;
    }

    /**
     * Constructor.
     *
     * @param error The {@link RetrofitError}.
     * @param httpCode The http error code.
     */

    public BitreserveClientException(RetrofitError error, Integer httpCode) {
        this.error = error;
        this.httpCode = httpCode;
    }

    /**
     * Constructor.
     *
     * @param detailMessage The exception message.
     * @param error The {@link RetrofitError}.
     * @param httpCode The http error code.
     */

    public BitreserveClientException(String detailMessage, RetrofitError error, Integer httpCode) {
        super(detailMessage);

        this.error = error;
        this.httpCode = httpCode;
    }

    /**
     * Constructor.
     *
     * @param detailMessage The exception message.
     * @param throwable The {@link java.lang.Throwable}.
     * @param error The {@link RetrofitError}.
     * @param httpCode The http error code.
     */

    public BitreserveClientException(String detailMessage, Throwable throwable, RetrofitError error, Integer httpCode) {
        super(detailMessage, throwable);

        this.error = error;
        this.httpCode = httpCode;
    }

    /**
     * Gets the error.
     *
     * @return the {@link retrofit.RetrofitError}.
     */

    public RetrofitError getError() {
        return error;
    }

    /**
     * Gets the http error code.
     *
     * @return the http error code.
     */

    public Integer getHttpCode() {
        return httpCode;
    }

}

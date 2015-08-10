package org.bitreserve.bitreserve_android_sdk.exception;

import retrofit.RetrofitError;

/**
 * ApiLimitExceedException exception.
 */

public class ApiLimitExceedException extends BitreserveClientException {

    /**
     * Constructor.
     *
     * @param detailMessage The exception message.
     * @param error The {@link RetrofitError}.
     * @param httpCode The http error code.
     */

    public ApiLimitExceedException(String detailMessage, RetrofitError error, Integer httpCode) {
        super(detailMessage, error, httpCode);
    }

}

package org.bitreserve.bitreserve_android_sdk.exception;

import retrofit.RetrofitError;

/**
 * AuthenticationRequiredException exception.
 */

public class AuthenticationRequiredException extends BitreserveClientException {

    /**
     * Constructor.
     *
     * @param detailMessage The exception message.
     */

    public AuthenticationRequiredException(String detailMessage) {
        super(detailMessage);
    }

    /**
     * Constructor.
     *
     * @param detailMessage The exception message.
     * @param error The {@link RetrofitError}.
     * @param httpCode The http error code.
     */

    public AuthenticationRequiredException(String detailMessage, RetrofitError error, Integer httpCode) {
        super(detailMessage, error, httpCode);
    }

}

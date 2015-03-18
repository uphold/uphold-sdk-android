package org.bitreserve.bitreserve_android_sdk.client.errorhandling;

import org.bitreserve.bitreserve_android_sdk.exception.ApiLimitExceedException;
import org.bitreserve.bitreserve_android_sdk.exception.AuthenticationRequiredException;
import org.bitreserve.bitreserve_android_sdk.exception.BadRequestException;
import org.bitreserve.bitreserve_android_sdk.exception.NotFoundException;
import org.bitreserve.bitreserve_android_sdk.exception.RuntimeException;
import org.bitreserve.bitreserve_android_sdk.exception.TwoFactorAuthenticationRequiredException;
import org.bitreserve.bitreserve_android_sdk.util.Header;

import java.util.List;

import retrofit.ErrorHandler;
import retrofit.RetrofitError;

/**
 * Bitreserve custom retrofit error handling.
 */

public class BitreserveRetrofitErrorHandling implements ErrorHandler {

    @Override
    public Throwable handleError(RetrofitError cause) {
        int httpStatusCode = cause.getResponse().getStatus();
        List<retrofit.client.Header> responseHeaders = cause.getResponse().getHeaders();

        switch (httpStatusCode) {
            case 400:
                return new BadRequestException(cause.getMessage(), cause, httpStatusCode);

            case 401:
                if (Header.isOTPRequired(responseHeaders)) {
                    return new TwoFactorAuthenticationRequiredException("Two factor authentication is enabled on this account", cause, httpStatusCode);
                }

                return new AuthenticationRequiredException(cause.getMessage(), cause, httpStatusCode);

            case 404:
                return new NotFoundException("Object or route not found: " + cause.getUrl(), cause, httpStatusCode);

            case 429:
                String exceptionMessage = String.format("You have exceeded Bitreserve's API rate limit of %s requests. Current time window ends in %s seconds.", Header.getRateLimitValue(responseHeaders), Header.getSecondsUntilRateLimitReset(responseHeaders));

                return new ApiLimitExceedException(exceptionMessage, cause, httpStatusCode);

            default:
                return new RuntimeException(cause.getMessage(), cause, httpStatusCode);

        }
    }
}

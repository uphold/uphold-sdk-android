package com.uphold.uphold_android_sdk.client.errorhandling;

import com.uphold.uphold_android_sdk.exception.ApiLimitExceedException;
import com.uphold.uphold_android_sdk.exception.AuthenticationRequiredException;
import com.uphold.uphold_android_sdk.exception.BadRequestException;
import com.uphold.uphold_android_sdk.exception.NotFoundException;
import com.uphold.uphold_android_sdk.exception.RuntimeException;
import com.uphold.uphold_android_sdk.util.Header;

import java.util.List;

import retrofit.ErrorHandler;
import retrofit.RetrofitError;

/**
 * Uphold custom retrofit error handling.
 */

public class UpholdRetrofitErrorHandling implements ErrorHandler {

    @Override
    public Throwable handleError(RetrofitError cause) {
        if (cause.getResponse() == null) {
            return new RuntimeException(cause.getMessage(), cause, null);
        }

        int httpStatusCode = cause.getResponse().getStatus();
        List<retrofit.client.Header> responseHeaders = cause.getResponse().getHeaders();

        switch (httpStatusCode) {
            case 400:
                return new BadRequestException(cause.getMessage(), cause, httpStatusCode);

            case 401:
                return new AuthenticationRequiredException(cause.getMessage(), cause, httpStatusCode);

            case 404:
                return new NotFoundException("Object or route not found: " + cause.getUrl(), cause, httpStatusCode);

            case 412:
                return new BadRequestException("Precondition failed", cause, httpStatusCode);

            case 419:
                return new BadRequestException("Requested range not satisfiable", cause, httpStatusCode);

            case 429:
                String exceptionMessage = String.format("You have exceeded Uphold's API rate limit of %s requests. Current time window ends in %s seconds.", Header.getRateLimitValue(responseHeaders), Header.getSecondsUntilRateLimitReset(responseHeaders));

                return new ApiLimitExceedException(exceptionMessage, cause, httpStatusCode);

            default:
                return new RuntimeException(cause.getMessage(), cause, httpStatusCode);

        }
    }
}

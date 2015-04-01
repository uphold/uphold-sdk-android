package org.bitreserve.bitreserve_android_sdk.client.restadapter;

import org.bitreserve.bitreserve_android_sdk.client.errorhandling.BitreserveRetrofitErrorHandling;
import org.bitreserve.bitreserve_android_sdk.config.GlobalConfigurations;
import org.bitreserve.bitreserve_android_sdk.util.Header;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/**
 * Bitreserve rest adapter.
 */

public class BitreserveRestAdapter {

    private RestAdapter adapter;

    /**
     * Generates a bitreserve rest adapter.
     *
     * @param token The token (if available) of the user.
     *
     * @return the {@link RestAdapter}.
     */

    public BitreserveRestAdapter(final String token) {
        this.adapter = new RestAdapter.Builder().setEndpoint(GlobalConfigurations.SERVER_URL)
            .setErrorHandler(new BitreserveRetrofitErrorHandling())
            .setLogLevel(GlobalConfigurations.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE)
            .setRequestInterceptor(getBitreserveRequestInterceptor(token))
            .build();
    }

    /**
     * Creates the service.
     *
     * @param service The service interface.
     * @param <T> The service type.
     *
     * @return The service instance.
     */

    public <T> T create(Class<T> service) {
        return this.adapter.create(service);
    }

    /**
     * Gets the {@link RestAdapter}.
     *
     * @return the {@link RestAdapter}.
     */

    public RestAdapter getAdapter() {
        return adapter;
    }

    /**
     * Gets the {@link RequestInterceptor}.
     *
     * @param token The token (if available) of the user.
     *
     * @return the {@link RequestInterceptor}.
     */
    public RequestInterceptor getBitreserveRequestInterceptor(final String token) {
        return new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                HashMap<String, String> map = Header.getHeaders();

                for (Map.Entry<String, String> entry : map.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();

                    request.addHeader(key, value);
                }

                if (token != null && !TextUtils.isEmpty(token)) {
                    request.addHeader("Authorization", Header.encodeCredentialsForBasicAuthorization(token, "X-OAuth-Basic"));
                }
            }
        };
    }

    /**
     * Sets the {@link RestAdapter}.
     *
     * @param adapter the {@link RestAdapter}.
     */

    public void setAdapter(RestAdapter adapter) {
        this.adapter = adapter;
    }

}

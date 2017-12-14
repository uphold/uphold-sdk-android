package com.uphold.uphold_android_sdk.client.restadapter;

import android.text.TextUtils;

import com.squareup.okhttp.OkHttpClient;
import com.uphold.uphold_android_sdk.BuildConfig;
import com.uphold.uphold_android_sdk.client.errorhandling.UpholdRetrofitErrorHandling;
import com.uphold.uphold_android_sdk.client.session.SessionManager;
import com.uphold.uphold_android_sdk.util.Header;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Uphold rest adapter.
 */

public class UpholdRestAdapter {

    private RestAdapter adapter;

    /**
     * Constructor.
     */

    public UpholdRestAdapter() {
        OkHttpClient okHttpClient = new OkHttpClient();

        try {
            okHttpClient.setSslSocketFactory(new TLSV12SSLSocketFactory());
        } catch (NoSuchAlgorithmException | KeyManagementException exception) {
            exception.printStackTrace();
        }

        this.adapter = new RestAdapter.Builder().setEndpoint(BuildConfig.API_SERVER_URL)
            .setClient(new OkClient(okHttpClient))
            .setErrorHandler(new UpholdRetrofitErrorHandling())
            .setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE)
            .setRequestInterceptor(getUpholdRequestInterceptor())
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
     * @return the {@link RequestInterceptor}.
     */

    public RequestInterceptor getUpholdRequestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                final HashMap<String, String> map = Header.getHeaders();

                for (Map.Entry<String, String> entry : map.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();

                    request.addHeader(key, value);
                }

                final String token = SessionManager.INSTANCE.getBearerToken();

                if (!TextUtils.isEmpty(token)) {
                    request.addHeader("Authorization", String.format("Bearer %s", token));
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

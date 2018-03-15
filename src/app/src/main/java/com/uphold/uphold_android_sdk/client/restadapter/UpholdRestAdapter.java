package com.uphold.uphold_android_sdk.client.restadapter;

import android.text.TextUtils;

import com.jakewharton.retrofit.Ok3Client;
import com.uphold.uphold_android_sdk.BuildConfig;
import com.uphold.uphold_android_sdk.client.errorhandling.UpholdRetrofitErrorHandling;
import com.uphold.uphold_android_sdk.client.session.SessionManager;
import com.uphold.uphold_android_sdk.util.Header;

import java.security.KeyStore;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/**
 * Uphold rest adapter.
 */

public class UpholdRestAdapter {

    private RestAdapter adapter;

    /**
     * Constructor.
     */

    public UpholdRestAdapter() {
        final OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();

        try {
            X509TrustManager x509TrustManager = null;
            final TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());

            trustManagerFactory.init((KeyStore) null);

            for (TrustManager trustManager : trustManagerFactory.getTrustManagers()) {
                if (trustManager instanceof X509TrustManager) {
                    x509TrustManager = (X509TrustManager) trustManager;

                    break;
                }
            }

            if (x509TrustManager != null) {
                clientBuilder.sslSocketFactory(new TLSV12SSLSocketFactory(), x509TrustManager);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        this.adapter = new RestAdapter.Builder().setEndpoint(BuildConfig.API_SERVER_URL)
            .setClient(new Ok3Client(clientBuilder.build()))
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

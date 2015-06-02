package org.bitreserve.bitreserve_android_sdk.test.unit.client;

import org.bitreserve.bitreserve_android_sdk.BuildConfig;
import org.bitreserve.bitreserve_android_sdk.client.BitreserveClient;
import org.bitreserve.bitreserve_android_sdk.test.unit.util.MockSharedPreferencesContext;
import org.bitreserve.bitreserve_android_sdk.util.Header;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import android.net.Uri;
import android.test.mock.MockContext;
import android.text.TextUtils;

import java.util.ArrayList;

/**
 * Unit tests to the class {@link Header}.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest({Uri.class, TextUtils.class})
public class BitreserveClientTest {

    @Test
    public void beginAuthorizationShouldCallUriParse() throws Exception {
        BitreserveClient.initialize(new MockSharedPreferencesContext());

        BitreserveClient bitreserveClient = new BitreserveClient();
        ArrayList<String> scopes = new ArrayList<String>() {{
            add("foo");
        }};

        PowerMockito.mockStatic(TextUtils.class);
        PowerMockito.when(TextUtils.join(" ", scopes)).thenReturn("foo");

        PowerMockito.mockStatic(Uri.class);
        bitreserveClient.beginAuthorization(new MockContext(), "foo", scopes, "bar");

        PowerMockito.verifyStatic();
        Uri.parse(String.format("%s/authorize/foo?scope=foo&state=bar", BuildConfig.AUTHORIZATION_SERVER_URL));
    }

}

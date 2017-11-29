package com.uphold.uphold_android_sdk.test.unit.client;

import android.net.Uri;
import android.test.mock.MockContext;
import android.text.TextUtils;

import com.uphold.uphold_android_sdk.BuildConfig;
import com.uphold.uphold_android_sdk.client.UpholdClient;
import com.uphold.uphold_android_sdk.test.unit.util.MockSharedPreferencesContext;
import com.uphold.uphold_android_sdk.util.Header;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;

/**
 * Unit tests to the class {@link Header}.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest({Uri.class, TextUtils.class})
@PowerMockIgnore("javax.net.ssl.*")
public class UpholdClientTest {

    @Test
    public void beginAuthorizationShouldCallUriParse() throws Exception {
        UpholdClient.initialize(new MockSharedPreferencesContext());

        UpholdClient upholdClient = new UpholdClient();
        ArrayList<String> scopes = new ArrayList<String>() {{
            add("foo");
        }};

        PowerMockito.mockStatic(TextUtils.class);
        PowerMockito.when(TextUtils.join(" ", scopes)).thenReturn("foo");

        PowerMockito.mockStatic(Uri.class);
        upholdClient.beginAuthorization(new MockContext(), "foo", scopes, "bar");

        PowerMockito.verifyStatic();
        Uri.parse(String.format("%s/authorize/foo?scope=foo&state=bar", BuildConfig.AUTHORIZATION_SERVER_URL));
    }

}

package com.uphold.uphold_android_sdk.test.integration.client.session;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import junit.framework.Assert;

import com.uphold.uphold_android_sdk.client.UpholdClient;
import com.uphold.uphold_android_sdk.client.session.SessionManager;
import com.uphold.uphold_android_sdk.model.Token;
import com.uphold.uphold_android_sdk.test.util.MockSharedPreferencesContext;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * SessionManagerTest integration tests.
 */

@RunWith(AndroidJUnit4.class)
@SmallTest
public class SessionManagerTest {

    @Before
    public void setup() throws Exception {
        Field sdkInitializedField = UpholdClient.class.getDeclaredField("sdkInitialized");
        Field sharedPreferencesField = SessionManager.class.getDeclaredField("sharedPreferences");

        sdkInitializedField.setAccessible(true);
        sdkInitializedField.set(null, false);
        sharedPreferencesField.setAccessible(true);
        sharedPreferencesField.set(SessionManager.INSTANCE, null);
    }

    @Test
    public void getBearerTokenShouldReturnNull() throws Exception {
        UpholdClient.initialize(new MockSharedPreferencesContext(new HashMap<String, Object>()));

        Assert.assertNull(SessionManager.INSTANCE.getBearerToken());
    }

    @Test
    public void getBearerTokenShouldReturnToken() throws Exception {
        HashMap<String, Object> mockPreferences = new HashMap<String, Object>() {{
            put("com.uphold.uphold_android_sdk.SessionManager.CachedAccessToken", "foobar");
        }};

        UpholdClient.initialize(new MockSharedPreferencesContext(mockPreferences));

        Assert.assertEquals(SessionManager.INSTANCE.getBearerToken(), "foobar");
    }

    @Test
    public void setBearerTokenShouldReturnToken() throws Exception {
        HashMap<String, Object> mockPreferences = new HashMap<>();

        UpholdClient.initialize(new MockSharedPreferencesContext(mockPreferences));
        SessionManager.INSTANCE.setBearerToken(new Token("foobar"));

        Assert.assertEquals(mockPreferences.get("com.uphold.uphold_android_sdk.SessionManager.CachedAccessToken"), "foobar");
    }

    @Test
    public void invalidateSessionShouldDeleteTheToken() throws Exception {
        HashMap<String, Object> mockPreferences = new HashMap<String, Object>() {{
            put("com.uphold.uphold_android_sdk.SessionManager.CachedAccessToken", "foobar");
        }};

        UpholdClient.initialize(new MockSharedPreferencesContext(mockPreferences));
        SessionManager.INSTANCE.invalidateSession();

        Assert.assertFalse(mockPreferences.containsKey("com.uphold.uphold_android_sdk.SessionManager.CachedAccessToken"));
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        Field sdkInitializedField = UpholdClient.class.getDeclaredField("sdkInitialized");
        Field sharedPreferencesField = SessionManager.class.getDeclaredField("sharedPreferences");

        sdkInitializedField.setAccessible(true);
        sdkInitializedField.set(null, false);
        sharedPreferencesField.setAccessible(true);
        sharedPreferencesField.set(SessionManager.INSTANCE, null);
    }

}

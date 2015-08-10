package org.bitreserve.bitreserve_android_sdk.test.unit.util;

import org.bitreserve.bitreserve_android_sdk.util.Header;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import android.util.Base64;

/**
 * Header unit tests.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest(Base64.class)
public class HeaderTest {

    @Test
    public void encodeCredentialsForBasicAuthorizationShouldCallEncodeToString() {
        final String user = "foo@bar.org";
        final String password = "foobar";
        final String userAndPassword = user + ":" + password;

        PowerMockito.mockStatic(Base64.class);

        Header.encodeCredentialsForBasicAuthorization(user, password);

        PowerMockito.verifyStatic();
        Base64.encodeToString(userAndPassword.getBytes(), Base64.NO_WRAP);
    }

}

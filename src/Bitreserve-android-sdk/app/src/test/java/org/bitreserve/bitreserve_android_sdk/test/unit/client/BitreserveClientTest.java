package org.bitreserve.bitreserve_android_sdk.test.unit.client;

import org.bitreserve.bitreserve_android_sdk.client.BitreserveClient;
import org.bitreserve.bitreserve_android_sdk.util.Header;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import android.net.Uri;
import android.test.mock.MockContext;

/**
 * Unit tests to the class {@link Header}.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest(Uri.class)
public class BitreserveClientTest {

    @Test
    public void beginAuthorizationShouldCallUriParse() {
        BitreserveClient bitreserveClient = new BitreserveClient();

        PowerMockito.mockStatic(Uri.class);
        bitreserveClient.beginAuthorization(new MockContext(), "foo", "bar");

        PowerMockito.verifyStatic();
        Uri.parse("https://bitreserve.org/authorize/foo?state=bar");
    }

}

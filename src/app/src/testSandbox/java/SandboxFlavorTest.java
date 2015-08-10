import junit.framework.Assert;

import org.bitreserve.bitreserve_android_sdk.BuildConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * SandboxFlavorTest unit.
 */

@RunWith(PowerMockRunner.class)
public class SandboxFlavorTest {

    @Test
    public void buildConfigAPIServerURLShouldReturnAPIServerURL() {
        Assert.assertEquals("https://api-sandbox.bitreserve.org", BuildConfig.API_SERVER_URL);
    }

    @Test
    public void buildConfigAuthorizationServerURLShouldReturnAPIServerURL() {
        Assert.assertEquals("https://sandbox.bitreserve.org", BuildConfig.AUTHORIZATION_SERVER_URL);
    }

    @Test
    public void buildConfigIsDebugEnableShouldReturnTrue() {
        Assert.assertTrue(BuildConfig.IS_DEBUG_ENABLE);
    }

}

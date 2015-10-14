import junit.framework.Assert;

import com.uphold.uphold_android_sdk.BuildConfig;
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
        Assert.assertEquals("https://api-sandbox.uphold.com", BuildConfig.API_SERVER_URL);
    }

    @Test
    public void buildConfigAuthorizationServerURLShouldReturnAPIServerURL() {
        Assert.assertEquals("https://sandbox.uphold.com", BuildConfig.AUTHORIZATION_SERVER_URL);
    }

    @Test
    public void buildConfigIsDebugEnableShouldReturnTrue() {
        Assert.assertTrue(BuildConfig.IS_DEBUG_ENABLE);
    }

}

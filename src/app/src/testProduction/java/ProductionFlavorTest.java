import junit.framework.Assert;

import org.bitreserve.bitreserve_android_sdk.BuildConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * ProductionFlavorTest unit.
 */

@RunWith(PowerMockRunner.class)
public class ProductionFlavorTest {

    @Test
    public void buildConfigAPIServerURLShouldReturnAPIServerURL() {
        Assert.assertEquals("https://api.bitreserve.org", BuildConfig.API_SERVER_URL);
    }

    @Test
    public void buildConfigAuthorizationServerURLShouldReturnAPIServerURL() {
        Assert.assertEquals("https://bitreserve.org", BuildConfig.AUTHORIZATION_SERVER_URL);
    }

    @Test
    public void buildConfigIsDebugEnableShouldReturnFalse() {
        Assert.assertFalse(BuildConfig.IS_DEBUG_ENABLE);
    }

}

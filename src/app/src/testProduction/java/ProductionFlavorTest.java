import junit.framework.Assert;

import com.uphold.uphold_android_sdk.BuildConfig;
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
        Assert.assertEquals("https://api.uphold.com", BuildConfig.API_SERVER_URL);
    }

    @Test
    public void buildConfigAuthorizationServerURLShouldReturnAPIServerURL() {
        Assert.assertEquals("https://uphold.com", BuildConfig.AUTHORIZATION_SERVER_URL);
    }

}

package org.bitreserve.androidsdkdemo;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.darylteo.rx.promises.java.functions.PromiseAction;

import org.bitreserve.bitreserve_android_sdk.client.BitreserveClient;
import org.bitreserve.bitreserve_android_sdk.model.AuthenticationResponse;

import java.math.BigInteger;
import java.security.SecureRandom;

public class MainActivity extends ActionBarActivity {

    private static final String CLIENT_ID = "e67a4ca1f3654e9cc0c530a717d1ada1e89a61fe";
    private static final String CLIENT_SECRET = "24e4135531b4518ae9424fc8f215f272967d5cfd";

    private BitreserveClient bitreserveClient;
    private Button buttonConnect;
    private String state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        buttonConnect = (Button) findViewById(R.id.buttonConnect);

        bitreserveClient = new BitreserveClient();
        SecureRandom secureRandom = new SecureRandom();

        state = new BigInteger(130, secureRandom).toString(32);

        buttonConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bitreserveClient.beginAuthorization(MainActivity.this, CLIENT_ID, state);
            }
        });
    }

    @Override
    protected void onNewIntent(final Intent intent) {
        if (intent == null || intent.getAction() == null || !intent.getAction().equals("android.intent.action.VIEW")) {
            return;
        }

        bitreserveClient.completeAuthorization(intent.getData(), CLIENT_ID, CLIENT_SECRET, "authorization_code", state).then(new PromiseAction<AuthenticationResponse>() {
            @Override
            public void call(AuthenticationResponse authenticationResponse) {
                Intent intent = new Intent(MainActivity.this, UserActivity.class);

                intent.putExtra(UserActivity.BUNDLE_EXTRA_BEARER_TOKEN, authenticationResponse.getAccessToken());
                startActivity(intent);
                finish();
            }
        }).fail(new PromiseAction<Exception>() {
            @Override
            public void call(Exception e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}

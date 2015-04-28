package org.bitreserve.androidsdkdemo;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.darylteo.rx.promises.java.functions.PromiseAction;

import org.bitreserve.bitreserve_android_sdk.client.BitreserveClient;
import org.bitreserve.bitreserve_android_sdk.exception.TwoFactorAuthenticationRequiredException;
import org.bitreserve.bitreserve_android_sdk.model.AuthenticationRequest;
import org.bitreserve.bitreserve_android_sdk.model.AuthenticationResponse;

public class MainActivity extends ActionBarActivity {

    private Button buttonLogin;
    private EditText editTextOTP;
    private EditText editTextPassword;
    private EditText editTextUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        editTextOTP = (EditText) findViewById(R.id.editTextOTP);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextUsername = (EditText) findViewById(R.id.editTextUsername);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AuthenticationRequest authenticationRequest = new AuthenticationRequest("Bitreserve-android-sdk-demo");
                BitreserveClient bitreserveClient = new BitreserveClient();

                Toast.makeText(MainActivity.this, getResources().getString(R.string.org_bitreserve_user_activity_toast_getting_information), Toast.LENGTH_SHORT).show();

                bitreserveClient.authenticateUser(editTextOTP.getText().toString(), editTextUsername.getText().toString(), editTextPassword.getText().toString(), authenticationRequest)
                    .then(new PromiseAction<AuthenticationResponse>() {
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
                            if(e instanceof TwoFactorAuthenticationRequiredException){
                                editTextOTP.setVisibility(View.VISIBLE);
                            }
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
            }
        });
    }

}

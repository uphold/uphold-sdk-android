package org.bitreserve.androidsdkdemo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.darylteo.rx.promises.java.Promise;
import com.darylteo.rx.promises.java.functions.PromiseAction;
import com.darylteo.rx.promises.java.functions.RepromiseFunction;

import org.bitreserve.bitreserve_android_sdk.client.BitreserveClient;
import org.bitreserve.bitreserve_android_sdk.exception.BitreserveSdkNotInitializedException;
import org.bitreserve.bitreserve_android_sdk.model.Card;
import org.bitreserve.bitreserve_android_sdk.model.User;

import java.util.List;

public class UserActivity extends ActionBarActivity {

    public static final String BUNDLE_EXTRA_BEARER_TOKEN = "BUNDLE_EXTRA_BEARER_TOKEN";

    private TextView textViewUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        textViewUsername = (TextView) findViewById(R.id.textViewUsername);
        Bundle extras = getIntent().getExtras();
        String bearerToken = null;

        if (extras != null) {
            bearerToken = extras.getString(BUNDLE_EXTRA_BEARER_TOKEN);
        }

        Toast.makeText(UserActivity.this, getResources().getString(R.string.org_bitreserve_user_activity_toast_getting_information), Toast.LENGTH_SHORT).show();

        try {
            BitreserveClient bitreserveClient = new BitreserveClient(bearerToken);

            bitreserveClient.getUser().then(new RepromiseFunction<User, List<Card>>() {
                @Override
                public Promise<List<Card>> call(User user) {
                    textViewUsername.setText(getResources().getString(R.string.org_bitreserve_user_activity_welcome_message, user.getName()));

                    return user.getCards();
                }
            }).then(new PromiseAction<List<Card>>() {
                @Override
                public void call(List<Card> cards) {
                    textViewUsername.setText(getResources().getString(R.string.org_bitreserve_user_activity_cards_message, textViewUsername.getText(), cards.size()));
                }
            }).fail(new PromiseAction<Exception>() {
                @Override
                public void call(Exception e) {
                    textViewUsername.setText(e.getMessage());
                }
            });
        } catch (BitreserveSdkNotInitializedException e) {
            Log.d(UserActivity.class.toString(), e.getMessage());
        }
    }

}

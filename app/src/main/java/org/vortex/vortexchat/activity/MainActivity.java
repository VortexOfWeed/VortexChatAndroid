package org.vortex.vortexchat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;

import org.vortex.vortexchat.R;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    // Choose an arbitrary request code value
    private static final int RC_SIGN_IN = 123;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            // already signed in
            startActivity(new Intent(this, SuccessActivity.class)
                    .putExtra("my_token", "empty")
                    .putExtra("number", "empty"));
        } else {
            // not signed in
            AuthUI.IdpConfig phoneConfigWithDefaultNumber = new AuthUI.IdpConfig.PhoneBuilder()
                    .setDefaultCountryIso("in")
                    .build();

            startActivityForResult(
                    // Get an instance of AuthUI based on the default app
                    AuthUI.getInstance().createSignInIntentBuilder()
                            .setAvailableProviders(Arrays.asList(
                                    new AuthUI.IdpConfig.PhoneBuilder().build()))
                            .setTosUrl("https://superapp.example.com/terms-of-service.html")
                            .setPrivacyPolicyUrl("https://superapp.example.com/privacy-policy.html")
                            .setIsSmartLockEnabled(false) // Enable this on release
                            .build(),
                    RC_SIGN_IN);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse idpResponse = IdpResponse.fromResultIntent(data);
            startActivity(new Intent(this, SuccessActivity.class)
                    .putExtra("my_token", idpResponse.getIdpToken())
                    .putExtra("number", idpResponse.getPhoneNumber())
            );
        }
    }
}

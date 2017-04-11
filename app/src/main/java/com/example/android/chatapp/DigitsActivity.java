package com.example.android.chatapp;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.digits.sdk.android.AuthCallback;
import com.digits.sdk.android.Digits;
import com.digits.sdk.android.DigitsException;
import com.digits.sdk.android.DigitsSession;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;

import io.fabric.sdk.android.Fabric;

/**
 * Created by X on 4/11/2017.
 */

public class DigitsActivity extends Application {
    private static final String TWITTER_KEY = "XoqWKdrJgxx759sWXktKo9zeZ";
    private static final String TWITTER_SECRET = "8EMaULgVDZ232LL1F2QE7iPtzvof4c9yIu3AMdAHiWMWvQtCTo";
    private static AuthCallback authCallback;
    private DatabaseReference database;

    @Override
    public void onCreate() {
        super.onCreate();
        database = FirebaseDatabase.getInstance().getReference();
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Crashlytics(), new TwitterCore(authConfig), new Digits.Builder().build());

        authCallback = new AuthCallback() {
            @Override
            public void success(DigitsSession session, String phoneNumber) {
                // Do something with the session
                Toast.makeText(getApplicationContext(), "Authentication successful for "
                        + phoneNumber, Toast.LENGTH_LONG).show();
                writeNewUser("0008", phoneNumber, "test@me.com");
            }

            @Override
            public void failure(DigitsException exception) {
                // Do something on failure
                Log.d("Digits", "Sign in with Digits failure", exception);
            }
        };
    }

    private void writeNewUser(String userId, String name, String email) {
        database.child("users").child(userId).child(name).child("info").child("email").setValue(email);
    }

    public AuthCallback getAuthCallback(){
        return authCallback;
    }
}

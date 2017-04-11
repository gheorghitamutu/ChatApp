package com.example.android.chatapp;

import android.Manifest;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.digits.sdk.android.DigitsAuthButton;

public class LoginActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_READ_PHONE_CONTACTS = 0;
    private static final int MY_PERMISSIONS_REQUEST_RECEIVE_SMS = 1;
    private static String[] PERMISSIONS_CONTACT = {Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.RECEIVE_SMS};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        requestContactsPermissions(findViewById(R.id.auth_button));
        DigitsAuthButton digitsButton = (DigitsAuthButton) findViewById(R.id.auth_button);
        digitsButton.setCallback(((DigitsActivity) getApplication()).getAuthCallback());

    }

    private void requestContactsPermissions(View view) {
        // BEGIN_INCLUDE(contacts_permission_request)
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_PHONE_STATE)) {

            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // For example, if the request has been denied previously.
            Log.i("Failure", "Displaying contacts permission rationale to provide additional context.");

            // Display a SnackBar with an explanation and a button to trigger the request.
            Snackbar.make(view, "Contacts permissions are needed to demonstrate access",
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction("ok", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ActivityCompat
                                    .requestPermissions(LoginActivity.this, PERMISSIONS_CONTACT,
                                            MY_PERMISSIONS_REQUEST_READ_PHONE_CONTACTS);
                            ActivityCompat
                                    .requestPermissions(LoginActivity.this, PERMISSIONS_CONTACT,
                                            MY_PERMISSIONS_REQUEST_RECEIVE_SMS);
                        }
                    })
                    .show();
        } else {
            // Contact permissions have not been granted yet. Request them directly.
            ActivityCompat.requestPermissions(this, PERMISSIONS_CONTACT, MY_PERMISSIONS_REQUEST_READ_PHONE_CONTACTS);
            ActivityCompat.requestPermissions(this, PERMISSIONS_CONTACT, MY_PERMISSIONS_REQUEST_RECEIVE_SMS);
        }
        // END_INCLUDE(contacts_permission_request)
    }
}


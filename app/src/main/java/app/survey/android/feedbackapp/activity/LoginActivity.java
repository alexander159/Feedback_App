package app.survey.android.feedbackapp.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import app.survey.android.feedbackapp.R;
import app.survey.android.feedbackapp.util.FontManager;
import app.survey.android.feedbackapp.util.RequestController;
import app.survey.android.feedbackapp.util.ServerApi;
import app.survey.android.feedbackapp.util.SharedPrefs;

public class LoginActivity extends AppCompatActivity {
    public static final String TAG = LoginActivity.class.getName();

    private TextInputLayout inputLayoutUsername;
    private EditText username;
    private TextInputLayout inputLayoutPassword;
    private EditText password;
    private Button loginButton;
    private LinearLayout loginFieldContainer;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        inputLayoutUsername = (TextInputLayout) findViewById(R.id.input_layout_username);
        username = (EditText) findViewById(R.id.username);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);
        password = (EditText) findViewById(R.id.password);
        loginButton = (Button) findViewById(R.id.login_button);
        loginFieldContainer = (LinearLayout) findViewById(R.id.login_field_container);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        inputLayoutUsername.setTypeface(FontManager.getFont(FontManager.Fonts.TW_CENT_MT_REGULAR, this));
        username.setTypeface(FontManager.getFont(FontManager.Fonts.TW_CENT_MT_REGULAR, this));
        inputLayoutPassword.setTypeface(FontManager.getFont(FontManager.Fonts.TW_CENT_MT_REGULAR, this));
        password.setTypeface(FontManager.getFont(FontManager.Fonts.TW_CENT_MT_REGULAR, this));
        loginButton.setTypeface(FontManager.getFont(FontManager.Fonts.TW_CENT_MT_BOLD, this));

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });
    }

    private void attemptLogin() {
        // Reset errors.
        username.setError(null);
        password.setError(null);

        // Store values at the time of the login attempt.
        String usernameStr = username.getText().toString();
        String passwordStr = password.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(passwordStr)) {
            password.setError(getString(R.string.error_field_required));
            focusView = password;
            cancel = true;
        }

        // Check for a valid username
        if (TextUtils.isEmpty(usernameStr)) {
            username.setError(getString(R.string.error_field_required));
            focusView = username;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgressBar();

            String loginUrl = ServerApi.GET_FEEDBACK_LIST
                    .replace(ServerApi.USER_LOGIN, usernameStr)
                    .replace(ServerApi.USER_PASSWORD, passwordStr);

            JsonObjectRequest userLoginRequest = new JsonObjectRequest(loginUrl, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            VolleyLog.d(TAG, jsonObject.toString());
                            hideProgressBar();

                            if (parseJsonLoginReq(jsonObject)) {
                                Intent loginIntent = new Intent(LoginActivity.this, MainActivity.class);
                                loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(loginIntent);
                                overridePendingTransition(0, 0);
                            } else {
                                Toast.makeText(getApplicationContext(), "Username/password is incorrect!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            VolleyLog.d(TAG, "Error: " + error.getMessage());
                            hideProgressBar();

                            if (error.getMessage() == null) {
                                if (error.networkResponse != null) {
                                    Toast.makeText(getApplicationContext(), "Error: " + error.networkResponse.statusCode, Toast.LENGTH_SHORT).show();
                                } else {                                                                                                                                        //timeout error
                                    showConnectionErrorAlertDialog();
                                    //Toast.makeText(getApplicationContext(), "Error! Message = " + error.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            } else if (error.networkResponse == null && error.getMessage().contains("java.net.UnknownHostException")) {                                         //no connection
                                showConnectionErrorAlertDialog();
                                //Toast.makeText(getApplicationContext(), "Check your connection!", Toast.LENGTH_SHORT).show();
                            } else if (error.networkResponse == null && error.getMessage().contains("org.json.JSONException: End of input at character 0 of")) {                 //got empty json (for uor case it's invalid username/password
                                Toast.makeText(getApplicationContext(), "Username/password is incorrect!", Toast.LENGTH_SHORT).show();
                            } else {                                                                                                                                            //response error, code = error.networkResponse.statusCode
                                Toast.makeText(getApplicationContext(), "Error: " + (error.networkResponse != null ? error.networkResponse.statusCode : 0), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
            );

            RequestController.getInstance().addToRequestQueue(userLoginRequest, TAG);
        }
    }

    private boolean parseJsonLoginReq(JSONObject response) {
        try {
            response.getString("userid");
            response.getString("hospital_id");
            response.getJSONArray("feedbacks");

            SharedPreferences sPref = getSharedPreferences(SharedPrefs.PREFS_NAME, MODE_PRIVATE);             //save selected team
            SharedPreferences.Editor ed = sPref.edit();
            ed.putString(SharedPrefs.USER_ID, response.getString("userid"));
            ed.putString(SharedPrefs.HOSPITAL_ID, response.getString("hospital_id"));
            ed.putString(SharedPrefs.FEEDBACK_OFFLINE_JSON_ARRAY, response.getJSONArray("feedbacks").toString());
            ed.commit();
            return true;
        } catch (JSONException e1) {
            e1.printStackTrace();
            return false;
        }
    }

    private void showProgressBar() {
        if (progressBar != null) {
            loginFieldContainer.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    private void hideProgressBar() {
        if (progressBar != null) {
            loginFieldContainer.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }

    public void showConnectionErrorAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
        builder.setMessage(getString(R.string.check_connection_dialog));
        builder.setNegativeButton(getString(R.string.check_connection_dialog_close), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setCancelable(false);
        builder.show();
    }
}


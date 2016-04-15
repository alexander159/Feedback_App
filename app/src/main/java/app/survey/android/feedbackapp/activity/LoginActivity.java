package app.survey.android.feedbackapp.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import app.survey.android.feedbackapp.R;
import app.survey.android.feedbackapp.util.ErrorGuiResponder;
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
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        activity = this;

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

        SharedPreferences sPref = getSharedPreferences(SharedPrefs.PREFS_NAME, Context.MODE_PRIVATE);
        if (sPref.getString(SharedPrefs.USER_ID, null) != null &&
                sPref.getString(SharedPrefs.HOSPITAL_ID, null) != null &&
                sPref.getString(SharedPrefs.FEEDBACK_OFFLINE_JSON_ARRAY, null) != null) {
            Intent loginIntent = new Intent(LoginActivity.this, MainActivity.class);
            loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(loginIntent);
            overridePendingTransition(0, 0);
        }
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
                                ErrorGuiResponder.showAlertDialog(activity, ErrorGuiResponder.USERNAME_PASSWORD_INCORRECT);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            VolleyLog.d(TAG, "Error: " + error.getMessage());
                            hideProgressBar();

                            //got empty json, in our case it's invalid username/pass
                            if (ErrorGuiResponder.getVolleyErrorType(error).equals(ErrorGuiResponder.PARSE_ERROR)) {
                                ErrorGuiResponder.showAlertDialog(activity, ErrorGuiResponder.USERNAME_PASSWORD_INCORRECT);
                            } else {
                                ErrorGuiResponder.showAlertDialog(activity, ErrorGuiResponder.getVolleyErrorType(error));
                            }
                        }
                    }
            );

            RequestController.getInstance().addToRequestQueue(userLoginRequest, TAG);
        }
    }

    private boolean parseJsonLoginReq(JSONObject response) {
        try {
            response.getString(ServerApi.Login.USER_ID);
            response.getString(ServerApi.Login.HOSPITAL_ID);
            response.getJSONArray(ServerApi.Login.FEEDBACKS);

            SharedPreferences sPref = getSharedPreferences(SharedPrefs.PREFS_NAME, MODE_PRIVATE);
            SharedPreferences.Editor ed = sPref.edit();
            ed.putString(SharedPrefs.USER_ID, response.getString(ServerApi.Login.USER_ID));
            ed.putString(SharedPrefs.HOSPITAL_ID, response.getString(ServerApi.Login.HOSPITAL_ID));
            ed.putString(SharedPrefs.FEEDBACK_OFFLINE_JSON_ARRAY, response.getJSONArray(ServerApi.Login.FEEDBACKS).toString());
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
}


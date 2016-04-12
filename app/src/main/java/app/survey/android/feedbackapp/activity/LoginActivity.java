package app.survey.android.feedbackapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import app.survey.android.feedbackapp.R;
import app.survey.android.feedbackapp.util.FontManager;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextInputLayout inputLayoutUsername = (TextInputLayout) findViewById(R.id.input_layout_username);
        EditText username = (EditText) findViewById(R.id.username);
        TextInputLayout inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);
        EditText password = (EditText) findViewById(R.id.password);
        Button loginButton = (Button) findViewById(R.id.login_button);

        inputLayoutUsername.setTypeface(FontManager.getFont(FontManager.Fonts.TW_CENT_MT_REGULAR, this));
        username.setTypeface(FontManager.getFont(FontManager.Fonts.TW_CENT_MT_REGULAR, this));
        inputLayoutPassword.setTypeface(FontManager.getFont(FontManager.Fonts.TW_CENT_MT_REGULAR, this));
        password.setTypeface(FontManager.getFont(FontManager.Fonts.TW_CENT_MT_REGULAR, this));
        loginButton.setTypeface(FontManager.getFont(FontManager.Fonts.TW_CENT_MT_BOLD, this));

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(LoginActivity.this, MainActivity.class);
                loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(loginIntent);
                overridePendingTransition(0, 0);
            }
        });
    }
}


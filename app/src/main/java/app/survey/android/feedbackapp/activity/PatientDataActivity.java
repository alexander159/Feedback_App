package app.survey.android.feedbackapp.activity;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import app.survey.android.feedbackapp.R;
import app.survey.android.feedbackapp.fragment.PatientDetailsFragment;
import app.survey.android.feedbackapp.fragment.PatientIpnoFragment;
import app.survey.android.feedbackapp.fragment.PatientTypeFragment;
import app.survey.android.feedbackapp.model.ServerJSON.Feedback;
import app.survey.android.feedbackapp.model.ServerJSON.Patient;
import app.survey.android.feedbackapp.responder.PatientDetailsFragmentResponder;
import app.survey.android.feedbackapp.responder.PatientIpnoFragmentResponder;
import app.survey.android.feedbackapp.responder.PatientTypeFragmentResponder;
import app.survey.android.feedbackapp.util.FontManager;
import app.survey.android.feedbackapp.util.RequestController;
import app.survey.android.feedbackapp.util.SharedPrefs;

public class PatientDataActivity extends AppCompatActivity
        implements PatientTypeFragmentResponder,
        PatientIpnoFragmentResponder,
        PatientDetailsFragmentResponder {

    public Feedback selectedSurvey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_data);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        changeToolbarTitle("");

        TextView offlineModeWarningTitle = (TextView) findViewById(R.id.offline_mode_warning_title);

        if (!RequestController.getInstance().isNetworkAvailable()) {
            //set warning about no connection
            offlineModeWarningTitle.setTypeface(FontManager.getFont(FontManager.Fonts.TW_CENT_MT_BOLD, this));
            offlineModeWarningTitle.setVisibility(View.VISIBLE);
        }

        selectedSurvey = (Feedback) getIntent().getSerializableExtra(SurveyActivity.SURVEY);

        setNextActiveFragment(new PatientTypeFragment(), false);
    }

    private void changeToolbarTitle(String title) {
        if (title == null || title.isEmpty()) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        } else {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setTitle(title);
        }
    }

    @SuppressWarnings("ResourceType")
    private void setNextActiveFragment(Fragment fragment, boolean addToBackStack) {
        if (addToBackStack) {
            getFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left)
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
        } else {
            getFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left)
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        }
    }

    @Override
    public void onAddNewPatientPressed() {
        changeToolbarTitle(getResources().getString(R.string.fragment_patient_details_toolbar_title));
        setNextActiveFragment(new PatientDetailsFragment(), true);
    }

    @Override
    public void onUseExistingPatientPressed() {
        changeToolbarTitle("");
        setNextActiveFragment(new PatientIpnoFragment(), true);
    }

    @Override
    public void onNextPatientIpnoPressed(Patient patient) {
        changeToolbarTitle(getResources().getString(R.string.fragment_patient_details_toolbar_title));
        setNextActiveFragment(PatientDetailsFragment.newInstance(patient), true);
    }

    @Override
    public void onDonePatientDetailsButtonPressed() {
        Intent surveyIntent = new Intent(PatientDataActivity.this, SurveyActivity.class);
        surveyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        surveyIntent.putExtra(SurveyActivity.SURVEY, selectedSurvey);
        startActivity(surveyIntent);
        overridePendingTransition(0, 0);
    }

    //PatientIpnoFragmentResponder, PatientDetailsFragmentResponder
    @Override
    public void onNullPreferencesPatientDetails() {
        Toast.makeText(this, getString(R.string.error_null_preferences), Toast.LENGTH_LONG).show();

        SharedPreferences sPref = getSharedPreferences(SharedPrefs.PREFS_NAME, Context.MODE_PRIVATE);
        sPref.edit().clear().commit();
        //move to login screen
        Intent loginIntent = new Intent(PatientDataActivity.this, LoginActivity.class);
        loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
        overridePendingTransition(0, 0);
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
        } else {
            changeToolbarTitle("");
            getFragmentManager().popBackStack();
        }
    }
}

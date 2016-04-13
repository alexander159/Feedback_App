package app.survey.android.feedbackapp.activity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import app.survey.android.feedbackapp.R;
import app.survey.android.feedbackapp.fragment.PatientDetailsFragment;
import app.survey.android.feedbackapp.fragment.PatientIpnoFragment;
import app.survey.android.feedbackapp.fragment.PatientTypeFragment;
import app.survey.android.feedbackapp.responder.PatientDetailsFragmentResponder;
import app.survey.android.feedbackapp.responder.PatientIpnoFragmentResponder;
import app.survey.android.feedbackapp.responder.PatientTypeFragmentResponder;

public class PatientDataActivity extends AppCompatActivity
        implements PatientTypeFragmentResponder,
        PatientIpnoFragmentResponder,
        PatientDetailsFragmentResponder {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_data);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        changeToolbarTitle("");

        setNextActiveFragment(new PatientTypeFragment());
    }

    private void changeToolbarTitle(String title) {
        if (title.isEmpty()) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        } else {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setTitle(title);
        }
    }

    @SuppressWarnings("ResourceType")
    private void setNextActiveFragment(Fragment fragment) {
        getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left)
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onAddNewPatientPressed() {
        changeToolbarTitle(getResources().getString(R.string.fragment_patient_details_toolbar_title));
        setNextActiveFragment(new PatientDetailsFragment());
    }

    @Override
    public void onUseExistingPatientPressed() {
        changeToolbarTitle("");
        setNextActiveFragment(new PatientIpnoFragment());
    }

    @Override
    public void onNextPatientIpnoPressed() {
        changeToolbarTitle(getResources().getString(R.string.fragment_patient_details_toolbar_title));
        setNextActiveFragment(new PatientDetailsFragment());
    }

    @Override
    public void onDonePatientDetailsButtonPressed() {
        Intent surveyIntent = new Intent(PatientDataActivity.this, SurveyActivity.class);
        surveyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(surveyIntent);
        overridePendingTransition(0, 0);
    }
}

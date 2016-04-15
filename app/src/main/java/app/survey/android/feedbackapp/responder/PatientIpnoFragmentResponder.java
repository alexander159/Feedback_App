package app.survey.android.feedbackapp.responder;

import app.survey.android.feedbackapp.model.ServerJSON.Patient;

public interface PatientIpnoFragmentResponder {
    void onNextPatientIpnoPressed(Patient patient);

    void onNullPreferencesPatientDetails();
}

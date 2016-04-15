package app.survey.android.feedbackapp.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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
import app.survey.android.feedbackapp.model.ServerJSON.Patient;
import app.survey.android.feedbackapp.responder.PatientIpnoFragmentResponder;
import app.survey.android.feedbackapp.util.ErrorGuiResponder;
import app.survey.android.feedbackapp.util.FontManager;
import app.survey.android.feedbackapp.util.RequestController;
import app.survey.android.feedbackapp.util.ServerApi;
import app.survey.android.feedbackapp.util.SharedPrefs;

public class PatientIpnoFragment extends Fragment {
    public static final String TAG = PatientIpnoFragment.class.getName();

    private LinearLayout ipnoContainer;
    private EditText ipno;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_patient_ipno, container, false);

        ipnoContainer = (LinearLayout) view.findViewById(R.id.ipno_container);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        TextInputLayout inputLayoutIpno = (TextInputLayout) view.findViewById(R.id.input_layout_ipno);
        ipno = (EditText) view.findViewById(R.id.ipno);
        Button nextButton = (Button) view.findViewById(R.id.next_button);

        inputLayoutIpno.setTypeface(FontManager.getFont(FontManager.Fonts.TW_CENT_MT_REGULAR, getActivity().getApplicationContext()));
        ipno.setTypeface(FontManager.getFont(FontManager.Fonts.TW_CENT_MT_REGULAR, getActivity().getApplicationContext()));
        nextButton.setTypeface(FontManager.getFont(FontManager.Fonts.TW_CENT_MT_BOLD, getActivity().getApplicationContext()));

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard(ipno, getActivity());
                attemptLoadPatientInfo();
            }
        });

        return view;
    }

    private void attemptLoadPatientInfo() {
        ipno.setError(null);
        String ipnoStr = ipno.getText().toString().trim();

        if (TextUtils.isEmpty(ipnoStr)) {
            ipno.setError(getString(R.string.error_field_required));
            ipno.requestFocus();
        } else {
            String hospital_id;
            SharedPreferences sPref = getActivity().getSharedPreferences(SharedPrefs.PREFS_NAME, Context.MODE_PRIVATE);
            if ((hospital_id = sPref.getString(SharedPrefs.HOSPITAL_ID, null)) == null) {
                //move to the login screen
                PatientIpnoFragmentResponder responder = (PatientIpnoFragmentResponder) getActivity();
                responder.onNullPreferencesPatientDetails();
                return;
            }

            showProgressBar();

            String registerPatientUrl = ServerApi.LOAD_PATIENT_INFO
                    .replace(ServerApi.ParameterValues.PATIENT_HOSPITAL_ID, hospital_id)
                    .replace(ServerApi.ParameterValues.PATIENT_IP_NO, ipno.getText().toString().trim());

            JsonObjectRequest loadPatientInfo = new JsonObjectRequest(registerPatientUrl, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            VolleyLog.d(TAG, jsonObject.toString());
                            hideProgressBar();

                            Patient patient = parsePatientJson(jsonObject);
                            if (patient == null) {
                                ErrorGuiResponder.showAlertDialog(getActivity(), ErrorGuiResponder.PARSE_PATIENT_INFO_ERROR);
                            } else {
                                //show all patient info
                                PatientIpnoFragmentResponder responder = (PatientIpnoFragmentResponder) getActivity();
                                responder.onNextPatientIpnoPressed(patient);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            VolleyLog.d(TAG, "Error: " + error.getMessage());
                            hideProgressBar();

                            //got empty json, in our case it's patient not found
                            if (ErrorGuiResponder.getVolleyErrorType(error).equals(ErrorGuiResponder.PARSE_ERROR)) {
                                ErrorGuiResponder.showAlertDialog(getActivity(), ErrorGuiResponder.PATIENT_IP_NO_NOT_FOUND_ERROR);
                            } else {
                                ErrorGuiResponder.showAlertDialog(getActivity(), ErrorGuiResponder.getVolleyErrorType(error));
                            }
                        }
                    }
            );

            RequestController.getInstance().addToRequestQueue(loadPatientInfo, TAG);
        }
    }

    @Nullable
    private Patient parsePatientJson(JSONObject jsonPatientObj) {
        try {
            return new Patient(
                    jsonPatientObj.getLong(ServerApi.PatientJSON.ID),
                    jsonPatientObj.getLong(ServerApi.PatientJSON.HOSPITAL_ID),
                    jsonPatientObj.getString(ServerApi.PatientJSON.NAME),
                    jsonPatientObj.getInt(ServerApi.PatientJSON.AGE),
                    jsonPatientObj.getString(ServerApi.PatientJSON.IP_NO),
                    jsonPatientObj.getString(ServerApi.PatientJSON.EMAIL),
                    jsonPatientObj.getString(ServerApi.PatientJSON.PHONE),
                    Patient.getCameAsFromString(jsonPatientObj.getString(ServerApi.PatientJSON.CAME_AS)),
                    Patient.getSexFromString(jsonPatientObj.getString(ServerApi.PatientJSON.SEX)),
                    jsonPatientObj.getString(ServerApi.PatientJSON.WARD_NO),
                    jsonPatientObj.getString(ServerApi.PatientJSON.BED_NO)
            );
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void showProgressBar() {
        if (progressBar != null) {
            ipnoContainer.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    private void hideProgressBar() {
        if (progressBar != null) {
            ipnoContainer.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }

    public void hideSoftKeyboard(EditText editText, Activity parent) {
        editText.clearFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) parent.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(parent.getCurrentFocus().getWindowToken(), 0);
    }
}
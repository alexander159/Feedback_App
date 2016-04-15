package app.survey.android.feedbackapp.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import app.survey.android.feedbackapp.R;
import app.survey.android.feedbackapp.responder.PatientDetailsFragmentResponder;
import app.survey.android.feedbackapp.util.ErrorGuiResponder;
import app.survey.android.feedbackapp.util.FontManager;
import app.survey.android.feedbackapp.util.RequestController;
import app.survey.android.feedbackapp.util.ServerApi;
import app.survey.android.feedbackapp.util.SharedPrefs;

public class PatientDetailsFragment extends Fragment {
    public static final String TAG = PatientDetailsFragment.class.getName();

    private EditText name;
    private EditText age;
    private EditText ipno;
    private EditText email;
    private CheckBox cameAsPatientCheck;
    private CheckBox cameAsRelativeCheck;
    private CheckBox cameAsVisitorCheck;
    private CheckBox sexMaleCheck;
    private CheckBox sexFemaleCheck;
    private CheckBox sexOthersCheck;
    private EditText phone;
    private EditText ward;
    private EditText bedNo;
    private ProgressBar progressBar;
    private FloatingActionButton fab;
    private ScrollView detailsScrollView;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_patient_details, container, false);

        detailsScrollView = (ScrollView) view.findViewById(R.id.details_scrollview);
        TextInputLayout inputLayoutName = (TextInputLayout) view.findViewById(R.id.input_layout_name);
        name = (EditText) view.findViewById(R.id.name);
        TextInputLayout inputLayoutAge = (TextInputLayout) view.findViewById(R.id.input_layout_age);
        age = (EditText) view.findViewById(R.id.age);
        TextInputLayout inputLayoutIpno = (TextInputLayout) view.findViewById(R.id.input_layout_ipno);
        ipno = (EditText) view.findViewById(R.id.ipno);
        TextInputLayout inputLayoutEmail = (TextInputLayout) view.findViewById(R.id.input_layout_email);
        email = (EditText) view.findViewById(R.id.email);
        TextView cameAsTitle = (TextView) view.findViewById(R.id.came_as_title);
        cameAsPatientCheck = (CheckBox) view.findViewById(R.id.came_as_patient_check);
        cameAsRelativeCheck = (CheckBox) view.findViewById(R.id.came_as_relative_check);
        cameAsVisitorCheck = (CheckBox) view.findViewById(R.id.came_as_visitor_check);
        TextView sexTitle = (TextView) view.findViewById(R.id.sex_title);
        sexMaleCheck = (CheckBox) view.findViewById(R.id.sex_male_check);
        sexFemaleCheck = (CheckBox) view.findViewById(R.id.sex_female_check);
        sexOthersCheck = (CheckBox) view.findViewById(R.id.sex_others_check);
        TextInputLayout inputLayoutPhone = (TextInputLayout) view.findViewById(R.id.input_layout_phone);
        phone = (EditText) view.findViewById(R.id.phone);
        TextInputLayout inputLayoutWard = (TextInputLayout) view.findViewById(R.id.input_layout_ward);
        ward = (EditText) view.findViewById(R.id.ward);
        TextInputLayout inputLayoutBedNo = (TextInputLayout) view.findViewById(R.id.input_layout_bed_no);
        bedNo = (EditText) view.findViewById(R.id.bed_no);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);

        inputLayoutName.setTypeface(FontManager.getFont(FontManager.Fonts.TW_CENT_MT_REGULAR, getActivity().getApplicationContext()));
        name.setTypeface(FontManager.getFont(FontManager.Fonts.TW_CENT_MT_REGULAR, getActivity().getApplicationContext()));
        inputLayoutAge.setTypeface(FontManager.getFont(FontManager.Fonts.TW_CENT_MT_REGULAR, getActivity().getApplicationContext()));
        age.setTypeface(FontManager.getFont(FontManager.Fonts.TW_CENT_MT_REGULAR, getActivity().getApplicationContext()));
        inputLayoutIpno.setTypeface(FontManager.getFont(FontManager.Fonts.TW_CENT_MT_REGULAR, getActivity().getApplicationContext()));
        ipno.setTypeface(FontManager.getFont(FontManager.Fonts.TW_CENT_MT_REGULAR, getActivity().getApplicationContext()));
        inputLayoutEmail.setTypeface(FontManager.getFont(FontManager.Fonts.TW_CENT_MT_REGULAR, getActivity().getApplicationContext()));
        email.setTypeface(FontManager.getFont(FontManager.Fonts.TW_CENT_MT_REGULAR, getActivity().getApplicationContext()));
        cameAsTitle.setTypeface(FontManager.getFont(FontManager.Fonts.TW_CENT_MT_REGULAR, getActivity().getApplicationContext()));
        cameAsPatientCheck.setTypeface(FontManager.getFont(FontManager.Fonts.TW_CENT_MT_REGULAR, getActivity().getApplicationContext()));
        cameAsRelativeCheck.setTypeface(FontManager.getFont(FontManager.Fonts.TW_CENT_MT_REGULAR, getActivity().getApplicationContext()));
        cameAsVisitorCheck.setTypeface(FontManager.getFont(FontManager.Fonts.TW_CENT_MT_REGULAR, getActivity().getApplicationContext()));
        sexTitle.setTypeface(FontManager.getFont(FontManager.Fonts.TW_CENT_MT_REGULAR, getActivity().getApplicationContext()));
        sexMaleCheck.setTypeface(FontManager.getFont(FontManager.Fonts.TW_CENT_MT_REGULAR, getActivity().getApplicationContext()));
        sexFemaleCheck.setTypeface(FontManager.getFont(FontManager.Fonts.TW_CENT_MT_REGULAR, getActivity().getApplicationContext()));
        sexOthersCheck.setTypeface(FontManager.getFont(FontManager.Fonts.TW_CENT_MT_REGULAR, getActivity().getApplicationContext()));
        inputLayoutPhone.setTypeface(FontManager.getFont(FontManager.Fonts.TW_CENT_MT_REGULAR, getActivity().getApplicationContext()));
        phone.setTypeface(FontManager.getFont(FontManager.Fonts.TW_CENT_MT_REGULAR, getActivity().getApplicationContext()));
        inputLayoutWard.setTypeface(FontManager.getFont(FontManager.Fonts.TW_CENT_MT_REGULAR, getActivity().getApplicationContext()));
        ward.setTypeface(FontManager.getFont(FontManager.Fonts.TW_CENT_MT_REGULAR, getActivity().getApplicationContext()));
        inputLayoutBedNo.setTypeface(FontManager.getFont(FontManager.Fonts.TW_CENT_MT_REGULAR, getActivity().getApplicationContext()));
        bedNo.setTypeface(FontManager.getFont(FontManager.Fonts.TW_CENT_MT_REGULAR, getActivity().getApplicationContext()));

        cameAsPatientCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard(cameAsPatientCheck, getActivity());
                cameAsPatientCheck.setChecked(true);
                cameAsRelativeCheck.setChecked(false);
                cameAsVisitorCheck.setChecked(false);
            }
        });
        cameAsRelativeCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard(cameAsRelativeCheck, getActivity());
                cameAsPatientCheck.setChecked(false);
                cameAsRelativeCheck.setChecked(true);
                cameAsVisitorCheck.setChecked(false);
            }
        });
        cameAsVisitorCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard(cameAsVisitorCheck, getActivity());
                cameAsPatientCheck.setChecked(false);
                cameAsRelativeCheck.setChecked(false);
                cameAsVisitorCheck.setChecked(true);
            }
        });

        sexMaleCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard(sexMaleCheck, getActivity());
                sexMaleCheck.setChecked(true);
                sexFemaleCheck.setChecked(false);
                sexOthersCheck.setChecked(false);
            }
        });
        sexFemaleCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard(sexFemaleCheck, getActivity());
                sexMaleCheck.setChecked(false);
                sexFemaleCheck.setChecked(true);
                sexOthersCheck.setChecked(false);
            }
        });
        sexOthersCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard(sexOthersCheck, getActivity());
                sexMaleCheck.setChecked(false);
                sexFemaleCheck.setChecked(false);
                sexOthersCheck.setChecked(true);
            }
        });

        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isAnyFieldEmpty()) {
                    Snackbar.make(view, R.string.fragment_patient_details_empty_fields_snackbar, Snackbar.LENGTH_LONG)
                            .setAction(R.string.fragment_patient_details_close_button_snackbar, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //shackbar closes itself on press by default
                                }
                            })
                            .setActionTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.colorPrimaryLight))
                            .show();
                } else {
                    attemptRegisterPatient();
                }
            }
        });

        return view;
    }

    private void attemptRegisterPatient() {
        String hospital_id;
        SharedPreferences sPref = getActivity().getSharedPreferences(SharedPrefs.PREFS_NAME, Context.MODE_PRIVATE);
        if ((hospital_id = sPref.getString(SharedPrefs.HOSPITAL_ID, null)) == null) {
            //move to the login screen
            PatientDetailsFragmentResponder responder = (PatientDetailsFragmentResponder) getActivity();
            responder.onNullPreferencesPatientDetails();
            return;
        }

        String registerPatientUrl = ServerApi.ADD_NEW_PATIENT
                .replace(ServerApi.PatientJSON.HOSPITAL_ID, hospital_id)
                .replace(ServerApi.PatientJSON.NAME, name.getText().toString().trim())
                .replace(ServerApi.PatientJSON.AGE, age.getText().toString().trim())
                .replace(ServerApi.PatientJSON.IP_NO, ipno.getText().toString().trim())
                .replace(ServerApi.PatientJSON.EMAIL, email.getText().toString().trim())
                .replace(ServerApi.PatientJSON.PHONE, phone.getText().toString().trim())
                .replace(ServerApi.PatientJSON.WARD_NO, ward.getText().toString().trim())
                .replace(ServerApi.PatientJSON.BED_NO, bedNo.getText().toString().trim());

        if (cameAsPatientCheck.isChecked()) {
            registerPatientUrl = registerPatientUrl.replace(ServerApi.PatientJSON.CAME_AS, ServerApi.PatientJSON.CAME_AS_VALUE.PATIENT);
        } else if (cameAsRelativeCheck.isChecked()) {
            registerPatientUrl = registerPatientUrl.replace(ServerApi.PatientJSON.CAME_AS, ServerApi.PatientJSON.CAME_AS_VALUE.RELATIVE);
        } else if (cameAsVisitorCheck.isChecked()) {
            registerPatientUrl = registerPatientUrl.replace(ServerApi.PatientJSON.CAME_AS, ServerApi.PatientJSON.CAME_AS_VALUE.VISITOR);
        }

        if (sexMaleCheck.isChecked()) {
            registerPatientUrl = registerPatientUrl.replace(ServerApi.PatientJSON.SEX, ServerApi.PatientJSON.SEX_VALUE.MALE);
        } else if (sexFemaleCheck.isChecked()) {
            registerPatientUrl = registerPatientUrl.replace(ServerApi.PatientJSON.SEX, ServerApi.PatientJSON.SEX_VALUE.FEMALE);
        } else if (sexFemaleCheck.isChecked()) {
            registerPatientUrl = registerPatientUrl.replace(ServerApi.PatientJSON.SEX, ServerApi.PatientJSON.SEX_VALUE.OTHERS);
        }

        JsonObjectRequest registerPatientRequest = new JsonObjectRequest(registerPatientUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        VolleyLog.d(TAG, jsonObject.toString());
                        hideProgressBar();

                        //move to the selected survey
                        PatientDetailsFragmentResponder responder = (PatientDetailsFragmentResponder) getActivity();
                        responder.onDonePatientDetailsButtonPressed();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                        hideProgressBar();

                        //got empty json, in our case it's updated/added user
                        if (ErrorGuiResponder.getVolleyErrorType(error).equals(ErrorGuiResponder.PARSE_ERROR)) {
                            //move to the selected survey
                            PatientDetailsFragmentResponder responder = (PatientDetailsFragmentResponder) getActivity();
                            responder.onDonePatientDetailsButtonPressed();
                        } else {
                            ErrorGuiResponder.showAlertDialog(getActivity(), ErrorGuiResponder.getVolleyErrorType(error));
                        }
                    }
                }
        );

        RequestController.getInstance().addToRequestQueue(registerPatientRequest, TAG);
    }

    private boolean isAnyFieldEmpty() {
        return name.getText().toString().trim().isEmpty() ||
                age.getText().toString().trim().isEmpty() ||
                ipno.getText().toString().trim().isEmpty() ||
                email.getText().toString().trim().isEmpty() ||
                phone.getText().toString().trim().isEmpty() ||
                ward.getText().toString().trim().isEmpty() ||
                bedNo.getText().toString().trim().isEmpty() ||
                (!cameAsPatientCheck.isChecked() && !cameAsRelativeCheck.isChecked() && !cameAsVisitorCheck.isChecked()) ||
                (!sexMaleCheck.isChecked() && !sexFemaleCheck.isChecked() && !sexOthersCheck.isChecked());
    }

    private void showProgressBar() {
        if (progressBar != null) {
            detailsScrollView.setVisibility(View.GONE);
            fab.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    private void hideProgressBar() {
        if (progressBar != null) {
            detailsScrollView.setVisibility(View.VISIBLE);
            fab.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }

    private void hideSoftKeyboard(CheckBox checkBox, Activity parent) {
        //set focus to the pressed checkbox
        checkBox.setFocusableInTouchMode(true);
        checkBox.requestFocus();
        //hide soft keyboard
        InputMethodManager inputMethodManager = (InputMethodManager) parent.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(parent.getCurrentFocus().getWindowToken(), 0);
        //clear focus of the pressed checkbox
        checkBox.setFocusableInTouchMode(false);
        checkBox.clearFocus();
    }
}

package app.survey.android.feedbackapp.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import app.survey.android.feedbackapp.R;
import app.survey.android.feedbackapp.responder.PatientDetailsFragmentResponder;
import app.survey.android.feedbackapp.util.FontManager;

public class PatientDetailsFragment extends Fragment {

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

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_patient_details, container, false);

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
                cameAsPatientCheck.setChecked(true);
                cameAsRelativeCheck.setChecked(false);
                cameAsVisitorCheck.setChecked(false);
            }
        });
        cameAsRelativeCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameAsPatientCheck.setChecked(false);
                cameAsRelativeCheck.setChecked(true);
                cameAsVisitorCheck.setChecked(false);
            }
        });
        cameAsVisitorCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameAsPatientCheck.setChecked(false);
                cameAsRelativeCheck.setChecked(false);
                cameAsVisitorCheck.setChecked(true);
            }
        });

        sexMaleCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sexMaleCheck.setChecked(true);
                sexFemaleCheck.setChecked(false);
                sexOthersCheck.setChecked(false);
            }
        });
        sexFemaleCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sexMaleCheck.setChecked(false);
                sexFemaleCheck.setChecked(true);
                sexOthersCheck.setChecked(false);
            }
        });
        sexOthersCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sexMaleCheck.setChecked(false);
                sexFemaleCheck.setChecked(false);
                sexOthersCheck.setChecked(true);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
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
                    //move to the selected survey
                    PatientDetailsFragmentResponder responder = (PatientDetailsFragmentResponder) getActivity();
                    responder.onDonePatientDetailsButtonPressed();
                }
            }
        });

        return view;
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
}

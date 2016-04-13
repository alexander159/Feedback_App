package app.survey.android.feedbackapp.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import app.survey.android.feedbackapp.R;
import app.survey.android.feedbackapp.util.FontManager;

public class PatientDetailsFragment extends Fragment {
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_patient_details, container, false);

        TextInputLayout inputLayoutName = (TextInputLayout) view.findViewById(R.id.input_layout_name);
        EditText name = (EditText) view.findViewById(R.id.name);
        TextInputLayout inputLayoutAge = (TextInputLayout) view.findViewById(R.id.input_layout_age);
        EditText age = (EditText) view.findViewById(R.id.age);
        TextInputLayout inputLayoutIpno = (TextInputLayout) view.findViewById(R.id.input_layout_ipno);
        EditText ipno = (EditText) view.findViewById(R.id.ipno);
        TextInputLayout inputLayoutEmail = (TextInputLayout) view.findViewById(R.id.input_layout_email);
        EditText email = (EditText) view.findViewById(R.id.email);
        TextView cameAsTitle = (TextView) view.findViewById(R.id.came_as_title);
        CheckBox cameAsPatientCheck = (CheckBox) view.findViewById(R.id.came_as_patient_check);
        CheckBox cameAsRelativeCheck = (CheckBox) view.findViewById(R.id.came_as_relative_check);
        CheckBox cameAsVisitorCheck = (CheckBox) view.findViewById(R.id.came_as_visitor_check);
        TextView sexTitle = (TextView) view.findViewById(R.id.sex_title);
        CheckBox sexMaleCheck = (CheckBox) view.findViewById(R.id.sex_male_check);
        CheckBox sexFemaleCheck = (CheckBox) view.findViewById(R.id.sex_female_check);
        CheckBox sexOthersCheck = (CheckBox) view.findViewById(R.id.sex_others_check);
        TextInputLayout inputLayoutPhone = (TextInputLayout) view.findViewById(R.id.input_layout_phone);
        EditText phone = (EditText) view.findViewById(R.id.phone);
        TextInputLayout inputLayoutWard = (TextInputLayout) view.findViewById(R.id.input_layout_ward);
        EditText ward = (EditText) view.findViewById(R.id.ward);
        TextInputLayout inputLayoutBedNo = (TextInputLayout) view.findViewById(R.id.input_layout_bed_no);
        EditText bedNo = (EditText) view.findViewById(R.id.bed_no);

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

        return view;
    }
}

package app.survey.android.feedbackapp.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import app.survey.android.feedbackapp.R;
import app.survey.android.feedbackapp.responder.PatientIpnoFragmentResponder;
import app.survey.android.feedbackapp.util.FontManager;

public class PatientIpnoFragment extends Fragment {
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_patient_ipno, container, false);

        TextInputLayout input_layout_ipno = (TextInputLayout) view.findViewById(R.id.input_layout_ipno);
        EditText ipno = (EditText) view.findViewById(R.id.ipno);
        Button nextButton = (Button) view.findViewById(R.id.next_button);

        input_layout_ipno.setTypeface(FontManager.getFont(FontManager.Fonts.TW_CENT_MT_REGULAR, getActivity().getApplicationContext()));
        ipno.setTypeface(FontManager.getFont(FontManager.Fonts.TW_CENT_MT_REGULAR, getActivity().getApplicationContext()));
        nextButton.setTypeface(FontManager.getFont(FontManager.Fonts.TW_CENT_MT_BOLD, getActivity().getApplicationContext()));

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PatientIpnoFragmentResponder responder = (PatientIpnoFragmentResponder) getActivity();
                responder.onNextPatientIpnoPressed();
            }
        });

        return view;
    }
}
package app.survey.android.feedbackapp.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import app.survey.android.feedbackapp.R;
import app.survey.android.feedbackapp.responder.PatientTypeFragmentResponder;
import app.survey.android.feedbackapp.util.FontManager;

public class PatientTypeFragment extends Fragment {
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_patient_type, container, false);

        Button addNewPatientButton = (Button) view.findViewById(R.id.add_new_patient_button);
        Button userExistingPatientButton = (Button) view.findViewById(R.id.use_existing_patient_button);

        addNewPatientButton.setTypeface(FontManager.getFont(FontManager.Fonts.TW_CENT_MT_BOLD, getActivity().getApplicationContext()));
        userExistingPatientButton.setTypeface(FontManager.getFont(FontManager.Fonts.TW_CENT_MT_BOLD, getActivity().getApplicationContext()));

        addNewPatientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PatientTypeFragmentResponder responder = (PatientTypeFragmentResponder) getActivity();
                responder.onAddNewPatientPressed();
            }
        });

        userExistingPatientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PatientTypeFragmentResponder responder = (PatientTypeFragmentResponder) getActivity();
                responder.onUseExistingPatientPressed();
            }
        });


/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
 */

        return view;
    }
}

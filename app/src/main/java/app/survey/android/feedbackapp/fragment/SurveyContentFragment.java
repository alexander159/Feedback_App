package app.survey.android.feedbackapp.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import app.survey.android.feedbackapp.R;
import app.survey.android.feedbackapp.adapter.SurveyContentRecyclerViewAdapter;
import app.survey.android.feedbackapp.model.SurveyItem;
import app.survey.android.feedbackapp.model.SurveyItemComment;
import app.survey.android.feedbackapp.model.SurveyItemSeekbar;
import app.survey.android.feedbackapp.model.SurveyItemSpinner;
import app.survey.android.feedbackapp.model.SurveyItemStarRate;
import app.survey.android.feedbackapp.model.SurveyItemYesNo;

public class SurveyContentFragment extends Fragment {

    private RecyclerView surveyContentRecyclerView;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_survey_content, container, false);

        surveyContentRecyclerView = (RecyclerView) view.findViewById(R.id.survey_content_recycler_view);

        populateRecyclerView();

        return view;
    }

    private void populateRecyclerView() {
        ArrayList<SurveyItem> surveyQuestions = new ArrayList<SurveyItem>() {{
            add(new SurveyItemYesNo(getResources().getString(R.string.fragment_survey_content_placeholder), false, false));
            add(new SurveyItemSeekbar(getResources().getString(R.string.fragment_survey_content_placeholder), 0));
            add(new SurveyItemSpinner(
                    getResources().getString(R.string.fragment_survey_content_placeholder),
                    new ArrayList<String>() {{
                        add("Value1");
                        add("Value2");
                        add("Value3");
                    }},
                    SurveyItemSpinner.NOTHING_SELECTED
            ));
            add(new SurveyItemComment(getResources().getString(R.string.fragment_survey_content_placeholder), ""));
            add(new SurveyItemStarRate(getResources().getString(R.string.fragment_survey_content_placeholder), 0));
            add(new SurveyItemYesNo(getResources().getString(R.string.fragment_survey_content_placeholder), false, false));
            add(new SurveyItemYesNo(getResources().getString(R.string.fragment_survey_content_placeholder), false, false));
            add(new SurveyItemSpinner(
                    getResources().getString(R.string.fragment_survey_content_placeholder),
                    new ArrayList<String>() {{
                        add("Value1");
                        add("Value2");
                        add("Value3");
                        add("Value4");
                        add("Value5");
                        add("Value6");
                        add("Value7");
                    }},
                    SurveyItemSpinner.NOTHING_SELECTED
            ));
            add(new SurveyItemYesNo(getResources().getString(R.string.fragment_survey_content_placeholder), false, false));
            add(new SurveyItemStarRate(getResources().getString(R.string.fragment_survey_content_placeholder), 0));
            add(new SurveyItemComment(getResources().getString(R.string.fragment_survey_content_placeholder), ""));
            add(new SurveyItemSpinner(
                    getResources().getString(R.string.fragment_survey_content_placeholder),
                    new ArrayList<String>() {{
                        add("Value1");
                        add("Value2");
                        add("Value3");
                        add("Value4");
                        add("Value5");
                        add("Value6");
                        add("Value7");
                        add("Value8");
                        add("Value9");
                        add("Value10");
                        add("Value11");
                        add("Value12");
                        add("Value13");
                        add("Value14");
                        add("Value15");
                        add("Value16");
                        add("Value17");
                        add("Value18");
                        add("Value19");
                    }},
                    SurveyItemSpinner.NOTHING_SELECTED
            ));
            add(new SurveyItemSeekbar(getResources().getString(R.string.fragment_survey_content_placeholder), 0));
        }};

        SurveyContentRecyclerViewAdapter adapter = new SurveyContentRecyclerViewAdapter(this, surveyQuestions);
        surveyContentRecyclerView.setAdapter(adapter);
        surveyContentRecyclerView.setItemAnimator(new DefaultItemAnimator());
        surveyContentRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
    }
}

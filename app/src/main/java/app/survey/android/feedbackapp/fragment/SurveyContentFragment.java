package app.survey.android.feedbackapp.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import app.survey.android.feedbackapp.R;
import app.survey.android.feedbackapp.adapter.SurveyContentRecyclerViewAdapter;
import app.survey.android.feedbackapp.model.ServerJSON.Category;
import app.survey.android.feedbackapp.model.ServerJSON.Question;
import app.survey.android.feedbackapp.model.SurveyItem;
import app.survey.android.feedbackapp.model.SurveyItemComment;
import app.survey.android.feedbackapp.model.SurveyItemSeekbar;
import app.survey.android.feedbackapp.model.SurveyItemSpinner;
import app.survey.android.feedbackapp.model.SurveyItemStarRate;
import app.survey.android.feedbackapp.model.SurveyItemYesNo;
import app.survey.android.feedbackapp.responder.SurveyContentFragmentResponder;
import app.survey.android.feedbackapp.util.ErrorGuiResponder;

/**
 * Use the {@link SurveyContentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SurveyContentFragment extends Fragment {
    public static final String TAG = SurveyContentFragment.class.getName();

    private static final String CATEGORY = "category";
    private static final String CATEGORY_CURRENT_POS = "categoryCurrentPos";
    private static final String CATEGORY_TOTAL_COUNT = "categoryTotalCount";
    private Category selectedCategory;
    private int categoryCurrentPos;
    private int categoryTotalCount;

    private SurveyContentRecyclerViewAdapter adapter;
    private RecyclerView surveyContentRecyclerView;
    private ArrayList<SurveyItem> surveyQuestions;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SurveyContentFragment.
     */
    public static SurveyContentFragment newInstance(Category category, int categoryCurrentPos, int categoryTotalCount) {
        SurveyContentFragment fragment = new SurveyContentFragment();
        Bundle args = new Bundle();
        args.putSerializable(CATEGORY, category);
        args.putInt(CATEGORY_CURRENT_POS, categoryCurrentPos);
        args.putInt(CATEGORY_TOTAL_COUNT, categoryTotalCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            selectedCategory = (Category) getArguments().getSerializable(CATEGORY);
            categoryCurrentPos = getArguments().getInt(CATEGORY_CURRENT_POS);
            categoryTotalCount = getArguments().getInt(CATEGORY_TOTAL_COUNT);
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_survey_content, container, false);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        if (categoryCurrentPos == categoryTotalCount) {
            fab.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_check));
        } else {
            fab.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_forward_arrow));
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adapter.isAnyAnswersEmpty()) {
                    ErrorGuiResponder.showAlertDialog(getActivity(), ErrorGuiResponder.ANSWERS_FIELDS_EMPTY_ERROR);
                } else if (categoryCurrentPos != categoryTotalCount) {
                    //move to the next
                    SurveyContentFragmentResponder responder = (SurveyContentFragmentResponder) getActivity();
                    responder.onMoveToNextCategoryPressed(adapter.getAnswersAsArrayList());
                } else {
                    //send answer to the server
                    SurveyContentFragmentResponder responder = (SurveyContentFragmentResponder) getActivity();
                    responder.onFinishSurveyPressed(adapter.getAnswersAsArrayList());
                }
            }
        });

        surveyQuestions = new ArrayList<>();

        surveyContentRecyclerView = (RecyclerView) view.findViewById(R.id.survey_content_recycler_view);
        adapter = new SurveyContentRecyclerViewAdapter(this, surveyQuestions);
        surveyContentRecyclerView.setAdapter(adapter);
        surveyContentRecyclerView.setItemAnimator(new DefaultItemAnimator());
        surveyContentRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        populateRecyclerView(selectedCategory);

        return view;
    }

    private void populateRecyclerView(Category category) {
        surveyQuestions.clear();
        for (Question question : category.getQuestions()) {
            if (question.getQuestionTypeId() == Question.SURVEY_ITEM_YES_NO) {
                surveyQuestions.add(new SurveyItemYesNo(question.getId(), question.getName(), false, false));
            } else if (question.getQuestionTypeId() == Question.SURVEY_ITEM_SPINNER) {
                surveyQuestions.add(new SurveyItemSpinner(question.getId(), question.getName(), question.getPossibleAnswers(), SurveyItemSpinner.NOTHING_SELECTED));
            } else if (question.getQuestionTypeId() == Question.SURVEY_ITEM_SEEKBAR) {
                surveyQuestions.add(new SurveyItemSeekbar(question.getId(), question.getName(), 0));
            } else if (question.getQuestionTypeId() == Question.SURVEY_ITEM_STAR_RATE) {
                surveyQuestions.add(new SurveyItemStarRate(question.getId(), question.getName(), 0));
            } else if (question.getQuestionTypeId() == Question.SURVEY_ITEM_COMMENT) {
                surveyQuestions.add(new SurveyItemComment(question.getId(), question.getName(), ""));
            }
        }
    }
}

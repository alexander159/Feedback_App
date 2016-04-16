package app.survey.android.feedbackapp.responder;

import org.json.JSONObject;

import java.util.ArrayList;

public interface SurveyContentFragmentResponder {
    void onMoveToNextCategoryPressed(ArrayList<JSONObject> categoryAnswers);

    void onFinishSurveyPressed(ArrayList<JSONObject> categoryAnswers);
}

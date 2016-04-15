package app.survey.android.feedbackapp.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import app.survey.android.feedbackapp.R;
import app.survey.android.feedbackapp.adapter.MainSurveyRecyclerViewAdapter;
import app.survey.android.feedbackapp.model.MainSurvey;
import app.survey.android.feedbackapp.model.ServerJSON.Category;
import app.survey.android.feedbackapp.model.ServerJSON.Feedback;
import app.survey.android.feedbackapp.model.ServerJSON.Question;
import app.survey.android.feedbackapp.responder.MainSurveyRecyclerViewResponder;
import app.survey.android.feedbackapp.util.ErrorGuiResponder;
import app.survey.android.feedbackapp.util.FontManager;
import app.survey.android.feedbackapp.util.ServerApi;
import app.survey.android.feedbackapp.util.SharedPrefs;

public class MainActivity extends AppCompatActivity
        implements MainSurveyRecyclerViewResponder {

    private RecyclerView surveyRecyclerView;
    private ArrayList<Feedback> parsedFeedbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(R.string.activity_main_toolbar_title);

        surveyRecyclerView = (RecyclerView) findViewById(R.id.survey_recycler_view);
        TextView poweredByTitle = (TextView) findViewById(R.id.powered_by_title);
        TextView neuralbitsTitle = (TextView) findViewById(R.id.neuralbits_title);

        poweredByTitle.setTypeface(FontManager.getFont(FontManager.Fonts.TW_CENT_MT_BOLD, this));
        neuralbitsTitle.setTypeface(FontManager.getFont(FontManager.Fonts.TW_CENT_MT_BOLD, this));

        SharedPreferences sPref = getSharedPreferences(SharedPrefs.PREFS_NAME, Context.MODE_PRIVATE);
        if (sPref.getString(SharedPrefs.FEEDBACK_OFFLINE_JSON_ARRAY, null) == null) {
            ErrorGuiResponder.showAlertDialog(this, ErrorGuiResponder.PREFS_LOADING_SURVEY_LIST_ERROR);
        } else if ((parsedFeedbacks = parseFeedbackJson(sPref.getString(SharedPrefs.FEEDBACK_OFFLINE_JSON_ARRAY, null))) == null) {
            ErrorGuiResponder.showAlertDialog(this, ErrorGuiResponder.PARSE_SURVEY_LIST_ERROR);
        } else {
            populateRecyclerView(parsedFeedbacks);
        }
    }

    private ArrayList<Feedback> parseFeedbackJson(String jsonFeedbacksString) {
        try {
            JSONArray feedbackArray = new JSONArray(jsonFeedbacksString);
            ArrayList<Feedback> feedbacks = new ArrayList<>();
            for (int i = 0; i < feedbackArray.length(); i++) {
                JSONObject feedbackObj = feedbackArray.getJSONObject(i);
                JSONArray categoriesArray = feedbackObj.getJSONArray(ServerApi.FeedbackJSON.CATEGORIES);
                ArrayList<Category> categories = new ArrayList<>();
                for (int j = 0; j < categoriesArray.length(); j++) {
                    JSONObject categoryObj = categoriesArray.getJSONObject(j);
                    JSONArray questionsArray = categoryObj.getJSONArray(ServerApi.CategoryJSON.QUESTIONS);
                    ArrayList<Question> questions = new ArrayList<>();
                    for (int k = 0; k < questionsArray.length(); k++) {
                        JSONObject questionObj = questionsArray.getJSONObject(k);
                        Question question = new Question(
                                questionObj.getLong(ServerApi.QuestionJSON.ID),
                                questionObj.getString(ServerApi.QuestionJSON.NAME),
                                questionObj.getInt(ServerApi.QuestionJSON.QUESTION_TYPE_ID),
                                getPossibleAnswers(questionObj.getString(ServerApi.QuestionJSON.POSSIBLE_ANSWERS))

                        );
                        questions.add(question);
                    }
                    Category category = new Category(
                            categoryObj.getLong(ServerApi.CategoryJSON.ID),
                            categoryObj.getString(ServerApi.CategoryJSON.NAME),
                            questions
                    );
                    categories.add(category);
                }
                Feedback feedback = new Feedback(
                        feedbackObj.getLong(ServerApi.FeedbackJSON.ID),
                        feedbackObj.getLong(ServerApi.FeedbackJSON.ADMIN_ID),
                        feedbackObj.getString(ServerApi.FeedbackJSON.NAME),
                        feedbackObj.getLong(ServerApi.FeedbackJSON.RESPONSES),
                        feedbackObj.getLong(ServerApi.FeedbackJSON.DOWNLOADED),
                        categories
                );
                feedbacks.add(feedback);
            }
            return feedbacks;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private ArrayList<String> getPossibleAnswers(String input) {
        String[] arr = input.split("\r\n");

        return new ArrayList<>(Arrays.asList(arr));
    }

    private void populateRecyclerView(ArrayList<Feedback> feedbacks) {
        ArrayList<MainSurvey> mainSurveys = new ArrayList<>();
        for (Feedback feedback : feedbacks) {
            mainSurveys.add(new MainSurvey(R.drawable.ic_barchart, feedback.getName(), feedback.getResponses(), feedback.getDownloaded()));
        }

        MainSurveyRecyclerViewAdapter adapter = new MainSurveyRecyclerViewAdapter(this, mainSurveys);
        surveyRecyclerView.setAdapter(adapter);
        surveyRecyclerView.setItemAnimator(new DefaultItemAnimator());
        surveyRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_send_receive:
                return true;
            case R.id.action_settings:
                return true;
            case R.id.action_help:
                return true;
            case R.id.action_logout:
                SharedPreferences sPref = getSharedPreferences(SharedPrefs.PREFS_NAME, Context.MODE_PRIVATE);
                sPref.edit().clear().commit();
                //move to login screen
                Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(loginIntent);
                overridePendingTransition(0, 0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSurveySelected(MainSurvey mainSurvey) {
        Intent patientDataIntent = new Intent(MainActivity.this, PatientDataActivity.class);
        patientDataIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        patientDataIntent.putExtra(SurveyActivity.SURVEY, mainSurvey);
        startActivity(patientDataIntent);
        overridePendingTransition(0, 0);
    }
}
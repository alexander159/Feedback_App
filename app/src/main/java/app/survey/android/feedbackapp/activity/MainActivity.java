package app.survey.android.feedbackapp.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import app.survey.android.feedbackapp.R;
import app.survey.android.feedbackapp.adapter.MainSurveyRecyclerViewAdapter;
import app.survey.android.feedbackapp.model.MainSurvey;
import app.survey.android.feedbackapp.model.ServerJSON.Category;
import app.survey.android.feedbackapp.model.ServerJSON.Feedback;
import app.survey.android.feedbackapp.model.ServerJSON.Question;
import app.survey.android.feedbackapp.responder.MainSurveyRecyclerViewResponder;
import app.survey.android.feedbackapp.util.ErrorGuiResponder;
import app.survey.android.feedbackapp.util.FontManager;
import app.survey.android.feedbackapp.util.RequestController;
import app.survey.android.feedbackapp.util.ServerApi;
import app.survey.android.feedbackapp.util.SharedPrefs;

public class MainActivity extends AppCompatActivity
        implements MainSurveyRecyclerViewResponder {
    public static final String TAG = MainActivity.class.getName();

    private RecyclerView surveyRecyclerView;
    private MainSurveyRecyclerViewAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<Feedback> parsedFeedbacks;
    private ArrayList<MainSurvey> mainSurveys;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(R.string.activity_main_toolbar_title);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFeedbackList();
            }
        });

        surveyRecyclerView = (RecyclerView) findViewById(R.id.survey_recycler_view);
        TextView poweredByTitle = (TextView) findViewById(R.id.powered_by_title);
        TextView neuralbitsTitle = (TextView) findViewById(R.id.neuralbits_title);

        poweredByTitle.setTypeface(FontManager.getFont(FontManager.Fonts.TW_CENT_MT_BOLD, this));
        neuralbitsTitle.setTypeface(FontManager.getFont(FontManager.Fonts.TW_CENT_MT_BOLD, this));

        mainSurveys = new ArrayList<>();
        adapter = new MainSurveyRecyclerViewAdapter(this, mainSurveys);
        surveyRecyclerView.setAdapter(adapter);
        surveyRecyclerView.setItemAnimator(new DefaultItemAnimator());
        surveyRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        SharedPreferences sPref = getSharedPreferences(SharedPrefs.PREFS_NAME, Context.MODE_PRIVATE);
        if (sPref.getString(SharedPrefs.FEEDBACK_OFFLINE_JSON_ARRAY, null) == null) {
            ErrorGuiResponder.showAlertDialog(this, ErrorGuiResponder.PREFS_LOADING_SURVEY_LIST_ERROR);
        } else if ((parsedFeedbacks = parseFeedbackJson(sPref.getString(SharedPrefs.FEEDBACK_OFFLINE_JSON_ARRAY, null))) == null) {
            ErrorGuiResponder.showAlertDialog(this, ErrorGuiResponder.PARSE_SURVEY_LIST_ERROR);
        } else {
            populateRecyclerView(parsedFeedbacks);
        }
    }

    private void refreshFeedbackList() {
        String username;
        String password;
        SharedPreferences sPref = getSharedPreferences(SharedPrefs.PREFS_NAME, Context.MODE_PRIVATE);
        if ((username = sPref.getString(SharedPrefs.USER_NAME, null)) == null ||
                (password = sPref.getString(SharedPrefs.USER_PASSWORD, null)) == null) {
            Toast.makeText(this, getString(R.string.error_null_preferences), Toast.LENGTH_LONG).show();
            logout();
            return;
        }

        String loginUrl = ServerApi.GET_FEEDBACK_LIST
                .replace(ServerApi.ParameterValues.USER_LOGIN, username)
                .replace(ServerApi.ParameterValues.USER_PASSWORD, password);

        JsonObjectRequest userLoginRequest = new JsonObjectRequest(loginUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        VolleyLog.d(TAG, jsonObject.toString());
                        swipeRefreshLayout.setRefreshing(false);

                        try {
                            ArrayList<Feedback> temp = parseFeedbackJson(jsonObject.getJSONArray(ServerApi.LoginJSON.FEEDBACKS).toString());
                            if (temp != null) {
                                //save updated info to shared prefs
                                SharedPreferences sPref = getSharedPreferences(SharedPrefs.PREFS_NAME, MODE_PRIVATE);
                                SharedPreferences.Editor ed = sPref.edit();
                                ed.putString(SharedPrefs.USER_ID, jsonObject.getString(ServerApi.LoginJSON.USER_ID));
                                ed.putString(SharedPrefs.HOSPITAL_ID, jsonObject.getString(ServerApi.LoginJSON.HOSPITAL_ID));
                                ed.putString(SharedPrefs.FEEDBACK_OFFLINE_JSON_ARRAY, jsonObject.getJSONArray(ServerApi.LoginJSON.FEEDBACKS).toString());
                                ed.commit();

                                parsedFeedbacks = temp;
                                populateRecyclerView(parsedFeedbacks);
                            } else {
                                ErrorGuiResponder.showAlertDialog(activity, ErrorGuiResponder.PARSE_SURVEY_LIST_ERROR);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                        swipeRefreshLayout.setRefreshing(false);

                        ErrorGuiResponder.showAlertDialog(activity, ErrorGuiResponder.getVolleyErrorType(error));
                    }
                }
        );

        RequestController.getInstance().addToRequestQueue(userLoginRequest, TAG);
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
        //delete empty answers
        ArrayList<String> res = new ArrayList<>();
        for (String answer : arr) {
            if (!answer.trim().isEmpty()) {
                res.add(answer);
            }
        }
        return res;
    }

    private void populateRecyclerView(ArrayList<Feedback> feedbacks) {
        mainSurveys.clear();
        for (Feedback feedback : feedbacks) {
            mainSurveys.add(new MainSurvey(R.drawable.ic_barchart, feedback.getId(), feedback.getName(), feedback.getResponses(), feedback.getDownloaded()));
        }
        adapter.notifyDataSetChanged();
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
                logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void logout() {
        SharedPreferences sPref = getSharedPreferences(SharedPrefs.PREFS_NAME, Context.MODE_PRIVATE);
        sPref.edit().clear().commit();
        //move to login screen
        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
        overridePendingTransition(0, 0);
    }

    @Override
    public void onSurveySelected(MainSurvey mainSurvey) {
        Feedback selectedFeedback = null;
        for (Feedback feedback : parsedFeedbacks) {
            if (feedback.getId() == mainSurvey.getId()) {
                selectedFeedback = feedback;
                break;
            }
        }

        Intent patientDataIntent = new Intent(MainActivity.this, PatientDataActivity.class);
        patientDataIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        patientDataIntent.putExtra(SurveyActivity.SURVEY, selectedFeedback);
        startActivity(patientDataIntent);
        overridePendingTransition(0, 0);
    }
}
package app.survey.android.feedbackapp.activity;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import app.survey.android.feedbackapp.R;
import app.survey.android.feedbackapp.fragment.SurveyContentFragment;
import app.survey.android.feedbackapp.model.ServerJSON.Feedback;
import app.survey.android.feedbackapp.responder.SurveyContentFragmentResponder;
import app.survey.android.feedbackapp.util.ErrorGuiResponder;
import app.survey.android.feedbackapp.util.FontManager;
import app.survey.android.feedbackapp.util.RequestController;
import app.survey.android.feedbackapp.util.ServerApi;
import app.survey.android.feedbackapp.util.SharedPrefs;

public class SurveyActivity extends AppCompatActivity
        implements SurveyContentFragmentResponder {
    public static final String TAG = SurveyActivity.class.getName();

    public static final String SURVEY = "survey";
    private Feedback selectedSurvey;
    private int categoryCurrentPos;
    private int categoryTotalCount;
    private TextView categoryValueTitle;
    private Activity activity;
    private ProgressBar progressBar;
    private LinearLayout categoryContainer;
    private FrameLayout surveyFragmentContainer;

    private ArrayList<JSONObject> answersJSONList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        activity = this;

        selectedSurvey = (Feedback) getIntent().getSerializableExtra(SURVEY);
        categoryCurrentPos = 1;
        categoryTotalCount = selectedSurvey.getCategories().size();

        answersJSONList = new ArrayList<>();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        getSupportActionBar().setTitle(selectedSurvey.getName());

        categoryContainer = (LinearLayout) findViewById(R.id.category_container);
        surveyFragmentContainer = (FrameLayout) findViewById(R.id.survey_fragment_container);
        TextView categoryTitle = (TextView) findViewById(R.id.category_title);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        categoryValueTitle = (TextView) findViewById(R.id.category_value_title);

        categoryTitle.setTypeface(FontManager.getFont(FontManager.Fonts.TW_CENT_MT_REGULAR, getApplicationContext()));
        categoryValueTitle.setTypeface(FontManager.getFont(FontManager.Fonts.TW_CENT_MT_BOLD, getApplicationContext()));

        setNextActiveFragment(
                SurveyContentFragment.newInstance(selectedSurvey.getCategories().get(categoryCurrentPos - 1), categoryCurrentPos, categoryTotalCount),
                false);
    }

    @SuppressWarnings("ResourceType")
    private void setNextActiveFragment(Fragment fragment, boolean addToBackStack) {
        categoryValueTitle.setText(selectedSurvey.getCategories().get(categoryCurrentPos - 1).getName());

        if (addToBackStack) {
            getFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left)
                    .replace(R.id.survey_fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
        } else {
            getFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left)
                    .replace(R.id.survey_fragment_container, fragment)
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        showExitAlertDialog();
//
//        if (getFragmentManager().getBackStackEntryCount() == 0) {
//            //super.onBackPressed();
//            showExitAlertDialog();
//        } else {
//            getFragmentManager().popBackStack();
//        }
    }


    private void showExitAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
        builder.setMessage(getString(R.string.alert_dialog_sure_exit));
        builder.setNegativeButton(getString(R.string.alert_dialog_no), null);
        builder.setPositiveButton(getString(R.string.alert_dialog_yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                //System.exit(0);
            }
        });
        builder.setCancelable(false);
        builder.show();
    }


    public void invalidPreferences() {
        Toast.makeText(this, getString(R.string.error_null_preferences), Toast.LENGTH_LONG).show();

        SharedPreferences sPref = getSharedPreferences(SharedPrefs.PREFS_NAME, Context.MODE_PRIVATE);
        sPref.edit().clear().commit();
        //move to login screen
        Intent loginIntent = new Intent(SurveyActivity.this, LoginActivity.class);
        loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
        overridePendingTransition(0, 0);
    }

    @Override
    public void onMoveToNextCategoryPressed(ArrayList<JSONObject> categoryAnswers) {
        answersJSONList.addAll(categoryAnswers);
        categoryCurrentPos++;

        //goto next fragment
        setNextActiveFragment(
                SurveyContentFragment.newInstance(selectedSurvey.getCategories().get(categoryCurrentPos - 1), categoryCurrentPos, categoryTotalCount),
                true);
    }

    @Override
    public void onFinishSurveyPressed(ArrayList<JSONObject> categoryAnswers) {
        answersJSONList.addAll(categoryAnswers);

        attemptSendAnswers();
    }

    public void attemptSendAnswers() {
        String userId;
        SharedPreferences sPref = getSharedPreferences(SharedPrefs.PREFS_NAME, Context.MODE_PRIVATE);
        if ((userId = sPref.getString(SharedPrefs.USER_ID, null)) == null) {
            //move to the login screen
            invalidPreferences();
        } else {
            showProgressBar();

            JSONArray answersJsonArray = new JSONArray();
            for (JSONObject answerJsonObj : answersJSONList) {
                answersJsonArray.put(answerJsonObj);
            }

            String sendAnswersUrl = ServerApi.SEND_ANSWERS
                    .replace(ServerApi.ParameterValues.ANSWER_USERID, userId)
                    .replace(ServerApi.ParameterValues.ANSWER_ANSWERS, answersJsonArray.toString());

            JsonObjectRequest sendAnswersRequest = new JsonObjectRequest(sendAnswersUrl, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            VolleyLog.d(TAG, jsonObject.toString());
                            hideProgressBar();

                            //move to he screen with survey list
                            Intent loginIntent = new Intent(SurveyActivity.this, MainActivity.class);
                            loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(loginIntent);
                            overridePendingTransition(0, 0);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            VolleyLog.d(TAG, "Error: " + error.getMessage());
                            hideProgressBar();

                            //got empty json, in our case it's successfully added answers
                            if (ErrorGuiResponder.getVolleyErrorType(error).equals(ErrorGuiResponder.PARSE_ERROR)) {
                                //move to he screen with survey list
                                Intent loginIntent = new Intent(SurveyActivity.this, MainActivity.class);
                                loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(loginIntent);
                                overridePendingTransition(0, 0);
                            } else {
                                //TODO SAVE TO SQLLITE
                                ErrorGuiResponder.showAlertDialog(activity, ErrorGuiResponder.getVolleyErrorType(error));
                            }
                        }
                    }
            );

            RequestController.getInstance().addToRequestQueue(sendAnswersRequest, TAG);
        }
    }

    private void showProgressBar() {
        if (progressBar != null) {
            categoryContainer.setVisibility(View.GONE);
            surveyFragmentContainer.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    private void hideProgressBar() {
        if (progressBar != null) {
            categoryContainer.setVisibility(View.VISIBLE);
            surveyFragmentContainer.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }
}

package app.survey.android.feedbackapp.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.TextView;

import app.survey.android.feedbackapp.R;
import app.survey.android.feedbackapp.fragment.SurveyContentFragment;
import app.survey.android.feedbackapp.model.MainSurvey;
import app.survey.android.feedbackapp.util.FontManager;

public class SurveyActivity extends AppCompatActivity {

    public static final String SURVEY = "survey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(
                ((MainSurvey) getIntent().getSerializableExtra(SurveyActivity.SURVEY)).getTitle()
        );
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        LinearLayout categoryContainer = (LinearLayout) findViewById(R.id.category_container);
        TextView categoryTitle = (TextView) findViewById(R.id.category_title);
        TextView categoryValueTitle = (TextView) findViewById(R.id.category_value_title);

        categoryTitle.setTypeface(FontManager.getFont(FontManager.Fonts.TW_CENT_MT_REGULAR, getApplicationContext()));
        categoryValueTitle.setTypeface(FontManager.getFont(FontManager.Fonts.TW_CENT_MT_BOLD, getApplicationContext()));

        setNextActiveFragment(new SurveyContentFragment(), false);
    }

    @SuppressWarnings("ResourceType")
    private void setNextActiveFragment(Fragment fragment, boolean addToBackStack) {
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
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
        } else {
            getFragmentManager().popBackStack();
        }
    }
}

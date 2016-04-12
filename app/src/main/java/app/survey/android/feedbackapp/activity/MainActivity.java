package app.survey.android.feedbackapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

import app.survey.android.feedbackapp.R;
import app.survey.android.feedbackapp.adapter.MainSurveyRecyclerViewAdapter;
import app.survey.android.feedbackapp.model.MainSurvey;
import app.survey.android.feedbackapp.util.FontManager;

public class MainActivity extends AppCompatActivity {

    private RecyclerView surveyRecyclerView;

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

        populateRecyclerView();
    }

    private void populateRecyclerView() {
        ArrayList<MainSurvey> mainSurveys = new ArrayList<MainSurvey>() {{
            add(new MainSurvey(R.drawable.ic_barchart, "Market Research", 0, 0));
            add(new MainSurvey(R.drawable.ic_barchart, "Audit & Inspection", 0, 0));
            add(new MainSurvey(R.drawable.ic_barchart, "Assessments & Evaluations", 0, 0));
            add(new MainSurvey(R.drawable.ic_barchart, "Lead Capture", 0, 0));
            add(new MainSurvey(R.drawable.ic_barchart, "Market Research", 0, 0));
            add(new MainSurvey(R.drawable.ic_barchart, "Audit & Inspection", 0, 0));
            add(new MainSurvey(R.drawable.ic_barchart, "Assessments & Evaluations", 0, 0));
            add(new MainSurvey(R.drawable.ic_barchart, "Lead Capture", 0, 0));
            add(new MainSurvey(R.drawable.ic_barchart, "Market Research", 0, 0));
            add(new MainSurvey(R.drawable.ic_barchart, "Audit & Inspection", 0, 0));
            add(new MainSurvey(R.drawable.ic_barchart, "Assessments & Evaluations", 0, 0));
            add(new MainSurvey(R.drawable.ic_barchart, "Lead Capture", 0, 0));
        }};

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
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

package app.survey.android.feedbackapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import app.survey.android.feedbackapp.R;
import app.survey.android.feedbackapp.util.FontManager;

public class SurveyActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        LinearLayout categoryContainer = (LinearLayout) findViewById(R.id.category_container);
        TextView categoryTitle = (TextView) findViewById(R.id.category_title);
        TextView categoryValueTitle = (TextView) findViewById(R.id.category_value_title);

        categoryTitle.setTypeface(FontManager.getFont(FontManager.Fonts.TW_CENT_MT_REGULAR, getApplicationContext()));
        categoryValueTitle.setTypeface(FontManager.getFont(FontManager.Fonts.TW_CENT_MT_BOLD, getApplicationContext()));
    }
}

package app.survey.android.feedbackapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import app.survey.android.feedbackapp.R;
import app.survey.android.feedbackapp.model.MainSurvey;
import app.survey.android.feedbackapp.responder.MainSurveyRecyclerViewResponder;
import app.survey.android.feedbackapp.util.FontManager;

public class MainSurveyRecyclerViewAdapter extends RecyclerView.Adapter<MainSurveyRecyclerViewAdapter.MainSurveyViewHolder> {

    private Context context;
    private ArrayList<MainSurvey> mainSurveys;

    public MainSurveyRecyclerViewAdapter(Context context, ArrayList<MainSurvey> mainSurveys) {
        this.context = context;
        this.mainSurveys = mainSurveys;
    }

    @Override
    public MainSurveyRecyclerViewAdapter.MainSurveyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MainSurveyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_survey, parent, false));
    }

    @Override
    public void onBindViewHolder(MainSurveyRecyclerViewAdapter.MainSurveyViewHolder holder, int position) {
        final MainSurvey survey = mainSurveys.get(position);

        holder.surveyTitle.setText(survey.getTitle());
        holder.completedTitle.setText(context.getResources().getString(R.string.activity_main_recyclerview_completed));
        holder.completedValue.setText(String.valueOf(survey.getCompletedCount()));
        holder.unsentTitle.setText(context.getResources().getString(R.string.activity_main_recyclerview_unsent));
        holder.unsentValue.setText(String.valueOf(survey.getUnsentCount()));

        holder.surveyIcon.setImageResource(survey.getIconRes());
        holder.surveyTitle.setTypeface(FontManager.getFont(FontManager.Fonts.TW_CENT_MT_REGULAR, context));
        holder.completedTitle.setTypeface(FontManager.getFont(FontManager.Fonts.TW_CENT_MT_REGULAR, context));
        holder.completedValue.setTypeface(FontManager.getFont(FontManager.Fonts.TW_CENT_MT_REGULAR, context));
        holder.unsentTitle.setTypeface(FontManager.getFont(FontManager.Fonts.TW_CENT_MT_REGULAR, context));
        holder.unsentValue.setTypeface(FontManager.getFont(FontManager.Fonts.TW_CENT_MT_REGULAR, context));

        holder.itemParentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainSurveyRecyclerViewResponder responder = (MainSurveyRecyclerViewResponder) context;
                responder.onSurveySelected(survey);
            }
        });

        holder.divider.setVisibility((position == mainSurveys.size() - 1) ? View.GONE : View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return mainSurveys.size();
    }

    public static class MainSurveyViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout itemParentLayout;
        private ImageView surveyIcon;
        private TextView surveyTitle;
        private TextView completedTitle;
        private TextView completedValue;
        private TextView unsentTitle;
        private TextView unsentValue;
        private ImageView infoIcon;
        private View divider;

        public MainSurveyViewHolder(View itemView) {
            super(itemView);

            itemParentLayout = (RelativeLayout) itemView.findViewById(R.id.item_parent_layout);
            surveyIcon = (ImageView) itemView.findViewById(R.id.survey_icon);
            surveyTitle = (TextView) itemView.findViewById(R.id.survey_title);
            completedTitle = (TextView) itemView.findViewById(R.id.completed_title);
            completedValue = (TextView) itemView.findViewById(R.id.completed_value);
            unsentTitle = (TextView) itemView.findViewById(R.id.unsent_title);
            unsentValue = (TextView) itemView.findViewById(R.id.unsent_value);
            infoIcon = (ImageView) itemView.findViewById(R.id.info_icon);
            divider = itemView.findViewById(R.id.divider);
        }
    }
}

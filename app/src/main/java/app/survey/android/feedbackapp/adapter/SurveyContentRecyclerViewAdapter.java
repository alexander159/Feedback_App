package app.survey.android.feedbackapp.adapter;

import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.whinc.widget.ratingbar.RatingBar;

import java.util.ArrayList;

import app.survey.android.feedbackapp.R;
import app.survey.android.feedbackapp.fragment.DialogFragmentSpinnerItems;
import app.survey.android.feedbackapp.fragment.SurveyContentFragment;
import app.survey.android.feedbackapp.model.SurveyItem;
import app.survey.android.feedbackapp.model.SurveyItemComment;
import app.survey.android.feedbackapp.model.SurveyItemSeekbar;
import app.survey.android.feedbackapp.model.SurveyItemSpinner;
import app.survey.android.feedbackapp.model.SurveyItemStarRate;
import app.survey.android.feedbackapp.model.SurveyItemYesNo;
import app.survey.android.feedbackapp.util.FontManager;

public class SurveyContentRecyclerViewAdapter extends RecyclerView.Adapter {

    public static final int SURVEY_ITEM_SPINNER = 0;
    public static final int SURVEY_ITEM_STAR_RATE = 1;
    public static final int SURVEY_ITEM_YES_NO = 2;
    public static final int SURVEY_ITEM_SEEKBAR = 3;
    public static final int SURVEY_ITEM_COMMENT = 4;

    private SurveyContentFragment parentFragment;
    private Context context;
    private ArrayList<SurveyItem> surveyQuestions;

    public SurveyContentRecyclerViewAdapter(SurveyContentFragment parentFragment, ArrayList<SurveyItem> surveyQuestions) {
        this.parentFragment = parentFragment;
        this.context = parentFragment.getActivity().getApplicationContext();
        this.surveyQuestions = surveyQuestions;
    }

    @Override
    public int getItemViewType(int position) {
        if (surveyQuestions.get(position) instanceof SurveyItemSpinner) {
            return SURVEY_ITEM_SPINNER;
        } else if (surveyQuestions.get(position) instanceof SurveyItemStarRate) {
            return SURVEY_ITEM_STAR_RATE;
        } else if (surveyQuestions.get(position) instanceof SurveyItemYesNo) {
            return SURVEY_ITEM_YES_NO;
        } else if (surveyQuestions.get(position) instanceof SurveyItemSeekbar) {
            return SURVEY_ITEM_SEEKBAR;
        } else if (surveyQuestions.get(position) instanceof SurveyItemComment) {
            return SURVEY_ITEM_COMMENT;
        } else {
            return -1;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case SURVEY_ITEM_SPINNER:
                return new SurveyItemSpinnerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_survey_spinner, parent, false));
            case SURVEY_ITEM_STAR_RATE:
                return new SurveyItemStarRateViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_survey_star_rate, parent, false));
            case SURVEY_ITEM_YES_NO:
                return new SurveyItemYesNoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_survey_yes_no, parent, false));
            case SURVEY_ITEM_SEEKBAR:
                return new SurveyItemSeekbarViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_survey_seekbar, parent, false));
            case SURVEY_ITEM_COMMENT:
                return new SurveyItemCommentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_survey_comment, parent, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final SurveyItem item = surveyQuestions.get(position);

        switch (getItemViewType(position)) {
            case SURVEY_ITEM_SPINNER: {
                ((SurveyItemSpinnerViewHolder) holder).question.setText(item.getQuestion());
                if (((SurveyItemSpinner) item).getSelectedPos() == SurveyItemSpinner.NOTHING_SELECTED) {
                    ((SurveyItemSpinnerViewHolder) holder).tapSelectTitle.setText(context.getResources().getString(R.string.fragment_survey_content_spinner_tap_select));
                    ((SurveyItemSpinnerViewHolder) holder).tapSelectTitle.setTextColor(ContextCompat.getColor(context, R.color.grey));
                } else {
                    ((SurveyItemSpinnerViewHolder) holder).tapSelectTitle.setText(((SurveyItemSpinner) item).getItems().get(((SurveyItemSpinner) item).getSelectedPos()));
                    ((SurveyItemSpinnerViewHolder) holder).tapSelectTitle.setTextColor(ContextCompat.getColor(context, R.color.black));
                }

                ((SurveyItemSpinnerViewHolder) holder).tapSelectContainer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FragmentManager fragmentManager = parentFragment.getFragmentManager();
                        DialogFragmentSpinnerItems dFragment = DialogFragmentSpinnerItems.newInstance(((SurveyItemSpinner) item).getItems());
                        dFragment.setSelectionListener(new DialogFragmentSpinnerItems.DialogFragmentSpinnerItemsListener() {
                            @Override
                            public void onItemSelected(String stribgValue, int position) {
                                ((SurveyItemSpinner) item).setSelectedPos(position);
                                notifyDataSetChanged();
                            }
                        });
                        dFragment.show(fragmentManager, "DialogFragmentSpinnerItems");
                    }
                });

                ((SurveyItemSpinnerViewHolder) holder).question.setTypeface(FontManager.getFont(FontManager.Fonts.TW_CENT_MT_REGULAR, context));
                ((SurveyItemSpinnerViewHolder) holder).tapSelectTitle.setTypeface(FontManager.getFont(FontManager.Fonts.TW_CENT_MT_BOLD, context));
                break;
            }
            case SURVEY_ITEM_STAR_RATE: {
                ((SurveyItemStarRateViewHolder) holder).question.setText(item.getQuestion());
                ((SurveyItemStarRateViewHolder) holder).whincRatingBar.setMaxCount(SurveyItemStarRate.MAX_VALUE);
                ((SurveyItemStarRateViewHolder) holder).whincRatingBar.setCount(0);

                ((SurveyItemStarRateViewHolder) holder).question.setTypeface(FontManager.getFont(FontManager.Fonts.TW_CENT_MT_REGULAR, context));
                break;
            }
            case SURVEY_ITEM_YES_NO: {
                ((SurveyItemYesNoViewHolder) holder).question.setText(item.getQuestion());
                ((SurveyItemYesNoViewHolder) holder).yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((SurveyItemYesNoViewHolder) holder).yes.setBackgroundResource(R.drawable.yesno_left_tab_selected);
                        ((SurveyItemYesNoViewHolder) holder).yes.setTextColor(ContextCompat.getColor(context, R.color.white));
                        ((SurveyItemYesNoViewHolder) holder).no.setBackgroundColor(Color.TRANSPARENT);
                        ((SurveyItemYesNoViewHolder) holder).no.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
                    }
                });
                ((SurveyItemYesNoViewHolder) holder).no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((SurveyItemYesNoViewHolder) holder).yes.setBackgroundColor(Color.TRANSPARENT);
                        ((SurveyItemYesNoViewHolder) holder).yes.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
                        ((SurveyItemYesNoViewHolder) holder).no.setBackgroundResource(R.drawable.yesno_right_tab_selected);
                        ((SurveyItemYesNoViewHolder) holder).no.setTextColor(ContextCompat.getColor(context, R.color.white));
                    }
                });

                ((SurveyItemYesNoViewHolder) holder).question.setTypeface(FontManager.getFont(FontManager.Fonts.TW_CENT_MT_REGULAR, context));
                ((SurveyItemYesNoViewHolder) holder).yes.setTypeface(FontManager.getFont(FontManager.Fonts.TW_CENT_MT_BOLD, context));
                ((SurveyItemYesNoViewHolder) holder).no.setTypeface(FontManager.getFont(FontManager.Fonts.TW_CENT_MT_BOLD, context));
                break;
            }
            case SURVEY_ITEM_SEEKBAR: {
                ((SurveyItemSeekbarViewHolder) holder).question.setText(item.getQuestion());
                ((SurveyItemSeekbarViewHolder) holder).maxValue.setText(String.valueOf(SurveyItemSeekbar.MAX_VALUE));
                ((SurveyItemSeekbarViewHolder) holder).minValue.setText(String.valueOf(0));
                ((SurveyItemSeekbarViewHolder) holder).currentValue.setText(String.valueOf(0));
                ((SurveyItemSeekbarViewHolder) holder).seekbar.setMax(SurveyItemSeekbar.MAX_VALUE);
                ((SurveyItemSeekbarViewHolder) holder).seekbar.setProgress(0);
                ((SurveyItemSeekbarViewHolder) holder).seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        ((SurveyItemSeekbarViewHolder) holder).currentValue.setText(String.valueOf(progress));
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }
                });

                ((SurveyItemSeekbarViewHolder) holder).question.setTypeface(FontManager.getFont(FontManager.Fonts.TW_CENT_MT_REGULAR, context));
                ((SurveyItemSeekbarViewHolder) holder).maxValue.setTypeface(FontManager.getFont(FontManager.Fonts.TW_CENT_MT_REGULAR, context));
                ((SurveyItemSeekbarViewHolder) holder).minValue.setTypeface(FontManager.getFont(FontManager.Fonts.TW_CENT_MT_REGULAR, context));
                ((SurveyItemSeekbarViewHolder) holder).currentValue.setTypeface(FontManager.getFont(FontManager.Fonts.TW_CENT_MT_BOLD, context));
                break;
            }
            case SURVEY_ITEM_COMMENT: {
                ((SurveyItemCommentViewHolder) holder).question.setText(item.getQuestion());

                ((SurveyItemCommentViewHolder) holder).question.setTypeface(FontManager.getFont(FontManager.Fonts.TW_CENT_MT_REGULAR, context));
                ((SurveyItemCommentViewHolder) holder).comment.setTypeface(FontManager.getFont(FontManager.Fonts.TW_CENT_MT_REGULAR, context));
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return surveyQuestions.size();
    }

    public static class SurveyItemSpinnerViewHolder extends RecyclerView.ViewHolder {
        private TextView question;
        private TextView tapSelectTitle;
        private LinearLayout tapSelectContainer;

        public SurveyItemSpinnerViewHolder(View itemView) {
            super(itemView);

            question = (TextView) itemView.findViewById(R.id.question);
            tapSelectTitle = (TextView) itemView.findViewById(R.id.tap_select_title);
            tapSelectContainer = (LinearLayout) itemView.findViewById(R.id.tap_select_container);
        }
    }

    public static class SurveyItemStarRateViewHolder extends RecyclerView.ViewHolder {
        private TextView question;
        private RatingBar whincRatingBar;

        public SurveyItemStarRateViewHolder(View itemView) {
            super(itemView);

            question = (TextView) itemView.findViewById(R.id.question);
            whincRatingBar = (RatingBar) itemView.findViewById(R.id.whinc_rating_bar);
        }
    }

    public static class SurveyItemYesNoViewHolder extends RecyclerView.ViewHolder {
        private TextView question;
        private TextView yes;
        private TextView no;

        public SurveyItemYesNoViewHolder(View itemView) {
            super(itemView);

            question = (TextView) itemView.findViewById(R.id.question);
            yes = (TextView) itemView.findViewById(R.id.yes);
            no = (TextView) itemView.findViewById(R.id.no);
        }
    }

    public static class SurveyItemSeekbarViewHolder extends RecyclerView.ViewHolder {
        private TextView question;
        private TextView currentValue;
        private SeekBar seekbar;
        private TextView minValue;
        private TextView maxValue;

        public SurveyItemSeekbarViewHolder(View itemView) {
            super(itemView);

            question = (TextView) itemView.findViewById(R.id.question);
            currentValue = (TextView) itemView.findViewById(R.id.current_value);
            seekbar = (SeekBar) itemView.findViewById(R.id.seekbar);
            minValue = (TextView) itemView.findViewById(R.id.min_value);
            maxValue = (TextView) itemView.findViewById(R.id.max_value);
        }
    }

    public static class SurveyItemCommentViewHolder extends RecyclerView.ViewHolder {
        private TextView question;
        private EditText comment;

        public SurveyItemCommentViewHolder(View itemView) {
            super(itemView);

            question = (TextView) itemView.findViewById(R.id.question);
            comment = (EditText) itemView.findViewById(R.id.comment);
        }
    }
}

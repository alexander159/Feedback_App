package app.survey.android.feedbackapp.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import app.survey.android.feedbackapp.R;
import app.survey.android.feedbackapp.model.SurveyItemSpinner;
import app.survey.android.feedbackapp.util.FontManager;

public class SpinnerItemsRecyclerViewAdapter extends RecyclerView.Adapter<SpinnerItemsRecyclerViewAdapter.ItemViewHolder> {

    private Context context;
    private ArrayList<String> items;
    private int pressedPosition = SurveyItemSpinner.NOTHING_SELECTED;

    public SpinnerItemsRecyclerViewAdapter(Context context, ArrayList<String> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public SpinnerItemsRecyclerViewAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_spinner_dialog_fragment, parent, false));
    }

    @Override
    public void onBindViewHolder(final SpinnerItemsRecyclerViewAdapter.ItemViewHolder holder, int position) {
        final String item = items.get(position);

        holder.title.setText(item);

        holder.title.setTypeface(FontManager.getFont(FontManager.Fonts.TW_CENT_MT_REGULAR, context));

        //Determine which tile still has pressed color and reset it
        if (pressedPosition != position &&
                holder.title.getCurrentTextColor() == ContextCompat.getColor(context, R.color.white)) {
            holder.titleContainer.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
            holder.title.setTextColor(ContextCompat.getColor(context, R.color.darkgrey));
        } else if (pressedPosition == position) {
            holder.titleContainer.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryLight));
            holder.title.setTextColor(ContextCompat.getColor(context, R.color.white));
        }

        holder.titleContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pressedPosition = holder.getAdapterPosition();
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void deselectAll() {
        pressedPosition = SurveyItemSpinner.NOTHING_SELECTED;
        notifyDataSetChanged();
    }

    public String getSelectedItem() {
        return (pressedPosition == SurveyItemSpinner.NOTHING_SELECTED) ? null : items.get(pressedPosition);
    }

    public int getSelectedPos() {
        return pressedPosition;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout titleContainer;
        private TextView title;

        public ItemViewHolder(View itemView) {
            super(itemView);

            titleContainer = (LinearLayout) itemView.findViewById(R.id.title_container);
            title = (TextView) itemView.findViewById(R.id.title);
        }
    }

}

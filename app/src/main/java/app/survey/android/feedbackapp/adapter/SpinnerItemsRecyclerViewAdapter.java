package app.survey.android.feedbackapp.adapter;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import app.survey.android.feedbackapp.R;
import app.survey.android.feedbackapp.fragment.DialogFragmentSpinnerItems;
import app.survey.android.feedbackapp.util.FontManager;

public class SpinnerItemsRecyclerViewAdapter extends RecyclerView.Adapter<SpinnerItemsRecyclerViewAdapter.ItemViewHolder> {

    private DialogFragmentSpinnerItems parentFragment;
    private ArrayList<String> items;
    private int pressedPosition = -1;

    public SpinnerItemsRecyclerViewAdapter(DialogFragmentSpinnerItems parentFragment, ArrayList<String> items) {
        this.parentFragment = parentFragment;
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

        holder.title.setTypeface(FontManager.getFont(FontManager.Fonts.TW_CENT_MT_REGULAR, parentFragment.getActivity().getApplicationContext()));

        //Determine which tile still has pressed color and reset it
        if (pressedPosition != position &&
                holder.title.getCurrentTextColor() == ContextCompat.getColor(parentFragment.getActivity().getApplicationContext(), R.color.white)) {
            holder.titleContainer.setBackgroundColor(ContextCompat.getColor(parentFragment.getActivity().getApplicationContext(), R.color.white));
            holder.title.setTextColor(ContextCompat.getColor(parentFragment.getActivity().getApplicationContext(), R.color.darkgrey));
        } else if (pressedPosition == position) {
            holder.titleContainer.setBackgroundColor(ContextCompat.getColor(parentFragment.getActivity().getApplicationContext(), R.color.colorPrimaryLight));
            holder.title.setTextColor(ContextCompat.getColor(parentFragment.getActivity().getApplicationContext(), R.color.white));
        }

        holder.titleContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pressedPosition = holder.getAdapterPosition();
                notifyDataSetChanged();

                SpinnerItemsRecyclerViewResponder responder = (SpinnerItemsRecyclerViewResponder) parentFragment;
                responder.onItemSelected(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void deselectAll() {
        pressedPosition = -1;
        notifyDataSetChanged();
    }

    public interface SpinnerItemsRecyclerViewResponder {
        void onItemSelected(String title);
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

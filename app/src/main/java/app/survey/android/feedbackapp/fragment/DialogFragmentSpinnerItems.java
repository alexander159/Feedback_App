package app.survey.android.feedbackapp.fragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import app.survey.android.feedbackapp.R;
import app.survey.android.feedbackapp.adapter.SpinnerItemsRecyclerViewAdapter;
import app.survey.android.feedbackapp.util.FontManager;

/**
 * Use the {@link DialogFragmentSpinnerItems#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DialogFragmentSpinnerItems extends DialogFragment
        implements SpinnerItemsRecyclerViewAdapter.SpinnerItemsRecyclerViewResponder {

    private static final String ITEMS_LIST = "itemsList";
    private ArrayList<String> items;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param items The ArrayList of spinner items
     * @return A new instance of fragment DialogFragmentSpinnerItems.
     */
    public static DialogFragmentSpinnerItems newInstance(ArrayList<String> items) {
        DialogFragmentSpinnerItems fragment = new DialogFragmentSpinnerItems();
        Bundle args = new Bundle();
        args.putStringArrayList(ITEMS_LIST, items);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            items = getArguments().getStringArrayList(ITEMS_LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dialog_spinner_items, container, false);

        RecyclerView spinnerRecyclerView = (RecyclerView) rootView.findViewById(R.id.spinner_recycler_view);
        TextView resetButton = (TextView) rootView.findViewById(R.id.reset_button);
        TextView doneButton = (TextView) rootView.findViewById(R.id.done_button);

        resetButton.setTypeface(FontManager.getFont(FontManager.Fonts.TW_CENT_MT_BOLD, getActivity().getApplicationContext()));
        doneButton.setTypeface(FontManager.getFont(FontManager.Fonts.TW_CENT_MT_BOLD, getActivity().getApplicationContext()));

        final SpinnerItemsRecyclerViewAdapter adapter = new SpinnerItemsRecyclerViewAdapter(this, items);
        spinnerRecyclerView.setAdapter(adapter);
        spinnerRecyclerView.setItemAnimator(new DefaultItemAnimator());
        spinnerRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.deselectAll();
            }
        });

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return rootView;
    }

    @Override
    public void onItemSelected(String title) {

    }
}

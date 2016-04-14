package app.survey.android.feedbackapp.model;

import java.util.ArrayList;

public class SurveyItemSpinner extends SurveyItem {
    public static final int NOTHING_SELECTED = -1;

    private ArrayList<String> items;
    private int selectedPos;

    public SurveyItemSpinner(String question, ArrayList<String> items, int selectedPos) {
        this.question = question;
        this.items = items;
        this.selectedPos = selectedPos;
    }

    public ArrayList<String> getItems() {
        return items;
    }

    public void addItem(String item) {
        items.add(item);
    }

    public int getSelectedPos() {
        return selectedPos;
    }

    public void setSelectedPos(int selectedPos) {
        this.selectedPos = selectedPos;
    }
}

package app.survey.android.feedbackapp.model;

import java.util.ArrayList;

public class SurveyItemSpinner extends SurveyItem {
    private ArrayList<String> items;

    public SurveyItemSpinner(String question, ArrayList<String> items) {
        this.question = question;
        this.items = items;
    }

    public ArrayList<String> getItems() {
        return items;
    }

    public void addItem(String item) {
        items.add(item);
    }
}

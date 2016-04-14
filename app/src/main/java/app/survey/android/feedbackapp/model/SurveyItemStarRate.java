package app.survey.android.feedbackapp.model;

public class SurveyItemStarRate extends SurveyItem {
    public static final int MAX_VALUE = 10;
    private int value;

    public SurveyItemStarRate(String question, int value) {
        this.question = question;
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
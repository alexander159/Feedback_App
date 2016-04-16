package app.survey.android.feedbackapp.model;

public class SurveyItemStarRate extends SurveyItem {
    public static final int MIN_ACCEPTED_VALUE = 1;
    public static final int MAX_VALUE = 10;
    private int value;

    public SurveyItemStarRate(long id, String question, int value) {
        this.id = id;
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
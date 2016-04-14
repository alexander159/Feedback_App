package app.survey.android.feedbackapp.model;

public class SurveyItemSeekbar extends SurveyItem {
    public static final int MAX_VALUE = 100;
    private int value;

    public SurveyItemSeekbar(String question, int value) {
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

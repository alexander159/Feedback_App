package app.survey.android.feedbackapp.model;

public class SurveyItemSeekbar extends SurveyItem {
    public static final int MIN_ACCEPTED_VALUE = 1;
    public static final int MAX_VALUE = 100;
    private int value;

    public SurveyItemSeekbar(long id, String question, int value) {
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

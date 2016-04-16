package app.survey.android.feedbackapp.model;

public class SurveyItemYesNo extends SurveyItem {
    public static final String YES = "Yes";
    public static final String NO = "No";

    private boolean isYes;
    private boolean isNo;

    public SurveyItemYesNo(long id, String question, boolean isYes, boolean isNo) {
        this.id = id;
        this.question = question;
        this.isYes = isYes;
        this.isNo = isNo;
    }

    public boolean isYes() {
        return isYes;
    }

    public boolean isNo() {
        return isNo;
    }

    public void setYes() {
        isYes = true;
        isNo = false;
    }

    public void setNo() {
        isYes = false;
        isNo = true;
    }
}

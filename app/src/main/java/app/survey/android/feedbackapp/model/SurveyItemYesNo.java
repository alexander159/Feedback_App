package app.survey.android.feedbackapp.model;

public class SurveyItemYesNo extends SurveyItem {
    private boolean isYes;
    private boolean isNo;

    public SurveyItemYesNo(String question, boolean isYes, boolean isNo) {
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

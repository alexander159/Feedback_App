package app.survey.android.feedbackapp.model;

public class SurveyItemComment extends SurveyItem {
    private String comment;

    public SurveyItemComment(String question, String comment) {
        this.question = question;
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}

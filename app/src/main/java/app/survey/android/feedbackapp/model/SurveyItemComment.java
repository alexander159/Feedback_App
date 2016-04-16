package app.survey.android.feedbackapp.model;

public class SurveyItemComment extends SurveyItem {
    private String comment;

    public SurveyItemComment(long id, String question, String comment) {
        this.id = id;
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

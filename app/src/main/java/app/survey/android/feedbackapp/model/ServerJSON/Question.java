package app.survey.android.feedbackapp.model.ServerJSON;

import java.util.ArrayList;

public class Question {
    private long id;
    private String name;
    private int questionTypeId;
    private ArrayList<String> possibleAnswers;

    public Question(long id, String name, int questionTypeId, ArrayList<String> possibleAnswers) {
        this.id = id;
        this.name = name;
        this.questionTypeId = questionTypeId;
        this.possibleAnswers = possibleAnswers;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuestionTypeId() {
        return questionTypeId;
    }

    public ArrayList<String> getPossibleAnswers() {
        return possibleAnswers;
    }
}

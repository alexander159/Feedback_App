package app.survey.android.feedbackapp.model.ServerJSON;

import java.io.Serializable;
import java.util.ArrayList;

public class Question implements Serializable {
    public static final int SURVEY_ITEM_YES_NO = 1;
    public static final int SURVEY_ITEM_SPINNER = 2;
    public static final int SURVEY_ITEM_SEEKBAR = 3;
    public static final int SURVEY_ITEM_STAR_RATE = 4;
    public static final int SURVEY_ITEM_COMMENT = 5;

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

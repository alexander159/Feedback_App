package app.survey.android.feedbackapp.model.ServerJSON;

import java.util.ArrayList;

public class Category {
    private long id;
    private String name;
    private ArrayList<Question> questions;

    public Category(long id, String name, ArrayList<Question> questions) {
        this.id = id;
        this.name = name;
        this.questions = questions;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }
}

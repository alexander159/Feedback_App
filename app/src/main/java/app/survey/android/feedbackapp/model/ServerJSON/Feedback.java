package app.survey.android.feedbackapp.model.ServerJSON;

import java.io.Serializable;
import java.util.ArrayList;

public class Feedback implements Serializable{
    private long id;
    private long adminId;
    private String name;
    private long responses;
    private long downloaded;
    private ArrayList<Category> categories;

    public Feedback(long id, long adminId, String name, long responses, long downloaded, ArrayList<Category> categories) {
        this.id = id;
        this.adminId = adminId;
        this.name = name;
        this.responses = responses;
        this.downloaded = downloaded;
        this.categories = categories;
    }

    public long getId() {
        return id;
    }

    public long getAdminId() {
        return adminId;
    }

    public String getName() {
        return name;
    }

    public long getResponses() {
        return responses;
    }

    public long getDownloaded() {
        return downloaded;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }
}

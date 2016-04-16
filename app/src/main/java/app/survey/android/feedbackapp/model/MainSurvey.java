package app.survey.android.feedbackapp.model;

import java.io.Serializable;

public class MainSurvey implements Serializable {
    private int iconRes;
    private long id;
    private String title;
    private long completedCount;
    private long unsentCount;

    public MainSurvey(int iconRes, long id, String title, long completedCount, long unsentCount) {
        this.iconRes = iconRes;
        this.id = id;
        this.title = title;
        this.completedCount = completedCount;
        this.unsentCount = unsentCount;
    }

    public int getIconRes() {
        return iconRes;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public long getCompletedCount() {
        return completedCount;
    }

    public long getUnsentCount() {
        return unsentCount;
    }
}

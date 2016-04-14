package app.survey.android.feedbackapp.model;

import java.io.Serializable;

public class MainSurvey implements Serializable {
    private int iconRes;
    private String title;
    private int completedCount;
    private int unsentCount;

    public MainSurvey(int iconRes, String title, int completedCount, int unsentCount) {
        this.iconRes = iconRes;
        this.title = title;
        this.completedCount = completedCount;
        this.unsentCount = unsentCount;
    }

    public int getIconRes() {
        return iconRes;
    }

    public String getTitle() {
        return title;
    }

    public int getCompletedCount() {
        return completedCount;
    }

    public int getUnsentCount() {
        return unsentCount;
    }
}

package app.survey.android.feedbackapp.model;

import java.io.Serializable;

public class MainSurvey implements Serializable {
    private int iconRes;
    private String title;
    private long completedCount;
    private long unsentCount;

    public MainSurvey(int iconRes, String title, long completedCount, long unsentCount) {
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

    public long getCompletedCount() {
        return completedCount;
    }

    public long getUnsentCount() {
        return unsentCount;
    }
}

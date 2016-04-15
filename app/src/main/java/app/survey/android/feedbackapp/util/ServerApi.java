package app.survey.android.feedbackapp.util;

public class ServerApi {
    public static final String SERVER_HOST_URL = "http://feedbackapp.in";
    public static final String USER_LOGIN = "user_login";
    public static final String USER_PASSWORD = "user_password";

    public static final String GET_FEEDBACK_LIST = SERVER_HOST_URL +
            "/getfeedbacks.php?data=%7B\"login\":\"" +
            USER_LOGIN +
            "\",\"password\":\"" +
            USER_PASSWORD +
            "\"%7D";

    public static class LoginJSON {
        public static final String USER_ID = "userid";
        public static final String HOSPITAL_ID = "hospital_id";
        public static final String FEEDBACKS = "feedbacks";
    }

    public static class FeedbackJSON {
        public static final String ID = "id";
        public static final String ADMIN_ID = "admin_id";
        public static final String NAME = "name";
        public static final String RESPONSES = "responses";
        public static final String DOWNLOADED = "downloaded";
        public static final String CATEGORIES = "categories";
    }

    public static class CategoryJSON {
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String QUESTIONS = "questions";
    }

    public static class QuestionJSON {
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String QUESTION_TYPE_ID = "question_type_id";
        public static final String POSSIBLE_ANSWERS = "possible_answer";

    }
}

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

}

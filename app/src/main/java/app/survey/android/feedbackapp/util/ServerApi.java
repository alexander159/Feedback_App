package app.survey.android.feedbackapp.util;

public class ServerApi {
    public static final String SERVER_HOST_URL = "http://feedbackapp.in";
    public static final String USER_LOGIN = "user_login_value";
    public static final String USER_PASSWORD = "user_password_value";

    public static final String GET_FEEDBACK_LIST = SERVER_HOST_URL +
            "/getfeedbacks.php?data=%7B" +
            "\"login\":\"" + USER_LOGIN + "\"," +
            "\"password\":\"" + USER_PASSWORD +
            "\"%7D";

    public static final String ADD_NEW_PATIENT = SERVER_HOST_URL +
            "/addpatient.php?data={" +
            "\"hospital_id\":\"" + PatientJSON.HOSPITAL_ID + "\"," +
            "\"name\":\"" + PatientJSON.NAME + "\"," +
            "\"age\":\"" + PatientJSON.AGE + "\"," +
            "\"ip_no\":\"" + PatientJSON.IP_NO + "\"," +
            "\"email\":\"" + PatientJSON.EMAIL + "\"," +
            "\"came_as\":\"" + PatientJSON.CAME_AS + "\"," +
            "\"sex\":\"" + PatientJSON.SEX +
            "\",\"phone\":\"" + PatientJSON.PHONE +
            "\",\"ward_no\":\"" + PatientJSON.WARD_NO +
            "\",\"be_no\":\"" + PatientJSON.BED_NO +
            "\"}";

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

    public static class PatientJSON {
        public static final String HOSPITAL_ID = "hospital_id_value";
        public static final String NAME = "name_value";
        public static final String AGE = "age_value";
        public static final String IP_NO = "ip_no_value";
        public static final String EMAIL = "email_value";
        public static final String CAME_AS = "came_as_value";
        public static final String SEX = "sex_value";
        public static final String PHONE = "phone_value";
        public static final String WARD_NO = "ward_no_value";
        public static final String BED_NO = "be_no_value";

        public interface CAME_AS_VALUE {
            String PATIENT = "Patient";
            String RELATIVE = "Relative";
            String VISITOR = "Visitor";
        }

        public interface SEX_VALUE {
            String MALE = "Male";
            String FEMALE = "Female";
            String OTHERS = "Others";
        }

    }
}

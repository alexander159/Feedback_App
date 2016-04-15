package app.survey.android.feedbackapp.util;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.android.volley.VolleyError;

import app.survey.android.feedbackapp.R;

public class ErrorGuiResponder {
    public static final String NO_CONNECTION = "No connection could be established when performing a request";
    public static final String TIMED_OUT = "Connection or the socket timed out";
    public static final String SERVER_ERROR = "Server responded with an error response";
    public static final String PARSE_ERROR = "Server's response could not be parsed";
    public static final String NETWORK_ERROR = "There was a network error when performing a request";
    public static final String AUTH_FAILURE_ERROR = "There was an authentication failure when performing a request";
    public static final String USERNAME_PASSWORD_INCORRECT = "Username or Password is incorrect";
    public static final String PREFS_LOADING_SURVEY_LIST_ERROR = "Error during loading survey list. Try to login again.";
    public static final String PARSE_SURVEY_LIST_ERROR = "Error during parsing survey list";
    public static final String UNKNOWN_ERROR = "Unknown error";

    public static void showAlertDialog(Activity parent, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(parent, R.style.AppCompatAlertDialogStyle);
        builder.setMessage(message);
        builder.setNegativeButton(parent.getResources().getString(R.string.alert_dialog_close), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    public static String getVolleyErrorType(VolleyError error) {
        if (error instanceof com.android.volley.NoConnectionError) {
            return NO_CONNECTION;
        } else if (error instanceof com.android.volley.TimeoutError) {
            return TIMED_OUT;
        } else if (error instanceof com.android.volley.ServerError) {
            return SERVER_ERROR;
        } else if (error instanceof com.android.volley.ParseError) {
            return PARSE_ERROR;
        } else if (error instanceof com.android.volley.NetworkError) {
            return NETWORK_ERROR;
        } else if (error instanceof com.android.volley.AuthFailureError) {
            return AUTH_FAILURE_ERROR;
        } else {
            return UNKNOWN_ERROR;
        }
    }
}

package dpparking.androidapp.peo.utils;

/**
 * Created by RBK on 1/9/14.
 */

import android.widget.EditText;
import java.util.regex.Pattern;

public class Validator {
    // Regular Expression
    private static final String REGEX_FOR_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String REGEX_FOR_PHONE = "\\d{10}";

    // Messages
    private static final String REQUIRED_VALIDATION = "required";
    private static final String INVALID_EMAIL = "invalid email";
    private static final String INVALID_PHONE = "invalid phone";
    private static final String PASSWORD_NOT_MATCH = "passwords do not match";


    public static boolean isValidEmailAddress(EditText editText, boolean required) {
        return isValid(editText, REGEX_FOR_EMAIL, INVALID_EMAIL, required);
    }

    public static boolean isValidEmailAddress(EditText editText, boolean required,String errorMessage) {
        return isValid(editText, REGEX_FOR_EMAIL, errorMessage, required);
    }

    public static boolean isValidPhoneNumber(EditText editText, boolean required) {
        return isValid(editText, REGEX_FOR_PHONE, INVALID_PHONE , required);
    }

    public static boolean isValidPhoneNumber(EditText editText, boolean required,String errorMessage) {
        return isValid(editText, REGEX_FOR_PHONE, errorMessage , required);
    }

    // return true if the input field is valid, based on the parameter passed
    public static boolean isValid(EditText editText, String regex, String errMsg, boolean required) {

        String text = editText.getText().toString().trim();
        // clearing the error, if it was previously set by some other values
        editText.setError(null);

        // text required and editText is blank, so return false
        if ( required && !hasText(editText) ) return false;

        // pattern doesn't match so returning false
        if (required && !Pattern.matches(regex, text)) {
            editText.setError(errMsg);
            return false;
        };

        return true;
    }

    // check the input field has any text or not
    // return true if it contains text otherwise false
    public static boolean hasText(EditText editText) {

        String text = editText.getText().toString().trim();
        editText.setError(null);

        // length 0 means there is no text
        if (text.length() == 0) {
            editText.setError(REQUIRED_VALIDATION);
            return false;
        }

        return true;
    }

    public static boolean hasText(EditText editText,String errorMessage) {

        String text = editText.getText().toString().trim();
        editText.setError(null);

        // length 0 means there is no text
        if (text.length() == 0) {
            editText.setError(errorMessage);
            return false;
        }

        return true;
    }

    public static boolean comparePasswords(EditText base,EditText compare){

        String baseText = base.getText().toString().trim();
        String compareText = compare.getText().toString().trim();

        base.setError(null);
        compare.setError(null);

        // length 0 means there is no text
        if (!baseText.equals(compareText)) {
            compare.setError(PASSWORD_NOT_MATCH);
            base.setError(PASSWORD_NOT_MATCH);
            return false;
        }

        return true;
    }

    public static boolean comparePasswords(EditText base,EditText compare,String errorMessage){

        String baseText = base.getText().toString().trim();
        String compareText = compare.getText().toString().trim();

        base.setError(null);
        compare.setError(null);

        // length 0 means there is no text
        if (!baseText.equals(compareText)) {
            compare.setError(errorMessage);
            base.setError(errorMessage);
            return false;
        }

        return true;
    }

}

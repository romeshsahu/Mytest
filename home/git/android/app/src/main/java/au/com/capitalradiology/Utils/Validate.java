package au.com.capitalradiology.Utils;

import android.graphics.Color;
import android.text.TextUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

public class Validate {

	public static final int VALID_TEXT_COLOR = Color.BLACK;
	public static final int INVALID_TEXT_COLOR = Color.RED;

	public static boolean hasText(EditText editText) {

		boolean validated = true;

		String text = editText.getText().toString().trim();

		if (text.length() == 0) {
			validated = false;
		}

		return validated;
	}

	public static String getText(EditText editText) {
		return editText.getText().toString().trim();
	}

	public static String getText(TextView editText) {
		return editText.getText().toString().trim();
	}

	public static boolean isEqual(EditText edittext1, EditText edittext2) {

		boolean validated = false;

		String text1 = edittext1.getText().toString().trim();
		String text2 = edittext2.getText().toString().trim();

		if (text1.equalsIgnoreCase(text2)) {
			validated = true;
		}

		return validated;
	}

	public static boolean hasRadioChecked(RadioGroup radiogroup) {

		boolean validated = true;

		int text = radiogroup.getCheckedRadioButtonId();

		if (text == -1) {
			validated = false;
		}

		return validated;
	}

	public static boolean hasCheckboxChecked(CheckBox checkbox) {
		boolean validated = true;
		if (checkbox.isChecked()) {
			validated = true;
		} else {
			validated = false;
		}
		return validated;
	}

	public static boolean hasValueGreater(EditText edittext) {
		boolean validated = true;
		int text = Integer.parseInt(edittext.getText().toString().trim());
		if (text >= 20) {
			validated = true;
		} else {
			validated = false;
		}
		return validated;

	}

	public final static boolean checkEmail(EditText email_edittext) {

		String strEmail=email_edittext.getText().toString().trim();
		
		return !TextUtils.isEmpty(strEmail)
				&& android.util.Patterns.EMAIL_ADDRESS.matcher(strEmail)
						.matches();
	}

	public static boolean isdigit(EditText input) {

		String data = input.getText().toString().trim();
		for (int i = 0; i < data.length(); i++) {
			if (!Character.isDigit(data.charAt(i)))
				return false;

		}
		return true;
	}

	public static boolean ischar(EditText input) {

		String data = input.getText().toString().trim();
		for (int i = 0; i < data.length(); i++) {
			if (!Character.isDigit(data.charAt(i)))
				return true;

		}
		return false;
	}

}
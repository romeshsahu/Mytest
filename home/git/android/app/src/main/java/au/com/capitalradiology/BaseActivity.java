package au.com.capitalradiology;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class BaseActivity extends AppCompatActivity
{
	private Typeface typeace;
	private MyApplication application;
	private Context context = BaseActivity.this;
	private static final int REQ_CREATE_PATTERN = 1;
    private String PREFS_NAME = "Capital_Pref";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//typeace = Typeface.createFromAsset(getAssets(), getResources().getString(com.igotforms.shared.R.string.font_name_avenir));
		application = (MyApplication)getApplication();

       // this.overridePendingTransition(R.anim.activity_open_translate,R.anim.activity_close_scale);

	}

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.
                INPUT_METHOD_SERVICE);
		if (!imm.isAcceptingText()) {
			//Utils.LogE("Showing keybaord");
		} else {
			imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
		}
        return true;
    }

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		//overrideFonts(this,getWindow().getDecorView());
	}

	
	private void overrideFonts(final Context context, final View v) {
	    try {
	        if (v instanceof ViewGroup) {
	            ViewGroup vg = (ViewGroup) v;
	            for (int i = 0; i < vg.getChildCount(); i++) {
	                View child = vg.getChildAt(i);
	                overrideFonts(context, child);
	         }
	        } else if (v instanceof TextView ) {
	            ((TextView) v).setTypeface(typeace);
	        }
	        else if (v instanceof EditText ) {
	            ((EditText) v).setTypeface(typeace);
	        }
	        else if (v instanceof Button ) {
	            ((Button) v).setTypeface(typeace);
	        }
	    } catch (Exception e) {
	 }
	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            /*this.overridePendingTransition(R.anim.slide_in_right,
                    R.anim.slide_out_right);*/
           // overridePendingTransition(R.anim.activity_open_scale,R.anim.activity_close_translate);

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
       /* this.overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_right);*/
       // overridePendingTransition(R.anim.activity_open_scale,R.anim.activity_close_translate);

    }

}

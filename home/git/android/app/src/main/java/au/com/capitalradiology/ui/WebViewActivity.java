package au.com.capitalradiology.ui;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.webkit.WebView;
import android.widget.TextView;

import au.com.capitalradiology.BaseActivity;
import au.com.capitalradiology.MyApplication;
import au.com.capitalradiology.R;
import au.com.capitalradiology.Utils.Adapter;
import au.com.capitalradiology.Utils.Utils;

public class WebViewActivity extends BaseActivity
{
    private String url;
    private WebView webView;
    private TextView txt_view;
    private Toolbar mToolbar;
    private MyApplication myApplication;
    private Context context = WebViewActivity.this;
    private String MRN,ACC;

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.activity_web_view);

        myApplication = (MyApplication)getApplication();
        Utils.googleAnalytics(myApplication, getString(R.string.Welcome));

        initUi();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        if (getIntent().getExtras() != null)
        {
            MRN = getIntent().getExtras().getString("MRN");
            ACC = getIntent().getExtras().getString("ACC");
            //getWebData(getIntent().getExtras().getString("MRN"),getIntent().getExtras().getString("ACC"));

            new getRecordDetails().execute();
        }

    }

    private void getWebData(String MRN, String ACC)
    {
        Adapter adb = new Adapter(context);
        adb.getPatient_Record_Details(MRN, ACC, new Adapter.SynceDataListener<String>() {
            @Override
            public void onSynced(String bin) {
                txt_view.setText(Html.fromHtml(bin));
            }
        });
    }

    private void initUi()
    {
        webView = (WebView)findViewById(R.id.webView);
        txt_view = (TextView)findViewById(R.id.txt_view);
        mToolbar = (Toolbar)findViewById(R.id.toolbar_action);
    }

    private class getRecordDetails extends AsyncTask<Void,Void,Void>
    {
        Adapter adb = new Adapter(context);
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected Void doInBackground(Void... voids) {
            adb.getPatientDetails(MRN, ACC);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package au.com.capitalradiology.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import au.com.capitalradiology.MyApplication;
import au.com.capitalradiology.R;
import au.com.capitalradiology.Utils.Utils;
import au.com.capitalradiology.ui.HomeActivity;

public class SignUpStartActivity extends AppCompatActivity
{
   private Button btnLetsGo;
   private TextView txtMaybeLater;
   private MyApplication myApplication;

    public SignUpStartActivity()
    {
    }

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.activity_sign_up_start);
        initUI();

        myApplication = (MyApplication)getApplication();
        Utils.googleAnalytics(myApplication, getString(R.string.Signup_Start));

        initListeners();
    }

    private void initListeners()
    {
        btnLetsGo.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view)
            {
                Intent intent = new Intent(SignUpStartActivity.this, SignUpAlreadyPatient.class);
                finish();
                startActivity(intent);
            }
        });

        txtMaybeLater.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view)
            {
                Intent intent= new Intent(SignUpStartActivity.this, HomeActivity.class);
                finish();
                startActivity(intent);
            }

        });
    }

    private void initUI()
    {
        btnLetsGo = (Button)findViewById(R.id.btnLetsGo);
        txtMaybeLater = (TextView)findViewById(R.id.txtMaybeLater);
        Utils.makeRoundedButton(btnLetsGo,getResources().getColor(R.color.button_background_white));
    }


}

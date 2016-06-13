// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package au.com.capitalradiology.ui.request_appointment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONObject;

import au.com.capitalradiology.BaseActivity;
import au.com.capitalradiology.MyApplication;
import au.com.capitalradiology.R;
import au.com.capitalradiology.Utils.Adapter;
import au.com.capitalradiology.Utils.Utils;
import au.com.capitalradiology.model.BinForPromoCode;

public class PromoCode extends BaseActivity
{
    private Button btn_Apply;
    private EditText edt_Promo;
    private MyApplication myApplication;
    private Context context = PromoCode.this;
    private Toolbar mToolbar;

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.promo_code);

        initUI();

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.PromoCode));
        myApplication = (MyApplication)getApplication();
        Utils.googleAnalytics(myApplication, getString(R.string.PromoCode));

        initListeners();
     }

    private void initListeners()
    {
        btn_Apply.setOnClickListener(new View.OnClickListener() {
           public void onClick(View view)
            {
                CheckPromo(edt_Promo.getText().toString());
            }
        });

        edt_Promo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

                if(!edt_Promo.getText().toString().equalsIgnoreCase("")) {
                    btn_Apply.setEnabled(true);
                    btn_Apply.setBackgroundColor(getResources().getColor(R.color.color_app_primary));
                }
                else {
                    btn_Apply.setEnabled(false);
                    btn_Apply.setBackgroundColor(getResources().getColor(R.color.button_disabled_color));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void CheckPromo(final String strPromo)
    {
        Adapter adb = new Adapter(context);
        adb.Promo_Applied_OR_Not(strPromo,new Adapter.SynceDataListener<JSONObject>() {
            @Override
            public void onSynced(JSONObject bin) {
                if(bin.has("resource"))
                {
                    try {
                        if (bin.getJSONArray("resource").length() > 0) {
                            Utils.dismissProgressDialog();
                            Utils.showToast(context, "You already used this promo code");
                        }
                        else
                            getPromoCodeValue(strPromo);
                    }
                    catch (Exception e)
                    {

                    }
                }
            }
        });
    }

    private void getPromoCodeValue(String strPromo)
    {
        Adapter adb = new Adapter(context);
        adb.Get_Promo_Code(strPromo,new Adapter.SynceDataListener<BinForPromoCode>() {
            @Override
            public void onSynced(BinForPromoCode bin) {

                if(bin!=null && bin.getResource().size()>0) {
                    Intent intent = new Intent();
                    intent.putExtra("PromoPercentage", bin.getResource().get(0).getDiscountPercent());
                    intent.putExtra("PromoValue", bin.getResource().get(0).getDiscountValue());
                    intent.putExtra("PromoCode", bin.getResource().get(0).getCode());
                    setResult(RESULT_OK, intent);
                    finish();
                }
                else
                    Utils.showToast(context,"Please enter valid promo code");
            }
        });
    }

    private void initUI()
    {
        btn_Apply = (Button)findViewById(R.id.txt_Apply);
        edt_Promo = (EditText)findViewById(R.id.edt_PromoCode);
        mToolbar = (Toolbar)findViewById(R.id.toolbar_action);
    }
}

// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package au.com.capitalradiology.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;

import java.util.Iterator;
import java.util.LinkedHashMap;

import au.com.capitalradiology.MyApplication;
import au.com.capitalradiology.R;
import au.com.capitalradiology.Utils.UserSession;
import au.com.capitalradiology.Utils.Utils;
import au.com.capitalradiology.ui.register.SignUpStartActivity;


public class WelcomeActivity extends AppCompatActivity
    implements BaseSliderView.OnSliderClickListener, com.daimajia.slider.library.Tricks.ViewPagerEx.OnPageChangeListener
{

    private Button btnSignIn;
    private Button btnSignUp;
    private PagerIndicator customPagerIndicator;
    private LinkedHashMap imagesRef;
    private SliderLayout mDemoSlider;
    private TextView txtHeader;
    private TextView txtSecondaryText;
    private MyApplication myApplication;

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.activity_welcome);

        myApplication = (MyApplication)getApplication();
        myApplication.Welcome = WelcomeActivity.this;
        Utils.googleAnalytics(myApplication, getString(R.string.Welcome));

        initUI();
        initListeners();
        initSlider();
    }

    private void initListeners()
    {
        btnSignUp.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view)
            {
                Intent intent = new Intent(WelcomeActivity.this, SignUpStartActivity.class);
                startActivity(intent);
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view)
            {
                Intent intent = new Intent(WelcomeActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initSlider()
    {
        imagesRef = new LinkedHashMap();
        imagesRef.put("Slider1", R.drawable.img_intro_1);
        imagesRef.put("Slider2", R.drawable.img_intro_2);
        imagesRef.put("Slider3", R.drawable.img_intro_3);
        imagesRef.put("Slider4", R.drawable.img_intro_4);
        DefaultSliderView defaultsliderview;
        for (Iterator iterator = imagesRef.keySet().iterator(); iterator.hasNext(); mDemoSlider.addSlider(defaultsliderview))
        {
            String s = (String)iterator.next();
            defaultsliderview = new DefaultSliderView(this);
            defaultsliderview.image(((Integer)imagesRef.get(s)).intValue()).setScaleType(BaseSliderView.ScaleType.CenterCrop).setOnSliderClickListener(this);
        }

        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Default);
        mDemoSlider.setCustomIndicator(customPagerIndicator);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(2000L);
        mDemoSlider.addOnPageChangeListener(this);
    }

    private void initUI()
    {
        mDemoSlider = (SliderLayout)findViewById(R.id.slider);
        customPagerIndicator = (PagerIndicator)findViewById(R.id.custom_indicator);
        txtHeader = (TextView)findViewById(R.id.txtHeader);
        txtSecondaryText = (TextView)findViewById(R.id.txtSecondaryText);
        btnSignUp = (Button)findViewById(R.id.btnSignUp);
        btnSignIn = (Button)findViewById(R.id.btnSignIn);

        Utils.makeRoundedButton(btnSignUp,getResources().getColor(R.color.green_light));
        Utils.makeRoundedButton(btnSignIn,getResources().getColor(R.color.blue_light));
        UserSession.save_Api_Key(getResources().getString(R.string.DF_ANDROID_API_KEY));
    }

    public void onPageScrollStateChanged(int i)
    {
    }

    public void onPageScrolled(int i, float f, int j)
    {
    }

    public void onPageSelected(int i)
    {
        switch (i)
        {
        default:
            txtHeader.setVisibility(View.VISIBLE);
            txtSecondaryText.setVisibility(View.VISIBLE);
            return;

        case 0: // '\0'
            txtHeader.setVisibility(View.VISIBLE);
            txtSecondaryText.setVisibility(View.VISIBLE);
            txtHeader.setText(getString(R.string.slider_1_header_text));
            txtSecondaryText.setText(getString(R.string.slider_1_secondary_text));
            return;

        case 1: // '\001'
            txtHeader.setVisibility(View.VISIBLE);
            txtSecondaryText.setVisibility(View.VISIBLE);
            txtHeader.setText(getString(R.string.slider_2_header_text));
            txtSecondaryText.setText(getString(R.string.slider_2_secondary_text));
            return;

        case 2: // '\002'
            txtHeader.setVisibility(View.VISIBLE);
            txtSecondaryText.setVisibility(View.VISIBLE);
            txtHeader.setText(getString(R.string.slider_3_header_text));
            txtSecondaryText.setText(getString(R.string.slider_3_secondary_text));
            return;

        case 3: // '\003'
            txtHeader.setVisibility(View.VISIBLE);
            break;
        }
        txtSecondaryText.setVisibility(View.VISIBLE);
        txtHeader.setText(getString(R.string.slider_4_header_text));
        txtSecondaryText.setText(getString(R.string.slider_4_secondary_text));
    }

    public void onSliderClick(BaseSliderView basesliderview)
    {
    }

    protected void onStop()
    {
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }
}

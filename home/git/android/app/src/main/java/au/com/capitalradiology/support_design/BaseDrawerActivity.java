// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package au.com.capitalradiology.support_design;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import au.com.capitalradiology.R;
import au.com.capitalradiology.Utils.UserSession;
import au.com.capitalradiology.Utils.Utils;
import au.com.capitalradiology.ui.SignInActivity;
import au.com.capitalradiology.ui.SplashScreenActivity;
import au.com.capitalradiology.ui.WebViewActivity;
import au.com.capitalradiology.ui.profile.Edit_Profile;


// Referenced classes of package au.com.capitalradiology.mobileapp.views:
//            SplashScreenActivity

public class BaseDrawerActivity extends AppCompatActivity
{

    ActionBarDrawerToggle actionBarDrawerToggle;
    protected DrawerLayout baseLayout;
    TextView drawerHeaderLogout;
    TextView drawerHeaderUsername;
    DrawerLayout drawerLayout;
    protected FrameLayout frameLayoutMain;
    private MenuItem mPrevMenuItem;
    NavigationView navigationView;
    Toolbar toolbar;
    private Context context = BaseDrawerActivity.this;

    public BaseDrawerActivity()
    {
    }

    public void onConfigurationChanged(Configuration configuration)
    {
        super.onConfigurationChanged(configuration);
        actionBarDrawerToggle.onConfigurationChanged(configuration);
    }

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
    }

    protected void onCreateDrawer()
    {
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigationView = (NavigationView)findViewById(R.id.navigation_view);
        Object obj = navigationView.inflateHeaderView(R.layout.header_drawer_user);
        drawerHeaderUsername = (TextView)((View) (obj)).findViewById(R.id.drawer_header_username);
        drawerHeaderLogout = (TextView)((View) (obj)).findViewById(R.id.drawer_header_logout);
        TextView textview = drawerHeaderUsername;
        StringBuilder stringbuilder = (new StringBuilder()).append(getString(R.string.drawer_empty_user)).append(" ");
        UserSession.sharedInstance(this);
        if (UserSession.currentSession().firstName != null)
        {
            UserSession.sharedInstance(this);
            obj = UserSession.currentSession().firstName;
        } else
        {
            obj = "";
        }
        textview.setText(stringbuilder.append(((String) (obj))).toString());
        UserSession.sharedInstance(this);
        if (UserSession.currentSession().firstName == null)
        {
            drawerHeaderLogout.setText(getString(R.string.drawer_sign_in));
        } else
        {
            drawerHeaderLogout.setText(getString(R.string.drawer_logout));
        }
        drawerHeaderLogout.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view)
            {
                if(drawerHeaderLogout.getText().toString().equalsIgnoreCase(getString(R.string.drawer_logout))) {
                    UserSession.sharedInstance(BaseDrawerActivity.this);
                    UserSession.removeCurrentUser();
                    Utils.clearRegisterationID(context);
                    Intent intent = new Intent(BaseDrawerActivity.this, SplashScreenActivity.class);
                    finish();
                    startActivity(intent);
                }
                else{
                    Intent intent1 = new Intent(context, SignInActivity.class);
                    finish();
                    startActivity(intent1);
                }
            }

        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            public boolean onNavigationItemSelected(MenuItem menuitem) {
                if (mPrevMenuItem != null) {
                    mPrevMenuItem.setChecked(false);
                }
                menuitem.setChecked(true);
                drawerLayout.closeDrawers();
                mPrevMenuItem = menuitem;

                switch (menuitem.getItemId()) {
                    case 2131493078:
                    default:
                        Utils.showToast(getApplicationContext(), "Somethings Wrong");
                        return true;

                    case R.id.drawer_care_invite:
                    /*Intent intent = new Intent(context, ContactEmail.class);
                    startActivity(intent);*/
                        Intent intent = new Intent("android.intent.action.SEND");
                        intent.setType("text/plain");
                        intent.putExtra("android.intent.extra.TEXT", "Try out this useful Capital Radiology App. coming Store: <link>");
                        startActivity(Intent.createChooser(intent, "Share via"));
                        return true;

               /* case R.id.drawer_care_switch:
                    Utils.showToast(getApplicationContext(), "Switch Account");
                    return true;*/

                    case R.id.drawer_more_settings:
                        if (UserSession.currentSession().userToken != null) {
                            Intent intent1 = new Intent(context, Edit_Profile.class);
                            startActivity(intent1);
                        } else {
                            Intent intent1 = new Intent(context, SignInActivity.class);
                            finish();
                            startActivity(intent1);
                        }
                        //Utils.showToast(getApplicationContext(), "Settings");
                        return true;

                    case R.id.drawer_more_about:
                        Intent intent2 = new Intent(context, WebViewActivity.class);
                        intent2.putExtra("KEY_TITLE", "About us");
                        intent2.putExtra("KEY_URL", getResources().getString(R.string.about_us));
                        startActivity(intent2);
                        //Utils.showToast(getApplicationContext(), "About");
                        break;
                }
                return true;
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open,R.string.drawer_close) {

            public void onDrawerClosed(View view)
            {
                super.onDrawerClosed(view);
            }

            public void onDrawerOpened(View view)
            {
                super.onDrawerOpened(view);
            }

        };
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuitem)
    {
        int i;
        for (i = menuitem.getItemId(); actionBarDrawerToggle.onOptionsItemSelected(menuitem) || i == R.id.action_settings;)
        {
            return true;
        }

        if (i == R.id.action_call)
        {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:"+getResources().getString(R.string.contact_us)));
            startActivity(intent);
            return true;
        } else
        {
            return super.onOptionsItemSelected(menuitem);
        }
    }

    protected void onPostCreate(Bundle bundle)
    {
        super.onPostCreate(bundle);
        actionBarDrawerToggle.syncState();
    }

    public void setContentView(int i)
    {
        baseLayout = (DrawerLayout)getLayoutInflater().inflate(R.layout.activity_base_drawer, null);
        frameLayoutMain = (FrameLayout)baseLayout.findViewById(R.id.content_frame);
        getLayoutInflater().inflate(i, frameLayoutMain, true);
        super.setContentView(baseLayout);
        onCreateDrawer();
    }



/*
    static MenuItem access$002(BaseDrawerActivity basedraweractivity, MenuItem menuitem)
    {
        basedraweractivity.mPrevMenuItem = menuitem;
        return menuitem;
    }

*/
}

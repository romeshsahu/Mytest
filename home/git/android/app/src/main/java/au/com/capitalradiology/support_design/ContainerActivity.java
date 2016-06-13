package au.com.capitalradiology.support_design;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import au.com.capitalradiology.R;
import au.com.capitalradiology.ldrawer.DrawerArrowDrawable;
import au.com.capitalradiology.ui.HomeFragment;

public class ContainerActivity extends AppCompatActivity
{
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerArrowDrawable drawerArrow;
    private LinearLayout mDrawerList;

    private boolean isDrawerOpen = false;
    private Toolbar mToolbar;
    //Fragment
    private FragmentManager fragmentManager;
    private HomeFragment homeFragment;

    //Fragment variable
    private Context context = ContainerActivity.this;
   /* private int currentFragmentId=1;
    private int HOME_FRAGMENT=1;
    public int LOGIN_FRAGMENT=2;
    public  int SETTING_FRAGMENT=3;*/

    //fragmentTag
    public  final String HOME_FRAGMENT_TAG="home";
    private String[] TAG_ARRAY = {HOME_FRAGMENT_TAG};
    // private FragmentHandler fragmentHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_container);
        // getActionBar().setDisplayHomeAsUpEnabled(true);
//        getActionBar().setHomeButtonEnabled(true);
		/*ActionBar ab = getActionBar();
	    ab.setDisplayHomeAsUpEnabled(true);
	    ab.setHomeButtonEnabled(true);
	    ab.show();*/
        // ab.setTitle("GoEval");
        initialization();

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.app_name);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        setFragmentManager(getFragmentManager());
		
        /*int width = getResources().getDisplayMetrics().widthPixels/2;
        DrawerLayout.LayoutParams params = (DrawerLayout.LayoutParams) mDrawerList.getLayoutParams();
        params.width = width;
        mDrawerList.setLayoutParams(params);*/

        //fragmentHandler = new FragmentHandler();
		/*Bundle bundle = new Bundle();
		bundle.putString("template", "My Templates");*/
        replaceFragment(1, null, false);

    }

    public void initialization()
    {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (LinearLayout) findViewById(R.id.navdrawer);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_action);
       // mDrawerLayout.setScrimColor(getResources().getColor(android.R.color.transparent));
        final RelativeLayout move_view = (RelativeLayout) findViewById(R.id.move_view);
        homeFragment = new HomeFragment();

        drawerArrow = new DrawerArrowDrawable(this)
        {
            @Override
            public boolean isLayoutRtl()
            {
                return false;
            }
        };

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,mToolbar, R.string.drawer_open,R.string.drawer_close)
        {
            public void onDrawerClosed(View view)
            {
                super.onDrawerClosed(view);
                //invalidateOptionsMenu();
                isDrawerOpen = true;
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //invalidateOptionsMenu();
                isDrawerOpen = false;
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                float moveFactor = (mDrawerList.getWidth() * slideOffset);
                move_view.setTranslationX(moveFactor);
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        //mDrawerToggle.syncState();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == android.R.id.home)
        {
            if (mDrawerLayout.isDrawerOpen(mDrawerList))
            {
                mDrawerLayout.closeDrawer(mDrawerList);
            }
            else
            {
                mDrawerLayout.openDrawer(mDrawerList);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public FragmentManager getFragmentmanager() {
        return fragmentManager;
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }


    @Override
    public void onBackPressed()
    {
        int cnt = getFragmentmanager().getBackStackEntryCount();
        if (cnt>1)
        {
            getFragmentmanager().popBackStack();

        } else {
            finish();
        }
    }


    public void replaceFragment(int frag_no,Bundle bundle,boolean addToBackStack)
    {
        Fragment frag = null;

        switch (frag_no)
        {
            case 1:
                frag = homeFragment;
                break;

            default:
                break;
        }

        FragmentHandler handler = new FragmentHandler();
        handler.displayFragment(frag, getFragmentManager(), TAG_ARRAY[frag_no-1], bundle, addToBackStack);
    }

}

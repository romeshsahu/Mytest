package au.com.capitalradiology;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import au.com.capitalradiology.Utils.Utils;

public class Foreground implements Application.ActivityLifecycleCallbacks {
 
    public static final long CHECK_DELAY = 500;
    public static final String TAG = Foreground.class.getName();
     private Context context;
    private Activity activity = null;
    private static final int REQ_CREATE_PATTERN = 1;
    private MyApplication myApplication;


    public interface Listener {
 
        public void onBecameForeground();
 
        public void onBecameBackground();
 
    }
 
    private static Foreground instance;
 
    private boolean foreground = false, paused = true;
    private Handler handler = new Handler();
    private List<Listener> listeners = new CopyOnWriteArrayList<Listener>();
    private Runnable check;

    /**
     * Its not strictly necessary to use this method - _usually_ invoking
     * get with a Context gives us a path to retrieve the Application and
     * initialise, but sometimes (e.g. in  test harness) the ApplicationContext
     * is != the Application, and the docs make no guarantees.
     *
     * @param application
     * @return an initialised Foreground instance
     */

    public Foreground(Context context, Activity act)
    {
        this.context=context;
        activity = act;
        myApplication = (MyApplication)context.getApplicationContext();
    }

    public static Foreground init(Application application,Context context,Activity activity){
        if (instance == null) {
            instance = new Foreground(context,activity);

            application.registerActivityLifecycleCallbacks(instance);
        }
        return instance;
    }
 
    public static Foreground get(Application application,Context context,Activity activity){
        if (instance == null) {
            init(application,context,activity);
        }
        return instance;
    }
 
    public static Foreground get(Context ctx,Activity act){
        if (instance == null) {
            Context appCtx = ctx.getApplicationContext();
            if (appCtx instanceof Application) {
                init((Application)appCtx,ctx,act);
            }
            throw new IllegalStateException(
                "Foreground is not initialised and " +
                "cannot obtain the Application object");
        }
        return instance;
    }
 
    public static Foreground get(){
        if (instance == null) {
            throw new IllegalStateException(
                "Foreground is not initialised - invoke " +
                "at least once with parameterised init/get");
        }
        return instance;
    }
 
    public boolean isForeground(){
        return foreground;
    }
 
    public boolean isBackground(){
        return !foreground;
    }
 
    public void addListener(Listener listener){
        listeners.add(listener);
    }
 
    public void removeListener(Listener listener){
        listeners.remove(listener);
    }
 
    @Override
    public void onActivityResumed(Activity activity) {
        paused = false;
        boolean wasBackground = !foreground;
        foreground = true;
 
        if (check != null)
            handler.removeCallbacks(check);
 
        if (wasBackground){
            Utils.LogE("still background - Resume");
            Utils.googleAnalytics(myApplication,"App Closed");

        } else {
            Utils.LogE("still foreground - Resume");
            Utils.googleAnalytics(myApplication,"App Opened");
        }
    }
 
    @Override
    public void onActivityPaused(Activity activity) {
        paused = true;
 
        if (check != null)
            handler.removeCallbacks(check);
 
        handler.postDelayed(check = new Runnable(){
            @Override
            public void run() {
                if (foreground && paused) {
                    foreground = false;
                    Utils.LogE("went background - pause");
                    Utils.googleAnalytics(myApplication,"App Closed");

                    for (Listener l : listeners) {
                        try {
                            l.onBecameBackground();
                        } catch (Exception exc) {
                            Utils.LogE( "Listener threw exception! "+exc);
                        }
                    }
                } else {
                    Utils.LogE( "still foreground - pause");
                    Utils.googleAnalytics(myApplication,"App Opened");
                }
            }
        }, CHECK_DELAY);
    }
 
    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {}
 
    @Override
    public void onActivityStarted(Activity activity) {}
 
    @Override
    public void onActivityStopped(Activity activity) {}
 
    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {}
 
    @Override
    public void onActivityDestroyed(Activity activity) {}




    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQ_CREATE_PATTERN: {
                if (resultCode ==  activity.RESULT_OK) {
                    char[] pattern = data.getCharArrayExtra(LockPatternActivity.EXTRA_PATTERN);

                    Utils.LogE("Activity Result");
                }

                break;
            }// REQ_CREATE_PATTERN
        }
    }*/

    private void setResult(int requestCode, int resultCode, Intent data)
    {
        //BaseActivity baseActivity = new BaseActivity();
        //baseActivity.onActivityResult(requestCode,resultCode,data);
    }
}
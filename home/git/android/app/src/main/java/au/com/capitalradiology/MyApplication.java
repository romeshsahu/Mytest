package au.com.capitalradiology;

import android.app.Activity;
import android.app.Application;
import android.text.TextUtils;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import au.com.capitalradiology.request.MyVolley;
import au.com.capitalradiology.volley.DefaultRetryPolicy;
import au.com.capitalradiology.volley.Request;
import au.com.capitalradiology.volley.RequestQueue;
import au.com.capitalradiology.volley.VolleyLog;
import au.com.capitalradiology.volley.toolbox.Volley;


public class MyApplication extends Application
{
    RequestQueue mRequestQueue = null;
    String TAG = "Application Class";
    private Tracker mTracker;
    public Activity Welcome;

	@Override
	public void onCreate() 
	{
		super.onCreate();
		
		init();

		// Foreground.init(this,MyApplication.this);
		
		DisplayImageOptions options = new DisplayImageOptions.Builder()
	    .showImageForEmptyUri(R.drawable.ic_female_user)
	    .showImageOnFail(R.drawable.ic_female_user)
	    .showStubImage(R.drawable.ic_female_user)
	     //rounded corner bitmap 
	    .delayBeforeLoading(1000) 
	    .resetViewBeforeLoading(false)  // default
	    .cacheInMemory(true) // default
	    .cacheOnDisc(true) // default
	    .build();
		
		 ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
		    getApplicationContext())
		    .memoryCache(new LruMemoryCache(100 * 1024 * 1024))
		    .memoryCacheSize(100 * 1024 * 1024)
		    .discCacheSize(500 * 1024 * 1024).discCacheFileCount(10000)
		    .defaultDisplayImageOptions(options).build();
		  ImageLoader.getInstance().init(config);

	}


    /*public static GoogleAnalytics analytics() {
        return analytics;
    }*/


	
	private void init() {

		MyVolley.init(this);
	}

	/**
	 * @return The Volley Request queue, the queue will be created if it is null
	 */
	public RequestQueue getRequestQueue() {
		// lazy initialize the request queue, the queue instance will be
		// created when it is accessed for the first time
		if (mRequestQueue == null) {
			mRequestQueue = Volley.newRequestQueue(getApplicationContext());
		}

		return mRequestQueue;
	}

    synchronized public Tracker getDefaultTracker() {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
            mTracker = analytics.newTracker(getResources().getString(R.string.google_tacker_Id));
           // getResources().getString(capital.com.capital.R.string.google_tra)
        }
        return mTracker;
    }

	/**
	 * Adds the specified request to the global queue, if tag is specified then
	 * it is used else Default TAG is used.
	 * 
	 * @param req
	 * @param tag
	 */
	public <T> void addToRequestQueue(Request<T> req, String tag) {
		// set the default tag if tag is empty
		req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);

		VolleyLog.d("Adding request to queue: %s", req.getUrl());

		getRequestQueue().add(req);
	}


	public <T> void addToRequestQueue(Request<T> req) {
		// set the default tag if tag is empty
		req.setTag(TAG);
		req.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));
		getRequestQueue().add(req);
	}

	/**
	 * Cancels all pending requests by the specified TAG, it is important to
	 * specify a TAG so that the pending/ongoing requests can be cancelled.
	 * 
	 * @param tag
	 */
	public void cancelPendingRequests(Object tag) {
		if (mRequestQueue != null) {
			mRequestQueue.cancelAll(tag);
		}
	}
	
	 public void cancelPendingRequests() 
	 {
		 if (mRequestQueue != null) 
		 {
		   // mRequestQueue.cancelAll(TAG);
		   mRequestQueue.cancelAll(new RequestQueue.RequestFilter() {
		    @Override
		    public boolean apply(Request<?> request)
		    {
		    	return true;
		    }
		   });
		 }
	 }
}
package com.chartboost.example;

import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

// Import the Chartboost SDK
import com.chartboost.sdk.Chartboost;
import com.chartboost.sdk.ChartboostDelegate;
import com.chartboost.sdk.Analytics.CBAnalytics;

public class ChartboostExampleActivity extends Activity {

	// Create the Chartboost object
    private Chartboost cb;
	
    // This is used to display Toast messages and is not necessary for your app
    private static final String TAG = "Chartboost";
     
    /*
     * Initialize Chartboost in your onCreate method
     * 
     * Make sure you use your own App ID & App Signature
     * You can create these in the Chartboost dashboard (Apps > Add New App...)
     */
	@Override
	public void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.main);

		/*
		 * Configure Chartboost
		 * 
		 * Copy/Paste the 4 lines below into your project
		 * 
		 * cb.onCreate() should only be called once when your activity is created
		 * format: cb.onCreate(Context, String appId, String appSignature, ChartboostDelegate)
		 * 
		 * If you are not using delegate methods, pass null into the 4th parameter
		 */
		this.cb = Chartboost.sharedChartboost();
		String appId = "4f7b433509b6025804000002";
		String appSignature = "dd2d41b69ac01b80f443f5b6cf06096d457f82bd";
		this.cb.onCreate(this, appId, appSignature, this.chartBoostDelegate);
		
		/*
		 *  Use activities instead of a view
		 *  
		 *  Implement cb.setImpressionsUseActivities(true);
		 *  
		 *  Note:
		 *  - Be sure to add the activity to your AndroidManifest.xml
		 */
		//this.cb.setImpressionsUseActivities(true);
		
		/*
		 * Notify the beginning of a user session
		 * 
		 * You may place cb.startSession() in onCreate(), onStart(), or onResume()
		 */
		this.cb.startSession();

	}

	/*
	 * Add cb.onStart(this) to your activity's onStart() method
	 * 
	 * If you do not have an onStart() method, create one using the code below
	 */
	@Override
	protected void onStart() {
		super.onStart();
		
		this.cb.onStart(this);
	}

	/*
	 * Add cb.onStop(this) to your activity's onStop() method
	 * 
	 * If you do not have an onStop() method, create one using the code below
	 */
	@Override
	protected void onStop() {
		super.onStop();
		
		this.cb.onStop(this);
	}
	
	/*
	 * Add cb.onDestroy(this) to your activity's onDestroy() method
	 * 
	 * If you do not have an onDestroy() method, create one using the code below
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		this.cb.onDestroy(this);
	}
	
	/*
	 * Add the code below to the top of your activity's onBackPressed() method
	 * 
	 * Required because Chartboost does not use an activity to display the view
	 * If the back button is pressed while a Chartboost view is showing,
	 * this will close it instead of quitting your app
	 * 
	 * If you do not have an onBackPressed() method, create one using the code below
	 */
	@Override
	public void onBackPressed() {
		if (this.cb.onBackPressed())
			// If a Chartboost view exists, close it and return
			return;
		else
			// If no Chartboost view exists, continue on as normal
			super.onBackPressed();
	}
    
	private ChartboostDelegate chartBoostDelegate = new ChartboostDelegate() {

		/*
		 * Chartboost delegate methods
		 * 
		 * Implement the delegate methods below to finely control Chartboost's behavior in your app
		 * 
		 * Minimum recommended: shouldDisplayInterstitial()
		 */
	
		
		/* 
		 * shouldDisplayInterstitial(String location)
		 *
		 * This is used to control when an interstitial should or should not be displayed
		 * If you should not display an interstitial, return false
		 *
		 * For example: during gameplay, return false.
		 *
		 * Is fired on:
		 * - showInterstitial()
		 * - Interstitial is loaded & ready to display
		 */
		@Override
		public boolean shouldDisplayInterstitial(String location) {
			Log.i(TAG, "SHOULD DISPLAY INTERSTITIAL '"+location+ "'?");
			return true;
		}
		
		/*
		 * shouldRequestInterstitial(String location)
		 * 
		 * This is used to control when an interstitial should or should not be requested
		 * If you should not request an interstitial from the server, return false
		 *
		 * For example: user should not see interstitials for some reason, return false.
		 *
		 * Is fired on:
		 * - cacheInterstitial()
		 * - showInterstitial() if no interstitial is cached
		 * 
		 * Notes: 
		 * - We do not recommend excluding purchasers with this delegate method
		 * - Instead, use an exclusion list on your campaign so you can control it on the fly
		 */
		@Override
		public boolean shouldRequestInterstitial(String location) {
			Log.i(TAG, "SHOULD REQUEST INSTERSTITIAL '"+location+ "'?");
			return true;
		}
		
		/*
		 * didCacheInterstitial(String location)
		 * 
		 * Passes in the location name that has successfully been cached
		 * 
		 * Is fired on:
		 * - cacheInterstitial() success
		 * - All assets are loaded
		 * 
		 * Notes:
		 * - Similar to this is: cb.hasCachedInterstitial(String location) 
		 * Which will return true if a cached interstitial exists for that location
		 */
		@Override
		public void didCacheInterstitial(String location) {
			Log.i(TAG, "INTERSTITIAL '"+location+"' CACHED");
		}

		/*
		 * didFailToLoadInterstitial(String location)
		 * 
		 * This is called when an interstitial has failed to load for any reason
		 * 
		 * Is fired on:
		 * - cacheInterstitial() failure
		 * - showInterstitial() failure if no interstitial was cached
		 * 
		 * Possible reasons:
		 * - No network connection
		 * - No publishing campaign matches for this user (go make a new one in the dashboard)
		 */
		@Override
		public void didFailToLoadInterstitial(String location) {
		    // Show a house ad or do something else when a chartboost interstitial fails to load

			Log.i(TAG, "INTERSTITIAL '"+location+"' REQUEST FAILED");
			Toast.makeText(ChartboostExampleActivity.this, "Interstitial '"+location+"' Load Failed",
					Toast.LENGTH_SHORT).show();
		}

		/*
		 * didDismissInterstitial(String location)
		 *
		 * This is called when an interstitial is dismissed
		 *
		 * Is fired on:
		 * - Interstitial click
		 * - Interstitial close
		 *
		 * #Pro Tip: Use the delegate method below to immediately re-cache interstitials
		 */
		@Override
		public void didDismissInterstitial(String location) {
			
			// Immediately re-caches an interstitial
			cb.cacheInterstitial(location);
			
			Log.i(TAG, "INTERSTITIAL '"+location+"' DISMISSED");
			Toast.makeText(ChartboostExampleActivity.this, "Dismissed Interstitial '"+location+"'",
					Toast.LENGTH_SHORT).show();
		}

		/*
		 * didCloseInterstitial(String location)
		 *
		 * This is called when an interstitial is closed
		 *
		 * Is fired on:
		 * - Interstitial close
		 */
		@Override
		public void didCloseInterstitial(String location) {
			Log.i(TAG, "INSTERSTITIAL '"+location+"' CLOSED");
			Toast.makeText(ChartboostExampleActivity.this, "Closed Interstitial '"+location+"'",
					Toast.LENGTH_SHORT).show();
		}

		/*
		 * didClickInterstitial(String location)
		 *
		 * This is called when an interstitial is clicked
		 *
		 * Is fired on:
		 * - Interstitial click
		 */
		@Override
		public void didClickInterstitial(String location) {
			Log.i(TAG, "DID CLICK INTERSTITIAL '"+location+"'");
			Toast.makeText(ChartboostExampleActivity.this, "Clicked Interstitial '"+location+"'",
					Toast.LENGTH_SHORT).show();
		}

		/*
		 * didShowInterstitial(String location)
		 *
		 * This is called when an interstitial has been successfully shown
		 *
		 * Is fired on:
		 * - showInterstitial() success
		 */
		@Override
		public void didShowInterstitial(String location) {
			Log.i(TAG, "INTERSTITIAL '" + location + "' SHOWN");
		}

		/*
		 * More Apps delegate methods
		 */
		
		/*
		 * shouldDisplayLoadingViewForMoreApps()
		 *
		 * Return false to prevent the pretty More-Apps loading screen
		 *
		 * Is fired on:
		 * - showMoreApps()
		 */
		@Override
		public boolean shouldDisplayLoadingViewForMoreApps() {
			return true;
		}

		/*
		 * shouldRequestMoreApps()
		 * 
		 * Return false to prevent a More-Apps page request
		 *
		 * Is fired on:
		 * - cacheMoreApps()
		 * - showMoreApps() if no More-Apps page is cached
		 */
		@Override
		public boolean shouldRequestMoreApps() {

			return true;
		}

		/*
		 * shouldDisplayMoreApps()
		 * 
		 * Return false to prevent the More-Apps page from displaying
		 *
		 * Is fired on:
		 * - showMoreApps() 
		 * - More-Apps page is loaded & ready to display
		 */
		@Override
		public boolean shouldDisplayMoreApps() {
			Log.i(TAG, "SHOULD DISPLAY MORE APPS?");
			return true;
		}

		/*
		 * didFailToLoadMoreApps()
		 * 
		 * This is called when the More-Apps page has failed to load for any reason
		 * 
		 * Is fired on:
		 * - cacheMoreApps() failure
		 * - showMoreApps() failure if no More-Apps page was cached
		 * 
		 * Possible reasons:
		 * - No network connection
		 * - No publishing campaign matches for this user (go make a new one in the dashboard)
		 */
		@Override
		public void didFailToLoadMoreApps() {
			Log.i(TAG, "MORE APPS REQUEST FAILED");
			Toast.makeText(ChartboostExampleActivity.this, "More Apps Load Failed",
					Toast.LENGTH_SHORT).show();
		}

		/*
		 * didCacheMoreApps()
		 * 
		 * Is fired on:
		 * - cacheMoreApps() success
		 * - All assets are loaded
		 */
		@Override
		public void didCacheMoreApps() {
			Log.i(TAG, "MORE APPS CACHED");
		}

		/*
		 * didDismissMoreApps()
		 *
		 * This is called when the More-Apps page is dismissed
		 *
		 * Is fired on:
		 * - More-Apps click
		 * - More-Apps close
		 */
		@Override
		public void didDismissMoreApps() {
			Log.i(TAG, "MORE APPS DISMISSED");
			Toast.makeText(ChartboostExampleActivity.this, "Dismissed More Apps",
					Toast.LENGTH_SHORT).show();
		}

		/*
		 * didCloseMoreApps()
		 *
		 * This is called when the More-Apps page is closed
		 *
		 * Is fired on:
		 * - More-Apps close
		 */
		@Override
		public void didCloseMoreApps() {
			Log.i(TAG, "MORE APPS CLOSED");
			Toast.makeText(ChartboostExampleActivity.this, "Closed More Apps",
					Toast.LENGTH_SHORT).show();
		}

		/*
		 * didClickMoreApps()
		 *
		 * This is called when the More-Apps page is clicked
		 *
		 * Is fired on:
		 * - More-Apps click
		 */
		@Override
		public void didClickMoreApps() {
			Log.i(TAG, "MORE APPS CLICKED");
			Toast.makeText(ChartboostExampleActivity.this, "Clicked More Apps",
					Toast.LENGTH_SHORT).show();
		}

		/*
		 * didShowMoreApps()
		 *
		 * This is called when the More-Apps page has been successfully shown
		 *
		 * Is fired on:
		 * - showMoreApps() success
		 */
		@Override
		public void didShowMoreApps() {
			Log.i(TAG, "MORE APPS SHOWED");
		}

		/*
		 * shouldRequestInterstitialsInFirstSession()
		 *
		 * Return false if the user should not request interstitials until the 2nd startSession()
		 * 
		 */
		@Override
		public boolean shouldRequestInterstitialsInFirstSession() {
			return true;
		}
	};
	
	

    public void onLoadButtonClick(View view) {
    	/*
    	 * showInterstitial()
    	 * 
    	 * Shows an interstitial on the screen
    	 * 
    	 * Notes:
    	 * - Shows a cached interstitial if one exists
    	 * - Otherwise requests an interstitial and shows it
    	 */
    	this.cb.showInterstitial();

    	Log.i(TAG, "showInterstitial");
    	String toastStr = "Loading Interstitial";
    	if (cb.hasCachedInterstitial()) toastStr = "Loading Interstitial From Cache";
    	Toast.makeText(this, toastStr, Toast.LENGTH_SHORT).show();
    }
    
    public void onMoreButtonClick(View view) {
    	/*
    	 * showMoreApps()
    	 * 
    	 * Shows the More-Apps page
    	 * 
    	 * Notes:
    	 * - Shows a cached More-Apps page if one exists
    	 * - Otherwise requests the More-Apps page and shows it
    	 */
    	this.cb.showMoreApps();
    	
    	Log.i(TAG, "showMoreApps");
    	String toastStr = "Showing More-Apps";
    	if (cb.hasCachedMoreApps()) toastStr = "Showing More-Apps From Cache";
    	Toast.makeText(this, toastStr, Toast.LENGTH_SHORT).show();
    }
    
    public void onPreloadClick(View v){
    	/*
    	 * cacheInterstitial()
    	 * 
    	 * Requests an interstitial from the server
    	 */
    	this.cb.cacheInterstitial();
    	
    	Log.i(TAG, "cacheInterstitial");
    	Toast.makeText(this, "Caching Interstitial", Toast.LENGTH_SHORT).show();
    }
    
    
    public void onPreloadMoreAppsClick(View v){
    	/*
    	 * cacheMoreApps()
    	 * 
    	 * Requests a More-Apps page from the server
    	 */
    	cb.cacheMoreApps();

    	Log.i(TAG, "cacheMoreApps");
    	Toast.makeText(this, "Caching More-Apps", Toast.LENGTH_SHORT).show();
    }
    
    
    public void onPreloadClearClick(View v){
    	/*
    	 * clearCache()
    	 * 
    	 * Requests a More-Apps page from the server
    	 */
   		cb.clearCache();
    	
    	Log.i(TAG, "clearCache");
    	Toast.makeText(this, "Clearing cache", Toast.LENGTH_SHORT).show();
    }
    
    /*
     * ARPU Beta Methods
     * 
     * Request access to the ARPU beta program before using these methods
     */
	public void onPurchaseClick(View view) {
				
		Log.i(TAG, "Purchase Clicked!");
		HashMap<String, Object> meta = new HashMap<String, Object>();
		meta.put("fakemeta1", 5);
		meta.put("fakemeta2", "string");
		CBAnalytics.sharedAnalytics().recordPaymentTransaction(
				"OBJECT_001", "Test Object", 0.99, "$", 1, meta);
		Toast.makeText(this, "Recording Purchase Transaction",
				Toast.LENGTH_SHORT).show();
	}

	public void onTrackEventClick(View view) {
		HashMap<String, Object> meta = new HashMap<String, Object>();
		meta.put("fakeeventmeta1", 5);
		meta.put("fakeeventmeta2", "string");
		Log.i(TAG, "Track Event Clicked!");
		CBAnalytics.sharedAnalytics().trackEvent("EventName", 5, meta);
	}
}

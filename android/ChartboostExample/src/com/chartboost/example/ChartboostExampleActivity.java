package com.chartboost.example;

import java.util.HashMap;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

// Add this line to import the Chartboost SDK
import com.chartboost.sdk.*;

public class ChartboostExampleActivity extends Activity {
    
    private static final String TAG = "ChartBoost";
    private ChartBoost cb;
     
    
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        
        Log.i(TAG, "Hello world!");
        
        // Configure ChartBoost
        this.cb = ChartBoost.getSharedChartBoost(this);
        this.cb.setDelegate(this.chartBoostDelegate);
        //this.cb.setConnectionTimeout(1);
        //this.cb.setReadTimeout(1);
        setOrientation(getResources().getConfiguration());
        //ViewGroup view = (ViewGroup)this.findViewById(R.id.TestContainer);
        //this.cb.setInterstitialDisplayContainer(view);
        
        // Notify an install
        this.cb.install();
    }
    
    @Override 
    protected void onResume()
    {
    	super.onResume();
    	ChartBoost.getSharedChartBoost(this); //make sure context is current
    }
    
    //allows testing of different orientations
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setOrientation(newConfig);
    }
    
    private void setOrientation(Configuration newConfig)
    {
    	if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        	this.cb.setAppId("4f7b433509b6025804000002");
        	this.cb.setAppSignature("dd2d41b69ac01b80f443f5b6cf06096d457f82bd");
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
        	this.cb.setAppId("4f7b433509b6025804000002");
        	this.cb.setAppSignature("dd2d41b69ac01b80f443f5b6cf06096d457f82bd");
        }
    }
    

    public void onLoadButtonClick(View view) {
    	Log.i(TAG, "Load!");
    	
    	String toastStr = "Loading Interstitial";
    	if (cb.hasCachedInterstitial()) toastStr = "Loading Interstitial From Cache";
    	Toast.makeText(this, toastStr, Toast.LENGTH_SHORT).show();
    	this.cb.showInterstitial();
    }
    
    public void onMoreButtonClick(View view) {
    	Log.i(TAG, "More!");
    	String toastStr = "Loading More Apps";
    	if (cb.hasCachedMoreApps()) toastStr = "Loading More Apps From Cache";
    	Toast.makeText(this, toastStr, Toast.LENGTH_SHORT).show();
    	this.cb.showMoreApps();
    }
    
    public void onPreloadClick(View v){
    	Log.i(TAG, "Preloading Interstitial Ad");
    	cb.cacheInterstitial();//cb.preloadStart();
    	Toast.makeText(this, "Preloading Interstitial", Toast.LENGTH_SHORT).show();
    }
    
    
    public void onPreloadMoreAppsClick(View v){
    	Log.i(TAG, "Preloading Interstitial Ad");
    	cb.cacheMoreApps();
    	Toast.makeText(this, "Preloading More Apps", Toast.LENGTH_SHORT).show();
    }
    
    
    public void onPreloadClearClick(View v){
    	Log.i(TAG, "Clearing preload ad data");
    	Toast.makeText(this, "Clearing preload ad data", Toast.LENGTH_SHORT).show();
   		cb.clearCache();
    }
    
    
    public void onPurchaseClick(View view) {
    	Log.i(TAG, "Purchase Clicked!");
    	HashMap<String, Object> meta = new HashMap<String, Object>();
    	meta.put("fakemeta1", 5);
    	meta.put("fakemeta2", "string");
        CBAnalytics.getSharedAnalytics(this).recordPaymentTransaction("OBJECT_001", "Test Object", 0.99, "$", 1, meta);
    	Toast.makeText(this, "Recording Purchase Transaction", Toast.LENGTH_SHORT).show();
    }
    
    public void onTrackEventClick(View view)
    {
    	HashMap<String, Object> meta = new HashMap<String, Object>();
    	meta.put("fakeeventmeta1", 5);
    	meta.put("fakeeventmeta2", "string");
    	Log.i(TAG, "Track Event Clicked!");
    	CBAnalytics.getSharedAnalytics(this).trackEvent("EventName", 5, meta);
    }

    public void onPerformTestClick(View view) {
    	Log.i(TAG, "Perform test!");
    	
    	try {
    		
    	
    	} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    private ChartBoostDelegate chartBoostDelegate = new ChartBoostDelegate() {

    	/**
    	 * Interstital
    	 */
    	@Override
    	public boolean shouldRequestInterstitial()
    	{
    		Log.i(TAG, "SHOULD REQUEST INSTERSTITIAL?");
    		return true;
    	}

    	@Override
    	public boolean shouldDisplayInterstitial(View interstitialView)
    	{
    		Log.i(TAG, "SHOULD DISPLAY INTERSTITIAL?");
    		return true;
    	}
    	
    	@Override
    	public void didFailToLoadInterstitial()
    	{
    		Log.i(TAG, "INTERSTITIAL REQUEST FAILED");
    		Toast.makeText(ChartboostExampleActivity.this, "Interstitial Load Failed", Toast.LENGTH_SHORT).show();
    	}

    	@Override
    	public void didDismissInterstitial(View interstitialView)
    	{
    		Log.i(TAG, "INTERSTITIAL DISMISSED");
    	}
    	
    	@Override
    	public void didCloseInterstitial(View interstitialView)
    	{
    		Log.i(TAG, "INSTERSTITIAL CLOSED");
    		Toast.makeText(ChartboostExampleActivity.this, "Closed Interstitial", Toast.LENGTH_SHORT).show();
    	}

    	@Override
		public void didClickInterstitial(View interstitialView)
    	{
    		Log.i(TAG, "DID CLICK INTERSTITIAL");
    		Toast.makeText(ChartboostExampleActivity.this, "Clicked Interstitial", Toast.LENGTH_SHORT).show();
    	}

    	/**
    	 * More Apps
    	 */
    	@Override 
    	public boolean shouldDisplayLoadingViewForMoreApps()
    	{
    		return true;
    	}
    	
    	@Override
		public boolean shouldRequestMoreApps() {
			
			return true;
		}
    	
    	@Override
    	public boolean shouldDisplayMoreApps(View interstitialView)
    	{
    		Log.i(TAG, "SHOULD DISPLAY MORE APPS?");
    		return true;
    	}
    	
    	@Override
    	public void didFailToLoadMoreApps()
    	{
    		Log.i(TAG, "MORE APPS REQUEST FAILED");
    		Toast.makeText(ChartboostExampleActivity.this, "More Apps Load Failed", Toast.LENGTH_SHORT).show();
    	}

    	@Override
		public void didDismissMoreApps(View moreAppsView)
    	{
    		Log.i(TAG, "MORE APPS DISMISSED");
    	}
    	
    	@Override
    	public void didCloseMoreApps(View interstitialView)
    	{
    		Log.i(TAG, "MORE APPS CLOSED");
    		Toast.makeText(ChartboostExampleActivity.this, "Closed More Apps", Toast.LENGTH_SHORT).show();
    	}

    	@Override
		public void didClickMoreApps(View moreAppsView)
    	{
    		Log.i(TAG, "MORE APPS CLICKED");
    		Toast.makeText(ChartboostExampleActivity.this, "Clicked More Apps", Toast.LENGTH_SHORT).show();
    	}		
	};
}
##### NOTE: This repo is *deprecated*!  
##### Please refer to https://answers.chartboost.com/hc/en-us/sections/200229069 for the latest sample projects, included in each SDK download package.

----------

####Download Instructions for Chartboost Sample Projects

In this repo, you'll find two Chartboost example integrations: one for iOS games, and one for apps on the Android side. To get started, hit the **ZIP** button to download the repo:

<img src="https://s3.amazonaws.com/chartboost/help_assets/client-examples1.jpeg" />

You'll get a folder that includes both platforms' projects; after unzipping, keep the folder you need and discard the other. After that, read on for platform-specific installation instructions.

---
#####Installing iOS Sample Project in Xcode

1. Fire up Xcode and open the **iOS** folder included in the .ZIP archive
2. Navigate to the **ExampleAppAppDelegate.m** file by expanding the menus shown below:

<img src="https://s3.amazonaws.com/chartboost/help_assets/client-examples2.jpeg" />

This file contains the Chartboost code you'd add to AppDelegate.m in your own game &ndash; you can configure it with your App ID (`cb.appId`) and App Signature (`cb.appSignature`) to test the Chartboost integration, independent of your code. 

When run in Simulator or on an iOS device, the project can display any interstitials or More Apps pages you've set up (via the Chartboost dashboard) to run in the game; it's a great way to debug problematic campaign logic.

---
#####Installing Android Sample Project in Eclipse

1. Open Eclipse and navigate to **File > Import**. Highlight **Existing Android Code Into Workspace** (in the **Android** folder) and hit **Next**.
2. Click **Browse**, highlight the **Android** folder from the .ZIP archive, then click **Open** and **Finish**
3. Navigate to the **ChartboostExampleActivity.java** file by expanding the menus shown below:

<img src="https://s3.amazonaws.com/chartboost/help_assets/client-examples4.jpeg" />

This file contains the Chartboost code you'd add to the file in your own game &ndash; you can configure it with your App ID (`String appId`) and App Signature (`String appSignature`) to test the Chartboost integration, independent of your code. 

When run on an Android device, the project can display any interstitials or More Apps pages you've set up (via the Chartboost dashboard) to run in the game; it's a great way to debug problematic campaign logic.

**ATTENTION:** While the Android Sample Project will run as is, this integration is skipping the Google Play Services Library integration step as described here: https://help.chartboost.com/documentation/android. This integration step is not required for Amazon builds, but is best practice for games submitted to the Google Play Store.

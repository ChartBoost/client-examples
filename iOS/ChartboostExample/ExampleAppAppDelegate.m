//
//  ExampleAppAppDelegate.m
//  ChartboostExample
//
//  Created by alfredo on 7/26/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "ExampleAppAppDelegate.h"
#import "ExampleAppViewController.h"

// Import Chartboost.h
#import "Chartboost.h"

/*
 * Required Frameworks
 *
 * Ensure that you are linking with the following libraries:
 *
 * QuartzCore.framework
 * SystemConfiguration.framework
 * CoreGraphics.framework
 * AdSupport.framework (weak-link)
 * StoreKit.framework (weak-link)
 *
 * Navigate to Project (ChartboostExample) > Targets (ChartboostExample) > Build Phases > Link Binary With Libraries
 *
 */


// Add the ChartboostDelegate to your AppDelegate
@interface ExampleAppAppDelegate () <ChartboostDelegate>
@end

@implementation ExampleAppAppDelegate

@synthesize window = _window;
@synthesize viewController = _viewController;


- (void)applicationDidBecomeActive:(UIApplication *)application
{
    
    
    // Initialize Chartboost
    Chartboost *cb = [Chartboost sharedChartboost];
        
    /*
     * Add your own app id & signature. These can be found on App Edit page for your app in the Chartboost dashboard
     *
     * Notes: 
     * 1) BE SURE YOU USE YOUR OWN CORRECT APP ID & SIGNATURE!
     * 2) We cant help if it is missing or incorrect in a live app. You will have to resubmit.
     */

    cb.appId = @"4f21c409cd1cb2fb7000001b";
    cb.appSignature = @"92e2de2fd7070327bdeb54c15a5295309c6fcd2d";
        
    cb.delegate = self;
    
    // Begin a user session. This should be done once per boot
    [cb startSession];
    
    // Cache an interstitial at the default location
    [cb cacheInterstitial];
    
    // Cache an interstitial at some named locations -- (Pro Tip: do this!)
    [cb cacheInterstitial:@"After level 1"];
    [cb cacheInterstitial:@"Pause screen"];

    /*
     * Once cached, use showInterstitial to display the interstitial immediately like this:
     *
     * [cb showInterstitial:@"After level 1"];
     * 
     * Notes:
     * 1) Each named location has it's own cache, only one interstitial is stored per named location
     * 2) Cached interstitials are deleted as soon as they're shown
     * 3) If no interstitial is cached for that location, showInterstitial will load one on the fly from Chartboost
     * 
     * Pro Tip: Implement didDismissInterstitial to immediately re-cache interstitials by location (see below)
     *
     */ 
    
    // Cache the more apps page so it's loaded & ready
    [cb cacheMoreApps];
}


/*
 * Chartboost Delegate Methods
 * 
 * Recommended for everyone: shouldDisplayInterstitial
 */


/* 
 * shouldDisplayInterstitial
 *
 * This is used to control when an interstitial should or should not be displayed
 * The default is YES, and that will let an interstitial display as normal
 * If it's not okay to display an interstitial, return NO
 *
 * For example: during gameplay, return NO.
 *
 * Is fired on:
 * -Interstitial is loaded & ready to display
 */

- (BOOL)shouldDisplayInterstitial:(NSString *)location {
    NSLog(@"about to display interstitial at location %@", location);
    
    // For example:
    // if the user has left the main menu and is currently playing your game, return NO;
    
    // Otherwise return YES to display the interstitial
    return YES;
}


/*
 * didFailToLoadInterstitial
 *
 * This is called when an interstitial has failed to load for any reason
 *
 * Is fired on:
 * - No network connection
 * - No publishing campaign matches for that user (go make a new one in the dashboard)
 */

- (void)didFailToLoadInterstitial:(NSString *)location {
    NSLog(@"failure to load interstitial at location %@", location);
    
    // Show a house ad or do something else when a chartboost interstitial fails to load
}


/*
 * didCacheInterstitial
 *
 * Passes in the location name that has successfully been cached.
 *
 * Is fired on:
 * - All assets loaded
 * - Triggered by cacheInterstitial
 */

- (void)didCacheInterstitial:(NSString *)location {
    NSLog(@"interstitial cached at location %@", location);
    
}

/*
 * didFailToLoadMoreApps
 *
 * This is called when the more apps page has failed to load for any reason
 *
 * Is fired on:
 * - No network connection
 * - No more apps page has been created (add a more apps page in the dashboard)
 * - No publishing campaign matches for that user (add more campaigns to your more apps page)
 *  -Find this inside the App > Edit page in the Chartboost dashboard
 */

- (void)didFailToLoadMoreApps {
    NSLog(@"failure to load more apps");
}


/*
 * didDismissInterstitial
 *
 * This is called when an interstitial is dismissed
 *
 * Is fired on:
 * - Interstitial click
 * - Interstitial close
 *
 * #Pro Tip: Use the delegate method below to immediately re-cache interstitials
 */

- (void)didDismissInterstitial:(NSString *)location {
    NSLog(@"dismissed interstitial at location %@", location);
    
    [[Chartboost sharedChartboost] cacheInterstitial:location];
}


/*
 * didDismissMoreApps
 *
 * This is called when the more apps page is dismissed
 *
 * Is fired on:
 * - More Apps click
 * - More Apps close
 *
 * #Pro Tip: Use the delegate method below to immediately re-cache the more apps page
 */

- (void)didDismissMoreApps {
    NSLog(@"dismissed more apps page, re-caching now");
    
    [[Chartboost sharedChartboost] cacheMoreApps];
}







// Default iOS stuff below here


- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
    self.window = [[UIWindow alloc] initWithFrame:[[UIScreen mainScreen] bounds]];
    // Override point for customization after application launch.
    if ([[UIDevice currentDevice] userInterfaceIdiom] == UIUserInterfaceIdiomPhone) {
        self.viewController = [[ExampleAppViewController alloc] initWithNibName:@"ExampleAppViewController_iPhone" bundle:nil];
    } else {
        self.viewController = [[ExampleAppViewController alloc] initWithNibName:@"ExampleAppViewController_iPad" bundle:nil];
    }
    self.window.rootViewController = self.viewController;
    [self.window makeKeyAndVisible];
    return YES;
}

- (void)applicationWillResignActive:(UIApplication *)application
{
    // Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
    // Use this method to pause ongoing tasks, disable timers, and throttle down OpenGL ES frame rates. Games should use this method to pause the game.
}

- (void)applicationDidEnterBackground:(UIApplication *)application
{
    // Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later. 
    // If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
}

- (void)applicationWillEnterForeground:(UIApplication *)application
{
    // Called as part of the transition from the background to the inactive state; here you can undo many of the changes made on entering the background.
}

- (void)applicationWillTerminate:(UIApplication *)application
{
    // Called when the application is about to terminate. Save data if appropriate. See also applicationDidEnterBackground:.
}

@end

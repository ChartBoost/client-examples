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

// Add the ChartboostDelegate to your AppDelegate
@interface ExampleAppAppDelegate () <ChartboostDelegate>
@end

@implementation ExampleAppAppDelegate

@synthesize window = _window;
@synthesize viewController = _viewController;



- (void)applicationDidBecomeActive:(UIApplication *)application
{
    // Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
    
    // Initialize Chartboost
    Chartboost *cb = [Chartboost sharedChartboost];
    
    // Add your own app id & signature. These can be found on App Edit page for your app in the Chartboost dashboard
    cb.appId = @"4e2db0c935c6d3c415000015";
    cb.appSignature = @"5c1a848c22862401cf62b66f72e087ed8c808b17";
    
    cb.delegate = self;
    
    // Begin a user session. This should be done once per boot
    [cb startSession];
    
    // Cache an interstitial at the default location
    [cb cacheInterstitial];
    
    // Cache an interstitial at some named locations -- recommended!!
    [cb cacheInterstitial:@"Main Menu"];
    [cb cacheInterstitial:@"After level 1"];

    // Once cached, you can use showInterstitial at any point in your app to display the interstitial immediately
    // If you are not using named locations, simply use [cb showInterstitial];
    // If you ARE using named locations, display the cached interstitials
    // [cb showInterstitial:@"Main Menu"];
    // [cb showInterstitial:@"After level 1"];
    
    // Note 1) Each named location has it's own cache, only one interstitial is stored per named location
    // and  2) Cached interstitials are deleted as soon as they're shown

    
    // Cache the more apps page so it's ready & loaded
    [cb cacheMoreApps];
    
}

// This lets your app know when an interstitial has been successfully cached
// You can use this to know which locations are ready to display immediately
- (void)didCacheInterstitial:(NSString *)location {
    NSLog(@"interstitial cached at location %@", location);
}

// This is called when an interstitial has failed to load for any reason
// Possible causes are network connection or there's no publishing campaign setup for your app
- (void)didFailToLoadInterstitial:(NSString *)location {
    NSLog(@"failure to load interstitial at location %@", location);
    
    // In here is where you would show a house ad, or do something else when a chartboost interstitial fails
}

// Same as above. If you get this, make sure you have added campaigns to the More Apps page setup for your app
// You can find this inside the App > Edit page in the Chartboost dashboard
- (void)didFailToLoadMoreApps {
    NSLog(@"failure to load more apps");
}

// This is used to control when an interstitial should or should not be displayed
// The default is YES, and that will let an interstitial display as normal
// If it's not preferable to display an interstitial, return NO to prevent the interstitial from displaying
- (BOOL)shouldDisplayInterstitial:(NSString *)location {
    NSLog(@"about to display interstitial at location %@", location);
    
    // For example:
    // if the user has left the main menu and is currently playing your game, return NO;
    
    // Otherwise return YES to display the interstitial
    return YES;
}

// This is called when an interstitial is dismissed for a click, or a close.
// If you expect to show another interstitial at this location, use this to cache another when they close it.
- (void)didDismissInterstitial:(NSString *)location {
    NSLog(@"dismissed interstitial at location %@", location);
    
    [[Chartboost sharedChartboost] cacheInterstitial:location];
}












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

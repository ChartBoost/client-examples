//
//  ViewController.m
//  ChartboostExampleApp
//
//  Created by Gonzalo Alsina on 11/6/13.
//  Copyright (c) 2013 Chartboost. All rights reserved.
//

#import "ViewController.h"
#import "Chartboost.h"
#import "CBNewsfeed.h"
#import "CBPostInstallAnalyticsTracker.h"

#import <StoreKit/StoreKit.h>

@interface ViewController ()

@end

@implementation ViewController

- (void)viewDidLoad {
    [super viewDidLoad];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}

- (IBAction)showInterstitial {
    [[Chartboost sharedChartboost] showInterstitial:CBLocationHomeScreen];
}

- (IBAction)showMoreApps {
    [[Chartboost sharedChartboost] showMoreApps:CBLocationHomeScreen];
}

- (IBAction)cacheInterstitial {
    [[Chartboost sharedChartboost] cacheInterstitial:CBLocationHomeScreen];
}

- (IBAction)cacheMoreApps {
    [[Chartboost sharedChartboost] cacheMoreApps:CBLocationHomeScreen];
}

- (IBAction)showNewsfeed {
    [[CBNewsfeed shared] showNewsfeedUI];
}

/*
 * This is an example of how to call the Chartboost Post Install Analytics API.
 * To fully use this feature you must implement the Apple In-App Purchase
 *
 * Checkout https://developer.apple.com/in-app-purchase/ for information on how to setup your app to use StoreKit
 */
- (void)trackInAppPurchase:(NSData *)transactionReceipt product:(SKProduct *)product {
    [[CBPostInstallAnalyticsTracker shared] trackInAppPurchaseEvent:transactionReceipt product:product];
}

@end

//
//  ViewController.m
//  ChartboostExampleApp
//
//  Created by Gonzalo Alsina on 11/6/13.
//  Copyright (c) 2013 Chartboost. All rights reserved.
//

#import "ViewController.h"
#import "Chartboost.h"

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

@end

//
//  ExampleAppViewController.m
//  ChartboostExample
//
//  Created by alfredo on 7/26/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "ExampleAppViewController.h"
#import "Chartboost.h"


@interface ExampleAppViewController () <ChartboostDelegate>
@end

@implementation ExampleAppViewController



- (IBAction)showInterstitial:(id)sender {
    // This will show an interstitial when the button is pressed
    // Note that this is for the default location, to use a named location see below
    [[Chartboost sharedChartboost] showInterstitial];
    
}

- (IBAction)showInterstitialMainMenu:(id)sender {
    // This will show an interstitial for the Main Menu location
    // This will first check to see if an interstitial is cached for that location, if so it will display it. If no cache exists for this location - it will retrieve an interstitial from the server
    [[Chartboost sharedChartboost] showInterstitial:@"Main Menu"];
    
}

- (IBAction)showInterstitialLevelOne:(id)sender {
    // This will show an interstitial for the After level 1 location
    [[Chartboost sharedChartboost] showInterstitial:@"After level 1"];
    
}

- (IBAction)showMoreApps:(id)sender {
    [[Chartboost sharedChartboost] showMoreApps];
}

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view, typically from a nib.
}

- (void)viewDidUnload
{
    [super viewDidUnload];
    // Release any retained subviews of the main view.
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    if ([[UIDevice currentDevice] userInterfaceIdiom] == UIUserInterfaceIdiomPhone) {
        return (interfaceOrientation != UIInterfaceOrientationPortraitUpsideDown);
    } else {
        return YES;
    }
}

@end

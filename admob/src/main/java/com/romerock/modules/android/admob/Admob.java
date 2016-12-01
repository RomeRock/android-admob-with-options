package com.romerock.modules.android.admob;

import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.NativeExpressAdView;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Ebricko on 24/11/2016.
 */
public class Admob extends LinearLayout implements RewardedVideoAdListener {
    private Context context;
    final String TAG = "Simple Admob Example";
    private AdView mAdView;
    private AdView mAdSizeView1;
    private AdView mAdSizeView2;
    private AdView mAdSizeView3;
    private InterstitialAd mInterstitialAd;
    private RewardedVideoAd mRewardedVideoAd;
    private String android_id, deviceId;

    public Admob(final Context context, int option) {
        super(context);
        this.context = context;
        Log.d(TAG, "Welcome " + TAG);
        AdRequest adRequest;
        android_id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        deviceId = TestDevice(android_id).toUpperCase();
        //Toast.makeText(context, "Device test: "+deviceId, Toast.LENGTH_LONG).show();
        switch (option) {
            case 1:
                inflate(context, R.layout.adbmob_sizes, this);
                adRequest = new AdRequest.Builder().build();
                mAdSizeView1 = (AdView) findViewById(R.id.admob_size_banner1);
                mAdSizeView1.loadAd(new AdRequest.Builder().build());


                adRequest = new AdRequest.Builder().build();
                mAdSizeView2 = (AdView) findViewById(R.id.admob_size_banner2);
                mAdSizeView2.loadAd(new AdRequest.Builder().build());


                adRequest = new AdRequest.Builder().build();
                mAdSizeView3 = (AdView) findViewById(R.id.admob_size_banner3);
                mAdSizeView3.loadAd(new AdRequest.Builder().build());
                mAdSizeView3.setAdListener(new AdListener() {
                    @Override
                    public void onAdLoaded() {
                        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBarBanner);
                        progressBar.setVisibility(GONE);
                    }
                });


                break;
            case 2:
                mInterstitialAd = new InterstitialAd(context);
                mInterstitialAd.setAdUnitId(context.getResources().getString(R.string.interstitial_ad_unit_id));
                adRequest = new AdRequest.Builder()
                        .addTestDevice(deviceId)
                        .build();

                mInterstitialAd.loadAd(adRequest);
                mInterstitialAd.setAdListener(new AdListener() {
                    public void onAdLoaded() {
                        displayInterstitial();
                    }
                });
                break;
            case 3:
                mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(context);
                mRewardedVideoAd.setRewardedVideoAdListener(this);
                loadRewardedVideoAd();
                break;
            case 4:
                inflate(context, R.layout.adbmob_native, this);
                NativeExpressAdView adView = (NativeExpressAdView) findViewById(R.id.adView);
                adView.loadAd(new AdRequest.Builder().build());
                adView.setAdListener(new AdListener() {
                    @Override
                    public void onAdLoaded() {
                        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBarBanner);
                        progressBar.setVisibility(GONE);
                    }
                });
                break;
            case 0:
            default:
                inflate(context, R.layout.adbmob_simple, this);
                mAdView = (AdView) findViewById(R.id.admob_simple_banner);
                adRequest = new AdRequest.Builder().build();
                mAdView.loadAd(adRequest);
                mAdView.setAdListener(new AdListener() {
                    @Override
                    public void onAdLoaded() {
                        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBarBanner);
                        progressBar.setVisibility(GONE);
                    }
                });
                break;
        }
    }

    private void displayInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }


    @Override
    public void onRewardedVideoAdLoaded() {
        if (!mRewardedVideoAd.isLoaded()) {
            mRewardedVideoAd.loadAd(context.getResources().getString(R.string.video_ad_unit_id), new AdRequest.Builder()
                    .addTestDevice(deviceId)
                    .build());
            if (mRewardedVideoAd.isLoaded()) {
                mRewardedVideoAd.show();
            }

        }
    }

    private void loadRewardedVideoAd() {
        if (mRewardedVideoAd != null)
            if (!mRewardedVideoAd.isLoaded()) {

                mRewardedVideoAd.setRewardedVideoAdListener(new RewardedVideoAdListener() {
                    @Override
                    public void onRewardedVideoAdLoaded() {

                    }

                    @Override
                    public void onRewardedVideoAdOpened() {

                    }

                    @Override
                    public void onRewardedVideoStarted() {

                    }

                    @Override
                    public void onRewardedVideoAdClosed() {

                    }

                    @Override
                    public void onRewarded(RewardItem rewardItem) {

                    }

                    @Override
                    public void onRewardedVideoAdLeftApplication() {

                    }

                    @Override
                    public void onRewardedVideoAdFailedToLoad(int i) {
                        Toast.makeText(
                                context,
                                "onRewardedVideoAdFailedToLoad",
                                Toast.LENGTH_SHORT ).show();
                    }
                });


                Bundle extras = new Bundle();
                extras.putBoolean("_noRefresh", true);
                mRewardedVideoAd.loadAd(context.getResources().getString(R.string.video_ad_unit_id), new AdRequest.Builder()
                        .addTestDevice(deviceId)
                        .addNetworkExtrasBundle(AdMobAdapter.class, extras)
                        .build());
                if (mRewardedVideoAd.isLoaded())
                    mRewardedVideoAd.show();
            }


    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {

    }

    @Override
    public void onRewarded(RewardItem rewardItem) {

    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

    }

    public static final String TestDevice(final String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
        }
        return "";
    }
}

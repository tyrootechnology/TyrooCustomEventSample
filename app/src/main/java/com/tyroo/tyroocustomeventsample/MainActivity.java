package com.tyroo.tyroocustomeventsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.tyroo.customevent.TyrooCustomEventInterstitial;

public class MainActivity extends AppCompatActivity {


    private Button btnRefreshAds;
    private Button btnCustomEventInterstitial;
    private Button btnCustomEventNative;

    private InterstitialAd mInterstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this,"ca-app-pub-9137500802659514~1623406507");

        initViews();
        initAdMobInterstitial();

    }

    private void initViews(){
        btnRefreshAds = (Button) findViewById(R.id.btn_refresh_ads);
        btnRefreshAds.setOnClickListener(clickRefreshAds);

        btnCustomEventInterstitial = (Button) findViewById(R.id.btn_customevent_interstitial);
        btnCustomEventInterstitial.setOnClickListener(clickShowInterstitial);
    }

    private View.OnClickListener clickRefreshAds = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            initAdMobInterstitial();
        }
    };

    private View.OnClickListener clickShowInterstitial = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
                Log.d("TAG", "The interstitial wasn't loaded yet.");
            }
        }
    };

    private void initAdMobInterstitial(){
        Bundle extras = new TyrooCustomEventInterstitial.TyrooCustomEventExtrasBundleBuilder()
                .setPlacementId("1637")
                .setPackageName("009")
                .setEnableVideoCaching(true)
                .build();

        AdRequest request = new AdRequest.Builder()
                .addCustomEventExtrasBundle(TyrooCustomEventInterstitial.class, extras)
                .build();

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-9137500802659514/9118753149");
        mInterstitialAd.loadAd(request);
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                Log.d("MainActivity", "onAdLoaded");
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                Log.e("MainActivity", "onAdFailedToLoad- "+errorCode);
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
                Log.d("MainActivity", "onAdOpened");
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
                Log.d("MainActivity", "onAdLeftApplication");
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when when the interstitial ad is closed.
                Log.d("MainActivity", "onAdClosed");
            }
        });
    }


}

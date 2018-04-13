package com.tyroo.tyroocustomeventsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.NativeAd;
import com.google.android.gms.ads.formats.NativeAppInstallAd;
import com.google.android.gms.ads.formats.NativeAppInstallAdView;
import com.google.android.gms.ads.formats.NativeContentAd;
import com.google.android.gms.ads.formats.NativeContentAdView;
import com.tyroo.customevent.TyrooCustomEventInterstitial;
import com.tyroo.customevent.TyrooCustomEventNativeAd;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    private Button btnRefreshAds;
    private Button btnCustomEventInterstitial;
    private Button btnCustomEventNative;

    private InterstitialAd mInterstitialAd;
    private AdLoader customEventNativeLoader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, "ca-app-pub-9137500802659514~1623406507");

        initViews();
        //initAdMobInterstitial();

    }

    private void initViews() {
        btnRefreshAds = (Button) findViewById(R.id.btn_refresh_ads);
        btnRefreshAds.setOnClickListener(clickRefreshAds);

        btnCustomEventInterstitial = (Button) findViewById(R.id.btn_customevent_interstitial);
        btnCustomEventInterstitial.setOnClickListener(clickShowInterstitial);

        btnCustomEventNative = (Button) findViewById(R.id.btn_customevent_native);
        btnCustomEventNative.setOnClickListener(clickShowNative);
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

    private void initAdMobInterstitial() {
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
                Log.e("MainActivity", "onAdFailedToLoad- " + errorCode);
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

    private View.OnClickListener clickShowNative = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            customEventNativeLoader = new AdLoader.Builder(MainActivity.this, "ca-app-pub-3940256099942544/9051804115")
                    .forAppInstallAd(new NativeAppInstallAd.OnAppInstallAdLoadedListener() {
                        @Override
                        public void onAppInstallAdLoaded(NativeAppInstallAd ad) {
                            FrameLayout frameLayout =
                                    (FrameLayout) findViewById(R.id.customeventnative_framelayout);
                            NativeAppInstallAdView adView = (NativeAppInstallAdView) getLayoutInflater()
                                    .inflate(R.layout.ad_app_install, null);
                            populateAppInstallAdView(ad, adView);
                            frameLayout.removeAllViews();
                            frameLayout.addView(adView);
                        }
                    })
                    .forContentAd(new NativeContentAd.OnContentAdLoadedListener() {
                        @Override
                        public void onContentAdLoaded(NativeContentAd ad) {
                            FrameLayout frameLayout =
                                    (FrameLayout) findViewById(R.id.customeventnative_framelayout);
                            NativeContentAdView adView = (NativeContentAdView) getLayoutInflater()
                                    .inflate(R.layout.ad_content, null);
                            populateContentAdView(ad, adView);
                            frameLayout.removeAllViews();
                            frameLayout.addView(adView);
                        }

                    })
                    .withAdListener(new AdListener() {
                        @Override
                        public void onAdFailedToLoad(int errorCode) {
                            Toast.makeText(MainActivity.this,
                                    "Custom event native ad failed with code: " + errorCode,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }).build();

            Bundle extras = new TyrooCustomEventInterstitial.TyrooCustomEventExtrasBundleBuilder()
                    .setPlacementId("1707")
                    .setPackageName("009")
                    .setEnableVideoCaching(true)
                    .build();

            AdRequest request = new AdRequest.Builder()
                    .addCustomEventExtrasBundle(TyrooCustomEventNativeAd.class, extras)
                    .build();
            customEventNativeLoader.loadAd(request);
        }
    };

    private void populateAppInstallAdView(NativeAppInstallAd nativeAppInstallAd,
                                          NativeAppInstallAdView adView) {
        VideoController videoController = nativeAppInstallAd.getVideoController();

        // Assign native ad object to the native view.
        adView.setNativeAd(nativeAppInstallAd);

        adView.setHeadlineView(adView.findViewById(R.id.appinstall_headline));
        adView.setBodyView(adView.findViewById(R.id.appinstall_body));
        adView.setCallToActionView(adView.findViewById(R.id.appinstall_call_to_action));
        adView.setIconView(adView.findViewById(R.id.appinstall_app_icon));
        adView.setPriceView(adView.findViewById(R.id.appinstall_price));
        adView.setStarRatingView(adView.findViewById(R.id.appinstall_stars));
        adView.setStoreView(adView.findViewById(R.id.appinstall_store));

        // Some assets are guaranteed to be in every NativeAppInstallAd.
        ((TextView) adView.getHeadlineView()).setText(nativeAppInstallAd.getHeadline());
        ((TextView) adView.getBodyView()).setText(nativeAppInstallAd.getBody());
        ((Button) adView.getCallToActionView()).setText(nativeAppInstallAd.getCallToAction());
        ((ImageView) adView.getIconView()).setImageDrawable(nativeAppInstallAd.getIcon()
                .getDrawable());

        MediaView sampleMediaView = adView.findViewById(R.id.appinstall_media);
        ImageView imageView = adView.findViewById(R.id.appinstall_image);

        if (videoController.hasVideoContent()) {
            imageView.setVisibility(View.GONE);
            adView.setMediaView(sampleMediaView);
        } else {
            sampleMediaView.setVisibility(View.GONE);
            adView.setImageView(imageView);

            List<NativeAd.Image> images = nativeAppInstallAd.getImages();

            if (images.size() > 0) {
                ((ImageView) adView.getImageView()).setImageDrawable(images.get(0).getDrawable());
            }
        }

        // Some aren't guaranteed, however, and should be checked.
        if (nativeAppInstallAd.getPrice() == null) {
            adView.getPriceView().setVisibility(View.INVISIBLE);
        } else {
            adView.getPriceView().setVisibility(View.VISIBLE);
            ((TextView) adView.getPriceView()).setText(nativeAppInstallAd.getPrice());
        }

        if (nativeAppInstallAd.getStore() == null) {
            adView.getStoreView().setVisibility(View.INVISIBLE);
        } else {
            adView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAppInstallAd.getStore());
        }

        if (nativeAppInstallAd.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) adView.getStarRatingView())
                    .setRating(nativeAppInstallAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }


    }

    /**
     * Populates a {@link NativeContentAdView} object with data from a given
     * {@link NativeContentAd}.
     *
     * @param nativeContentAd the object containing the ad's assets
     * @param adView          the view to be populated
     */
    private void populateContentAdView(NativeContentAd nativeContentAd,
                                       NativeContentAdView adView) {
        // Assign native ad object to the native view.
        adView.setNativeAd(nativeContentAd);

        adView.setHeadlineView(adView.findViewById(R.id.contentad_headline));
        adView.setImageView(adView.findViewById(R.id.contentad_image));
        adView.setBodyView(adView.findViewById(R.id.contentad_body));
        adView.setCallToActionView(adView.findViewById(R.id.contentad_call_to_action));
        adView.setLogoView(adView.findViewById(R.id.contentad_logo));
        adView.setAdvertiserView(adView.findViewById(R.id.contentad_advertiser));

        // Some assets are guaranteed to be in every NativeContentAd.
        ((TextView) adView.getHeadlineView()).setText(nativeContentAd.getHeadline());
        ((TextView) adView.getBodyView()).setText(nativeContentAd.getBody());
        ((TextView) adView.getCallToActionView()).setText(nativeContentAd.getCallToAction());
        ((TextView) adView.getAdvertiserView()).setText(nativeContentAd.getAdvertiser());

        List<NativeAd.Image> images = nativeContentAd.getImages();

        if (images.size() > 0) {
            ((ImageView) adView.getImageView()).setImageDrawable(images.get(0).getDrawable());
        }

        // Some aren't guaranteed, however, and should be checked.
        NativeAd.Image logoImage = nativeContentAd.getLogo();

        if (logoImage == null) {
            adView.getLogoView().setVisibility(View.INVISIBLE);
        } else {
            ((ImageView) adView.getLogoView())
                    .setImageDrawable(logoImage.getDrawable());
            adView.getLogoView().setVisibility(View.VISIBLE);
        }

    }

}

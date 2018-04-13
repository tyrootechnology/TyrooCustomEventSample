package com.tyroo.customevent;

import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.mediation.NativeContentAdMapper;
import com.google.android.gms.ads.mediation.customevent.CustomEventNativeListener;
import com.tyroo.tva.sdk.AdView;
import com.tyroo.tva.sdk.ErrorCode;
import com.tyroo.tva.sdk.TyrooVidAiSdk;

public class TyrooCustomNativeEventForwarder extends NativeContentAdMapper implements TyrooVidAiSdk.TyrooAdListener {

    private static final String TAG = "EventForwarderNative";
    private final CustomEventNativeListener nativeListener;
    private final AdView sampleAd;

    public TyrooCustomNativeEventForwarder(CustomEventNativeListener listener, AdView nativeAdView) {
        this.nativeListener = listener;
        this.sampleAd = nativeAdView;
    }


    @Override
    public void onAdLoaded(String placementId) {
        Log.d(TAG, "onAdLoaded");
        setHasVideoContent(true);
        sampleAd.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        sampleAd.setEnableFooter(true);
        sampleAd.setEnableHeader(true);
        sampleAd.setActionButtonEnabled(true);
        //sampleAd.setHeaderTextColor(ContextCompat.getColor(this, R.color.tvaColorPrimary));
        //sampleAd.setHeaderTextSize(Utils.convertDpToPixel(15, MainActivity.this));
        //sampleAd.setFooterTextColor(ContextCompat.getColor(this, R.color.tvaColorPrimary));
        //sampleAd.setFooterTextSize(Utils.convertDpToPixel(13, MainActivity.this));
        sampleAd.setVideoAutoPlay(true);
        setMediaView(sampleAd);
        setMediaView(sampleAd);
        nativeListener.onAdLoaded(this);
    }

    @Override
    public void onAdDisplayed() {
        Log.d(TAG, "onAdDisplayed");
    }

    @Override
    public void onAdOpened() {
        Log.d(TAG, "onAdOpened");
        nativeListener.onAdOpened();
    }

    @Override
    public void onAdClosed() {
        Log.d(TAG, "onAdClosed");
        nativeListener.onAdClosed();
    }

    @Override
    public void onAdCompleted() {
        Log.d(TAG, "onAdCompleted");
    }

    @Override
    public void onAdClicked() {
        Log.d(TAG, "onAdClicked");
        nativeListener.onAdClicked();
    }

    @Override
    public void onAdLeftApplication() {
        Log.d(TAG, "onAdLeftApplication");
        nativeListener.onAdLeftApplication();
    }

    @Override
    public void onFailure(int errorCode, String errorMsg) {
        Log.d(TAG, "onFailure: " + errorMsg);
        switch (errorCode) {
            case ErrorCode.UNKNOWN:
                nativeListener.onAdFailedToLoad(AdRequest.ERROR_CODE_INTERNAL_ERROR);
                break;
            case ErrorCode.BAD_REQUEST:
                nativeListener.onAdFailedToLoad(AdRequest.ERROR_CODE_INVALID_REQUEST);
                break;
            case ErrorCode.NETWORK_ERROR:
                nativeListener.onAdFailedToLoad(AdRequest.ERROR_CODE_NETWORK_ERROR);
                break;
            case ErrorCode.NO_INVENTORY:
                nativeListener.onAdFailedToLoad(AdRequest.ERROR_CODE_NO_FILL);
                break;
        }
    }
}




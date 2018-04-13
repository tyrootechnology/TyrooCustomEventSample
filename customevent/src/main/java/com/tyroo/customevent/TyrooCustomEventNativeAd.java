package com.tyroo.customevent;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Keep;
import android.util.Log;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.mediation.MediationAdRequest;
import com.google.android.gms.ads.mediation.NativeMediationAdRequest;
import com.google.android.gms.ads.mediation.customevent.CustomEventInterstitial;
import com.google.android.gms.ads.mediation.customevent.CustomEventInterstitialListener;
import com.google.android.gms.ads.mediation.customevent.CustomEventNative;
import com.google.android.gms.ads.mediation.customevent.CustomEventNativeListener;
import com.tyroo.tva.sdk.AdView;
import com.tyroo.tva.sdk.TyrooVidAiSdk;

/**
 * Created by Sukhpal Singh on 18/4/10.
 */

@Keep
public class TyrooCustomEventNativeAd implements CustomEventNative {
    private static final String TAG = TyrooCustomEventNativeAd.class.getSimpleName();

    private TyrooVidAiSdk tyrooVidAiSdk;
    private AdView adViewNative;


    @Override
    public void onDestroy() {
        if (!Utils.checkClassExist("com.tyroo.tva.sdk.TyrooVidAiSdk")) {
            String msg = "No Class found TyrooVidAiSdk";
            Log.e(TAG, msg);
            return;
        }
        if (tyrooVidAiSdk != null) {
            Log.d(TAG, "destroy ");
            tyrooVidAiSdk.flush();
        }
    }

    @Override
    public void onPause() {
        Log.i(TAG, "TyrooCustomEventNativeAd onPause");
    }

    @Override
    public void onResume() {
        Log.d(TAG, "TyrooCustomEventNativeAd onResume");
    }

    @Override
    public void requestNativeAd(Context context, CustomEventNativeListener customEventNativeListener, String serverParams, NativeMediationAdRequest nativeMediationAdRequest, Bundle bundle) {
        Log.d(TAG, "request TyrooCustomEventNativeAd");

        if (context == null) {
            customEventNativeListener.onAdFailedToLoad(AdRequest.ERROR_CODE_INVALID_REQUEST);
            return;
        }



        Log.d(TAG, "String found :" + serverParams);
        // Log.d(TAG, "String package: "+bundle.getString(TyrooCustomEventExtrasBundleBuilder.KEY_PACKAGE_NAME));
        if (bundle != null) {
           adViewNative = new AdView(context);
            tyrooVidAiSdk = TyrooVidAiSdk.initialize(context, serverParams.trim(),
                    bundle.getString(TyrooCustomEventExtrasBundleBuilder.KEY_PACKAGE_NAME),
                    new TyrooCustomNativeEventForwarder(customEventNativeListener, adViewNative));
            tyrooVidAiSdk.enableCaching(bundle.getBoolean(TyrooCustomEventExtrasBundleBuilder.KEY_ENABLE_VIDEO_CACHING));
            tyrooVidAiSdk.loadAds();
        }else {
            customEventNativeListener.onAdFailedToLoad(AdRequest.ERROR_CODE_INTERNAL_ERROR);
        }
    }

    /**
     * The {@link TyrooCustomEventExtrasBundleBuilder} class is used to create a mediation extras bundle
     * that can be passed to the adapter as extra data to be used in making requests. In this
     * example the sample SDK has two extra parameters that it can use to customize its ad requests.
     */
    public static final class TyrooCustomEventExtrasBundleBuilder {

        // Keys to add and obtain the extra parameters from the bundle.
        private static final String KEY_PLACEMENT_ID = "tyroo_placement_id";
        private static final String KEY_PACKAGE_NAME = "tyroo_package_name";
        private static final String KEY_ENABLE_VIDEO_CACHING = "tyroo_enable_video_cache";

        /**
         * An extra value used to populate the "Placement ID" property of the Tyroo SDK's ad request.
         */
        private String placementId;

        /**
         * An extra value used to populate the "package name" property of the Tyroo SDK's ad request.
         */
        private String packageName;

        /**
         * An extra value used to populate the "Video Cache" property of the Tyroo SDK's ad request.
         */
        private boolean enableVideoCache;


        public TyrooCustomEventExtrasBundleBuilder setPlacementId(String placementId) {
            this.placementId = placementId;
            return TyrooCustomEventExtrasBundleBuilder.this;
        }

        public TyrooCustomEventExtrasBundleBuilder setPackageName(String packageName) {
            this.packageName = packageName;
            return TyrooCustomEventExtrasBundleBuilder.this;
        }

        public TyrooCustomEventExtrasBundleBuilder setEnableVideoCaching(boolean videoCaching) {
            this.enableVideoCache = videoCaching;
            return TyrooCustomEventExtrasBundleBuilder.this;
        }


        public Bundle build() {
            Bundle extras = new Bundle();
            extras.putString(KEY_PLACEMENT_ID, placementId);
            extras.putString(KEY_PACKAGE_NAME, packageName);
            extras.putBoolean(KEY_ENABLE_VIDEO_CACHING, enableVideoCache);
            return extras;
        }
    }
}

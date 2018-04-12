/*
 * Copyright (C) 2014 Google, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tyroo.customevent;

import android.support.annotation.Keep;
import android.util.Log;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.mediation.customevent.CustomEventInterstitialListener;
import com.tyroo.tva.sdk.ErrorCode;
import com.tyroo.tva.sdk.TyrooVidAiSdk;

/**
 * A {@link TyrooVidAiSdk.TyrooAdListener} that forwards events to AdMob Mediation's
 * {@link CustomEventInterstitialListener}.
 */
@Keep
public class TyrooCustomInterstitialEventForwarder implements TyrooVidAiSdk.TyrooAdListener {

    private static final String TAG = "EventForwarder";
    private final CustomEventInterstitialListener interstitialListener;


    public TyrooCustomInterstitialEventForwarder(CustomEventInterstitialListener listener) {
        this.interstitialListener = listener;
    }

    @Override
    public void onAdLoaded(String s) {
        Log.d(TAG, "onAdLoaded");
        interstitialListener.onAdLoaded();
    }

    @Override
    public void onAdDisplayed() {
        Log.d(TAG, "onAdDisplayed");
    }

    @Override
    public void onAdOpened() {
        Log.d(TAG, "onAdOpened");
        interstitialListener.onAdOpened();
    }

    @Override
    public void onAdClosed() {
        Log.d(TAG, "onAdClosed");
        interstitialListener.onAdClosed();
    }

    @Override
    public void onAdCompleted() {
        Log.d(TAG, "onAdCompleted");
    }

    @Override
    public void onAdClicked() {
        Log.d(TAG, "onAdClicked");
        interstitialListener.onAdClicked();
    }

    @Override
    public void onAdLeftApplication() {
        Log.d(TAG, "onAdLeftApplication");
        interstitialListener.onAdLeftApplication();
    }

    @Override
    public void onFailure(int errorCode, String errorMsg) {
        Log.d(TAG, "onFailure: "+errorMsg);
        switch (errorCode) {
            case ErrorCode.UNKNOWN:
                interstitialListener.onAdFailedToLoad(AdRequest.ERROR_CODE_INTERNAL_ERROR);
                break;
            case ErrorCode.BAD_REQUEST:
                interstitialListener.onAdFailedToLoad(AdRequest.ERROR_CODE_INVALID_REQUEST);
                break;
            case ErrorCode.NETWORK_ERROR:
                interstitialListener.onAdFailedToLoad(AdRequest.ERROR_CODE_NETWORK_ERROR);
                break;
            case ErrorCode.NO_INVENTORY:
                interstitialListener.onAdFailedToLoad(AdRequest.ERROR_CODE_NO_FILL);
                break;
        }
    }
}

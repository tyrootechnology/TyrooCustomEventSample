package com.tyroo.customevent;

import android.view.View;

import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.mediation.NativeContentAdMapper;

public class TyrooNativeContentAdMapper extends NativeContentAdMapper {

    private final AdView sampleAd;

    public TyrooNativeContentAdMapper(AdView ad) {
        sampleAd = ad;



        setOverrideClickHandling(false);
        setOverrideImpressionRecording(false);

        //setAdChoicesContent(sampleAd.getInformationIcon());
    }

    @Override
    public void recordImpression() {
        //sampleAd.recordImpression();
    }

    @Override
    public void handleClick(View view) {
        //sampleAd.handleClick(view);
    }


    @Override
    public void trackView(View view) {
        super.trackView(view);
        // Here you would pass the View back to the mediated network's SDK.


    }

    @Override
    public void untrackView(View view) {
        super.untrackView(view);
        // Here you would remove any trackers from the View added in trackView.

    }
}

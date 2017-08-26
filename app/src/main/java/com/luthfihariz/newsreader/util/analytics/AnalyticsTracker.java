package com.luthfihariz.newsreader.util.analytics;

import android.content.Context;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.luthfihariz.newsreader.data.Source;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by luthfihariz on 8/26/17.
 */

public class AnalyticsTracker {
    private static AnalyticsTracker sInstance;

    private FirebaseAnalytics mFirebase;

    public static AnalyticsTracker getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new AnalyticsTracker(context.getApplicationContext());
        }

        return sInstance;
    }

    private AnalyticsTracker(Context appContext) {
        mFirebase = FirebaseAnalytics.getInstance(appContext);
    }

    public void logReadNews(String channel, String title) {
        Bundle bundle = new Bundle();
        bundle.putString(AnalyticsConstant.Param.NEWS_TITLE, title);
        bundle.putString(AnalyticsConstant.Param.NEWS_CHANNEL, channel);
        mFirebase.logEvent(AnalyticsConstant.Event.READ_NEWS, bundle);
    }

    public void logSelectChannel(List<Source> selectedChannel) {
        for (Source source : selectedChannel) {
            Bundle bundle = new Bundle();
            bundle.putString(AnalyticsConstant.Param.CHANNEL_NAME, source.getName());
            mFirebase.logEvent(AnalyticsConstant.Event.SELECT_CHANNEL, bundle);
        }
    }

    public void logShare(String channel, String title) {
        Bundle bundle = new Bundle();
        bundle.putString(AnalyticsConstant.Param.NEWS_TITLE, title);
        bundle.putString(AnalyticsConstant.Param.NEWS_CHANNEL, channel);
        mFirebase.logEvent(FirebaseAnalytics.Event.SHARE, bundle);
    }

    public void logEditChannel() {
        mFirebase.logEvent(AnalyticsConstant.Event.EDIT_CHANNEL, null);
    }
}

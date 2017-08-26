package com.luthfihariz.newsreader.util.analytics;

/**
 * Created by luthfihariz on 8/26/17.
 */

public class AnalyticsConstant {

    public static class Param {
        public static final String NEWS_TITLE = "news_title";
        public static final String NEWS_CHANNEL = "news_channel";
        public static final String CHANNEL_NAME = "channel_name";
    }

    public static class Event {
        public static final String READ_NEWS = "read_news";
        public static final String SELECT_CHANNEL = "select_channel";
        public static final String EDIT_CHANNEL = "edit_channel";
    }
}

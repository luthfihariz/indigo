package com.luthfihariz.newsreader.browser;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.luthfihariz.newsreader.R;
import com.luthfihariz.newsreader.databinding.ActivityNewsBrowserBinding;
import com.luthfihariz.newsreader.util.analytics.AnalyticsTracker;

/**
 * Created by luthfihariz on 6/13/17.
 */

public class NewsBrowserActivity extends AppCompatActivity implements NewsBrowserContract.View {

    public static final String KEY_URL = "url";
    public static final String KEY_NEWS_TITLE = "newsTitle";

    private ActivityNewsBrowserBinding mBinding;
    private AnalyticsTracker mAnalytics;

    private String mTitle;
    private String mSubtitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_news_browser);
        mAnalytics = AnalyticsTracker.getInstance(this);

        String url = getIntent().getStringExtra(KEY_URL);
        mTitle = getIntent().getStringExtra(KEY_NEWS_TITLE);
        mSubtitle = Uri.parse(url).getHost();
        setupToolbar();
        setupWebView(url);
    }

    private void setupToolbar() {
        setSupportActionBar(mBinding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(mTitle);
            getSupportActionBar().setSubtitle(mSubtitle);
        }
    }

    private void setupWebView(String url) {
        mBinding.wvNewsBrowser.setWebViewClient(new BrowserWebViewClient());
        mBinding.wvNewsBrowser.loadUrl(url);

        mBinding.srlNewsBrowser.setOnRefreshListener(() -> {
            mBinding.wvNewsBrowser.loadUrl(url);
        });
    }

    @Override
    public void setPresenter(NewsBrowserContract.Presenter presenter) {

    }


    public static void intent(Context context, String url, String newsTitle) {
        Intent intent = new Intent(context, NewsBrowserActivity.class);
        intent.putExtra(KEY_URL, url);
        intent.putExtra(KEY_NEWS_TITLE, newsTitle);
        context.startActivity(intent);
    }

    private class BrowserWebViewClient extends WebViewClient {

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());
            return true;
        }

        @SuppressWarnings("deprecation")
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            mBinding.srlNewsBrowser.setRefreshing(true);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            mBinding.srlNewsBrowser.setRefreshing(false);
            mAnalytics.logReadNews(mSubtitle, mTitle);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.news_browser_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else if (item.getItemId() == R.id.menu_share) {
            doShareIntent();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void doShareIntent() {
        String url = getIntent().getStringExtra(KEY_URL);
        String newsTitle = getIntent().getStringExtra(KEY_NEWS_TITLE);

        String stringBuilder = newsTitle + " " + url + "\n\nIndigo bit.ly/indigonewsapp";

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, stringBuilder);

        startActivity(Intent.createChooser(intent, "Share news via"));

        mAnalytics.logShare(mSubtitle, mTitle);
    }
}

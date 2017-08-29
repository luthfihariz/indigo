package com.luthfihariz.newsreader.data.source.remote;

import android.content.Context;

import com.luthfihariz.newsreader.data.Article;
import com.luthfihariz.newsreader.data.ArticleResponse;
import com.luthfihariz.newsreader.data.Source;
import com.luthfihariz.newsreader.data.SourceResponse;
import com.luthfihariz.newsreader.data.source.NewsDataSource;

import java.util.List;

import io.reactivex.Observable;


/**
 * Created by luthfihariz on 5/20/17.
 */

public class RemoteDataSource implements NewsDataSource {

    private static RemoteDataSource sInstance = null;
    private RetrofitEndpointService mApiService;

    private RemoteDataSource(Context context) {
        mApiService = RetrofitHelper.getInstance(context).provideRetrofit()
                .create(RetrofitEndpointService.class);
    }

    public static RemoteDataSource getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new RemoteDataSource(context);
        }

        return sInstance;
    }

    @Override
    public Observable<List<Article>> getArticles(String sources) {
        return mApiService.getArticles(sources)
                .map(ArticleResponse::getArticles);
    }

    @Override
    public Observable<Void> feedLocalSources() {
        throw new RuntimeException("Function not implemented");
    }

    @Override
    public Observable<List<Source>> getSourcesByCategory(String category) {
        throw new RuntimeException("Function not implemented");
    }

    @Override
    public Observable<List<Article>> getArticlesByCategory(String category) {
        return null;
    }

    @Override
    public Observable<Void> saveSources(List<Source> sources) {
        return null;
    }

    @Override
    public Observable<List<Source>> getSources() {
        return mApiService.getSources().map(SourceResponse::getSources);
    }
}

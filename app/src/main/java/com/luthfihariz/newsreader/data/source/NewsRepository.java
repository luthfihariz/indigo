package com.luthfihariz.newsreader.data.source;

import com.luthfihariz.newsreader.data.Article;
import com.luthfihariz.newsreader.data.Source;
import com.luthfihariz.newsreader.data.source.local.LocalDataSource;
import com.luthfihariz.newsreader.data.source.remote.RemoteDataSource;

import java.util.List;

import io.reactivex.Observable;


/**
 * Created by luthfihariz on 5/21/17.
 */

public class NewsRepository implements NewsDataSource {

    private LocalDataSource mLocalDataSource;
    private RemoteDataSource mRemoteDataSource;

    private static NewsRepository sInstance;

    private NewsRepository(LocalDataSource localDataSource, RemoteDataSource remoteDataSource) {
        mLocalDataSource = localDataSource;
        mRemoteDataSource = remoteDataSource;
    }

    public static NewsRepository getInstance(LocalDataSource localDataSource,
                                             RemoteDataSource remoteDataSource) {
        if (sInstance == null) {
            sInstance = new NewsRepository(localDataSource, remoteDataSource);
        }
        return sInstance;
    }


    public Observable<List<Article>> getArticles(String source) {
        return mRemoteDataSource.getArticles(source);
    }

    @Override
    public Observable<List<Article>> getArticlesByCategory(String category) {
        Observable<List<Source>> userSourcesObservable = getSourcesByCategory(category);

        return userSourcesObservable.flatMap(sources -> Observable.fromIterable(sources)
                .flatMap(source -> mRemoteDataSource.getArticles(source.getId())));
    }

    @Override
    public Observable<List<Source>> getSources() {
        return mRemoteDataSource.getSources();
    }

    @Override
    public Observable<Void> feedLocalSources() {
        return mRemoteDataSource.getSources().flatMap(this::saveSources);
    }

    @Override
    public Observable<Void> saveSources(List<Source> sources) {
        return mLocalDataSource.saveSources(sources);
    }


    @Override
    public Observable<List<Source>> getSourcesByCategory(String category) {
        return mLocalDataSource.getSourcesByCategory(category);
    }
}

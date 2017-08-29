package com.luthfihariz.newsreader.data.source;

import com.luthfihariz.newsreader.data.Article;
import com.luthfihariz.newsreader.data.Source;

import java.util.List;

import io.reactivex.Observable;


/**
 * Created by luthfihariz on 5/20/17.
 */

public interface NewsDataSource {

    Observable<List<Article>> getArticles(String source);

    Observable<List<Article>> getArticlesByCategory(String category);

    Observable<Void> feedLocalSources();

    Observable<List<Source>> getSourcesByCategory(String category);

    Observable<Void> saveSources(List<Source> sources);

    Observable<List<Source>> getSources();
}

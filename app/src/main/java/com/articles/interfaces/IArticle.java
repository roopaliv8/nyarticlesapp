package com.articles.interfaces;

import com.articles.model.response.ArticleResponse;

import retrofit2.Response;

public interface IArticle extends IView {

    void onsuccess(Response<ArticleResponse> response);


}

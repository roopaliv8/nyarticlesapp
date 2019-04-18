package com.articles.presenter;

import android.support.annotation.NonNull;

import com.articles.api.APIHelper;
import com.articles.api.ServiceGenerator;
import com.articles.interfaces.ArticleApis;
import com.articles.interfaces.IArticle;
import com.articles.model.response.ArticleResponse;
import com.articles.utils.BasicUtils;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticlePresenter extends BasePresenter<IArticle> {

    private ArticleApis service = ServiceGenerator.createServiceWithCache(ArticleApis.class);

    public void getArticles() {

        if (BasicUtils.isOnline(getView().getContext())) {

            getView().enableLoadingBar(true);

            Call<ArticleResponse> getArticles = service.getArticles();

            APIHelper.enqueueWith(getArticles, new Callback<ArticleResponse>() {
                @Override
                public void onResponse(@NonNull Call<ArticleResponse> call, @NonNull Response<ArticleResponse> response) {
                    getView().enableLoadingBar(false);

                    if (response.body() != null) {

                        if (response.code() == HttpURLConnection.HTTP_OK && response.body().getStatus().equalsIgnoreCase("OK")) {
                            getView().onsuccess(response);
                        } else {
                            handleError(response.message());
                        }

                    }
                }

                @Override
                public void onFailure(@NonNull Call<ArticleResponse> call, @NonNull Throwable t) {
                    getView().enableLoadingBar(false);
                    handleError(t.getMessage());
                }


            });
        }
    }


}

package com.articles.ui.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.articles.R;
import com.articles.databinding.ActivityMainBinding;
import com.articles.interfaces.IArticle;
import com.articles.model.response.Article;
import com.articles.model.response.ArticleResponse;
import com.articles.presenter.ArticlePresenter;
import com.articles.ui.adapter.ArticlesAdapter;
import com.articles.utils.Filter;
import com.articles.utils.Util;

import java.util.ArrayList;

import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements IArticle {
    private ProgressDialog progressDialog;
    private ArrayList<Article> articlestemp = new ArrayList<>();
    private ArrayList<Article> articles = new ArrayList<>();
    private ArticlesAdapter adapter;
    private ActivityMainBinding activityMainBinding;
    private ImageView searchClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        init();
        getArticles();
    }

    private void init() {
        activityMainBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ArticlesAdapter(articles);
        activityMainBinding.recyclerView.setAdapter(adapter);
        searchClose = Util.setSearchView(this, activityMainBinding.searchview, activityMainBinding.ivSearch, activityMainBinding.rlTitle, R.drawable.searchview_background_green, android.R.color.white);
        activityMainBinding.searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                setOnSearchChange(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchClose.setVisibility(newText.isEmpty() ? View.GONE : View.VISIBLE);
                setOnSearchChange(newText);
                return false;
            }
        });
    }

    private void getArticles() {
        ArticlePresenter articlePresenter = new ArticlePresenter();
        articlePresenter.setView(this);
        articlePresenter.getArticles();

    }

    @Override
    public Activity getContext() {
        return this;
    }

    @Override
    public void enableLoadingBar(boolean enable) {
        enableLoader(enable);
    }

    @Override
    public void onError(String reason) {
        Toast.makeText(this, reason, Toast.LENGTH_LONG).show();
    }


    private void enableLoader(boolean enable) {
        if (enable) {
            loadProgressBar();
        } else {
            dismissProgressBar();
        }
    }

    private void loadProgressBar() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this, R.style.MyTheme);
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
            progressDialog.show();
        }
    }

    private void dismissProgressBar() {
        if (!isDestroyed() && progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        progressDialog = null;
    }

    @Override
    public void onsuccess(Response<ArticleResponse> response) {
        if (response.body() != null) {
            articles.addAll(response.body().getResult());
            articlestemp.addAll(response.body().getResult());
            adapter.notifyDataSetChanged();

        }
    }

    public void onSearchClick(View v) {
        activityMainBinding.rlTitle.setVisibility(View.GONE);
        activityMainBinding.searchview.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        if (Util.onBackSearchView(activityMainBinding.searchview, activityMainBinding.ivSearch, activityMainBinding.rlTitle)) {
            super.onBackPressed();
        }
    }

    public void setOnSearchChange(String searchKey) {
        if (!TextUtils.isEmpty(searchKey)) {
            adapter.animateTo(Filter.search(searchKey, articles));

        } else {
            articles.clear();
            articles.addAll(articlestemp);
            adapter.notifyDataSetChanged();
        }
    }


}

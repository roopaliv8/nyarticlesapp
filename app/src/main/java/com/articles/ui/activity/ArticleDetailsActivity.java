package com.articles.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.articles.R;
import com.articles.databinding.ActivityArticledetailsBinding;
import com.articles.model.response.Article;

public class ArticleDetailsActivity extends AppCompatActivity {
    ActivityArticledetailsBinding activityArticledetailsBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityArticledetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_articledetails);
        activityArticledetailsBinding.setArticle((Article) getIntent().getSerializableExtra("data"));

    }
}

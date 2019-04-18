package com.articles.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.articles.R;

public class Util {
    public static ImageView setSearchView(final Context context, final SearchView searchView, final ImageButton iv_search, final View ll_search, int drawable, int color) {
        iv_search.setVisibility(View.VISIBLE);
        ll_search.setVisibility(View.VISIBLE);
        ImageView searchClose = searchView.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
        searchClose.setImageResource(R.drawable.ic_close_icon);
        searchClose.setImageTintList(ColorStateList.valueOf(context.getResources().getColor(color)));

        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.setVisibility(View.VISIBLE);
                searchView.setIconified(false);
                ll_search.setVisibility(View.GONE);
            }
        });
        final ImageView finalSearchClose1 = searchClose;
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = searchView.getQuery().toString();
                finalSearchClose1.setVisibility(query.isEmpty() ? View.GONE : View.VISIBLE);
            }
        });

        searchClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = searchView.getQuery().toString();
                if (!query.isEmpty()) {
                    searchView.setQuery("", true);
                } else {
                    onBackSearchView(searchView, iv_search, ll_search);
                }
            }
        });
        TextView textView = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        textView.setHint(context.getResources().getString(R.string.search));
        textView.setTextColor(context.getResources().getColor(android.R.color.white));
        textView.setHintTextColor(context.getResources().getColor(android.R.color.white));
        View v = searchView.findViewById(android.support.v7.appcompat.R.id.search_plate);
        v.setBackgroundResource(drawable);

        return searchClose;
    }

    public static boolean onBackSearchView(SearchView searchView, ImageButton iv_search, View ll_search) {
        if (ll_search.getVisibility() == View.GONE) {
            ll_search.setVisibility(View.VISIBLE);
            searchView.setVisibility(View.GONE);
            iv_search.setVisibility(View.VISIBLE);
            searchView.setQuery("", true);
            return false;
        } else
            return true;
    }
}

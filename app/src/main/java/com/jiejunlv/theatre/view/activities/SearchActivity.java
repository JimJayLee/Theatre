package com.jiejunlv.theatre.view.activities;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.PopupWindow;

import com.jiejunlv.theatre.R;
import com.jiejunlv.theatre.databinding.ActivitySearchBinding;
import com.jiejunlv.theatre.util.UiUtil;
import com.jiejunlv.theatre.view.fragments.SearchFragment;
import com.jiejunlv.theatre.view.views.PopupSearchWindow;

public class SearchActivity extends AppCompatActivity {

    /**
     * String constants to extract the query string from an Intent.
     */
    private static final String QUERY_TEXT = "queryText";

    private ActivitySearchBinding mBinding;

    /**
     * Factory method to make a desired intent.
     */
    public static Intent makeIntent(Context context, String queryText){
        return new Intent(context, SearchActivity.class).putExtra(QUERY_TEXT, queryText);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        setup();
    }


    private void setupFragment(String query){
        // Setup fragment to show data
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.search_container, SearchFragment.newInstance(query));
        transaction.commit();
    }

    private void setup() {
        if (getIntent() != null){
            handleIntent(getIntent());
        }

        // Popup a search window
        mBinding.searchText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UiUtil.isCover(view)){
                    return;
                }

                final int anchorLoc[] = new int[2];
                mBinding.toolbarSearch.getLocationOnScreen(anchorLoc);
                PopupSearchWindow searchBar =  PopupSearchWindow
                        .from(SearchActivity.this)
                        .setParentView(mBinding.toolbarSearch)
                        .setWidthAndHeight(mBinding.toolbarSearch.getWidth(), mBinding.toolbarSearch.getHeight())
                        .setAnchorLocation(anchorLoc)
                        .setFocusable()
                        .build();

                searchBar.setEtText(mBinding.getQueryText());
                searchBar.show();
            }
        });

        mBinding.backSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    /**
     * Extract the query string from intent and perform a search action.
     */
    private void handleIntent(Intent intent){
        String query = intent.getStringExtra(QUERY_TEXT);
        mBinding.setQueryText(query);
        setupFragment(query);
    }

    /**
     * Called when there's another search query is taken place.
     * @param intent Get the query text from it.
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null){
            handleIntent(intent);
        }
    }
}

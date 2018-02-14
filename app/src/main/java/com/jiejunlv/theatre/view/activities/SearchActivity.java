package com.jiejunlv.theatre.view.activities;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.PopupWindow;

import com.jiejunlv.theatre.R;
import com.jiejunlv.theatre.databinding.ActivitySearchBinding;
import com.jiejunlv.theatre.util.UiUtil;
import com.jiejunlv.theatre.view.views.PopupSearchWindow;

public class SearchActivity extends AppCompatActivity {

    /**
     * String constants to extract the query string from an Intent.
     */
    private static final String QUERY_TEXT = "queryText";

    private ActivitySearchBinding mBinding;
    private PopupSearchWindow mSearchBar;


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

    private void setup() {
        if (getIntent() != null){
            handleIntent(getIntent());
        }


        // Popup a search window
        mBinding.searchText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSearchBar == null){
                    final int anchorLoc[] = new int[2];
                    mBinding.toolbarSearch.getLocationOnScreen(anchorLoc);
                    mSearchBar =  PopupSearchWindow
                            .from(SearchActivity.this)
                            .setParentView(mBinding.toolbarSearch)
                            .setWidthAndHeight(mBinding.toolbarSearch.getWidth(), mBinding.toolbarSearch.getHeight())
                            .setAnchorLocation(anchorLoc)
                            .setFocusable()
                            .build();
                    mSearchBar.setOnDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                            UiUtil.dimOutside(SearchActivity.this, false);
                        }
                    });
                }
                mSearchBar.setEtText(mBinding.getQueryText());
                mSearchBar.show();
                UiUtil.dimOutside(SearchActivity.this, true);
            }
        });
    }

    /**
     * Extract the query string from intent and perform a search action.
     * @param intent
     */
    private void handleIntent(Intent intent){
        mBinding.setQueryText(intent.getStringExtra(QUERY_TEXT));
        // Perform a search action
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

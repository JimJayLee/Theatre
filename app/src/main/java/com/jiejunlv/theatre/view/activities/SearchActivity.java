package com.jiejunlv.theatre.view.activities;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.PopupWindow;

import com.jiejunlv.theatre.CustomApplication;
import com.jiejunlv.theatre.R;
import com.jiejunlv.theatre.bean.ItemData;
import com.jiejunlv.theatre.bean.ParamsBean;
import com.jiejunlv.theatre.databinding.ActivitySearchBinding;
import com.jiejunlv.theatre.datamodel.DataListResponse;
import com.jiejunlv.theatre.util.UiUtil;
import com.jiejunlv.theatre.view.views.PopupSearchWindow;
import com.jiejunlv.theatre.viewmodel.MainViewModel;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SearchActivity extends AppCompatActivity {

    /**
     * String constants to extract the query string from an Intent.
     */
    private static final String QUERY_TEXT = "queryText";

    private ActivitySearchBinding mBinding;
    private PopupSearchWindow mSearchBar;
    private MainViewModel mViewModel;
    private CompositeDisposable mCompositeDisposable;

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
        bind();
    }

    private void bind() {
        if (mViewModel == null) {
            mViewModel = getViewModel();
        }
        mCompositeDisposable = new CompositeDisposable();
        mCompositeDisposable.add(
                        mViewModel
                        .makeSearchQuery()
                        .observeOn(Schedulers.io())
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<DataListResponse>() {
                            @Override
                            public void accept(DataListResponse dataListResponse) throws Exception {
                                setData(dataListResponse.getItemData());
                                Log.i("SearchActivity", dataListResponse.getItemData().toString());
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Log.i("SearchActivity", throwable.getMessage());
                            }
                        })
        );
    }

    private void setData(List<ItemData> data){
        mBinding.setData(data);
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

        Log.i("SearchActivity", "Search " + query);
        // Perform a search action
        ParamsBean params = new ParamsBean();
        params.setQueryText(query);
        params.setType("multi");
        params.setPage(1);
        if (mViewModel == null) {
            mViewModel = getViewModel();
        }
        mViewModel.searchQuery(params);
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

    @Override
    protected void onPause() {
        super.onPause();
        mCompositeDisposable.clear();
    }

    private MainViewModel getViewModel(){
        return ((CustomApplication) getApplication()).getViewModel();
    }
}

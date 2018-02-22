package com.jiejunlv.theatre.view.activities;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.jiejunlv.theatre.CustomApplication;
import com.jiejunlv.theatre.R;
import com.jiejunlv.theatre.bean.DetailBean;
import com.jiejunlv.theatre.bean.ParamsBean;
import com.jiejunlv.theatre.databinding.ActivityDetailBinding;
import com.jiejunlv.theatre.viewmodel.MainViewModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class DetailActivity extends AppCompatActivity {

    /**
     * An specific id for an item.
     */
    private static final String ITEM_ID = "itemId";

    private static final String ITEM_TYPE = "itemType";

    private ActivityDetailBinding mBinding;
    private CompositeDisposable mCompositeDisposable;
    private MainViewModel mViewModel;

    public static Intent makeIntent(Context context, String id, String mediaType){
        return new Intent(context, DetailActivity.class).putExtra(ITEM_ID, id).putExtra(ITEM_TYPE, mediaType);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        setup();
        bind();

        String id = getIntent().getStringExtra(ITEM_ID);
        if (id != null){
            queryDetail(id, getIntent().getStringExtra(ITEM_TYPE));
        }
    }

    private void queryDetail(String id, String type) {
        ParamsBean params = new ParamsBean();
        params.setType(type);
        params.setItemId(id);
        mViewModel.detailQuery(params);
    }

    private void bind() {
        mCompositeDisposable = new CompositeDisposable();
        mViewModel = getViewModel();
        mCompositeDisposable.add(
                mViewModel
                .getDetail()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DetailBean>() {
                    @Override
                    public void accept(DetailBean detailBean) throws Exception {
                        setData(detailBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("DetailActivity", throwable.getMessage());
                    }
                })
        );
    }

    private void setData(DetailBean detail) {
        mBinding.setDetail(detail);
    }

    private void setup() {
        setSupportActionBar(mBinding.detailToolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
        }

        return super.onOptionsItemSelected(item);
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

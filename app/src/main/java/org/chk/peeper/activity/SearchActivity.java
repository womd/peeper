package org.chk.peeper.activity;

import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import org.chk.peeper.R;
import org.chk.peeper.utils.MediaUtil;

public class SearchActivity extends MediaSrcBaseActivity{

    public SearchActivity(){
        super.parentActivity = this;
        super.mediaUtil =new MediaUtil(this);
        super.mediaType = "audio";
        super.mediaLocation = "external";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);



        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            //https://developer.android.com/guide/topics/search/search-dialog
            //https://developer.android.com/training/search/setup

            Bundle appData = getIntent().getBundleExtra(SearchManager.APP_DATA);
            if (appData != null) {
                this.mediaType = appData.getString("mediaType");
                this.mediaLocation = appData.getString("mediaLocation");

                Uri source = this.mediaUtil.findMediaUri(this.mediaType, this.mediaLocation);
                super.mediaItemList = this.mediaUtil.queryMediaItems(source, query);

                super.setupListView();
            }
        }
    }


}
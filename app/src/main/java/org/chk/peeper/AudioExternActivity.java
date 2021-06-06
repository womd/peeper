package org.chk.peeper;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.SearchView;
import org.chk.peeper.utils.MediaUtil;


public class AudioExternActivity extends MediaSrcBaseActivity {

    public AudioExternActivity() {
       super.parentActivity = this;
       super.mediaUtil =new MediaUtil(this);
       super.mediaType = "audio";
       super.mediaLocation = "external";
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        super.checkPermissionAndLoadMedia();
        super.setupListView();

    }

    @Override
    public boolean onSearchRequested() {
        Bundle appData = new Bundle();
        appData.putString("mediaType", this.mediaType);
        appData.putString("mediaLocation", this.mediaLocation);
        startSearch(null, false, appData, false);
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }
}
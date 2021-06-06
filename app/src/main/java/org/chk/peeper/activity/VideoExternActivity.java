package org.chk.peeper.activity;

import android.os.Bundle;
import org.chk.peeper.R;
import org.chk.peeper.utils.MediaUtil;


public class VideoExternActivity extends MediaSrcBaseActivity {

    public VideoExternActivity() {
       super.parentActivity = this;
       super.mediaUtil =new MediaUtil(this);
       super.mediaType = "video";
       super.mediaLocation = "external";
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        super.checkPermissionAndLoadMedia();
        super.setupListView();

    }

}
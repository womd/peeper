package org.chk.peeper;

import android.os.Bundle;
import org.chk.peeper.utils.MediaUtil;


public class VideoInternActivity extends MediaSrcBaseActivity {

    public VideoInternActivity() {
       super.parentActivity = this;
       super.mediaUtil =new MediaUtil(this);
       super.mediaType = "video";
       super.mediaLocation = "internal";
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        super.checkPermissionAndLoadMedia();
        super.setupListView();

    }

}
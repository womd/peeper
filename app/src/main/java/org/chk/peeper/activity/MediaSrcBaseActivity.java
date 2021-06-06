package org.chk.peeper.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.chk.peeper.R;
import org.chk.peeper.adapter.SongViewAdapter;
import org.chk.peeper.dto.MediaItem;
import org.chk.peeper.utils.MediaUtil;

import java.util.ArrayList;
import java.util.List;


public class MediaSrcBaseActivity extends AppCompatActivity implements SongViewAdapter.ItemClickListener {

    String mediaType;
    String mediaLocation;

    AppCompatActivity parentActivity;
    MediaUtil mediaUtil;
    final int MY_PERMISSIONS_REQUEST_READ_MEDIA = 1;
    List<MediaItem> mediaItemList = new ArrayList<MediaItem>();
    MediaPlayer mp;
    SongViewAdapter adapter;

    public MediaSrcBaseActivity() {
        mp = new MediaPlayer();


    }

    public void setupListView() {


        RecyclerView recyclerView = findViewById(R.id.listView);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        adapter = new SongViewAdapter(this, mediaItemList);
        adapter.setClickListener(this);
        adapter.setLongClickListener(this);
        recyclerView.setAdapter(adapter);


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void checkPermissionAndLoadMedia() {

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_MEDIA);
        } else {
            mediaItemList = this.getSongList();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private List<MediaItem>getSongList() {
        Uri source = this.mediaUtil.findMediaUri(this.mediaType, this.mediaLocation);
        return this.mediaUtil.queryMediaItems(source);
    }

    @Override
    public void onItemClick(View view, int position) {
        // Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();

       playMedia(adapter.getItem(position));

    }

    @Override
    public boolean onItemLongClick(View view, int position){
        //Toast.makeText(this, "You longClicked " + adapter.getItem(position) + "on row number " + position, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, MediaViewActivity.class);
        intent.putExtra("mediaUri",adapter.getItem(position).getUri().toString());
        startActivity(intent);

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_MEDIA:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    mediaItemList = getSongList();
                }
                break;

            default:
                break;
        }
    }

    private void playMedia(MediaItem mediaItem) {
        if(mp != null && mp.isPlaying()){
            mp.stop();
        }

        mp = MediaPlayer.create(parentActivity, mediaItem.getUri());
        mp.start();
    }

    public void onSearchButtonClick(){
        super.onSearchRequested();
    }
}

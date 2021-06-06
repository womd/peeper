package org.chk.peeper.utils;

import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import org.chk.peeper.dto.MediaItem;

import java.util.ArrayList;
import java.util.List;

public class MediaUtil {

    private AppCompatActivity parentActivity;

    public MediaUtil(AppCompatActivity parentActivity){
        this.parentActivity = parentActivity;
    }

    /*
    type can be "audio" or "video"
    location can be "internal" or "external"
     */
    public Uri findMediaUri(String type, String location){

        switch (type){
            case "audio":
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    if(location == "internal"){ return MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_INTERNAL);}
                    else { return  MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL);}
                }
                else{
                        if (location == "internal") { return  MediaStore.Audio.Media.INTERNAL_CONTENT_URI;}
                        else { return MediaStore.Audio.Media.EXTERNAL_CONTENT_URI; }
                    }

            case "video":
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    if(location == "internal"){ return MediaStore.Video.Media.getContentUri(MediaStore.VOLUME_INTERNAL);}
                    else { return  MediaStore.Video.Media.getContentUri(MediaStore.VOLUME_EXTERNAL);}
                }
                else{
                    if (location == "internal") { return  MediaStore.Video.Media.INTERNAL_CONTENT_URI;}
                    else { return MediaStore.Video.Media.EXTERNAL_CONTENT_URI; }
                }

        }
        return null;
    }

    public List<MediaItem>queryMediaItems(Uri fromVolume, String query){
        List<MediaItem>resultList = new ArrayList<>();
        String[] projection = useProjection();


        String selection = MediaStore.Audio.Media.ARTIST + " like '" + query + "%'";

        String[] selectionArgs = new String[] {
                //query
        };

        String sortOrder = MediaStore.Video.Media.DISPLAY_NAME + " ASC";

        try (Cursor cursor = parentActivity.getApplicationContext().getContentResolver().query(
                fromVolume,
                projection,
                selection,
                selectionArgs,
                sortOrder
        ))
        {
            // Cache column indices.
            int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID);
            int nameColumn =
                    cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME);
            int durationColumn =
                    cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION);
            int sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE);
            int artistColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.ARTIST);
            int albumColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.ALBUM);


            while (cursor.moveToNext()) {
                // Get values of columns for a given video.
                long id = cursor.getLong(idColumn);
                String name = cursor.getString(nameColumn);
                int duration = cursor.getInt(durationColumn);
                int size = cursor.getInt(sizeColumn);
                String artist = cursor.getString(artistColumn);
                String album = cursor.getString(albumColumn);

                Uri contentUri = ContentUris.withAppendedId(
                        fromVolume, id);


                // Stores column values and the contentUri in a local object
                // that represents the media file.
                MediaItem mediaItem = new MediaItem.Builder(contentUri)
                        .withName(name)
                        .withDuration(duration)
                        .withSize(size)
                        .withArtist(artist)
                        .withAlbum(album).build();

                resultList.add(mediaItem);
            }
        }


        return resultList;
    }



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public List<MediaItem> queryMediaItems(Uri fromVolume){

        List<MediaItem>resultList = new ArrayList<>();

        String[] projection = useProjection();


        /*
        String selection = MediaStore.Video.Media.DURATION +
                " >= ?";

        String[] selectionArgs = new String[] {
                String.valueOf(TimeUnit.MILLISECONDS.convert(5, TimeUnit.MINUTES))
        };
        */

        String sortOrder = MediaStore.Video.Media.DISPLAY_NAME + " ASC";
/*
        try (Cursor cursor = getApplicationContext().getContentResolver().query(
                videoCollectionExternal,
                projection,
                selection,
                selectionArgs,
                sortOrder
        ))
*/

        try (Cursor cursor = parentActivity.getApplicationContext().getContentResolver().query(
                fromVolume,
                projection,
                null,
                null,
                sortOrder
        ))
        {
            // Cache column indices.
            int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID);
            int nameColumn =
                    cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME);
            int durationColumn =
                    cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION);
            int sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE);
            int artistColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.ARTIST);
            int albumColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.ALBUM);
            

            while (cursor.moveToNext()) {
                // Get values of columns for a given video.
                long id = cursor.getLong(idColumn);
                String name = cursor.getString(nameColumn);
                int duration = cursor.getInt(durationColumn);
                int size = cursor.getInt(sizeColumn);
                String artist = cursor.getString(artistColumn);
                String album = cursor.getString(albumColumn);

                Uri contentUri = ContentUris.withAppendedId(
                        fromVolume, id);


                // Stores column values and the contentUri in a local object
                // that represents the media file.
                MediaItem mediaItem = new MediaItem.Builder(contentUri)
                        .withName(name)
                        .withDuration(duration)
                        .withSize(size)
                        .withArtist(artist)
                        .withAlbum(album).build();
                
                resultList.add(mediaItem);
            }
        }
        return  resultList;
    }

    private String[] useProjection(){
        String[] projection = new String[] {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.SIZE,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.ARTIST
        };
        return projection;
    }

}

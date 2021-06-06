package org.chk.peeper.dto;

import android.net.Uri;
import lombok.Data;

@Data
public class MediaItem {

    private Uri uri;
    private String name;
    private int duration;
    private int size;
    private String artist;
    private String album;

    private MediaItem(){

    }

    public static class Builder{

        private Uri uri;
        private String name;
        private int duration;
        private int size;
        private String artist;
        private String album;

        public Builder(Uri uri){
            this.uri = uri;
        }

        public Builder withName(String name){
            this.name = name;
            return this;
        }

        public Builder withDuration(int duration){
            this.duration = duration;
            return this;
        }

        public Builder withSize(int size){
            this.size = size;
            return this;
        }

        public Builder withArtist(String artist){
            this.artist = artist;
            return this;
        }

        public Builder withAlbum(String album){
            this.album = album;
            return this;
        }

        public MediaItem build(){
            MediaItem mediaItem = new MediaItem();
            mediaItem.uri = this.uri;
            mediaItem.name = this.name;
            mediaItem.duration = this.duration;
            mediaItem.size = this.size;
            mediaItem.artist = this.artist;
            mediaItem.album = this.album;

            return mediaItem;
        }

    }
}

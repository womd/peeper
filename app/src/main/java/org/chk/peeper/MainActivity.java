package org.chk.peeper;

import android.content.Intent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    public static final String NAME = "org.chk.peeper.NAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void audioInternActivity(View view){
        Intent intent = new Intent(this, AudioInternActivity.class);
        startActivity(intent);
    }

    public void audioExternActivity(View view){
        Intent intent = new Intent(this, AudioExternActivity.class);
        startActivity(intent);
    }

    public void videoInternActivity(View view){
        Intent intent = new Intent(this, VideoInternActivity.class);
        startActivity(intent);
    }

    public void videoExternActivity(View view){
        Intent intent = new Intent(this, VideoExternActivity.class);
        startActivity(intent);
    }

    public void hfPingActivity(View view){
        Intent intent = new Intent(this, HFPingActivity.class);
        startActivity(intent);
    }

}
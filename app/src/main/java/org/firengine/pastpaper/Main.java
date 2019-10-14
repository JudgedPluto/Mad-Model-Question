package org.firengine.pastpaper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void launchAddArtist(View view) {
        Intent intent = new Intent(this, AddArtist.class);
        startActivity(intent);
    }

    public void launchAddPhoto(View view) {
        Intent intent = new Intent(this, AddPhotograph.class);
        startActivity(intent);
    }

    public void launchDelete(View view) {
        Intent intent = new Intent(this, RemovePhotoOrArtist.class);
        startActivity(intent);
    }

    public void launchList(View view) {
        Intent intent = new Intent(this, ViewPhotos.class);
        startActivity(intent);
    }
}

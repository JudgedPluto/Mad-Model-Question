package org.firengine.pastpaper;

import android.os.Bundle;
import android.view.View;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Map;

public class ViewPhotos extends AppCompatActivity {

    private Spinner inputSearchArtist;
    private RecyclerView imageList;
    private DBHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_photos);

        inputSearchArtist = findViewById(R.id.input_search_artist);
        imageList = findViewById(R.id.image_list);
        handler = new DBHandler(this);

        SimpleAdapter artistAdapter = new SimpleAdapter(
                this,
                handler.loadArtists(),
                android.R.layout.simple_spinner_item,
                new String[] {ArtistMaster.ArtistDetails.COLUMN_ARTIST_NAME},
                new int[] {android.R.id.text1}
        );
        artistAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inputSearchArtist.setAdapter(artistAdapter);

        imageList.setLayoutManager(new GridLayoutManager(this, 3));
    }

    public void onSearch(View view) {
        if (inputSearchArtist.getSelectedItem() != null) {
            int artistId = (int) ((Map<String, Object>) inputSearchArtist.getSelectedItem()).get(ArtistMaster.ArtistDetails._ID);
            ImageAdapter photoAdapter = new ImageAdapter(handler.searchPhotograph(artistId));
            imageList.setAdapter(photoAdapter);
        }
    }
}

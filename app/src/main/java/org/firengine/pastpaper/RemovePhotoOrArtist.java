package org.firengine.pastpaper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Map;

public class RemovePhotoOrArtist extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner inputRemovePhoto;
    private Spinner inputRemoveArtist;
    private DBHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_photo_or_artist);

        inputRemovePhoto = findViewById(R.id.input_remove_photo);
        inputRemoveArtist = findViewById(R.id.input_remove_artist);
        handler = new DBHandler(this);

        SimpleAdapter artistAdapter = new SimpleAdapter(
                this,
                handler.loadArtists(),
                android.R.layout.simple_spinner_item,
                new String[] {ArtistMaster.ArtistDetails.COLUMN_ARTIST_NAME},
                new int[] {android.R.id.text1}
        );
        artistAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inputRemoveArtist.setAdapter(artistAdapter);

        inputRemoveArtist.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        SimpleAdapter photoAdapter = new SimpleAdapter(
                this,
                handler.searchPhotograph((int) ((Map<String, Object>) inputRemoveArtist.getSelectedItem()).get(ArtistMaster.ArtistDetails._ID)),
                android.R.layout.simple_spinner_item,
                new String[] {ArtistMaster.PhotographDetails.COLUMN_PHOTOGRAPH_NAME},
                new int[] {android.R.id.text1}
        );
        photoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inputRemovePhoto.setAdapter(photoAdapter);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void onRemovePhoto(View view) {
        if (inputRemovePhoto.getSelectedItem() != null) {
            int id = (int) ((Map<String, Object>) inputRemovePhoto.getSelectedItem()).get(ArtistMaster.PhotographDetails._ID);
            if (handler.deleteDetails(id, ArtistMaster.PhotographDetails._ID, ArtistMaster.PhotographDetails.TABLE_NAME)) {
                Toast.makeText(this, "Photograph Deleted Successfully.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Error Deleting Photograph.", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Error Deleting Photograph.", Toast.LENGTH_LONG).show();
        }
        finish();
    }

    public void onRemoveArtist(View view) {
        if (inputRemoveArtist.getSelectedItem() != null && inputRemovePhoto.getSelectedItem() != null) {
            int id = (int) ((Map<String, Object>) inputRemoveArtist.getSelectedItem()).get(ArtistMaster.ArtistDetails._ID);
            if (handler.deleteDetails(id, ArtistMaster.PhotographDetails.COLUMN_ARTIST_ID, ArtistMaster.PhotographDetails.TABLE_NAME)
                    && handler.deleteDetails(id, ArtistMaster.ArtistDetails._ID, ArtistMaster.ArtistDetails.TABLE_NAME)) {
                Toast.makeText(this, "Artist Deleted Successfully.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Error Deleting Artist.", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Error Deleting Artist.", Toast.LENGTH_LONG).show();
        }
        finish();
    }
}

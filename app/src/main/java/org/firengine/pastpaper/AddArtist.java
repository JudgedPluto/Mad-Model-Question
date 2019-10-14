package org.firengine.pastpaper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddArtist extends AppCompatActivity {

    private EditText inputAddArtist;
    private DBHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_artist);

        inputAddArtist = findViewById(R.id.input_add_artist);
        handler = new DBHandler(this);
    }

    public void onAddArtist(View view) {
        String artistName = inputAddArtist.getText().toString();
        if (handler.addArtist(artistName)) {
            Toast.makeText(this, "Artist Added Successfully.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Error Adding Artist.", Toast.LENGTH_LONG).show();
        }
        finish();
    }
}

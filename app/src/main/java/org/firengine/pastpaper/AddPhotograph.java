package org.firengine.pastpaper;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import java.util.Map;

public class AddPhotograph extends AppCompatActivity {

    private EditText inputAddPhoto;
    private Spinner inputSelectArtist;
    private Spinner inputSelectCategory;
    private ImageView photographImage;
    private Bitmap image;
    private DBHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photograph);

        inputAddPhoto = findViewById(R.id.input_add_photo);
        inputSelectArtist = findViewById(R.id.input_select_artist);
        inputSelectCategory = findViewById(R.id.input_select_category);
        photographImage = findViewById(R.id.photograph_image);
        handler = new DBHandler(this);

        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.photo_category_list,
                android.R.layout.simple_spinner_item
        );
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inputSelectCategory.setAdapter(categoryAdapter);

        SimpleAdapter artistAdapter = new SimpleAdapter(
                this,
                handler.loadArtists(),
                android.R.layout.simple_spinner_item,
                new String[] {ArtistMaster.ArtistDetails.COLUMN_ARTIST_NAME},
                new int[] {android.R.id.text1}
        );
        artistAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inputSelectArtist.setAdapter(artistAdapter);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.rgb((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255)));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(50);

        image = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(image);
        canvas.drawColor(Color.rgb((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255)));
        canvas.drawRect(150 ,150, canvas.getWidth() - 150, canvas.getHeight() - 150, paint);
        photographImage.setImageBitmap(image);
    }

    public void onAddPhotograph(View view) {
        if (inputSelectArtist.getSelectedItem() != null) {
            String photographName = inputAddPhoto.getText().toString();
            int artistId = (int) ((Map<String, Object>) inputSelectArtist.getSelectedItem()).get(ArtistMaster.ArtistDetails._ID);
            String photoCategory = inputSelectCategory.getSelectedItem().toString();
            handler.addPhotos(photographName, artistId, photoCategory, image);
        }
        finish();
    }
}

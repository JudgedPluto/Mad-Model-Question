package org.firengine.pastpaper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "App.db";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_ARTIST_TABLE =
            "CREATE TABLE " + ArtistMaster.ArtistDetails.TABLE_NAME + " (" +
                    ArtistMaster.ArtistDetails._ID + " INTEGER PRIMARY KEY, " +
                    ArtistMaster.ArtistDetails.COLUMN_ARTIST_NAME + " TEXT)";

    private static final String CREATE_PHOTOGRAPH_TABLE =
            "CREATE TABLE " + ArtistMaster.PhotographDetails.TABLE_NAME + " (" +
                    ArtistMaster.PhotographDetails._ID + " INTEGER PRIMARY KEY, " +
                    ArtistMaster.PhotographDetails.COLUMN_PHOTOGRAPH_NAME + " TEXT, " +
                    ArtistMaster.PhotographDetails.COLUMN_ARTIST_ID + " INTEGER, " +
                    ArtistMaster.PhotographDetails.COLUMN_PHOTO_CATEGORY + " TEXT, " +
                    ArtistMaster.PhotographDetails.COLUMN_IMAGE + " BLOB, " +
                    "FOREIGN KEY (" + ArtistMaster.PhotographDetails.COLUMN_ARTIST_ID +
                    ") REFERENCES " + ArtistMaster.ArtistDetails.TABLE_NAME + "(" +
                    ArtistMaster.ArtistDetails._ID + "))";

    private static final String DELETE_ARTIST_TABLE =
            "DROP TABLE IF EXISTS " + ArtistMaster.ArtistDetails.TABLE_NAME;

    private static final String DELETE_PHOTOGRAPH_TABLE =
            "DROP TABLE IF EXISTS " + ArtistMaster.PhotographDetails.TABLE_NAME;

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_ARTIST_TABLE);
        sqLiteDatabase.execSQL(CREATE_PHOTOGRAPH_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DELETE_PHOTOGRAPH_TABLE);
        sqLiteDatabase.execSQL(DELETE_ARTIST_TABLE);
        onCreate(sqLiteDatabase);
    }

    public void addPhotos(String photoName, int artistID, String photographCategory, Bitmap image) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] imageByteArray = stream.toByteArray();

        ContentValues values = new ContentValues();
        values.put(ArtistMaster.PhotographDetails.COLUMN_PHOTOGRAPH_NAME, photoName);
        values.put(ArtistMaster.PhotographDetails.COLUMN_ARTIST_ID, artistID);
        values.put(ArtistMaster.PhotographDetails.COLUMN_PHOTO_CATEGORY, photographCategory);
        values.put(ArtistMaster.PhotographDetails.COLUMN_IMAGE, imageByteArray);

        getWritableDatabase().insert(ArtistMaster.PhotographDetails.TABLE_NAME, null, values);
    }

    public boolean addArtist(String artistName) {
        ContentValues values = new ContentValues();
        values.put(ArtistMaster.ArtistDetails.COLUMN_ARTIST_NAME, artistName);

        long result = getWritableDatabase().insert(ArtistMaster.ArtistDetails.TABLE_NAME, null, values);
        return result != -1;
    }

    public boolean deleteDetails(int value, String columnName, String tableName) {
        String selection = columnName + " LIKE ?";
        String[] selectionArgs = {String.valueOf(value)};
        int result = getReadableDatabase().delete(tableName, selection, selectionArgs);
        return result > 0;
    }

    public List<Map<String, Object>> loadArtists() {
        String[] projection = {
                ArtistMaster.ArtistDetails._ID,
                ArtistMaster.ArtistDetails.COLUMN_ARTIST_NAME
        };

        String sortOrder = ArtistMaster.ArtistDetails.COLUMN_ARTIST_NAME;

        Cursor c = getReadableDatabase().query(
                ArtistMaster.ArtistDetails.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder);

        List<Map<String, Object>> artistDetails = new ArrayList<>();

        while (c.moveToNext()) {
            Map<String, Object> item = new HashMap<>();
            item.put(ArtistMaster.ArtistDetails._ID, c.getInt(c.getColumnIndex(ArtistMaster.ArtistDetails._ID)));
            item.put(ArtistMaster.ArtistDetails.COLUMN_ARTIST_NAME, c.getString(c.getColumnIndex(ArtistMaster.ArtistDetails.COLUMN_ARTIST_NAME)));
            artistDetails.add(item);
        }
        c.close();
        return artistDetails;
    }

    public List<Map<String, Object>> searchPhotograph(int artistId) {
        String[] projection = {
                ArtistMaster.PhotographDetails._ID,
                ArtistMaster.PhotographDetails.COLUMN_PHOTOGRAPH_NAME,
                ArtistMaster.PhotographDetails.COLUMN_IMAGE,
        };

        String selection = ArtistMaster.PhotographDetails.COLUMN_ARTIST_ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(artistId)};

        String sortOrder = ArtistMaster.PhotographDetails.COLUMN_PHOTOGRAPH_NAME;

        Cursor c = getReadableDatabase().query(
                ArtistMaster.PhotographDetails.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder);

        List<Map<String, Object>> photoDetails = new ArrayList<>();

        while (c.moveToNext()) {
            Map<String, Object> item = new HashMap<>();
            item.put(ArtistMaster.PhotographDetails._ID, c.getInt(c.getColumnIndex(ArtistMaster.PhotographDetails._ID)));
            item.put(ArtistMaster.PhotographDetails.COLUMN_PHOTOGRAPH_NAME, c.getString(c.getColumnIndex(ArtistMaster.PhotographDetails.COLUMN_PHOTOGRAPH_NAME)));
            byte[] imageByteArray = c.getBlob(c.getColumnIndex(ArtistMaster.PhotographDetails.COLUMN_IMAGE));
            item.put(ArtistMaster.PhotographDetails.COLUMN_IMAGE, BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length));
            photoDetails.add(item);
        }
        c.close();
        return photoDetails;
    }
}

package org.firengine.pastpaper;

import android.provider.BaseColumns;

public final class ArtistMaster {
    private ArtistMaster() {}

    public static class ArtistDetails implements BaseColumns {
        public static final String TABLE_NAME = "ArtistDetails";
        public static final String COLUMN_ARTIST_NAME = "artistName";
    }

    public static class PhotographDetails implements BaseColumns {
        public static final String TABLE_NAME = "PhotographDetails";
        public static final String COLUMN_PHOTOGRAPH_NAME = "photographName";
        public static final String COLUMN_ARTIST_ID = "artistID";
        public static final String COLUMN_PHOTO_CATEGORY = "photoCategory";
        public static final String COLUMN_IMAGE = "image";
    }
}

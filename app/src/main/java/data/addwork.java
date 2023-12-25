package data;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
public final class addwork {

    private addwork() {
    };
    public static final class GuestEntry implements BaseColumns {
        public final static String TABLE_NAME = "exercise";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_NAME = "name";
            public final static String COLUMN_DESCRIPTION = "description";
        public final static String COLUMN_TREINING = "training";
        public final static String COLUMN_CALORIES = "calories";

        public static final int cardio_training= 0;
        public static final int power_training = 1;
        public static final int strerching_training = 2;
        public static final int ballet_training = 3;
    }
}
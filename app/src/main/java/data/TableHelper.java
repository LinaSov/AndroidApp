package data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class TableHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "trenager.db";
    private static final int DATABASE_VERSION = 2;

    public TableHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_GUESTS_TABLE = "CREATE TABLE " + addwork.GuestEntry.TABLE_NAME + " ("
                + addwork.GuestEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + addwork.GuestEntry.COLUMN_NAME + " TEXT NOT NULL, "
                + addwork.GuestEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL, "
                + addwork.GuestEntry.COLUMN_TREINING + " INTEGER NOT NULL DEFAULT 4, "
                + addwork.GuestEntry.COLUMN_CALORIES + " INTEGER NOT NULL DEFAULT 0);";

        db.execSQL(SQL_CREATE_GUESTS_TABLE);

        DatabaseInitializer.initializeDefaultData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + addwork.GuestEntry.TABLE_NAME);
        onCreate(db);
    }

    public List<String> getAvailableExercises(int workoutType, int limit) {
        List<String> result = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {addwork.GuestEntry.COLUMN_NAME};

        String selection = addwork.GuestEntry.COLUMN_TREINING + " = ?";
        String[] selectionArgs = {String.valueOf(workoutType)};

        try (Cursor cursor = db.query(
                addwork.GuestEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                "RANDOM()", // Добавим случайный порядок
                String.valueOf(limit)
        )) {
            if (cursor != null && cursor.moveToFirst()) {
                int nameColumnIndex = cursor.getColumnIndex(addwork.GuestEntry.COLUMN_NAME);

                if (nameColumnIndex != -1) {
                    do {
                        String exerciseName = cursor.getString(nameColumnIndex);
                        result.add(exerciseName);
                    } while (cursor.moveToNext());
                } else {
                    Log.e("TableHelper", "Column 'name' not found in the cursor");
                }
            } else {
                Log.e("TableHelper", "Cursor is null or empty");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public String getExerciseDescription(String exerciseName) {
        String exerciseDescription = "";

        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                addwork.GuestEntry.COLUMN_DESCRIPTION
        };

        String selection = addwork.GuestEntry.COLUMN_NAME + " = ?";
        String[] selectionArgs = {exerciseName};

        try (Cursor cursor = db.query(
                addwork.GuestEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        )) {
            if (cursor != null && cursor.moveToFirst()) {
                int descriptionColumnIndex = cursor.getColumnIndex(addwork.GuestEntry.COLUMN_DESCRIPTION);

                if (descriptionColumnIndex != -1 && !cursor.isNull(descriptionColumnIndex)) {
                    exerciseDescription = cursor.getString(descriptionColumnIndex);
                } else {
                    Log.e("TableHelper", "Column 'description' not found or contains null values");
                }
            } else {
                Log.e("TableHelper", "Cursor is null or empty");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return exerciseDescription;
    }
}

package ru.whitegray.sunscreetdict;

import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "wordstore.db"; // название бд
    private static final int SCHEMA = 1; // версия базы данных
    static final String TABLE = "words"; // название таблицы в бд
    // названия столбцов
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_WORF_SANSKRIT = "word_sanskrit";
    public static final String COLUMN_WORD_RUSSIAN = "word_russian";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE "+ TABLE +" (" + COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_WORF_SANSKRIT
                + " TEXT, " + COLUMN_WORD_RUSSIAN + " TEXT);");
        // добавление начальных данных
        db.execSQL("INSERT INTO "+ TABLE +" (" + COLUMN_WORF_SANSKRIT
                + ", " + COLUMN_WORD_RUSSIAN + ") VALUES ('HelloSanskrit', 'HelloRussion');");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,  int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE);
        onCreate(db);
    }
}
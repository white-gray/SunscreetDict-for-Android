package ru.whitegray.sunscreetdict;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Sanskrit extends AppCompatActivity {

    EditText wordSanskrit;
    EditText wordRussian;
    Button delButton;
    Button saveButton;

    DatabaseHelper sqlHelper;
    SQLiteDatabase db;
    Cursor wordCursor;
    long wordId=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);

        wordSanskrit = findViewById(R.id.wordSanskrit);
        wordRussian = findViewById(R.id.wordRussian);
        delButton = findViewById(R.id.deleteButton);
        saveButton = findViewById(R.id.saveButton);

        sqlHelper = new DatabaseHelper(this);
        db = sqlHelper.getWritableDatabase();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            wordId = extras.getLong("id");
        }
        // если 0, то добавление
        if (wordId > 0) {
            // получаем элемент по id из бд
            wordCursor = db.rawQuery("select * from " + DatabaseHelper.TABLE + " where " +
                    DatabaseHelper.COLUMN_ID + "=?", new String[]{String.valueOf(wordId)});
            wordCursor.moveToFirst();
            wordSanskrit.setText(wordCursor.getString(1));
            wordRussian.setText(String.valueOf(wordCursor.getInt(2)));
            wordCursor.close();
        } else {
            // скрываем кнопку удаления
            delButton.setVisibility(View.GONE);
        }
    }

    public void save(View view){
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_WORF_SANSKRIT, wordSanskrit.getText().toString());
        cv.put(DatabaseHelper.COLUMN_WORD_RUSSIAN, wordRussian.getText().toString());

        if (wordId > 0) {
            db.update(DatabaseHelper.TABLE, cv, DatabaseHelper.COLUMN_ID + "=" + wordId, null);
        } else {
            db.insert(DatabaseHelper.TABLE, null, cv);
        }
        goHome();
    }
    public void delete(View view){
        db.delete(DatabaseHelper.TABLE, "_id = ?", new String[]{String.valueOf(wordId)});
        goHome();
    }
    private void goHome(){
        // закрываем подключение
        db.close();
        // переход к главной activity
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}
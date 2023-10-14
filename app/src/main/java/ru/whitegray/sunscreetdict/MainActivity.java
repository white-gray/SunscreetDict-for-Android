package ru.whitegray.sunscreetdict;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleCursorAdapter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    ListView wordList;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor wordCursor;
    SimpleCursorAdapter wordAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wordList = findViewById(R.id.list);
        wordList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), Sanskrit.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        databaseHelper = new DatabaseHelper(getApplicationContext());
    }

    @Override
    public void onResume() {
        super.onResume();
        // открываем подключение
        db = databaseHelper.getReadableDatabase();

        //получаем данные из бд в виде курсора
        wordCursor = db.rawQuery("select * from " + DatabaseHelper.TABLE, null);
        // определяем, какие столбцы из курсора будут выводиться в ListView
        String[] headers = new String[]{DatabaseHelper.COLUMN_WORF_SANSKRIT, DatabaseHelper.COLUMN_WORD_RUSSIAN};
        // создаем адаптер, передаем в него курсор
        wordAdapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item,
                wordCursor, headers, new int[]{android.R.id.text1, android.R.id.text2}, 0);
        wordList.setAdapter(wordAdapter);
    }

    // по нажатию на кнопку запускаем Sanskrit для добавления данных
    public void add(View view) {
        Intent intent = new Intent(this, Sanskrit.class);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Закрываем подключение и курсор
        db.close();
        wordCursor.close();
    }
}
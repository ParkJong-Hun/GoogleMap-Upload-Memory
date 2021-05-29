package pjh.mjc.project_gimal_2017081066;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class PostListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_list);

        ListView list;

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");

        HashMap<Integer, String> title = new HashMap<Integer, String>();
        HashMap<Integer, String> date = new HashMap<Integer, String>();
        HashMap<Integer, Integer> key = new HashMap<Integer, Integer>();

        DBHelper dbHelper;
        SQLiteDatabase db;

        dbHelper = new DBHelper(this);
        db = dbHelper.getReadableDatabase();

        Cursor cursor;
        cursor = db.rawQuery("SELECT code, title, date FROM Post WHERE poster = '" + id + "' ORDER BY date;", null);
        while(cursor.moveToNext()) {
            title.put(cursor.getInt(0), cursor.getString(1));
            date.put(cursor.getInt(0), cursor.getString(2));
            key.put(cursor.getInt(0), cursor.getInt(0));
        }
        db.close();

        list = findViewById(R.id.post_list);
        PostListAdapter adapter = new PostListAdapter(getApplicationContext(), title, date);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //이동
                Intent intent = new Intent(PostListActivity.this, PostActivity.class);
                Cursor cursor;
                SQLiteDatabase db;
                db = dbHelper.getReadableDatabase();
                //해당 클릭한 것의 코드를 불러오기
                int currentKey = key.get(position);
                cursor = db.rawQuery("SELECT * FROM Post WHERE code = " + currentKey + ";", null);
                if(cursor.moveToNext()) {
                    intent.putExtra("title", cursor.getString(1));
                    intent.putExtra("article", cursor.getString(2));
                    intent.putExtra("url", cursor.getString(3));
                    intent.putExtra("lat", cursor.getDouble(4));
                    intent.putExtra("lng", cursor.getDouble(5));
                    intent.putExtra("date", cursor.getString(6));
                    intent.putExtra("poster", cursor.getString(7));
                    startActivityForResult(intent, 1);
                }
            }
        });
    }
}

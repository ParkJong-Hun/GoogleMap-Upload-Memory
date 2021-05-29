package pjh.mjc.MarkMyMemory_2017081066;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

//게시글창
public class PostActivity extends AppCompatActivity {

    String title, article, url, date, poster;
    Double lat, lng;
    int code;
    TextView title_tv, article_tv, date_tv, edit, delete;
    ImageView image;
    ActionBar actionBar;
    DBHelper dbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        code = intent.getIntExtra("code", 0);
        title = intent.getStringExtra("title");
        article = intent.getStringExtra("article");
        url = intent.getStringExtra("url");
        lat = intent.getDoubleExtra("lat", 0);
        lng = intent.getDoubleExtra("lng", 0);
        date = intent.getStringExtra("date");
        poster = intent.getStringExtra("poster");

        setTitle(poster);

        title_tv = findViewById(R.id.post_real_title);
        article_tv = findViewById(R.id.post_real_article);
        image = findViewById(R.id.post_real_image);
        date_tv = findViewById(R.id.post_real_date);
        title_tv.setText(title);
        article_tv.setText(article);
        edit = findViewById(R.id.post_edit);
        delete = findViewById(R.id.post_delete);
        dbHelper = new DBHelper(this);

        //null이 아니면 이미지 표시
        if(!(url.equals("null"))) {
            image.setVisibility(View.VISIBLE);
            try {
                Toast.makeText(getApplicationContext(), url, Toast.LENGTH_SHORT).show();
                Uri uri = Uri.parse("file://" + url);
                image.setImageURI(uri);
            } catch (Exception e) {
            }
        } else {
            image.setVisibility(View.GONE);
        }
        date_tv.setText(date);

        //작성자의 글이라면
        if(MapActivity.id.equals(poster)) {
            edit.setVisibility(View.VISIBLE);
            delete.setVisibility(View.VISIBLE);
            //편집
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), PostingEditActivity.class);
                    intent.putExtra("Lat", lat);
                    intent.putExtra("Lng", lng);
                    intent.putExtra("title", title);
                    intent.putExtra("article", article);
                    intent.putExtra("url", url);
                    startActivityForResult(intent, 0);
                }
            });
            //삭제
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    db = dbHelper.getWritableDatabase();
                    db.execSQL("DELETE FROM Post WHERE code = " + code + ";");
                    Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                    intent.putExtra("id", MapActivity.id);
                    startActivity(intent);
                    finish();
                }
            });
        } else {
            edit.setVisibility(View.GONE);
            delete.setVisibility(View.GONE);
        }
    }

    //뒤로가기 이벤트 처리
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    //포스트 완료 하고 돌아왔을 때 값 수정하고 데이터베이스 업데이트
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0) {//PostingEditActivity
            if(resultCode == RESULT_FIRST_USER) {
                String out_title = data.getStringExtra("out_title");
                String out_article = data.getStringExtra("out_article");
                Double out_latitude = data.getDoubleExtra("out_latitude", 0);
                Double out_longitude = data.getDoubleExtra("out_longitude", 0);
                String out_date = data.getStringExtra("out_date");
                String out_url = data.getStringExtra("out_url");
                //DB에 추가
                db = dbHelper.getWritableDatabase();
                db.execSQL("UPDATE Post SET title='" + out_title + "', article='" + out_article + "', date='" + out_date + "', url='" + out_url + "' WHERE code=" + code + ";");
                db.close();
                //db 업데이트한 것 반영.
                Intent intent = new Intent(getApplicationContext(), PostActivity.class);
                intent.putExtra("code", code);
                intent.putExtra("title", out_title);
                intent.putExtra("article", out_article);
                intent.putExtra("lat", out_latitude);
                intent.putExtra("lng", out_longitude);
                intent.putExtra("url", out_url);
                intent.putExtra("date", out_date);
                intent.putExtra("poster", poster);
                startActivity(intent);
                finish();
            }
        }
    }
}

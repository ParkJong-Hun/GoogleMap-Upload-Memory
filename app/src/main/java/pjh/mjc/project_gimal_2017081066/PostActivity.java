package pjh.mjc.project_gimal_2017081066;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class PostActivity extends AppCompatActivity {

    String title, article, url, date, poster;
    Double lat, lng;
    TextView title_tv, article_tv, date_tv;
    ImageView image;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
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

        //null이 아니면 이미지 표시
        if(!(url.equals("null"))) {
            image.setVisibility(View.VISIBLE);
            try {
                Uri uri = Uri.parse("file://" + url);
                image.setImageURI(uri);
            } catch (Exception e) {
            }
        } else {
            image.setVisibility(View.GONE);
        }
        date_tv.setText(date);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }
}

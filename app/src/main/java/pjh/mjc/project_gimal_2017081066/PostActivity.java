package pjh.mjc.project_gimal_2017081066;

import android.Manifest;
import android.content.ContentProvider;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.InputStream;

public class PostActivity extends AppCompatActivity {

    String title, article, url, date, poster;
    Double lat, lng;
    TextView title_tv, article_tv, date_tv;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post);

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

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MODE_PRIVATE);
        }

        if(!(url.equals("null"))) {
            image.setVisibility(View.VISIBLE);
            try {
                Uri uri = Uri.parse("file://" + url);
                image.setImageURI(uri);
                Toast.makeText(getApplicationContext(), "아아 잘 됨", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            image.setVisibility(View.GONE);
        }
        date_tv.setText(date);
    }
}

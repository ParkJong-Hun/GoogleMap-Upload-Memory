package pjh.mjc.project_gimal_2017081066;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class PostingActivity extends AppCompatActivity {
    //선언
    Button cancel, submit, upload;
    EditText title, article;
    TextView url_text;
    Uri uri;
    String url_str = "", title_str = "", article_str = "";
    Double longitude, latitude;

    public static Context context_posting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.posting);
        context_posting = this;
        //바인딩
        cancel = findViewById(R.id.post_cancel_button);
        submit = findViewById(R.id.post_submit_button);
        
        title = findViewById(R.id.post_title);
        article = findViewById(R.id.post_article);

        url_text = findViewById(R.id.upload_file);
        upload = findViewById(R.id.upload_file_button);

        //좌표값 받아오기
        Intent intent = getIntent();
        longitude = intent.getDoubleExtra("Lng", 0);
        latitude = intent.getDoubleExtra("Lat", 0);

        //이미지 업로드
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, 0);
            }
        });

        //취소 버튼
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
        //작성 버튼
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title_str = title.getText().toString();
                article_str = article.getText().toString();
                //작성 시간 기준 한국 표준시로 date 객체 생성해서 문자열로 만듦.
                long now = System.currentTimeMillis();
                TimeZone tz = TimeZone.getTimeZone("Asia/Seoul");
                Date mDate = new Date(now);
                SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.KOREAN);
                simpleDate.setTimeZone(tz);
                String getTime = simpleDate.format(mDate);
                //Dialog 표시
                if(!(title_str.equals("") || article_str.equals(""))) {
                    Intent out_Intent = new Intent(PostingActivity.this, MapActivity.class);
                    if (url_str.equals("")) {//이미지 업로드 x?
                        PostingDialog dlg = new PostingDialog(PostingActivity.this, title_str, article_str, latitude, longitude, getTime);
                        dlg.show();
                    } else {//이미지 업로드?
                        PostingDialog dlg = new PostingDialog(PostingActivity.this, title_str, article_str, latitude, longitude, url_str, getTime);
                        //url 값 보내기
                        out_Intent.putExtra("out_url", url_str);
                        dlg.show();
                    }
                    //MapActivity에 값 보내기.
                    out_Intent.putExtra("out_title", title_str);
                    out_Intent.putExtra("out_article", article_str);
                    out_Intent.putExtra("out_latitude", latitude);
                    out_Intent.putExtra("out_longitude", longitude);
                    out_Intent.putExtra("out_date", getTime);
                    setResult(RESULT_OK, out_Intent);
                } else {
                    Toast.makeText(getApplicationContext(), "내용을 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            try {//이미지 선택안하고 돌아올 수 있으니깐 nullPointException 안되게
                uri = data.getData();
                url_str = uri.toString();
                url_text.setText(url_str);
            } catch (Exception e) {
                url_text.setText("...");
            }
        }
    }
}

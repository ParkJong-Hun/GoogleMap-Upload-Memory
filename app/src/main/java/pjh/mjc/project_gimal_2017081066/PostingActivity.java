package pjh.mjc.project_gimal_2017081066;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PostingActivity extends AppCompatActivity {
    //선언
    Button cancel, submit, upload;
    EditText title, article;
    TextView url_text;
    Uri uri;
    String url_str = "", title_str = "", article_str = "";
    Double longitude, latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.posting);
        //바인딩
        cancel = findViewById(R.id.post_cancel_button);
        submit = findViewById(R.id.post_submit_button);
        
        title = findViewById(R.id.post_title);
        article = findViewById(R.id.post_article);

        url_text = findViewById(R.id.upload_file);
        upload = findViewById(R.id.upload_file_button);

        //좌표값 받아오기
        Intent intent = getIntent();
        longitude = intent.getDoubleExtra("Lat", 0);
        latitude = intent.getDoubleExtra("Lng", 0);

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
                if(!(title_str.equals("") || article_str.equals(""))) {
                    if (url_str.equals("")) {//이미지 업로드 x?
                        PostingDialog dlg = new PostingDialog(PostingActivity.this);
                        dlg.show();
                    } else {//이미지 업로드?
                        PostingDialog dlg = new PostingDialog(PostingActivity.this);
                        dlg.show();
                    }
                    setResult(RESULT_OK);
                    finish();
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

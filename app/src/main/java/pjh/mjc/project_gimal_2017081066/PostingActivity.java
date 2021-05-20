package pjh.mjc.project_gimal_2017081066;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PostingActivity extends AppCompatActivity {
    //선언
    Button cancel, submit, upload;
    EditText title, article;
    TextView url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.posting);
        //바인딩
        cancel = findViewById(R.id.post_cancel_button);
        submit = findViewById(R.id.post_submit_button);
        
        title = findViewById(R.id.post_title);
        article = findViewById(R.id.post_article);

        url = findViewById(R.id.upload_file);
        upload = findViewById(R.id.upload_file_button);

        //좌표값 받아오기
        Intent intent = getIntent();
        intent.getDoubleExtra("Lat", 0);
        intent.getDoubleExtra("Lng", 0);

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
                if(true) {//이미지 업로드?
                    //insert
                } else if(false) {//이미지 업로드 x?
                    //insert
                }
                finish();
            }
        });
    }
}

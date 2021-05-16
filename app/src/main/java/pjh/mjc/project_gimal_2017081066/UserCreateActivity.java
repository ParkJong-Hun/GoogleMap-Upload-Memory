package pjh.mjc.project_gimal_2017081066;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UserCreateActivity extends AppCompatActivity {

    UserDBHelper dbHelper;
    SQLiteDatabase db;

    EditText id, password;
    Button cancel, submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_create);
        id = findViewById(R.id.id2);
        password = findViewById(R.id.pw2);

        dbHelper = new UserDBHelper(this);

        //아이디 입력창에 자동으로 포커스
        id.requestFocus();

        //취소
        cancel = (Button)findViewById(R.id.cancel_button);
        cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                setResult(2);
                finish();
            }
        });
        //로그인 완료
        submit = (Button)findViewById(R.id.submit_button);
        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //아이디 비밀번호 입력 값 받아오고 디비 켜기.
                String idInput = id.getText().toString();
                String pwInput = password.getText().toString();
                db = dbHelper.getWritableDatabase();
                //조건에 맞지 않으면 회원가입 안되게 하기
                if(!(idInput.equals("") || pwInput.equals(""))) {
                    db.execSQL("INSERT INTO user VALUES ('" + idInput + "', '" + pwInput + "');");
                    db.close();

                    Intent submit_intent = new Intent(UserCreateActivity.this, LoginActivity.class);
                    submit_intent.putExtra("id", idInput);
                    submit_intent.putExtra("password", pwInput);
                    setResult(RESULT_OK, submit_intent);
                    finish();
                } else if (idInput.length() > 15 || pwInput.length() > 15){
                    Toast.makeText(getApplicationContext(), "15자 이내로 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "정보를 모두 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
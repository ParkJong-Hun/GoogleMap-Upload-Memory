package pjh.mjc.project_gimal_2017081066;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
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
        String idInput = id.getText().toString();
        String pwInput = password.getText().toString();

        dbHelper = new UserDBHelper(this);

        //취소
        cancel = (Button)findViewById(R.id.cancel_button);
        cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent cancel_intent = new Intent(UserCreateActivity.this, LoginActivity.class);
                startActivity(cancel_intent);
            }
        });
        //로그인 완료
        submit = (Button)findViewById(R.id.submit_button);
        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                db = dbHelper.getWritableDatabase();
                if(!(idInput.equals("") && pwInput.equals(""))) {
                    db.execSQL("INSERT INTO user VALUES ( '" + idInput + "', ''" + pwInput + "');");
                    db.close();

                    Intent submit_intent = new Intent(UserCreateActivity.this, LoginActivity.class);
                    submit_intent.putExtra("id", idInput);
                    submit_intent.putExtra("password", pwInput);
                    setResult(RESULT_OK, submit_intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "정보를 모두 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
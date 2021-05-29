package pjh.mjc.MarkMyMemory_2017081066;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
//로그인창
public class LoginActivity extends AppCompatActivity {

    //선언
    EditText id, password;
    Button userCreateButton, loginButton;
    DBHelper dbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        setTitle("로그인");
        
        //바인딩
        id = (EditText)findViewById(R.id.id);
        password = (EditText)findViewById(R.id.pw);
        userCreateButton = (Button)findViewById(R.id.user_create);
        loginButton = (Button)findViewById(R.id.login);
        dbHelper = new DBHelper(this);

        //외부 저장소에서 이미지 읽기 권한 요청
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MODE_PRIVATE);
        }

        //로그인 버튼
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idValue = id.getText().toString();
                String pwValue = password.getText().toString();
                db = dbHelper.getReadableDatabase();
                Cursor cursor;

                //아이디 비밀번호 일치하면 로그인되게
                cursor = db.rawQuery("SELECT * FROM user WHERE id = '" + idValue + "';", null);
                if(cursor.moveToNext()) {
                    if((idValue.equals(cursor.getString(0))) && (pwValue.equals(cursor.getString(1)))) {
                        Intent intent = new Intent(LoginActivity.this, MapActivity.class);
                        intent.putExtra("id", idValue);
                        db.close();
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "패스워드가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "일치하는 아이디가 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //회원가입 버튼
        userCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, UserCreateActivity.class);
                startActivityForResult(intent, 0);
            }
        });
    }

    //회원가입 완료 검사
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String idOutput = data.getStringExtra("id");
            String pwOutput = data.getStringExtra("password");
            id.setText(idOutput);
            password.setText(pwOutput);
            Toast.makeText(getApplicationContext(), "회원가입이 성공적으로 완료되었습니다.", Toast.LENGTH_SHORT).show();
        } else if (resultCode == 0){
            Toast.makeText(getApplicationContext(), "회원가입이 실패했습니다.", Toast.LENGTH_SHORT).show();
        }
    }

}
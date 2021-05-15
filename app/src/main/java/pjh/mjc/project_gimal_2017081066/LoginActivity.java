package pjh.mjc.project_gimal_2017081066;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    MyDBHelper myDBHelper;
    EditText id, password;
    Button userCreateButton, loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        //바인딩
        id = (EditText)findViewById(R.id.id);
        password = (EditText)findViewById(R.id.pw);
        userCreateButton = (Button)findViewById(R.id.user_create);
        loginButton = (Button)findViewById(R.id.login);
        //로그인 버튼
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });
        //회원가입 버튼
        userCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, UserCreateActivity.class);
                startActivity(intent);
            }
        });

    }
}
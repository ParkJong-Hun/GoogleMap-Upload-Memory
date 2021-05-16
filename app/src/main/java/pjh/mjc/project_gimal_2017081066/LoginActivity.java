package pjh.mjc.project_gimal_2017081066;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

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
                String idValue = id.getText().toString();
                String pwValue = password.getText().toString();
                if( (idValue.equals("admin")) && (pwValue.equals("1234")) ) {
                    Intent intent = new Intent(LoginActivity.this, MapActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "로그인 정보가 일치하지 않습니다." + id.getText().toString() + password.getText().toString(), Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getApplicationContext(), "회원가입이 실패했습니다." + resultCode, Toast.LENGTH_SHORT).show();
        }
    }

}
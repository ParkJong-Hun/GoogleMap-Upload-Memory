package pjh.mjc.project_gimal_2017081066;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UserCreateActivity extends AppCompatActivity {

    Button cancel, submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_create);
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
                Intent submit_intent = new Intent(UserCreateActivity.this, LoginActivity.class);
                startActivity(submit_intent);
            }
        });
    }
}
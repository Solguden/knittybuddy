package dk.au.mad21spring.group20.knittybuddy.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import dk.au.mad21spring.group20.knittybuddy.MenuActivity;
import dk.au.mad21spring.group20.knittybuddy.PDFActivity;
import dk.au.mad21spring.group20.knittybuddy.R;

public class LoginActivity extends AppCompatActivity {
    Button loginBtn;
    Button registerBtn;
    public static final int REQUEST_CODE_MENU = 444;
    public static final int REQUEST_CODE_REGISTER = 555;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                gotoMenu();
            }
        });


        registerBtn = findViewById(R.id.registerRegisterBtn);
        registerBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                gotoRegister();
            }
        });
    }

    private void gotoMenu() {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivityForResult(intent,REQUEST_CODE_MENU );
    }

    private void gotoRegister() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivityForResult(intent,REQUEST_CODE_REGISTER );
    }
}
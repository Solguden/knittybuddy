package dk.au.mad21spring.group20.knittybuddy.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import dk.au.mad21spring.group20.knittybuddy.Constants;
import dk.au.mad21spring.group20.knittybuddy.MenuActivity;
import dk.au.mad21spring.group20.knittybuddy.PDFActivity;
import dk.au.mad21spring.group20.knittybuddy.R;
import dk.au.mad21spring.group20.knittybuddy.repository.Repository;

public class LoginActivity extends AppCompatActivity {
    Button loginBtn;
    Button registerBtn;
    EditText emailTxt, passwordTxt;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        emailTxt = findViewById(R.id.emailLoginTxt);
        passwordTxt = findViewById(R.id.passwordLoginText);

        loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                login();
//                gotoMenu();
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

    private void login() {
        String email = emailTxt.getText().toString().trim();
        String password = passwordTxt.getText().toString().trim();

        if(email.isEmpty()){
            emailTxt.setError("Email is required");
            emailTxt.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailTxt.setError("Email must be valid");
            emailTxt.requestFocus();
        }

        if(password.isEmpty()){
            passwordTxt.setError("Password is required");
            passwordTxt.requestFocus();
            return;
        }

        if(password.length() < 6){
            passwordTxt.setError("Password must be longer than 6 characters");
            passwordTxt.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    gotoMenu();
                }else{
                    Toast.makeText(LoginActivity.this,"Login failed. Try again.",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void gotoMenu() {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivityForResult(intent, Constants.REQUEST_CODE_MENU );
    }

    private void gotoRegister() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivityForResult(intent,Constants.REQUEST_CODE_REGISTER );
    }
}
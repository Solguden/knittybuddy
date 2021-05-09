package dk.au.mad21spring.group20.knittybuddy.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import dk.au.mad21spring.group20.knittybuddy.R;
import dk.au.mad21spring.group20.knittybuddy.model.User;

//Inspired by: https://www.youtube.com/watch?v=Z-RE1QuUWPg&ab_channel=CodeWithMazn

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    Button registerBtn;
    EditText emailTxt, fullNameTxt, passwordTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        emailTxt = findViewById(R.id.emailRegisterTxt);
        fullNameTxt = findViewById(R.id.fullNameRegisterTxt);
        passwordTxt = findViewById(R.id.passwordRegisterTxt);

        registerBtn = findViewById(R.id.registerRegisterBtn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

    }

    private void registerUser() {
        String email = emailTxt.getText().toString().trim();
        String fullName = fullNameTxt.getText().toString().trim();
        String password = passwordTxt.getText().toString().trim();

        if(fullName.isEmpty()){
            fullNameTxt.setError("Full name is required");
            fullNameTxt.requestFocus();
            return;
        }

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

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(fullName, email);

                            

                        }

                    }
                });
    }
}
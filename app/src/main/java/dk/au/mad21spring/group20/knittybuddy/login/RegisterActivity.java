package dk.au.mad21spring.group20.knittybuddy.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

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

import java.util.ArrayList;
import java.util.List;

import dk.au.mad21spring.group20.knittybuddy.R;
import dk.au.mad21spring.group20.knittybuddy.model.User;
import dk.au.mad21spring.group20.knittybuddy.repository.Repository;

//Inspired by: https://www.youtube.com/watch?v=Z-RE1QuUWPg&ab_channel=CodeWithMazn

public class RegisterActivity extends AppCompatActivity {

    //Widgets and variables
    private FirebaseAuth mAuth;
    Repository repository;
    Button registerBtn;
    EditText emailTxt, fullNameTxt, passwordTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // Initialize repository
        repository = Repository.getRepositoryInstance();

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        //UI setup
        emailTxt = findViewById(R.id.emailRegisterTxt);
        fullNameTxt = findViewById(R.id.fullNameRegisterTxt);
        passwordTxt = findViewById(R.id.passwordRegisterTxt);

        //Button listener setup for click on "register"
        registerBtn = findViewById(R.id.registerRegisterBtn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

    }

    //Method for validating user input and user registration
    private void registerUser() {
        //Strings
        String email = emailTxt.getText().toString().trim();
        String fullName = fullNameTxt.getText().toString().trim();
        String password = passwordTxt.getText().toString().trim();

        //Name validation
        if(fullName.isEmpty()){
            fullNameTxt.setError("Full name is required");
            fullNameTxt.requestFocus();
            return;
        }

        //Email validation
        if(email.isEmpty()){
            emailTxt.setError("Email is required");
            emailTxt.requestFocus();
            return;
        }

        //Email validation
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailTxt.setError("Email must be valid");
            emailTxt.requestFocus();
        }

        //Password validation
        if(password.isEmpty()){
            passwordTxt.setError("Password is required");
            passwordTxt.requestFocus();
            return;
        }

        //Password validation
        if(password.length() < 6){
            passwordTxt.setError("Password must be longer than 6 characters");
            passwordTxt.requestFocus();
            return;
        }

        //Using Firebase Auth to create user with email and password
        mAuth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        //new user object
                        User user = new User(fullName, email, FirebaseAuth.getInstance().getCurrentUser().getUid(), new ArrayList<String>());
                        //Save user in firestore
                        repository.createUser(user).observe(RegisterActivity.this, new Observer<Boolean>() {
                            @Override
                            public void onChanged(Boolean created) {
                                if(created){
                                    //Success toast
                                    Toast.makeText(RegisterActivity.this,"User has successfully been created",Toast.LENGTH_LONG).show();
                                    //Redirect to login
                                    gotoLogin();
                                }
                            }
                        });
                    }
                }
            });
    }

    //Redirect to login activity
    private void gotoLogin()
    {
        startActivity(new Intent(this,LoginActivity.class));
    }
}
package com.example.praneet.todo;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Signup extends AppCompatActivity {

    private TextView signin, incorrect_creds;
    private EditText email, password;
    private Button signup;
    private ProgressBar progressBar;

    private FirebaseAuth firebaseAuth;
    private String error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        firebaseAuth = FirebaseAuth.getInstance();

        signin = findViewById(R.id.login);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        signup = findViewById(R.id.signup);
        progressBar = findViewById(R.id.progress);
        incorrect_creds = findViewById(R.id.incorrect_creds);

        progressBar.setVisibility(View.INVISIBLE);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Signup.this, Login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(password.getWindowToken(), 0);

                signup.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);

                String email_text = email.getText().toString();
                String password_text = password.getText().toString();

                if (password_text.length() <= 7){
                    error = "Password length should be 8 or more";
                    signup.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                    incorrect_creds.setText(error);
                }
                else {
                    firebaseAuth.createUserWithEmailAndPassword(email_text, password_text)
                            .addOnCompleteListener(Signup.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(Signup.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        error = "Failed to create account";
                                        signup.setVisibility(View.VISIBLE);
                                        progressBar.setVisibility(View.INVISIBLE);
                                        incorrect_creds.setText(error);
                                    }
                                }
                            });
                }
            }
        });


    }
}

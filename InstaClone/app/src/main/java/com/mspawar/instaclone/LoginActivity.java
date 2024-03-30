package com.mspawar.instaclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.mspawar.instaclone.Models.User;
import com.mspawar.instaclone.databinding.ActivityLoginBinding;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        binding =ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Button signup=findViewById(R.id.signupButton);
        Button loginButton=findViewById(R.id.loginButton);

        final String regex = "^[a-zA-Z0-9_.]+@[a-zA-Z0-9.-]+$";
        Pattern pattern = Pattern.compile(regex);


        loginButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String email=binding.emailInput.getEditText().getText().toString();
                        String password=binding.passwordInput.getEditText().getText().toString();
                        Matcher matcher = pattern.matcher(email);
                        if(email.equals("") ||password.equals(""))
                        {
                            Toast.makeText(LoginActivity.this, "fill all the info needed", Toast.LENGTH_SHORT).show();
                        }
                        else if(password.length()<6)
                        {
                            Toast.makeText(LoginActivity.this, "Password Length should be greater than equal to 6", Toast.LENGTH_SHORT).show();
                        }
                        else if(!matcher.matches())
                        {
                            Toast.makeText(LoginActivity.this, "please fill a correct email", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            User user =new User(email,password);
                            FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password).addOnCompleteListener(
                                    new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if(task.isSuccessful())
                                            {
                                                startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                                                finish();
                                            }
                                            else
                                            {
                                                Toast.makeText(LoginActivity.this, "wrong credentials"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    }
                            );
                        }

                    }
                }
        );
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
                finish();
            }
        });

    }
}
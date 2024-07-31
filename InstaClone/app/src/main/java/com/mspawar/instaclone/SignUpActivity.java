package com.mspawar.instaclone;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mspawar.instaclone.Models.User;
import com.mspawar.instaclone.databinding.ActivitySignUpBinding;
import com.mspawar.instaclone.utils.CallBack;
import com.mspawar.instaclone.utils.Utils;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity<SignInCredential> extends AppCompatActivity implements CallBack {
    private Button register;
    private final String TAG="SignUpActivity";
    private FirebaseAuth mAuth;
    private ActivitySignUpBinding binding;
    private final  String USER_PROFILE_FOLDER=new Utils().getUSER_PROFILE_FOLDER();

    private Uri imageUri;
    private User user;

    private ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult img) {
            if(img.getResultCode()==RESULT_OK)
            {
                imageUri=img.getData().getData();
                Glide.with(getApplicationContext()).load(imageUri).into(binding.profileImage);
            }
            else {
                Toast.makeText(SignUpActivity.this, "eror in uploading", Toast.LENGTH_SHORT).show();
            }
        }
    });



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        mAuth = FirebaseAuth.getInstance();




        binding.plusImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                launcher.launch(intent);
            }
        });
        final String regex = "^[a-zA-Z0-9_.]+@[a-zA-Z0-9.-]+$";
        Pattern pattern = Pattern.compile(regex);
//        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//        final String uid=user.getUid() ;
        binding.registerButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name=binding.nameInput.getEditText().getText().toString();
                        String email=binding.emailInput.getEditText().getText().toString();
                        String password=binding.passwordInput.getEditText().getText().toString();
                        Matcher matcher = pattern.matcher(email);
                        if(name.equals("") ||email.equals("") ||password.equals(""))
                        {
                            Toast.makeText(SignUpActivity.this, "fill all the info needed", Toast.LENGTH_SHORT).show();
                        }
                        else if(password.length()<6)
                        {
                            Toast.makeText(SignUpActivity.this, "Password Length should be greater than equal to 6", Toast.LENGTH_SHORT).show();
                        }
                        else if(!matcher.matches())
                        {
                            Toast.makeText(SignUpActivity.this, "please fill a correct email", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            mAuth.createUserWithEmailAndPassword(email
                                    ,password).addOnCompleteListener(
                                    task->
                                    {
                                        if(task.isSuccessful())
                                        {
                                            Utils ob=new Utils();
                                            ob.setCallBack(SignUpActivity.this);
                                            user=new User(name,email,password);
                                            user.setProfileLink(mAuth.getCurrentUser().getUid());


                                            if(imageUri!=null) {
                                                ob.uploadImage(imageUri, USER_PROFILE_FOLDER );
                                            }
                                            else
                                            {
                                                storeDB();
                                            }
//

//                  Toast.makeText(SignUpActivity.this, "error not", Toast.LENGTH_SHORT).show();
                                        }
                                        else {
//                                            Toast.makeText(SignUpActivity.this, "error", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                            );
                        }
                    }
                }
        );

        binding.loginButtonInput.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
                        finish();
                    }
                }
        );



    }
//    private String uploadImage(Uri imageUri)
//    {
//
//    }

    public void storeDB() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userNode = new Utils().getUSER_NODE();
//       User user = new User(image, name, email, password);
        db.collection(userNode).document(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                .set(user).addOnSuccessListener(
                        new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                startActivity(new Intent(SignUpActivity.this,HomeActivity.class));
                                finish();
                            }
                        }
                );
    }


    @Override
    public void setIt(String it) {
       user.setImage(it);
       storeDB();
    }
}
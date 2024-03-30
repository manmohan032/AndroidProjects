package com.mspawar.instaclone;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mspawar.instaclone.Models.User;
import com.mspawar.instaclone.databinding.ActivityEditProfileBinding;
import com.mspawar.instaclone.utils.CallBack;
import com.mspawar.instaclone.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditProfileActivity extends AppCompatActivity implements CallBack {
    private ActivityEditProfileBinding binding;
    private  User user;
    private final  String USER_PROFILE_FOLDER=new Utils().getUSER_PROFILE_FOLDER();

    private Uri imageUri;
    private ConstraintLayout constraintLayout;

    private ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult img) {
            if(img.getResultCode()==RESULT_OK)
            {
                imageUri=img.getData().getData();
                Glide.with(getApplicationContext()).load(imageUri).into(binding.profileImage);
            }
            else {
                Toast.makeText(EditProfileActivity.this, "eror in uploading", Toast.LENGTH_SHORT).show();
            }
        }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FirebaseFirestore.getInstance().collection(new Utils().getUSER_NODE())
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        user = documentSnapshot.toObject(User.class);
                        binding.nameInput.getEditText().setText(user.getName());
                        binding.bioInput.getEditText().setText(user.getBio());
                        binding.emailInput.getEditText().setText(user.getEmail());
                        if(user.getImage()!=null)
                            Picasso.get().load(user.getImage()).into(binding.profileImage);

                    }
                });


        binding.plusImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                launcher.launch(intent);
            }
        });

        binding.updateProfileButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name=binding.nameInput.getEditText().getText().toString();
                        String email=binding.emailInput.getEditText().getText().toString();
                        String bio=binding.bioInput.getEditText().getText().toString();
                        final String regex = "^[a-zA-Z0-9_.]+@[a-zA-Z0-9.-]+$";
                        Pattern pattern = Pattern.compile(regex);
                        Matcher matcher = pattern.matcher(email);
                        if(name.equals("") ||email.equals("") )                        {
                            Toast.makeText(EditProfileActivity.this, "fill all the info needed", Toast.LENGTH_SHORT).show();
                        }
                        else if(!matcher.matches())
                        {
                            Toast.makeText(EditProfileActivity.this, "please fill a correct email", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            user.setName(name);
                            user.setBio(bio);
                            Utils ob=new Utils();
                            ob.setCallBack(EditProfileActivity.this);
                            if(imageUri!=null) {
                                ob.uploadImage(imageUri, USER_PROFILE_FOLDER );
                            }
                            else
                            {
                                storeDB();
                            }
                        }
                    }
                }
        );
        binding.loginButtonInput.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(EditProfileActivity.this,LoginActivity.class));
                        finish();
                    }
                }
        );
    }
    public void storeDB() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userNode = new Utils().getUSER_NODE();
//       User user = new User(image, name, email, password);
        db.collection(userNode).document(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                .set(user).addOnSuccessListener(
                        new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                startActivity(new Intent(EditProfileActivity.this,HomeActivity.class));
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
    private void addButton() {
        Button newButton = new Button(this);
        newButton.setId(View.generateViewId());
//        newButton.setText("Button " + (buttonCount + 1));
//        buttonCount++;

        constraintLayout.addView(newButton);

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);

        // Set constraints for the new button
        constraintSet.connect(newButton.getId(), ConstraintSet.TOP, R.id.plus_image, ConstraintSet.BOTTOM, 16);
        constraintSet.connect(newButton.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 0);
        constraintSet.connect(newButton.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 0);

        constraintSet.applyTo(constraintLayout);
    }
}
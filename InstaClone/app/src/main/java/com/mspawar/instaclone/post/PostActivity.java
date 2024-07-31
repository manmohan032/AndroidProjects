package com.mspawar.instaclone.post;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mspawar.instaclone.HomeActivity;
import com.mspawar.instaclone.Models.Post;
import com.mspawar.instaclone.databinding.ActivityPostBinding;
import com.mspawar.instaclone.utils.CallBack;
import com.mspawar.instaclone.utils.Utils;

import java.util.Objects;

public class PostActivity extends AppCompatActivity implements CallBack {
    private ActivityPostBinding binding;
    private Toolbar toolbar;
    private Post post;
    private String post_folder=new Utils().getPOST_FOLDER();
    private Uri imageUri;
    private final String mAuthId=Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
    private ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult img) {
            if(img.getResultCode()==RESULT_OK)
            {
                imageUri=img.getData().getData();
                Glide.with(getApplicationContext()).load(imageUri).into(binding.selectImage);
            }
            else {
                Toast.makeText(PostActivity.this, "eror in uploading", Toast.LENGTH_SHORT).show();
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityPostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toolbar = binding.materialToolbar;
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

        binding.selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                launcher.launch(intent);
            }
        });

        binding.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PostActivity.this, HomeActivity.class));
                finish();
            }
        });
        binding.postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title=binding.captionInput.getEditText().getText().toString();
                post=new Post();
                post.setPostTitle(title);
                post.setProfileLink(mAuthId);
                post.setTime(String.valueOf(System.currentTimeMillis()));
                Utils ob=new Utils();
                ob.setCallBack(PostActivity.this);
                if(imageUri!=null) {
                    ob.uploadImage(imageUri, post_folder );
                }
                else
                {
                    Toast.makeText(PostActivity.this, "no image ", Toast.LENGTH_SHORT).show();
//                    storeDB();
                }
            }
        });
   }

    public void storeDB() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String postNode = new Utils().getPOST_NODE();

        db.collection(postNode).document()
                .set(post).addOnSuccessListener(
                        new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
//                    Toast.makeText(PostActivity.this, "stored image ", Toast.LENGTH_SHORT).show();
//                                startActivity(new Intent(PostActivity.this,HomeActivity.class));
//                                finish();
                            }
                        }
                );
        db.collection(mAuthId).document()
                .set(post).addOnSuccessListener(
                        new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(PostActivity.this, "stored image ", Toast.LENGTH_SHORT).show();
//                                startActivity(new Intent(PostActivity.this,HomeActivity.class));
                                finish();
                            }
                        }
                );
    }
    @Override
    public void setIt(String it) {
        post.setPostUrl(it);
        storeDB();
    }
}
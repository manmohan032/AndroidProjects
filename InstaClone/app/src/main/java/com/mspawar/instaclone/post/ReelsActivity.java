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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mspawar.instaclone.HomeActivity;
import com.mspawar.instaclone.Models.Reel;
import com.mspawar.instaclone.databinding.ActivityReelsBinding;
import com.mspawar.instaclone.utils.CallBack;
import com.mspawar.instaclone.utils.Utils;

import java.util.Objects;

public class ReelsActivity extends AppCompatActivity implements CallBack {

    private ActivityReelsBinding binding;
    private Toolbar toolbar;
    private Reel reel;
    private Uri reelUri;
    private final String reel_folder= Utils.REEL_FOLDER;
    private final String mAuthId=Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
    private ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult img) {
            if(img.getResultCode()==RESULT_OK)
            {
                reelUri=img.getData().getData();
//                Glide.with(getApplicationContext()).load(imageUri).into(binding.sel);
            }
            else {
                Toast.makeText(ReelsActivity.this, "eror in uploading", Toast.LENGTH_SHORT).show();
            }
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding=ActivityReelsBinding.inflate(getLayoutInflater());
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
        binding.selectReel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("video/*");
                launcher.launch(intent);
            }
        });

        binding.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(PostActivity.this, HomeActivity.class));
                finish();
            }
        });
        binding.postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title=binding.captionInput.getEditText().getText().toString();
                reel=new Reel();
                reel.setPostTitle(title);
                reel.setProfileLink(mAuthId);
                Utils ob=new Utils();
                ob.setCallBack(ReelsActivity.this);
                if(reelUri!=null) {
                    ob.uploadVideo(reelUri,ReelsActivity.this, reel_folder );
                }
                else
                {
                    Toast.makeText(ReelsActivity.this, "no image ", Toast.LENGTH_SHORT).show();
//                    storeDB();
                }
            }
        });

    }
    public void storeDB() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String reelNode = Utils.REEL_NODE;

        db.collection(reelNode).document()
                .set(reel).addOnSuccessListener(
                        new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
//                    Toast.makeText(PostActivity.this, "stored image ", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ReelsActivity.this,HomeActivity.class));
                                finish();
                            }
                        }
                );
        db.collection(mAuthId+reelNode).document()
                .set(reel).addOnSuccessListener(
                        new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
//                                Toast.makeText(PostActivity.this, "stored image ", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ReelsActivity.this,HomeActivity.class));
                                finish();
                            }
                        }
                );
    }

    @Override
    public void setIt(String it) {
        reel.setPostUrl(it);
        storeDB();
    }
}
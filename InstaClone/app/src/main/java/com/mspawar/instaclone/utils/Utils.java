package com.mspawar.instaclone.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class Utils {

    private final String USER_NODE="users";
    private final String POST_NODE="posts";
    public static final String REEL_NODE="reels";
    private final String POST_FOLDER="postImages";
    public static final String REEL_FOLDER="reelVideo";
    public static final String  FOLLOW="follow";
    private final String USER_PROFILE_FOLDER="userProfilePhoto";

    public String getPOST_FOLDER() {
        return POST_FOLDER;
    }

    //    private final String USER_NODE="users";
    private String imageUrl=null;

    public String getPOST_NODE() {
        return POST_NODE;
    }

    public static final String Tag="Utils";
    CallBack callBack;

    public void setCallBack(CallBack callBack)
    {
        this.callBack=callBack;
    }
    public void uploadImage(Uri uri, String folderName)
    {
        FirebaseStorage storage=FirebaseStorage.getInstance();
        StorageReference storageReference=storage.getReference(folderName);
        StorageReference imageReference=storageReference.child(String.valueOf(UUID.randomUUID()));
                imageReference.putFile(uri).
        addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {


                            callBack.setIt(uri.toString());
                        }
                    }
                );
            }
        });
    }
    public void uploadVideo(Uri uri, Context context, String folderName)
    {
        ProgressDialog pd=new ProgressDialog(context);
        pd.setTitle("Uploading");
        pd.show();
        FirebaseStorage storage=FirebaseStorage.getInstance();
        StorageReference storageReference=storage.getReference(folderName);
        StorageReference imageReference=storageReference.child(String.valueOf(UUID.randomUUID()));
        imageReference.putFile(uri).
                addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        imageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                                 @Override
                                                                                 public void onSuccess(Uri uri) {

                                                                                     pd.dismiss();
                                                                                     callBack.setIt(uri.toString());
                                                                                 }
                                                                             }
                        );
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        float pr= (float) (snapshot.getBytesTransferred() /snapshot.getTotalByteCount())*100;
                        pd.setMessage("Uploaded ... "+pr+"% ");
                    }
                });
    }
    public  String getUSER_NODE()
    {
        return USER_NODE;
    }
    public String getUSER_PROFILE_FOLDER()
    {
        return USER_PROFILE_FOLDER;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl()
    {
        return imageUrl;
    }
}

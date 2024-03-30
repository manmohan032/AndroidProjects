package com.mspawar.instaclone.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mspawar.instaclone.EditProfileActivity;
import com.mspawar.instaclone.Models.User;
import com.mspawar.instaclone.R;
import com.mspawar.instaclone.SignUpActivity;
import com.mspawar.instaclone.StartActivity;
import com.mspawar.instaclone.adapters.ViewPageAdapter;
import com.mspawar.instaclone.databinding.FragmentProfileBinding;
import com.mspawar.instaclone.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {


    private FragmentProfileBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding=FragmentProfileBinding.inflate(inflater, container, false);

        binding.editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), EditProfileActivity.class);
                intent.putExtra("MODE",1);
                startActivity(intent);
            }
        });

        ViewPager viewPager = binding.viewPager;

        // Pass the desired behavior to the adapter
        ViewPageAdapter adapter = new ViewPageAdapter(this.getActivity().getSupportFragmentManager());
        adapter.addFragment(new my_post(),"Posts");
        adapter.addFragment(new my_reels(),"Reels");
        binding.viewPager.setAdapter(adapter);

        binding.tabLayout.setupWithViewPager(viewPager);

        return binding.getRoot();

    }
    public static final String TAG="ProfileFragment";
    private User user;
    @SuppressLint("SetTextI18n")
    @Override
    public void onStart() {

        super.onStart();



        FirebaseFirestore.getInstance().collection(new Utils().getUSER_NODE())
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        user = documentSnapshot.toObject(User.class);
                        binding.name.setText(user.getName());
                        binding.bio.setText(user.getBio());
                        if(user.getImage()!=null)
                            Picasso.get().load(user.getImage()).into(binding.profileImage);

                    }
                });
    }
}
package com.mspawar.instaclone.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.mspawar.instaclone.LoginActivity;
import com.mspawar.instaclone.R;
import com.mspawar.instaclone.SignUpActivity;
import com.mspawar.instaclone.databinding.FragmentAddBinding;
import com.mspawar.instaclone.post.PostActivity;
import com.mspawar.instaclone.post.ReelsActivity;

public class AddFragment extends BottomSheetDialogFragment {
    private FragmentAddBinding binding;

    public AddFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentAddBinding.inflate(inflater,container,false);
        binding.post.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                startActivity(new Intent(getContext(), PostActivity.class));
                                                    getDialog().cancel();
                                            }
                                        });
        binding.reel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(getContext(), ReelsActivity.class));

                                                    getDialog().cancel();

            }
        });

        return binding.getRoot();
    }
}
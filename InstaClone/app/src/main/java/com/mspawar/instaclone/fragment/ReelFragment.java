package com.mspawar.instaclone.fragment;

import android.icu.lang.UCharacter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.mspawar.instaclone.Models.Reel;
import androidx.fragment.app.Fragment;

import com.mspawar.instaclone.adapters.ReelAdapter;
import com.mspawar.instaclone.databinding.FragmentReelBinding;
import com.mspawar.instaclone.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class ReelFragment extends Fragment {

    private ArrayList<Reel> reelList=new ArrayList<>();
    private ReelAdapter reelAdapter;
    public ReelFragment() {
        // Required empty public constructor
    }


    private FragmentReelBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentReelBinding.inflate(inflater,container,false);
        reelAdapter=new ReelAdapter(getContext(),reelList);
        binding.viewPager.setAdapter(reelAdapter);
        getData();
        return binding.getRoot();
    }
    private void getData()
    {
        FirebaseFirestore.getInstance().collection( Utils.REEL_NODE).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                ArrayList<Reel> tempList =new ArrayList<>();
                List<DocumentSnapshot> dbList=queryDocumentSnapshots.getDocuments();
                for (int i = 0; i < dbList.size(); i++) {
                    Reel ob=dbList.get(i).toObject(Reel.class);
                    tempList.add(ob);
                }
                reelList.addAll(tempList);
                reelAdapter.notifyDataSetChanged();
            }
        });
    }
}
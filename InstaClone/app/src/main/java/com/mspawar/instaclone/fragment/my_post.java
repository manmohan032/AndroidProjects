package com.mspawar.instaclone.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.mspawar.instaclone.Models.Post;
import com.mspawar.instaclone.adapters.MyPostRvAdapter;
import com.mspawar.instaclone.databinding.FragmentMyPostBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class my_post extends Fragment {

    private FragmentMyPostBinding binding;
    private ArrayList<Post> postList;
    private MyPostRvAdapter adapter;
    public my_post() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
//        binding =FragmentMyPostBinding.inflate()
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMyPostBinding.inflate(inflater,container,false);

        postList=new ArrayList<Post>();
        adapter=new MyPostRvAdapter(getContext(),postList);
        StaggeredGridLayoutManager staggeredGridLayoutManager=new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        binding.rv.setLayoutManager(staggeredGridLayoutManager);
        binding.rv.setAdapter(adapter);
        getData();
        return binding.getRoot();
    }

    private void getData()
    {
        FirebaseFirestore.getInstance().collection(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                ArrayList<Post> tempList =new ArrayList<>();
                List<DocumentSnapshot> dbList=queryDocumentSnapshots.getDocuments();
                for (int i = 0; i < dbList.size(); i++) {
                    Post ob=dbList.get(i).toObject(Post.class);
                    tempList.add(ob);
                }
                postList.addAll(tempList);
                adapter.notifyDataSetChanged();
            }
        });
    }
}
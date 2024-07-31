package com.mspawar.instaclone.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.mspawar.instaclone.Models.Post;
import com.mspawar.instaclone.Models.User;
import com.mspawar.instaclone.R;
import com.mspawar.instaclone.adapters.HomePostRvAdapter;
import com.mspawar.instaclone.adapters.StoryFollowAdapter;
import com.mspawar.instaclone.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    ArrayList<Post> postList=new ArrayList<Post>();
    ArrayList<User> followList=new ArrayList<User>();
    private HomePostRvAdapter adapter;
    private StoryFollowAdapter adapterFollow;
    private FragmentHomeBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentHomeBinding.inflate(inflater,container,false);
        setHasOptionsMenu(true);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(binding.materialToolbar);


        adapter=new HomePostRvAdapter(requireContext(),postList);
        binding.rvPost.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvPost.setAdapter(adapter);
        getData();

        adapterFollow=new StoryFollowAdapter(requireContext(),followList);
        binding.rvStoryFollow.setLayoutManager(
                new LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false));
        binding.rvStoryFollow.setAdapter(adapterFollow);
        getFollowData();

        return  binding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_home,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    private void getData()
    {
        FirebaseFirestore.getInstance().collection("posts").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                ArrayList<Post> tempList =new ArrayList<Post>();
                List<DocumentSnapshot> dbList=queryDocumentSnapshots.getDocuments();
                for (int i = 0; i < dbList.size(); i++) {
                    Post ob=dbList.get(i).toObject(Post.class);
                    tempList.add(ob);
                }
                postList.clear();
                postList.addAll(tempList);
                adapter.notifyDataSetChanged();
            }
        });
    }
    private void getFollowData()
    {
        FirebaseFirestore.getInstance().collection(FirebaseAuth.getInstance().getCurrentUser().getUid()+"follow").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                ArrayList<User> tempList =new ArrayList<User>();
                List<DocumentSnapshot> dbList=queryDocumentSnapshots.getDocuments();
                for (int i = 0; i < dbList.size(); i++) {
                    User ob=dbList.get(i).toObject(User.class);
                    tempList.add(ob);
                }
                followList.clear();
                followList.addAll(tempList);
                adapterFollow.notifyDataSetChanged();
            }
        });
    }
}
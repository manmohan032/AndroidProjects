package com.mspawar.instaclone.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.mspawar.instaclone.Models.User;
import com.mspawar.instaclone.adapters.SearchAdapeter;
import com.mspawar.instaclone.databinding.FragmentSearchBinding;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

   FragmentSearchBinding binding;
   private SearchAdapeter adapter;
   Context context;
   ArrayList<User> userList=new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentSearchBinding.inflate(inflater, container, false);
        adapter=new SearchAdapeter(requireContext(),userList);
        binding.rv.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rv.setAdapter(adapter);
        getData();
        binding.searchButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String text =binding.editTextText.getText().toString();
                        getData(text);
                    }
                }
        );
        return binding.getRoot();
    }
    private void getData()
    {
        FirebaseFirestore.getInstance().collection("users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                ArrayList<User> tempList =new ArrayList<User>();
                List<DocumentSnapshot> dbList=queryDocumentSnapshots.getDocuments();
                for (int i = 0; i < dbList.size(); i++) {
                    DocumentSnapshot documentSnapshot=dbList.get(i);
                    if(!documentSnapshot.getId().toString().equals(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()))
                    {
                        User ob=documentSnapshot.toObject(User.class);
                        tempList.add(ob);
                    }
                }
                userList.clear();
                userList.addAll(tempList);
                adapter.notifyDataSetChanged();
            }
        });
    } private void getData(String text)
    {
        FirebaseFirestore.getInstance().collection("users").whereEqualTo("name",text).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(queryDocumentSnapshots.isEmpty())
                {
                    binding.editTextText.setText("no name");
                }
                else {
                    ArrayList<User> tempList = new ArrayList<User>();
                    List<DocumentSnapshot> dbList = queryDocumentSnapshots.getDocuments();
                    for (int i = 0; i < dbList.size(); i++) {
                        DocumentSnapshot documentSnapshot = dbList.get(i);
                        if (!documentSnapshot.getId().toString().equals(FirebaseAuth.getInstance().getCurrentUser().getUid().toString())) {
                            User ob = documentSnapshot.toObject(User.class);
                            tempList.add(ob);
                        }
                    }
                    userList.clear();
                    userList.addAll(tempList);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }
}
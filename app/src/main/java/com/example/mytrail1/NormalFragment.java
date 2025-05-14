package com.example.mytrail1;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class NormalFragment extends Fragment {

    public NormalFragment() {
        // Required empty public constructor
    }


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_normal, container, false);

        // Set up click listeners for category1 and category2
        view.findViewById(R.id.textView).setOnClickListener(v -> changeFragment(new CategoryFragment()));

        return view;
    }

    // Method to change fragment within the CategoryFragment
    private void changeFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerView, fragment);
        fragmentTransaction.addToBackStack(null); // Optional: add to backstack to allow navigation back
        fragmentTransaction.commit();
    }
}
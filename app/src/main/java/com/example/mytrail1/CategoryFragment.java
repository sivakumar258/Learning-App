package com.example.mytrail1;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CategoryFragment extends Fragment {

    public CategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        // Set up click listeners for category1 and category2
        view.findViewById(R.id.category1).setOnClickListener(v -> changeFragment(new NormalFragment()));
        view.findViewById(R.id.category2).setOnClickListener(v -> changeFragment(new SignedFragment()));

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

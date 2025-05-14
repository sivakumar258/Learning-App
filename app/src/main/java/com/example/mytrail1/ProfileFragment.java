package com.example.mytrail1;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ProfileFragment extends Fragment {
    TextView logout, emailTextView, nameTextView;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Initialize views
        logout = view.findViewById(R.id.button4); // Logout button
        emailTextView = view.findViewById(R.id.pemail); // TextView for email
        nameTextView = view.findViewById(R.id.pname);   // TextView for name

        // Retrieve data from arguments
        Bundle bundle = getArguments();
        if (bundle != null) {
            String emailid = bundle.getString("emailid");
            String name = bundle.getString("name");

            // Display email and name
            emailTextView.setText(emailid);
            nameTextView.setText(name);
        }

        // Set OnClickListener on logout TextView
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start MainActivity
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);

                // Optional: finish the current activity to prevent returning to this activity
                requireActivity().finish();
            }
        });

        return view;
    }
}

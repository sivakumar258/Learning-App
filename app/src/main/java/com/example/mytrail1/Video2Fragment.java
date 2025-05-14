package com.example.mytrail1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import com.google.firebase.auth.FirebaseAuth;

public class Video2Fragment extends Fragment {

    private VideoView videoView;
    private Button startQuizButton;
    private FirebaseAuth firebaseAuth;

    public Video2Fragment() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_video2, container, false);

        // Initialize FirebaseAuth instance
        firebaseAuth = FirebaseAuth.getInstance();

        // Initialize the VideoView and set up the media controller
        videoView = rootView.findViewById(R.id.video);
        MediaController mediaController = new MediaController(getContext());
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        // Set the video URI and start playing
        videoView.setVideoURI(Uri.parse("android.resource://" + getContext().getPackageName() + "/" + R.raw.video2));
        videoView.start();

        // Initialize the Button for starting the quiz
        startQuizButton = rootView.findViewById(R.id.startQuiz);

        // Set the click listener for the button
        startQuizButton.setOnClickListener(view -> {
            // Redirect to the quiz page activity
            Intent intent = new Intent(getActivity(), QuizPage.class);
            startActivity(intent);
        });

        return rootView;
    }
}

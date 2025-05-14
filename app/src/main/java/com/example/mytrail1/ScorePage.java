package com.example.mytrail1;

// Importing the required libraries
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser ;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.mytrail1.databinding.ActivityScorePageBinding;

// Creating a class for the Score Page Activity
public class ScorePage extends AppCompatActivity {

    // Creating a variable for the ActivityScorePageBinding
    ActivityScorePageBinding binding;

    // Creating a variable for the text view, button
    TextView textViewCorrect, textViewWrong;
    Button buttonExit;

    // Creating a variable for the FirebaseDatabase and DatabaseReference
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference("scores");

    // Creating a variable for the FirebaseAuth and FirebaseUser
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser  user = auth.getCurrentUser ();

    // Creating a variable for the user correct and wrong answers
    String userCorrect;
    String userWrong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityScorePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Getting the reference of the text view, button
        buttonExit = binding.buttonExit;
        textViewCorrect = binding.txtCorrectAnswer;
        textViewWrong = binding.txtWrong;

        // Check if user is logged in
        if (user != null) {
            // Setting the Scores of the user in the text view from the database
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String userUID = user.getUid();

                    // Getting the user correct and wrong answers from the database
                    userCorrect = String.valueOf(snapshot.child(userUID).child("correct").getValue());
                    userWrong = String.valueOf(snapshot.child(userUID).child("wrong").getValue());

                    // Setting the user correct and wrong answers in the text view
                    textViewCorrect.setText(userCorrect);
                    textViewWrong.setText(userWrong);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle possible errors
                    // You can log the error or show a message to the user
                }
            });
        } else {
            // Handle the case where the user is not logged in
            textViewCorrect.setText("User  not logged in");
            textViewWrong.setText("User  not logged in");
        }

        // Setting the click listener for the button
        // If the user clicks on the button, then the user will exit the app
        buttonExit.setOnClickListener(view -> {
            Intent i = new Intent(ScorePage.this, MainPage.class);
            startActivity(i);
            finish();
        });
    }
}
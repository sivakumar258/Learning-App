package com.example.mytrail1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mytrail1.databinding.ActivityQuizPageBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class QuizPage extends AppCompatActivity {

    ActivityQuizPageBinding binding;
    TextView time, correct, wrong;
    Button question, a, b, c, d;
    Button next, finish;

    String quizQuestion, quizAnswerA, quizAnswerB, quizAnswerC, quizAnswerD, quizCorrectAnswer;
    int questionNumber = 1, userCorrect = 0, userWrong = 0;
    String userAnswer;
    boolean isQuizFinished = false;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference().child("Question");
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();
    DatabaseReference databaseReferenceSecond = database.getReference();

    CountDownTimer countDownTimer;
    public static final long TOTAL_TIME = 25000;
    boolean timerContinue;
    long timeLeft = TOTAL_TIME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuizPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        time = binding.txtTime;
        correct = binding.txtCorrect;
        wrong = binding.txtWrong;
        next = binding.buttonNextQuestion;
        finish = binding.buttonFinishGame;
        question = binding.textViewQuestion;
        a = binding.textViewA;
        b = binding.textViewB;
        c = binding.textViewC;
        d = binding.textViewD;

        game();

        next.setOnClickListener(view -> {
            resetTimer();
            game();
        });

        finish.setOnClickListener(view -> {
            sendScore();
            Intent i = new Intent(QuizPage.this, ScorePage.class);
            i.putExtra("correct", userCorrect);
            i.putExtra("wrong", userWrong);
            startActivity(i);
            finish();
        });

        setOptionClickListener();
    }

    private void setOptionClickListener() {
        a.setOnClickListener(view -> handleAnswer("a", a));
        b.setOnClickListener(view -> handleAnswer("b", b));
        c.setOnClickListener(view -> handleAnswer("c", c));
        d.setOnClickListener(view -> handleAnswer("d", d));
    }

    private void handleAnswer(String selectedAnswer, TextView option) {
        if (!timerContinue) return; // Prevent clicking after timer ends

        disableOptions();
        checkAnswer(selectedAnswer, option);
    }

    private void disableOptions() {
        a.setClickable(false);
        b.setClickable(false);
        c.setClickable(false);
        d.setClickable(false);
    }

    private void enableOptions() {
        a.setClickable(true);
        b.setClickable(true);
        c.setClickable(true);
        d.setClickable(true);
    }

    private void checkAnswer(String selectedAnswer, TextView option) {
        pauseTimer();
        userAnswer = selectedAnswer;

        if (quizCorrectAnswer.equals(userAnswer)) {
            option.setBackground(ContextCompat.getDrawable(this, R.drawable.gradiant4)); // Correct answer style
            userCorrect++;
            correct.setText(String.valueOf(userCorrect));
        } else {
            option.setBackground(ContextCompat.getDrawable(this, R.drawable.gradiant5)); // Incorrect answer style
            userWrong++;
            wrong.setText(String.valueOf(userWrong));
            findAnswer(); // Highlight correct answer
        }
    }


    private void game() {
        if (isQuizFinished) {
            Toast.makeText(QuizPage.this, "Quiz already finished!", Toast.LENGTH_SHORT).show();
            return;
        }

        enableOptions();
        resetTimer();
        startTimer();

        a.setBackground(ContextCompat.getDrawable(this, R.drawable.gradiant5));
        b.setBackground(ContextCompat.getDrawable(this, R.drawable.gradiant5));
        c.setBackground(ContextCompat.getDrawable(this, R.drawable.gradiant5));
        d.setBackground(ContextCompat.getDrawable(this, R.drawable.gradiant5));

        databaseReference.child(String.valueOf(questionNumber)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    isQuizFinished = true;
                    Toast.makeText(QuizPage.this, "You answered all questions", Toast.LENGTH_SHORT).show();
                    finish.setEnabled(true);
                    return;
                }

                // Fetch and trim the data
                quizQuestion = dataSnapshot.child("q").getValue(String.class).trim();
                quizAnswerA = dataSnapshot.child("a").getValue(String.class).trim();
                quizAnswerB = dataSnapshot.child("b").getValue(String.class).trim();
                quizAnswerC = dataSnapshot.child("c").getValue(String.class).trim();
                quizAnswerD = dataSnapshot.child("d").getValue(String.class).trim();
                quizCorrectAnswer = dataSnapshot.child("answer").getValue(String.class).trim();

                // Populate UI elements
                question.setText(quizQuestion);
                a.setText(quizAnswerA);
                b.setText(quizAnswerB);
                c.setText(quizAnswerC);
                d.setText(quizAnswerD);

                questionNumber++;
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(QuizPage.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void findAnswer() {
        if ("a".equals(quizCorrectAnswer)) {
            a.setBackground(ContextCompat.getDrawable(this, R.drawable.gradiant4));
        } else if ("b".equals(quizCorrectAnswer)) {
            b.setBackground(ContextCompat.getDrawable(this, R.drawable.gradiant4));
        } else if ("c".equals(quizCorrectAnswer)) {
            c.setBackground(ContextCompat.getDrawable(this, R.drawable.gradiant4));
        } else if ("d".equals(quizCorrectAnswer)) {
            d.setBackground(ContextCompat.getDrawable(this, R.drawable.gradiant4));
        }
    }


    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long l) {
                timeLeft = l;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timerContinue = false;
                pauseTimer();
                question.setText("Sorry, time is up!");
                userWrong++;
                wrong.setText(String.valueOf(userWrong));
                findAnswer();

                new android.os.Handler().postDelayed(() -> game(), 2000);
            }
        }.start();
        timerContinue = true;
    }

    private void pauseTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        timerContinue = false;
    }

    private void resetTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        timeLeft = TOTAL_TIME;
        updateCountDownText();
    }

    private void updateCountDownText() {
        int second = (int) (timeLeft / 1000) % 60;
        time.setText(String.valueOf(second));
    }

    private void sendScore() {
        if (user != null) {
            String userUID = user.getUid();
            databaseReferenceSecond.child("scores").child(userUID).child("correct")
                    .setValue(userCorrect).addOnSuccessListener(unused ->
                            Toast.makeText(QuizPage.this, "Score sent successfully", Toast.LENGTH_SHORT).show());

            databaseReferenceSecond.child("scores").child(userUID).child("wrong").setValue(userWrong);
        }
    }
}

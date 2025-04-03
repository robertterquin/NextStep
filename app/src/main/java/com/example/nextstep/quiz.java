package com.example.nextstep;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.ArrayList;
import java.util.List;

public class quiz extends AppCompatActivity {

    private List<Questions> questionList;
    private int currentQuestionIndex = 0;

    private TextView tvQuestion, tvQuestionNumber;
    private ProgressBar progressBar;
    private View questionCard;
    private GestureDetector gestureDetector;
    private UserResponses userResponses;
    private float dX = 0f;
    private static final int SWIPE_THRESHOLD = 300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quiz);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvQuestion = findViewById(R.id.tvQuestion);
        tvQuestionNumber = findViewById(R.id.tvQuestionNumber);
        progressBar = findViewById(R.id.progressBar);
        questionCard = findViewById(R.id.questionCard);

        userResponses = new UserResponses(this);
        userResponses.resetResponses();

        gestureDetector = new GestureDetector(this, new GestureListener());

        loadQuestions();
        displayQuestion();

        questionCard.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    dX = event.getRawX() - questionCard.getTranslationX();
                    return true;

                case MotionEvent.ACTION_MOVE:
                    questionCard.setTranslationX(event.getRawX() - dX);
                    return true;

                case MotionEvent.ACTION_UP:
                    handleSwipeRelease();
                    return true;

                default:
                    return gestureDetector.onTouchEvent(event);
            }
        });
    }

    private void loadQuestions() {
        questionList = new ArrayList<>();
        questionList.add(new Questions("Do you like writing code and creating apps or websites?", 1, 10));
        questionList.add(new Questions("Do you enjoy working with internet connections, routers, and networks?", 2, 20));
        questionList.add(new Questions("Do you like helping people solve computer or tech issues?", 3, 30));
        questionList.add(new Questions("Do you enjoy working with numbers, charts, and analyzing data?", 4, 40));
        questionList.add(new Questions("Do you like designing how apps or websites look and feel?", 5, 50));
        questionList.add(new Questions("Do you enjoy leading a team and managing projects?", 6, 60));
        questionList.add(new Questions("Are you interested in protecting computers and data from hackers?", 7, 70));
        questionList.add(new Questions("Do you like fixing errors in programs and making them work better?", 8, 80));
        questionList.add(new Questions("Do you enjoy setting up and managing computer systems for companies?", 9, 90));
        questionList.add(new Questions("Do you like figuring out how to make technology solve real-world problems?", 10, 100));
        questionList.add(new Questions("Are you interested in testing systems to find weaknesses and improve security?", 11, 110));
        questionList.add(new Questions("Do you like planning and organizing tasks to make sure projects get done?", 12, 120));
        questionList.add(new Questions("Do you prefer designing buttons, icons, and layouts rather than coding?", 13, 130));
        questionList.add(new Questions("Do you enjoy working with data to find useful information for businesses?", 14, 140));
        questionList.add(new Questions("Do you like keeping up with online security trends and protecting digital accounts?", 15, 150));
    }

    private void displayQuestion() {
        if (currentQuestionIndex < questionList.size()) {
            Questions currentQuestion = questionList.get(currentQuestionIndex);
            tvQuestion.setText(currentQuestion.getQuestionText());
            tvQuestionNumber.setText("Question " + currentQuestion.getQuestionNumber() + " of " + questionList.size());
            progressBar.setProgress((currentQuestion.getQuestionNumber() * 100) / questionList.size());

            questionCard.setTranslationX(0);
            questionCard.setAlpha(1f);
        } else {
            goToResultsPage();
        }
    }

    private void handleSwipeRelease() {
        float translationX = questionCard.getTranslationX();
        if (translationX > SWIPE_THRESHOLD) {
            animateCardSwipe(true);  // Swipe right (Like)
        } else if (translationX < -SWIPE_THRESHOLD) {
            animateCardSwipe(false); // Swipe left (Dislike)
        } else {
            // Reset position if not far enough
            questionCard.animate().translationX(0).setDuration(200).start();
        }
    }

    private void animateCardSwipe(boolean isLike) {
        float endX = isLike ? 1000f : -1000f; 
        Questions currentQuestion = questionList.get(currentQuestionIndex);
        userResponses.saveResponse(currentQuestion.getQuestionNumber(), isLike);
        
        ObjectAnimator animator = ObjectAnimator.ofFloat(questionCard, "translationX", endX);
        animator.setDuration(300); 
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                questionCard.setTranslationX(0); 
                questionCard.setAlpha(0f); 
                nextQuestion();
                questionCard.animate().alpha(1f).setDuration(300); 
            }
    
            @Override public void onAnimationStart(Animator animation) {}
            @Override public void onAnimationCancel(Animator animation) {}
            @Override public void onAnimationRepeat(Animator animation) {}
        });
        animator.start();
    }

    private void nextQuestion() {
        if (currentQuestionIndex < questionList.size() - 1) {
            currentQuestionIndex++;
            displayQuestion();
        } else {
            goToResultsPage();
        }
    }

    private void goToResultsPage() {
        Intent intent = new Intent(quiz.this, result_page.class);
        startActivity(intent);
        finish();
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float diffX = e2.getX() - e1.getX();
            if (Math.abs(diffX) > SWIPE_THRESHOLD) {
                if (diffX > 0) {
                    animateCardSwipe(true);  // Swipe right (Like)
                } else {
                    animateCardSwipe(false); // Swipe left (Dislike)
                }
                return true;
            }
            return false;
        }
    }
}

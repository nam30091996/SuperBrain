package com.teamthayhung.superbrain;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class SecondGame extends AppCompatActivity{

    private View timeView;
    private ImageView imgUp, imgDown;
    private TextView txtQuestion;
    private int pre = 0;
    private int cur = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_game);
        timeView = (View) this.findViewById(R.id.timeview);
        Thread timeThread = new Thread(new TimePlayThread(timeView, this));
        timeThread.start();

        imgUp = (ImageView) this.findViewById(R.id.img_up);
        imgUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cur > pre){
                    trueAnswer();
                    randomQuestion();
                }
                else wrongAnswer();
            }
        });
        imgDown = (ImageView) this.findViewById(R.id.img_down);
        imgDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cur < pre){
                    trueAnswer();
                    randomQuestion();
                }
                else wrongAnswer();
            }
        });

        txtQuestion = (TextView) this.findViewById(R.id.txt_question);
        txtQuestion.setText(String.valueOf(cur));
    }

    private void trueAnswer() {
        GameUtils.playSound(getBaseContext(), R.raw.correct, false);
        GameConfig.score += 5;

        ObjectAnimator alphaAnimation1 = ObjectAnimator.ofFloat(txtQuestion, View.ALPHA, 1.0f, 0.0f);
        alphaAnimation1.setDuration(50);
        ObjectAnimator alphaAnimation2 = ObjectAnimator.ofFloat(txtQuestion, View.ALPHA, 0.0f, 1.0f);
        alphaAnimation1.setDuration(0);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(alphaAnimation1, alphaAnimation2);
        animatorSet.start();
    }

    private void wrongAnswer() {
        GameUtils.playSound(getBaseContext(), R.raw.wrong, false);
        if(GameConfig.score >= 2) GameConfig.score -= 2;

        ObjectAnimator animatorX1 = ObjectAnimator.ofFloat(txtQuestion, "x", txtQuestion.getX() - 20);
        animatorX1.setDuration(10);
        ObjectAnimator animatorX2 = ObjectAnimator.ofFloat(txtQuestion, "x", txtQuestion.getX() + 40);
        animatorX2.setDuration(10);
        ObjectAnimator animatorX3 = ObjectAnimator.ofFloat(txtQuestion, "x", txtQuestion.getX() - 40);
        animatorX3.setDuration(10);
        ObjectAnimator animatorX4 = ObjectAnimator.ofFloat(txtQuestion, "x", txtQuestion.getX());
        animatorX4.setDuration(10);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(animatorX1, animatorX2, animatorX3, animatorX4);
        animatorSet.start();
    }

    private void randomQuestion() {
        pre = cur;
        do{
            cur = new Random().nextInt(100);
        } while (cur == pre);
        txtQuestion.setText(String.valueOf(cur));
    }

    @Override
    public void onBackPressed() {

    }
}

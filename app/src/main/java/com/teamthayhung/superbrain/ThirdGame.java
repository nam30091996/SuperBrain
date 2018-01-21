package com.teamthayhung.superbrain;

import android.animation.AnimatorSet;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.animation.ObjectAnimator;

import java.util.Random;

public class ThirdGame extends AppCompatActivity {

    private View timeView;
    private int question;
    private ImageView imgQuestion, imgBao, imgBua, imgKeo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_game);
        timeView = (View) this.findViewById(R.id.timeview);
        Thread timeThread = new Thread(new TimePlayThread(timeView, this));
        timeThread.start();

        imgQuestion = (ImageView) this.findViewById(R.id.img_question);
        imgBao = (ImageView) this.findViewById(R.id.img_bao);
        imgBua = (ImageView) this.findViewById(R.id.img_bua);
        imgKeo = (ImageView) this.findViewById(R.id.img_keo);

        imgBua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (question == 2 || question == 4) {
                    trueAnswer();
                    randomQuestion();
                } else wrongAnswer();

            }
        });

        imgBao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (question == 0 || question == 5) {
                    trueAnswer();
                    randomQuestion();
                } else wrongAnswer();
            }
        });

        imgKeo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (question == 1 || question == 3) {
                    trueAnswer();
                    randomQuestion();
                } else wrongAnswer();
            }
        });
    }

    private void trueAnswer() {
        GameUtils.playSound(getBaseContext(), R.raw.correct, false);
        GameConfig.score += 5;

        ObjectAnimator alphaAnimation1 = ObjectAnimator.ofFloat(imgQuestion, View.ALPHA, 1.0f, 0.0f);
        alphaAnimation1.setDuration(100);
        ObjectAnimator alphaAnimation2 = ObjectAnimator.ofFloat(imgQuestion, View.ALPHA, 0.0f, 1.0f);
        alphaAnimation1.setDuration(0);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(alphaAnimation1, alphaAnimation2);
        animatorSet.start();
    }

    private void wrongAnswer() {
        GameUtils.playSound(getBaseContext(), R.raw.wrong, false);
        if(GameConfig.score >= 2) GameConfig.score -= 2;

        ObjectAnimator animatorX1 = ObjectAnimator.ofFloat(imgQuestion, "x", imgQuestion.getX() - 20);
        animatorX1.setDuration(50);
        ObjectAnimator animatorX2 = ObjectAnimator.ofFloat(imgQuestion, "x", imgQuestion.getX() + 40);
        animatorX2.setDuration(50);
        ObjectAnimator animatorX3 = ObjectAnimator.ofFloat(imgQuestion, "x", imgQuestion.getX() - 40);
        animatorX3.setDuration(50);
        ObjectAnimator animatorX4 = ObjectAnimator.ofFloat(imgQuestion, "x", imgQuestion.getX());
        animatorX4.setDuration(50);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(animatorX1, animatorX2, animatorX3, animatorX4);
        animatorSet.start();
    }

    private void randomQuestion() {
        question = new Random().nextInt(6);
        imgQuestion.setBackgroundColor(Color.rgb(255, 255, 255));
        switch (question) {
            case 0:
                imgQuestion.setImageResource(R.drawable.bua_blue);
                break;
            case 1:
                imgQuestion.setImageResource(R.drawable.bao_blue);
                break;
            case 2:
                imgQuestion.setImageResource(R.drawable.keo_blue);
                break;
            case 3:
                imgQuestion.setImageResource(R.drawable.bua_red);
                break;
            case 4:
                imgQuestion.setImageResource(R.drawable.bao_red);
                break;
            case 5:
                imgQuestion.setImageResource(R.drawable.keo_red);
                break;
        }

    }

    @Override
    public void onBackPressed() {

    }

}

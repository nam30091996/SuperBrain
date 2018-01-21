package com.teamthayhung.superbrain;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class FirstGame extends AppCompatActivity implements View.OnClickListener {

    private View timeView;
    private TextView txtA, txtB, txtC;
    private int a, b, c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_game);
        timeView = (View) this.findViewById(R.id.timeview);
        Thread timeThread = new Thread(new TimePlayThread(timeView, this));
        timeThread.start();

        this.findViewById(R.id.btn_plus).setOnClickListener(this);
        this.findViewById(R.id.btn_min).setOnClickListener(this);
        this.findViewById(R.id.btn_mul).setOnClickListener(this);
        this.findViewById(R.id.btn_div).setOnClickListener(this);
        txtA = (TextView) this.findViewById(R.id.txt_a);
        txtB = (TextView) this.findViewById(R.id.txt_b);
        txtC = (TextView) this.findViewById(R.id.txt_c);
        randomQuestion();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_plus:
                if (a + b == c) {
                    trueAnswer();
                    randomQuestion();
                } else wrongAnswer();
                break;
            case R.id.btn_min:
                if (a - b == c) {
                    trueAnswer();
                    randomQuestion();
                } else wrongAnswer();
                break;
            case R.id.btn_mul:
                if (a * b == c) {
                    trueAnswer();
                    randomQuestion();
                } else wrongAnswer();
                break;
            case R.id.btn_div:
                if (a / b == c) {
                    trueAnswer();
                    randomQuestion();
                } else wrongAnswer();
                break;
            default:
                break;
        }

    }

    private void randomQuestion() {
        Random rand = new Random();
        int answer = rand.nextInt(4);
        switch (answer) {
            case 0:
                a = rand.nextInt(9) + 1;
                b = rand.nextInt(9) + 1;
                c = a + b;
                break;
            case 1:
                b = rand.nextInt(9) + 1;
                c = rand.nextInt(9) + 1;
                a = b + c;
                break;
            case 2:
                a = rand.nextInt(5) + 1;
                b = rand.nextInt(5) + 1;
                c = a * b;
                break;
            case 3:
                c = rand.nextInt(5) + 1;
                b = rand.nextInt(5) + 1;
                a = b * c;
                break;
            default:
                break;
        }
        txtA.setText(String.valueOf(a));
        txtB.setText(String.valueOf(b));
        txtC.setText(String.valueOf(c));
    }

    private void trueAnswer() {
        GameUtils.playSound(getBaseContext(), R.raw.correct, false);
        GameConfig.score += 5;

        ObjectAnimator alphaAnimationA1 = ObjectAnimator.ofFloat(txtA, View.ALPHA, 1.0f, 0.0f);
        alphaAnimationA1.setDuration(100);
        ObjectAnimator alphaAnimationA2 = ObjectAnimator.ofFloat(txtA, View.ALPHA, 0.0f, 1.0f);
        alphaAnimationA1.setDuration(0);

        ObjectAnimator alphaAnimationB1 = ObjectAnimator.ofFloat(txtB, View.ALPHA, 1.0f, 0.0f);
        alphaAnimationB1.setDuration(100);
        ObjectAnimator alphaAnimationB2 = ObjectAnimator.ofFloat(txtB, View.ALPHA, 0.0f, 1.0f);
        alphaAnimationB1.setDuration(0);

        ObjectAnimator alphaAnimationC1 = ObjectAnimator.ofFloat(txtC, View.ALPHA, 1.0f, 0.0f);
        alphaAnimationC1.setDuration(100);
        ObjectAnimator alphaAnimationC2 = ObjectAnimator.ofFloat(txtC, View.ALPHA, 0.0f, 1.0f);
        alphaAnimationC1.setDuration(0);


        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(alphaAnimationA1, alphaAnimationA2);
        animatorSet.start();
        animatorSet.playSequentially(alphaAnimationB1, alphaAnimationB2);
        animatorSet.start();
        animatorSet.playSequentially(alphaAnimationC1, alphaAnimationC2);
        animatorSet.start();
    }

    private void wrongAnswer() {
        GameUtils.playSound(getBaseContext(), R.raw.wrong, false);
        if(GameConfig.score >= 2) GameConfig.score -= 2;

        ObjectAnimator animatorAX1 = ObjectAnimator.ofFloat(txtA, "x", txtA.getX() - 20);
        animatorAX1.setDuration(50);
        ObjectAnimator animatorAX2 = ObjectAnimator.ofFloat(txtA, "x", txtA.getX() + 40);
        animatorAX2.setDuration(50);
        ObjectAnimator animatorAX3 = ObjectAnimator.ofFloat(txtA, "x", txtA.getX() - 40);
        animatorAX3.setDuration(50);
        ObjectAnimator animatorAX4 = ObjectAnimator.ofFloat(txtA, "x", txtA.getX());
        animatorAX4.setDuration(50);

        ObjectAnimator animatorBX1 = ObjectAnimator.ofFloat(txtB, "x", txtB.getX() - 20);
        animatorBX1.setDuration(50);
        ObjectAnimator animatorBX2 = ObjectAnimator.ofFloat(txtB, "x", txtB.getX() + 40);
        animatorBX2.setDuration(50);
        ObjectAnimator animatorBX3 = ObjectAnimator.ofFloat(txtB, "x", txtB.getX() - 40);
        animatorBX3.setDuration(50);
        ObjectAnimator animatorBX4 = ObjectAnimator.ofFloat(txtB, "x", txtB.getX());
        animatorBX4.setDuration(50);

        ObjectAnimator animatorCX1 = ObjectAnimator.ofFloat(txtC, "x", txtC.getX() - 20);
        animatorCX1.setDuration(50);
        ObjectAnimator animatorCX2 = ObjectAnimator.ofFloat(txtC, "x", txtC.getX() + 40);
        animatorCX2.setDuration(50);
        ObjectAnimator animatorCX3 = ObjectAnimator.ofFloat(txtC, "x", txtC.getX() - 40);
        animatorCX3.setDuration(50);
        ObjectAnimator animatorCX4 = ObjectAnimator.ofFloat(txtC, "x", txtC.getX());
        animatorCX4.setDuration(50);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(animatorAX1, animatorAX2, animatorAX3, animatorAX4);
        animatorSet.start();
        animatorSet.playSequentially(animatorBX1, animatorBX2, animatorBX3, animatorBX4);
        animatorSet.start();
        animatorSet.playSequentially(animatorCX1, animatorCX2, animatorCX3, animatorCX4);
        animatorSet.start();
    }

    public void timeup(){
        Intent intent = new Intent(FirstGame.this, FirstGame.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {

    }
}

package com.teamthayhung.superbrain;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Admin on 14/1/2018.
 */

public class TimePlayThread implements Runnable {

    private View timeView;
    private Context context;
    public static final int TIME = 20;

    public TimePlayThread(View timeView, Context context) {
        this.timeView = timeView;
        this.context = context;
    }

    @Override
    public void run() {
        int time = 0;
        float deltaX = timeView.getScaleX() / TIME;
        while (true) {
            try {
                Thread.sleep(1000);
                time++;
                timeView.setScaleX(timeView.getScaleX() - deltaX);
                if (time >= TIME) {
                    break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        if (GameConfig.training) {
            GameConfig.training = false;
            Intent intent = new Intent(context, ScoreActivity.class);
            context.startActivity(intent);
        }
        if (GameConfig.playing) {
            GameConfig.yourScore += GameConfig.score;
            GameConfig.score = 0;
            GameConfig.game++;
            if (GameConfig.game == 4) {
                GameConfig.finishFight = true;
                if(GameConfig.firstPlayer){
                    FirebaseDatabase.getInstance().getReference().child("fight").child(GameConfig.user.getUid()).child("score1").setValue(String.valueOf(GameConfig.yourScore));
                } else {
                    FirebaseDatabase.getInstance().getReference().child("fight").child(GameConfig.oppnent.getId()).child("score2").setValue(String.valueOf(GameConfig.yourScore));
                }

            }
            Intent intent = new Intent(context, FightActivity.class);
            context.startActivity(intent);
        }
    }
}

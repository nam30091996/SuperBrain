package com.teamthayhung.superbrain;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Created by Admin on 20/1/2018.
 */

public class GameUtils {

    public static void playSound(Context context, int i, boolean loop){
        MediaPlayer  mp = MediaPlayer.create(context, i);
        mp.setLooping(loop);
        mp.start();
    }
}

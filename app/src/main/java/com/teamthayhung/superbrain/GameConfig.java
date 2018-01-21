package com.teamthayhung.superbrain;

import android.content.Intent;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

/**
 * Created by Admin on 19/1/2018.
 */

public class GameConfig {

    public static FirebaseUser user;
    public static User mUser;
    public static boolean training = false;
    public static int score = 0;
    public static boolean exist = false;

    private static ArrayList<User> listU;

    public static ArrayList<User> getListUser() {
        if (listU == null) {
            listU = new ArrayList<User>();
        }
        return listU;
    }

    public static void addUser(User u) {
        listU.add(u);
    }

    public static boolean playing = false;
    public static boolean firstPlayer = true;
    public static int yourScore = 0;
    public static int opponentScore = 0;
    public static User oppnent = null;
    public static int game = 1;
    public static boolean finishFight = false;

    public static void reset(){
        playing = false;
        firstPlayer = true;
        yourScore = 0;
        opponentScore = 0;
        oppnent = null;
        game = 1;
        finishFight = false;
    }
}

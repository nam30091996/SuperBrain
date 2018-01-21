package com.teamthayhung.superbrain;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    BottomBar mBottomBar;
    private FirebaseAuth mAuth;
    public DatabaseReference mRef;
    private int pos = 1;
    ProgressDialog mDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        GameConfig.user = mAuth.getCurrentUser();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        mRef = database.getReference().child("user");

        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!GameConfig.exist) {
                    User u = new User(GameConfig.user.getEmail(),
                            GameConfig.user.getDisplayName(), "0", GameConfig.user.getPhotoUrl().toString());
                    mRef.child(GameConfig.user.getUid()).setValue(u);
                    GameConfig.exist = true;
                }
                mDialog.dismiss();
                updateUI();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                User u = dataSnapshot.getValue(User.class);
                u.setId(dataSnapshot.getKey());
                if (!checkExist(u)) GameConfig.addUser(u);
                if (GameConfig.user.getUid().equals(u.getId())) {
                    GameConfig.exist = true;
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                User u = dataSnapshot.getValue(User.class);
                u.setId(dataSnapshot.getKey());
                for (int i = 0; i < GameConfig.getListUser().size(); i++) {
                    if (u.getId().equals(GameConfig.getListUser().get(i).getId())) {
                        GameConfig.getListUser().get(i).setPoint(u.getPoint());
                        return;
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        setContentView(R.layout.activity_main);

        GameUtils.playSound(getBaseContext(), R.raw.background, true);

        mBottomBar = BottomBar.attach(this, savedInstanceState);
        Log.d("ABC", String.valueOf(mBottomBar.getHeight()));
        mBottomBar.setItemsFromMenu(R.menu.menu_main, new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(int menuItemId) {
                switch (menuItemId) {
                    case R.id.Bottombaritemone:
                        pos = 1;
                        GameUtils.playSound(getBaseContext(), R.raw.click, false);
                        HomeFragment f1 = new HomeFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, f1).commit();
                        break;
                    case R.id.Bottombaritemtwo:
                        pos = 2;
                        GameUtils.playSound(getBaseContext(), R.raw.click, false);
                        RankingFragment f2 = new RankingFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, f2).commit();
                        break;
                    case R.id.Bottombaritemthree:
                        pos = 3;
                        GameUtils.playSound(getBaseContext(), R.raw.click, false);
                        TrainingFragment f3 = new TrainingFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, f3).commit();
                        break;
                    case R.id.Bottombaritemfour:
                        pos = 4;
                        GameUtils.playSound(getBaseContext(), R.raw.click, false);
                        CreditsFragment f4 = new CreditsFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, f4).commit();
                        break;
                }
            }

            @Override
            public void onMenuTabReSelected(int menuItemId) {

            }
        });

        mBottomBar.mapColorForTab(0, "#90C563");
        mBottomBar.mapColorForTab(1, "#9C27B0");
        mBottomBar.mapColorForTab(2, "#03A9F4");
        mBottomBar.mapColorForTab(3, "#795548");

        mDialog = new ProgressDialog(this);
        mDialog.setMessage("Please wait...");
        mDialog.show();

    }

    private boolean checkExist(User u) {
        for (User user : GameConfig.getListUser()) {
            if (u.getId().equals(user.getId())) return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public static int getRank() {
        Collections.sort(GameConfig.getListUser());
        for (int i = 0; i < GameConfig.getListUser().size(); i++) {
            if (GameConfig.user.getUid().equals(GameConfig.getListUser().get(i).getId())){
                GameConfig.mUser = GameConfig.getListUser().get(i);
                return i + 1;
            }
        }
        return 0;
    }

    @Override
    public void onBackPressed() {
//        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
//        builder1.setMessage("Back?");
//        builder1.setCancelable(true);
//
//        builder1.setPositiveButton(
//                "Yes",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        startActivity(new Intent(MainActivity.this, StartActivity.class));
//                    }
//                });
//
//        builder1.setNegativeButton(
//                "No",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        dialog.cancel();
//                    }
//                });
//
//        AlertDialog alert11 = builder1.create();
//        alert11.show();
    }

    public void updateUI() {
        if (pos == 1) {
            HomeFragment f1 = new HomeFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.frame, f1).commit();
        } else if (pos == 2) {
            RankingFragment f2 = new RankingFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.frame, f2).commit();
        }
    }
}

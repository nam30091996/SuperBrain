package com.teamthayhung.superbrain;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class FightActivity extends AppCompatActivity {

    private ImageView imgYourAva, imgOpAva;
    private TextView txtYourName, txtOpName, txtTime, txtYourScore, txtOpScore;
    private FirebaseDatabase database;
    private DatabaseReference mRef;
    private ValueEventListener mValueEventListener;
    private ChildEventListener mChildEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fight);

        imgYourAva = (ImageView) this.findViewById(R.id.img_your_ava);
        Picasso.with(this).load(GameConfig.user.getPhotoUrl()).transform(new FightActivity.TransformBitmap()).into(imgYourAva);
        txtYourName = (TextView) this.findViewById(R.id.txt_your_name);
        txtYourName.setText(GameConfig.user.getDisplayName());
        imgOpAva = (ImageView) this.findViewById(R.id.img_op_ava);
        imgOpAva.setImageResource(R.drawable.waiting);
        txtOpName = (TextView) this.findViewById(R.id.txt_op_name);
        txtOpName.setText("");
        txtTime = (TextView) this.findViewById(R.id.txt_time);
        if (GameConfig.oppnent != null) {
            Picasso.with(getBaseContext()).load(GameConfig.oppnent.getPhotoUrl()).transform(new FightActivity.TransformBitmap()).into(imgOpAva);
            txtOpName.setText(GameConfig.oppnent.getName());
        }
        if (GameConfig.playing && !GameConfig.finishFight) {
            coundown(3);
        }

        findViewById(android.R.id.content).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int x = Integer.parseInt(GameConfig.mUser.getPoint()) + GameConfig.yourScore - GameConfig.opponentScore;
                if (x > 0)
                    mRef.getParent().child("user").child(GameConfig.user.getUid()).child("point").setValue(String.valueOf(x));
                else
                    mRef.getParent().child("user").child(GameConfig.user.getUid()).child("point").setValue(String.valueOf(0));
                if (GameConfig.finishFight) {
                    if (GameConfig.firstPlayer) {
                        mRef.child(GameConfig.user.getUid()).setValue(null);
                    }
                    GameConfig.reset();

                    startActivity(new Intent(FightActivity.this, MainActivity.class));
                }
            }
        });

        database = FirebaseDatabase.getInstance();
        mRef = database.getReference().child("fight");
        mValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!GameConfig.playing) { //Ko co phong nao thieu nguoi
                    Fight f = new Fight(GameConfig.user.getUid(), "0", "0", "0");
                    mRef.child(GameConfig.user.getUid()).setValue(f);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mRef.addListenerForSingleValueEvent(mValueEventListener);

        final FightActivity fightActivity = this;
        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Fight f = dataSnapshot.getValue(Fight.class);

                //Da choi xong
                if (GameConfig.finishFight && ((f.getId1().equals(GameConfig.user.getUid()) && f.getId2().equals(GameConfig.oppnent.getId()))
                        || (f.getId1().equals(GameConfig.oppnent.getId()) && f.getId2().equals(GameConfig.user.getUid())))) {

                    if (GameConfig.firstPlayer) {
                        GameConfig.opponentScore = Integer.parseInt(dataSnapshot.child("score2").getValue(String.class));
                    } else {
                        GameConfig.opponentScore = Integer.parseInt(dataSnapshot.child("score1").getValue(String.class));
                    }
                    ((TextView) findViewById(R.id.txt_your_score)).setText(String.valueOf(GameConfig.yourScore));
                    ((TextView) findViewById(R.id.txt_op_score)).setText(String.valueOf(GameConfig.opponentScore));
                    if (GameConfig.yourScore > GameConfig.opponentScore)
                        txtTime.setText("W");
                    else if (GameConfig.yourScore < GameConfig.opponentScore)
                        txtTime.setText("L");
                    else txtTime.setText("D");



                }

                //Tim phong con thieu nguoi
                if (GameConfig.playing || f.getId1().equals(GameConfig.user.getUid())) return;
                if (f.getId2().equals("0")) {
                    GameConfig.playing = true;
                    GameConfig.firstPlayer = false;
                    GameConfig.oppnent = searchOp(f.getId1());
                    Picasso.with(getBaseContext()).load(GameConfig.oppnent.getPhotoUrl()).transform(new FightActivity.TransformBitmap()).into(imgOpAva);
                    txtOpName.setText(GameConfig.oppnent.getName());
                    mRef.child(GameConfig.oppnent.getId()).child("id2").setValue(GameConfig.user.getUid());
                    coundown(3);
                }


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Fight f = dataSnapshot.getValue(Fight.class);

                if (GameConfig.finishFight && ((f.getId1().equals(GameConfig.user.getUid()) && f.getId2().equals(GameConfig.oppnent.getId()))
                        || (f.getId1().equals(GameConfig.oppnent.getId()) && f.getId2().equals(GameConfig.user.getUid())))) {

                    if (GameConfig.firstPlayer) {
                        GameConfig.opponentScore = Integer.parseInt(dataSnapshot.child("score2").getValue(String.class));
                    } else {
                        GameConfig.opponentScore = Integer.parseInt(dataSnapshot.child("score1").getValue(String.class));
                    }
                    ((TextView) findViewById(R.id.txt_your_score)).setText(String.valueOf(GameConfig.yourScore));
                    ((TextView) findViewById(R.id.txt_op_score)).setText(String.valueOf(GameConfig.opponentScore));
                    if (GameConfig.yourScore > GameConfig.opponentScore)
                        txtTime.setText("W");
                    else if (GameConfig.yourScore < GameConfig.opponentScore)
                        txtTime.setText("L");
                    else txtTime.setText("D");

//                    int x = Integer.parseInt(GameConfig.mUser.getPoint()) + GameConfig.yourScore - GameConfig.opponentScore;
//                    if (x > 0)
//                        mRef.getParent().child("user").child(GameConfig.user.getUid()).child("point").setValue(String.valueOf(x));
//                    else
//                        mRef.getParent().child("user").child(GameConfig.user.getUid()).child("point").setValue(String.valueOf(0));

                }

                //Khi co 1 thang thu 2 vao
                if (f.getId1().equals(GameConfig.user.getUid()) && !f.getId2().equals("0") && !GameConfig.finishFight) {
                    GameConfig.firstPlayer = true;
                    GameConfig.playing = true;
                    GameConfig.oppnent = searchOp(f.getId2());
                    Picasso.with(getBaseContext()).load(GameConfig.oppnent.getPhotoUrl()).transform(new FightActivity.TransformBitmap()).into(imgOpAva);
                    txtOpName.setText(GameConfig.oppnent.getName());
                    coundown(3);
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
        };
        mRef.addChildEventListener(mChildEventListener);
    }

    @Override
    protected void onPause() {
        if (mRef != null && mChildEventListener != null && mValueEventListener != null) {
            mRef.removeEventListener(mChildEventListener);
            mRef.removeEventListener(mChildEventListener);
        }
        super.onPause();
    }

    @Override
    protected void onStop() {
        if (mRef != null && mChildEventListener != null && mValueEventListener != null) {
            mRef.removeEventListener(mChildEventListener);
            mRef.removeEventListener(mChildEventListener);
        }
        super.onStop();
    }

    private User searchOp(String id1) {
        for (User u : GameConfig.getListUser()) {
            if (u.getId().equals(id1)) return u;
        }
        return null;
    }

    public class TransformBitmap implements com.squareup.picasso.Transformation {

        @Override
        public Bitmap transform(Bitmap source) {
            Bitmap output = getCircleBitmap(source);
            return output;
        }

        @Override
        public String key() {
            return "" + 1;
        }
    }

    private Bitmap getCircleBitmap(Bitmap bitmap) {
        final Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(output);

        final int color = Color.RED;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawOval(rectF, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        bitmap.recycle();

        return output;
    }

    @Override
    public void onBackPressed() {
        if (GameConfig.finishFight) return;
        if (!GameConfig.playing) {
            mRef.child(GameConfig.user.getUid()).setValue(null);
            super.onBackPressed();
        }
    }

    private void coundown(int i) {
        if (i < 0) {
            if (GameConfig.game == 1) {
                Intent intent1 = new Intent(this, FirstGame.class);
                startActivity(intent1);
            } else if (GameConfig.game == 2) {
                Intent intent1 = new Intent(this, SecondGame.class);
                startActivity(intent1);
            } else if (GameConfig.game == 3) {
                Intent intent1 = new Intent(this, ThirdGame.class);
                startActivity(intent1);
            }
        }


        final int time = i;
        final Animation anim_out = AnimationUtils.loadAnimation(this, android.R.anim.fade_out);
        anim_out.setDuration(1000);

        anim_out.setAnimationListener(new Animation.AnimationListener()

        {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                txtTime.setText(String.valueOf(String.valueOf(time)));
                coundown(time - 1);
            }
        });
        txtTime.startAnimation(anim_out);
    }
}

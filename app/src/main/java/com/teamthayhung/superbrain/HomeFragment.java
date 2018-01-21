package com.teamthayhung.superbrain;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class HomeFragment extends Fragment {

    private Button btnChallenge;
    private TextView txtName, txtRanking;
    private ImageView imgAvatar;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        btnChallenge = (Button) v.findViewById(R.id.btn_challenge);
        txtName = (TextView) v.findViewById(R.id.txt_name);
        txtRanking = (TextView) v.findViewById(R.id.txt_ranking);
        imgAvatar = (ImageView) v.findViewById(R.id.img_ava);

        txtName.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        txtRanking.setText("Rank: " + MainActivity.getRank());
        Picasso.with(getContext()).load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl()).transform(new TransformBitmap()).into(imgAvatar);

        btnChallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GameUtils.playSound(getContext(), R.raw.click, false);
                Intent intent = new Intent(getContext(), FightActivity.class);
                startActivity(intent);

            }
        });

        return v;
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
}

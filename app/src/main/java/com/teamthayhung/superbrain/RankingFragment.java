package com.teamthayhung.superbrain;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Admin on 19/1/2018.
 */

public class RankingFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private TextView txtRanking, txtName, txtPoint;
    private ImageView imgAva;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ranking, container, false);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.my_recycler_view);

        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new MyAdapter(GameConfig.getListUser(), this.getContext());
        mRecyclerView.setAdapter(mAdapter);

        txtName = (TextView) v.findViewById(R.id.txt_name);
        txtName.setText(GameConfig.user.getDisplayName());
        txtRanking = (TextView) v.findViewById(R.id.txt_ranking);
        txtRanking.setText(String.valueOf(MainActivity.getRank()));
        imgAva = (ImageView) v.findViewById(R.id.img_ava);
        Picasso.with(getContext()).load(GameConfig.user.getPhotoUrl()).transform(new RankingFragment.TransformBitmap()).into(imgAva);
        txtPoint = (TextView) v.findViewById(R.id.txt_point);
        txtPoint.setText("Point: " + GameConfig.getListUser().get(MainActivity.getRank() - 1).getPoint());
        return v;
    }

    public class TransformBitmap implements com.squareup.picasso.Transformation{

        @Override
        public Bitmap transform(Bitmap source) {
            Bitmap output =getCircleBitmap(source);
            return output;
        }

        @Override
        public String key() {
            return "abc" + 1;
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

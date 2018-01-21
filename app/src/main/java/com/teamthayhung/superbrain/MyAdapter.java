package com.teamthayhung.superbrain;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Admin on 12/11/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private ArrayList<User> listUser;
    private Context context;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView txtRanking, txtName, txtPoint;
        private ImageView imgAva;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtRanking = (TextView) itemView.findViewById(R.id.txt_ranking);
            txtName = (TextView) itemView.findViewById(R.id.txt_name);
            txtPoint = (TextView) itemView.findViewById(R.id.txt_point);
            imgAva = (ImageView) itemView.findViewById(R.id.img_ava);
        }
    }

    public MyAdapter(ArrayList<User> listUser, Context context) {
        this.listUser = listUser;
        this.context = context;
    }

    public ArrayList<User> getListUser() {
        return listUser;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        User u = listUser.get(position);
        holder.txtRanking.setText(String.valueOf(position + 1));
        holder.txtName.setText(u.getName());
        holder.txtPoint.setText("Point: " + u.getPoint());
        Picasso.with(context).load(GameConfig.getListUser().get(position).getPhotoUrl()).transform(new MyAdapter.TransformBitmap()).into(holder.imgAva);
    }

    @Override
    public int getItemCount() {
        return listUser.size();
    }

    public class TransformBitmap implements com.squareup.picasso.Transformation{

        @Override
        public Bitmap transform(Bitmap source) {
            Bitmap output =getCircleBitmap(source);
            return output;
        }

        @Override
        public String key() {
            return "fuckyou" + 1;
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

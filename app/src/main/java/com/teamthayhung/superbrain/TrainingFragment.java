package com.teamthayhung.superbrain;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Admin on 19/1/2018.
 */

public class TrainingFragment extends Fragment {

    private ImageView btnFirstGame, btnSecondGame, btnThirdGame;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_training, container, false);

        btnFirstGame = (ImageView) v.findViewById(R.id.btn_first_game);
        btnFirstGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GameUtils.playSound(getContext(), R.raw.click, false);
                GameConfig.training = true;
                Intent intent = new Intent(getContext(), FirstGame.class);
                startActivity(intent);
            }
        });

        btnSecondGame = (ImageView) v.findViewById(R.id.btn_second_game);
        btnSecondGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GameUtils.playSound(getContext(), R.raw.click, false);
                GameConfig.training = true;
                Intent intent = new Intent(getContext(), SecondGame.class);
                startActivity(intent);
            }
        });

        btnThirdGame = (ImageView) v.findViewById(R.id.btn_third_game);
        btnThirdGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GameUtils.playSound(getContext(), R.raw.click, false);
                GameConfig.training = true;
                Intent intent = new Intent(getContext(), ThirdGame.class);
                startActivity(intent);
            }
        });

        return v;
    }
}

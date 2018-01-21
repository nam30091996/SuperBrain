package com.teamthayhung.superbrain;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CreditsFragment extends Fragment {

    private TextView txt_team,txt_thanks;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_credits, container, false);

        txt_team = (TextView) v.findViewById(R.id.txt_team);

        txt_thanks = (TextView) v.findViewById(R.id.txt_thanks);

        return v;
    }
}

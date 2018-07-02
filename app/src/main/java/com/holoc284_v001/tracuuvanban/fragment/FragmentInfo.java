package com.holoc284_v001.tracuuvanban.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.holoc284_v001.tracuuvanban.R;

/**
 * Created by holoc on 5/21/2018.
 */

public class FragmentInfo extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        return inflater.inflate(R.layout.fragment_info,container,false);
    }
}

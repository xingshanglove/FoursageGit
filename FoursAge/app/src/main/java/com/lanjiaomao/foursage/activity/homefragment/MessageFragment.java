package com.lanjiaomao.foursage.activity.homefragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lanjiaomao.foursage.R;

/**
 * Created by root on 2016/5/9.
 */
public class MessageFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.layout_message_fragment,null);
        return view;
    }
}

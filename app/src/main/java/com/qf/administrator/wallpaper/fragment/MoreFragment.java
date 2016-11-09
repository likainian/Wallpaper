package com.qf.administrator.wallpaper.fragment;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.qf.administrator.wallpaper.R;
import com.qf.administrator.wallpaper.activity.MyCollectActivity;
import com.qf.administrator.wallpaper.activity.SetActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoreFragment extends Fragment implements View.OnClickListener {


    private Button myCollect;
    private Button myDownload;
    private Button share;
    private Button good;
    private Button set;


    public MoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_more, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myCollect = (Button) view.findViewById(R.id.my_collect);
        myDownload = (Button) view.findViewById(R.id.my_download);
        share = (Button) view.findViewById(R.id.share);
        good = (Button) view.findViewById(R.id.good);
        set = (Button) view.findViewById(R.id.set);
        myCollect.setOnClickListener(this);
        myDownload.setOnClickListener(this);
        share.setOnClickListener(this);
        good.setOnClickListener(this);
        set.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.my_collect:
                Intent intent = new Intent(getActivity(), MyCollectActivity.class);
                intent.putExtra("come","我的收藏");
                startActivity(intent);
            break;
            case R.id.my_download:
                Intent intent2 = new Intent(getActivity(), MyCollectActivity.class);
                intent2.putExtra("come","我的下载");
                startActivity(intent2);
            break;
            case R.id.share:
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.pw_share, null);
                new AlertDialog.Builder(getActivity()).setView(view).show();
            break;
            case R.id.good:
                Toast.makeText(getActivity(), "谢谢", Toast.LENGTH_SHORT).show();
            break;
            case R.id.set:
                Intent intent3 = new Intent(getActivity(), SetActivity.class);
                startActivity(intent3);
            break;
        }
    }
}

package com.qf.administrator.wallpaper.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.qf.administrator.wallpaper.R;
import com.qf.administrator.wallpaper.fragment.GridFragment;
import com.qf.administrator.wallpaper.fragment.GridViewFragment;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class DetailActivity extends AppCompatActivity {

    private TextView keyWord;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initView();
        String word = getIntent().getStringExtra("keyWord");
        String id = getIntent().getStringExtra("id");
        keyWord.setText(word);
        if(id!=null){
            url ="http://bz.budejie.com/?typeid=2&ver=3.4.3&no_cry=1&client=android&c=topic&a=detail&index=MyPager&size=9&typeid=2&topicid=";
            url = url+id;
            getSupportFragmentManager().beginTransaction().add(R.id.frame_detail, GridFragment.newInstance(url)).commit();
            Log.i("ttt", "onCreate: "+url);
        }else{
            url = "http://bz.budejie.com/?typeid=2&ver=3.4.3&no_cry=1&client=android&c=search&a=search&q=MyWordKey&p=1&s=30";
            try {
                url = url.replaceAll("MyWordKey", URLEncoder.encode(word,"utf-8"));
                getSupportFragmentManager().beginTransaction().add(R.id.frame_detail, GridViewFragment.newInstance(url)).commit();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

    }

    private void initView() {
        keyWord = (TextView) findViewById(R.id.key_word);
    }
}

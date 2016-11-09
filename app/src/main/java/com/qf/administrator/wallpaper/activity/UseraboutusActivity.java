package com.qf.administrator.wallpaper.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.qf.administrator.wallpaper.R;

public class UseraboutusActivity extends AppCompatActivity {

    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_useraboutus);
        initView();
        title.setText(getIntent().getStringExtra("come"));
    }

    private void initView() {
        title = (TextView) findViewById(R.id.title);
    }
}

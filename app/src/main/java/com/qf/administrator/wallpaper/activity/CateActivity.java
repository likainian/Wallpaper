package com.qf.administrator.wallpaper.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.qf.administrator.wallpaper.R;
import com.qf.administrator.wallpaper.fragment.RecommendFragment;

public class CateActivity extends AppCompatActivity {
    private int position;
    private String[] url = new String[]{"http://bz.budejie.com/?typeid=2&ver=3.4.3&no_cry=1&client=android&c=wallPaper&a=wallPaperNew&index=1&size=60&bigid=","http://bz.budejie.com/?typeid=2&ver=3.4.3&no_cry=1&client=android&c=wallPaper&a=hotRecent&index=1&size=60&bigid=","http://bz.budejie.com/?typeid=2&ver=3.4.3&no_cry=1&client=android&c=wallPaper&a=random&bigid="};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cate);
        String cate = getIntent().getStringExtra("cate");
        String ID = getIntent().getStringExtra("ID");
        position = getIntent().getIntExtra("position",0);
        url[0] = url[0]+ID;
        url[1] = url[1]+ID;
        url[2] = url[2]+ID;
        getSupportFragmentManager().beginTransaction().add(R.id.frame_cate, RecommendFragment.newInstance(cate,url)).commit();
    }

}

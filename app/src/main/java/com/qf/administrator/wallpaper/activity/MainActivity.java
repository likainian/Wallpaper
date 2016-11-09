package com.qf.administrator.wallpaper.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.qf.administrator.wallpaper.R;
import com.qf.administrator.wallpaper.fragment.CategoryFragment;
import com.qf.administrator.wallpaper.fragment.MoreFragment;
import com.qf.administrator.wallpaper.fragment.RecommendFragment;
import com.qf.administrator.wallpaper.fragment.SearchFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private int pro;
    private RadioGroup radioGroup;
    private ArrayList<Fragment> list = new ArrayList<>();
    private String[] url = new String[]{"http://bz.budejie.com/?typeid=2&ver=3.4.3&no_cry=1&client=android&c=wallPaper&a=wallPaperNew&index=1&size=60&bigid=0","http://bz.budejie.com/?typeid=2&ver=3.4.3&no_cry=1&client=android&c=wallPaper&a=hotRecent&index=1&size=60&bigid=0","http://bz.budejie.com/?typeid=2&ver=3.4.3&no_cry=1&client=android&c=wallPaper&a=random&bigid=0"};
    private long exittime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initFragment();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) findViewById(checkedId);
                int position = Integer.parseInt(rb.getTag().toString());
                if(list.get(position).isAdded()){
                    getSupportFragmentManager().beginTransaction().show(list.get(position)).commit();
                }else{
                    getSupportFragmentManager().beginTransaction().add(R.id.frame,list.get(position)).commit();
                }
                getSupportFragmentManager().beginTransaction().hide(list.get(pro)).commit();
                pro =position;
            }
        });
    }

    private void initFragment() {
        list.add(RecommendFragment.newInstance("壁纸精选",url));
        list.add(new CategoryFragment());
        list.add(new SearchFragment());
        list.add(new MoreFragment());
        getSupportFragmentManager().beginTransaction().add(R.id.frame,list.get(0)).commit();
    }

    private void initView() {
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
    }

    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis() - exittime>2000){
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            exittime = System.currentTimeMillis();
        }else{
            finish();
        }
    }
}

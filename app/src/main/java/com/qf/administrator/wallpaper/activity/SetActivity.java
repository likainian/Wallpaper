package com.qf.administrator.wallpaper.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.qf.administrator.wallpaper.R;

import java.io.File;

public class SetActivity extends AppCompatActivity implements View.OnClickListener {

    private Button heep;
    private Button cache;
    private Button version;
    private Button suggest;
    private Button about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        initView();
    }

    private void initView() {
        heep = (Button) findViewById(R.id.heep);
        cache = (Button) findViewById(R.id.cache);
        version = (Button) findViewById(R.id.version);
        suggest = (Button) findViewById(R.id.suggest);
        about = (Button) findViewById(R.id.about);

        heep.setOnClickListener(this);
        cache.setOnClickListener(this);
        version.setOnClickListener(this);
        suggest.setOnClickListener(this);
        about.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.heep:
                Intent intent1 = new Intent(SetActivity.this, UseraboutusActivity.class);
                intent1.putExtra("come","heep");
                startActivity(intent1);
                break;
            case R.id.cache:
               new AlertDialog.Builder(SetActivity.this).setTitle("清理缓存")
                       .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               File file = new File("/mnt/sdcard/wallpaper/cache");
                               if(!file.exists()){
                                   file.mkdirs();
                               }
                               File[] files = file.listFiles();
                               for (int i = 0; i <files.length; i++) {
                                   Log.i("tmd", "onClick: "+files[i]);
                                   files[i].delete();
                               }
                               Toast.makeText(SetActivity.this, "删除成功", Toast.LENGTH_SHORT).show();

                           }
                       }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {

                           }
               }).show();
                break;
            case R.id.version:
                Intent intent2 = new Intent(SetActivity.this, UseraboutusActivity.class);
                intent2.putExtra("come","version");
                startActivity(intent2);
                break;
            case R.id.suggest:
                Intent intent4 = new Intent(SetActivity.this, UseraboutusActivity.class);
                intent4.putExtra("come","反馈");
                startActivity(intent4);
                break;
            case R.id.about:
                Intent intent3 = new Intent(SetActivity.this, UseraboutusActivity.class);
                intent3.putExtra("come","about");
                startActivity(intent3);
                break;
        }
    }
}

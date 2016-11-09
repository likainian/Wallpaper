package com.qf.administrator.wallpaper.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qf.administrator.wallpaper.R;
import com.qf.administrator.wallpaper.view.ProgressImageView;

import java.io.File;
import java.util.ArrayList;

public class MyCollectActivity extends AppCompatActivity {

    private TextView title;
    private Button control;
    private String path;
    private GridView mGridView;
    private ArrayList<Bitmap> list = new ArrayList<>();
    private ArrayList<File> listPosition = new ArrayList<>();
    private MyAdapter adapter;
    private File[] files;
    private boolean flag;
    private boolean allCheck;
    private PopupWindow pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collect);

        String come = getIntent().getStringExtra("come");

        if (come.equals("我的收藏")) {
            path = "/mnt/sdcard/wallpaper/collect";
        } else if (come.equals("我的下载")) {
            path = "/mnt/sdcard/wallpaper/download";
        }
        initView();
        title.setText(come);
        initData();
    }

    private void initData() {
        File file = new File(path);
        files = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            Bitmap bitmap = BitmapFactory.decodeFile(path + File.separator + files[i].getName());
            list.add(bitmap);

        }
        if(files.length!=0){
            initAdapter();
        }
    }

    private void initView() {
        mGridView = (GridView) findViewById(R.id.local_grid);
        title = (TextView) findViewById(R.id.title);
        control = (Button) findViewById(R.id.bt_control);
        control.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = control.getText().toString();
                if (s.equals("管理")) {
                    control.setText("取消");
                    flag = true;
                    adapter.notifyDataSetChanged();
                    View view = LayoutInflater.from(MyCollectActivity.this).inflate(R.layout.pw_delete, null);
                    final Button btAll = (Button) view.findViewById(R.id.bt_all);
                    btAll.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String s1 = btAll.getText().toString();
                            if (s1.equals("全选")) {
                                allCheck = true;
                                btAll.setText("取消");
                                adapter.notifyDataSetChanged();
                            } else if (s1.equals("取消")) {
                                allCheck = false;
                                btAll.setText("全选");
                                adapter.notifyDataSetChanged();
                            }

                        }
                    });
                    Button btDelete = (Button) view.findViewById(R.id.bt_delete);
                    btDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.i("tmd", "onClick: " + listPosition.size());
                            for (int i = 0; i < listPosition.size(); i++) {
                                listPosition.get(i).delete();
                                list.clear();
                                initData();
                                adapter.notifyDataSetChanged();
                            }

                        }
                    });
                    pw = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    pw.showAtLocation(v, Gravity.BOTTOM, 0, 0);
                } else if (s.equals("取消")) {
                    control.setText("管理");
                    if(pw.isShowing()){
                        pw.dismiss();
                    }
                    flag = false;
                    allCheck = false;
                    adapter.notifyDataSetChanged();
                }

            }
        });
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Intent intent = new Intent(MyCollectActivity.this, BigActivity.class);
                Log.i("tmd", "onItemClick: " + files[position].getName());
                intent.putExtra("name", files[position].getName());
                String imageUri = "file://" + path + File.separator + files[position].getName();
                intent.putExtra("url", imageUri);
                startActivity(intent);
            }
        });
        mGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                CheckBox box = (CheckBox) view.findViewById(R.id.check_item);
                box.setVisibility(View.VISIBLE);
                return false;
            }
        });
    }

    private void initAdapter() {
        adapter = new MyAdapter();
        mGridView.setAdapter(adapter);
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(MyCollectActivity.this).inflate(R.layout.item_gridview, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.itemImage = (ProgressImageView) convertView.findViewById(R.id.item_image);
                viewHolder.box = (CheckBox) convertView.findViewById(R.id.check_item);
                int width = getResources().getDisplayMetrics().widthPixels / 3;
                int height = getResources().getDisplayMetrics().heightPixels / 3;
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
                viewHolder.itemImage.setLayoutParams(params);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            if (flag) {
                viewHolder.box.setVisibility(View.VISIBLE);
            } else {
                viewHolder.box.setVisibility(View.GONE);
            }
            if (allCheck) {
                viewHolder.box.setChecked(true);
            } else {
                viewHolder.box.setChecked(false);
            }
            viewHolder.box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        listPosition.add(files[position]);
                    } else {
                        listPosition.remove(files[position]);
                    }

                }
            });
            viewHolder.itemImage.setImageBitmap(list.get(position));
            return convertView;
        }

        public class ViewHolder {
            public ProgressImageView itemImage;
            public CheckBox box;
        }
    }

}

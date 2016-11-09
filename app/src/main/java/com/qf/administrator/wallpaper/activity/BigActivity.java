package com.qf.administrator.wallpaper.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.qf.administrator.wallpaper.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class BigActivity extends Activity {
    private ImageView imageView;
    private View bottomView;
    private Button collect;
    private Button setAs;
    private Button download;
    private PopupWindow bottompw;
    private ImageLoader imageLoader;
    private Bitmap image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big);
        String url = getIntent().getStringExtra("url");
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(BigActivity.this));
        initView();

//        Glide.with(this).load(url).placeholder(R.drawable.loading1).into(imageView);
        imageLoader.displayImage(url, imageView, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                image =loadedImage;
                imageView.setImageBitmap(loadedImage);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bottomView=LayoutInflater.from(BigActivity.this).inflate(R.layout.pw_bottom, null);
                bottomView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(bottompw.isShowing()){
                            bottompw.dismiss();
                        }
                    }
                });
                collect = (Button) bottomView.findViewById(R.id.collect);
                setAs = (Button) bottomView.findViewById(R.id.set_as);
                download = (Button) bottomView.findViewById(R.id.download);
                bottompw= new PopupWindow(bottomView, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
                bottompw.showAtLocation(v, Gravity.CENTER, 0, 0);
            }
        });
    }

    private void initView() {
        imageView = (ImageView) findViewById(R.id.big_image);

    }

    public void click(View v){
        String name = getIntent().getStringExtra("name");
        switch(v.getId()){
            case R.id.image_back:
                finish();
            break;
            case R.id.top_share:
                View view = getLayoutInflater().inflate(R.layout.pw_share, null);
                new AlertDialog.Builder(this).setView(view).show();

                break;
            case R.id.collect:
                File file = new File("/mnt/sdcard/wallpaper/collect");
                if(!file.exists()){
                    file.mkdirs();
                }
                try {
                    FileOutputStream fos = new FileOutputStream(new File("/mnt/sdcard/wallpaper/collect/"+name));
                    image.compress(Bitmap.CompressFormat.PNG,100,fos);
                    try {
                        fos.flush();
                        Toast.makeText(BigActivity.this,"收藏成功",Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                break;
            case R.id.set_as:
                int width = getResources().getDisplayMetrics().widthPixels;
                int height = getResources().getDisplayMetrics().heightPixels;
                WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
                Bitmap scaledBitmap = Bitmap.createScaledBitmap(image, width, height, false);
                try {
                    wallpaperManager.suggestDesiredDimensions(width,height);
                    wallpaperManager.setBitmap(scaledBitmap);
                    Toast.makeText(BigActivity.this,"壁纸设置成功",Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.download:
                File file2 = new File("/mnt/sdcard/wallpaper/download");
                if(!file2.exists()){
                    file2.mkdirs();
                }
                try {
                    FileOutputStream fos = new FileOutputStream(new File("/mnt/sdcard/wallpaper/download/"+name));
                    image.compress(Bitmap.CompressFormat.PNG,100,fos);
                    try {
                        fos.flush();
                        Toast.makeText(BigActivity.this,"收藏成功",Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }


                break;
        }
    }




}

package com.qf.administrator.wallpaper.fragment;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.RelativeLayout;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.nostra13.universalimageloader.cache.disc.impl.LimitedAgeDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.qf.administrator.wallpaper.R;
import com.qf.administrator.wallpaper.activity.BigActivity;
import com.qf.administrator.wallpaper.asynctask.BeansRequest;
import com.qf.administrator.wallpaper.beans.WallPagerBeans;
import com.qf.administrator.wallpaper.view.ProgressImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class GridViewFragment extends Fragment {
    private GridView gridView;
    List<WallPagerBeans.DataBean.WallpaperListInfoBean> list = new ArrayList<>();
    private Context context;

    private RequestQueue queue;
    private ImageLoader imageLoader;

    public GridViewFragment() {
        // Required empty public constructor
    }

    public static GridViewFragment newInstance(String url) {
        Bundle args = new Bundle();
        args.putString("url", url);
        GridViewFragment fragment = new GridViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getContext();
        queue = Volley.newRequestQueue(context);
        imageLoader = ImageLoader.getInstance();
        File file = new File("/mnt/sdcard/wallpaper/cache");
        if(!file.exists()){
            file.mkdirs();
        }
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .diskCache(new LimitedAgeDiskCache(file,3600))
                .diskCacheSize(2 * 1024 * 1024)
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSizePercentage(20)
                .build();
        imageLoader.init(config);
//        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        return inflater.inflate(R.layout.fragment_grid_view, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gridView = (GridView) view.findViewById(R.id.grid_view);
        initDate();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(),BigActivity.class);
                intent.putExtra("name",list.get(position).getPicName());
                intent.putExtra("url",list.get(position).getWallPaperDownloadPath());
                startActivity(intent);
            }
        });
    }

    private void initDate() {
//        new TextAsyncTask<WallPagerBeans>(new TextAsyncTask.TextCallback<WallPagerBeans>() {
//            @Override
//            public void callback(WallPagerBeans result) {
//                list.addAll(result.getData().getWallpaperListInfo());
//                initAdapter();
//            }
//        }, context,WallPagerBeans.class).execute(getArguments().getString("url"));
        BeansRequest<WallPagerBeans> request = new BeansRequest<WallPagerBeans>(
                getArguments().getString("url"), WallPagerBeans.class,
                new Response.Listener<WallPagerBeans>(){
                    @Override
                    public void onResponse(WallPagerBeans response) {
                        list.addAll(response.getData().getWallpaperListInfo());
                        initAdapter();
                    }
                },null);
        queue.add(request);
    }

    private void initAdapter() {
        MyAdapter adapter = new MyAdapter();
        gridView.setAdapter(adapter);
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder viewHolder ;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_gridview, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.itemImage = (ProgressImageView) convertView.findViewById(R.id.item_image);
                int width = getResources().getDisplayMetrics().widthPixels / 3;
                int heigh = getResources().getDisplayMetrics().heightPixels / 3;
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width,heigh);
                viewHolder.itemImage.setLayoutParams(params);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) convertView.getTag();
            }

//            new ImageTask(viewHolder.itemImage).execute(list.get(position).getWallPaperMiddle());
//            Glide.with(context).load(list.get(position).getWallPaperMiddle()).placeholder(R.drawable.loading1).into(viewHolder.itemImage);
            DisplayImageOptions opts = new DisplayImageOptions.Builder()
                    .bitmapConfig(Bitmap.Config.ARGB_8888)
                    .showImageOnFail(R.mipmap.ic_launcher)
                    .showImageOnLoading(R.drawable.loading1)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                    .resetViewBeforeLoading(true)
                    .build();
            imageLoader.displayImage(list.get(position).getWallPaperMiddle()
                    , viewHolder.itemImage, opts, null, new ImageLoadingProgressListener() {
                        @Override
                        public void onProgressUpdate(String imageUri, View view, int current, int total) {
                            Log.i("tmd", "onProgressUpdate: " + current + total);
                            viewHolder.itemImage.setProgress(current*100/total);
                        }
                    });
//            AnimationDrawable ad = (AnimationDrawable) viewHolder.itemImage.getBackground();
//            ad.start();
            return convertView;
        }

        public class ViewHolder {
            public ProgressImageView itemImage;
        }
    }
}

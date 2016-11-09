package com.qf.administrator.wallpaper.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.qf.administrator.wallpaper.R;
import com.qf.administrator.wallpaper.activity.BigActivity;
import com.qf.administrator.wallpaper.asynctask.BeansRequest;
import com.qf.administrator.wallpaper.beans.GridBeans;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class GridFragment extends Fragment {
    private GridView gridView;
    List<GridBeans.DataBean.PicListBean> list = new ArrayList<>();
    private Context context;

    private RequestQueue queue;

    public GridFragment() {
        // Required empty public constructor
    }

    public static GridFragment newInstance(String url) {
        Bundle args = new Bundle();
        args.putString("url", url);
        GridFragment fragment = new GridFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getContext();
        queue = Volley.newRequestQueue(context);
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
        BeansRequest<GridBeans> request = new BeansRequest<>(
                getArguments().getString("url"), GridBeans.class,
                new Response.Listener<GridBeans>(){
                    @Override
                    public void onResponse(GridBeans response) {
                        list.addAll(response.getData().getPic_List());
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
            ViewHolder viewHolder ;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_gridview, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.itemImage = (ImageView) convertView.findViewById(R.id.item_image);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) convertView.getTag();
            }

//            new ImageTask(viewHolder.itemImage).execute(list.get(position).getWallPaperMiddle());
            Glide.with(context).load(list.get(position).getWallPaperMiddle()).placeholder(R.drawable.loading1).into(viewHolder.itemImage);
            return convertView;
        }

        public class ViewHolder {
            public ImageView itemImage;
        }
    }
}

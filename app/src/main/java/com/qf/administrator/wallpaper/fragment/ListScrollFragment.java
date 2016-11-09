package com.qf.administrator.wallpaper.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.qf.administrator.wallpaper.R;
import com.qf.administrator.wallpaper.activity.DetailActivity;
import com.qf.administrator.wallpaper.asynctask.BeansRequest;
import com.qf.administrator.wallpaper.beans.KeyWordBeans;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListScrollFragment extends Fragment {
    private ListView listView;
    private RequestQueue queue;
    private Context context;
    private ArrayList<KeyWordBeans.DataBean> list = new ArrayList<>();
    private MyAdapter adapter;

    public ListScrollFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        queue = Volley.newRequestQueue(getActivity());
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_scroll, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = (ListView) view.findViewById(R.id.list_view_scroll);
        initData();
        initAdapter();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(),DetailActivity.class);
                intent.putExtra("keyWord",list.get(position).getKeyword());
                startActivity(intent);
            }
        });

    }

    private void initData() {

        BeansRequest<KeyWordBeans> request = new BeansRequest<>(
                "http://bz.budejie.com/?typeid=2&ver=3.4.3&no_cry=1&client=android&c=search&a=hot&location=3",
                KeyWordBeans.class, new Response.Listener<KeyWordBeans>() {
            @Override
            public void onResponse(KeyWordBeans response) {
                list.addAll(response.getData());
                adapter.notifyDataSetChanged();
            }
        }, null
        );
        queue.add(request);
    }

    private void initAdapter() {
        adapter = new MyAdapter();
        listView.setAdapter(adapter);
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
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_list_scroll, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            Log.i("tdd", "getView: "+list.get(position).getKeyword());
            holder.textScrollList.setText(list.get(position).getKeyword());
            Glide.with(context).load(list.get(position).getImgs().get(0)).placeholder(R.drawable.loading1).into(holder.imageListSc1);
            Glide.with(context).load(list.get(position).getImgs().get(1)).into(holder.imageListSc2);
            Glide.with(context).load(list.get(position).getImgs().get(2)).into(holder.imageListSc3);
            return convertView;
        }


        public class ViewHolder {
            public View rootView;
            public TextView textScrollList;
            public ImageView imageListSc1;
            public ImageView imageListSc2;
            public ImageView imageListSc3;

            public ViewHolder(View rootView) {
                this.rootView = rootView;
                this.textScrollList = (TextView) rootView.findViewById(R.id.text_scroll_list);
                this.imageListSc1 = (ImageView) rootView.findViewById(R.id.image_list_sc1);
                this.imageListSc2 = (ImageView) rootView.findViewById(R.id.image_list_sc2);
                this.imageListSc3 = (ImageView) rootView.findViewById(R.id.image_list_sc3);
            }

        }
    }
}

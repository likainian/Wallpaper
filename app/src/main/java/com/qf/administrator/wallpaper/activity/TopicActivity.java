package com.qf.administrator.wallpaper.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.qf.administrator.wallpaper.R;
import com.qf.administrator.wallpaper.asynctask.BeansRequest;
import com.qf.administrator.wallpaper.beans.TopicBeans;

import java.util.ArrayList;

import static com.qf.administrator.wallpaper.R.id.list_topic;

public class TopicActivity extends AppCompatActivity {
    private RequestQueue queue;
    private ListView listTopic;
    private ArrayList<TopicBeans.DataBean.TopicBean> topicList = new ArrayList<>();
    private TopicAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);
        queue = Volley.newRequestQueue(this);
        initView();
        initData();
        initAdapter();
        listTopic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(TopicActivity.this,DetailActivity.class);
                intent.putExtra("id",topicList.get(position).getId());
                intent.putExtra("keyWord",topicList.get(position).getSubject());
                startActivity(intent);
            }
        });
    }

    private void initAdapter() {
        adapter = new TopicAdapter();
        listTopic.setAdapter(adapter);
    }

    private void initData() {
        BeansRequest<TopicBeans> request = new BeansRequest<>(
                "http://bz.budejie.com/?typeid=2&ver=3.4.3&no_cry=1&client=android&c=topic&a=list&topictype=2&size=10",
                TopicBeans.class, new Response.Listener<TopicBeans>() {
            @Override
            public void onResponse(TopicBeans response) {
                topicList.addAll(response.getData().getTopic());
                adapter.notifyDataSetChanged();
            }
        }, null
        );
        queue.add(request);
    }

    private void initView() {
        listTopic = (ListView) findViewById(list_topic);
    }

    class TopicAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return topicList.size();
        }

        @Override
        public Object getItem(int position) {
            return topicList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(TopicActivity.this).inflate(R.layout.item_topic, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }
            Glide.with(TopicActivity.this).load(topicList.get(position).getFocus_picture_path()).placeholder(R.drawable.loading1).into(holder.imageTopic);

            return convertView;
        }

        public class ViewHolder {
            public View rootView;
            public ImageView imageTopic;

            public ViewHolder(View rootView) {
                this.rootView = rootView;
                this.imageTopic = (ImageView) rootView.findViewById(R.id.image_topic);
            }

        }
    }
}

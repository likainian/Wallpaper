package com.qf.administrator.wallpaper.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.qf.administrator.wallpaper.R;
import com.qf.administrator.wallpaper.activity.DetailActivity;
import com.qf.administrator.wallpaper.activity.TopicActivity;
import com.qf.administrator.wallpaper.asynctask.BeansRequest;
import com.qf.administrator.wallpaper.beans.KeyWordBeans;
import com.qf.administrator.wallpaper.beans.TopicBeans;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HorizontalFragment extends Fragment {

    private LinearLayout hsLayout;
    private ViewPager scrollPager;
    private RadioGroup group;

    private ArrayList<KeyWordBeans.DataBean> linesrList = new ArrayList<>();
    private ArrayList<TopicBeans.DataBean.TopicBean> topicList = new ArrayList<>();
    private ArrayList<View> imageList = new ArrayList<>();

    private RequestQueue queue;
    private Context context;
    private MyAdapter adapter;
    private TextView textMore;

    private Handler handler = new Handler();
    private Runnable r = new Runnable() {
        @Override
        public void run() {
            scrollPager.setCurrentItem(scrollPager.getCurrentItem()+1);
            handler.postDelayed(r,2000);
        }
    };

    public HorizontalFragment() {
        // Required empty public constructor
        Log.i("tdd", "HorizontalFragment: ");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        queue = Volley.newRequestQueue(getActivity());
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_horizontal, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        hsLayout = (LinearLayout) view.findViewById(R.id.hs_layout);
        scrollPager = (ViewPager) view.findViewById(R.id.scroll_pager);
        group = (RadioGroup) view.findViewById(R.id.group);
        textMore = (TextView) view.findViewById(R.id.Text_more);
        initLinear();
        initPager();
        initListener();

    }

    private void initListener() {
        textMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),TopicActivity.class);
                startActivity(intent);
            }
        });
        scrollPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                group.check(position%imageList.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initLinear() {

        BeansRequest<KeyWordBeans> request = new BeansRequest<>(
                "http://bz.budejie.com/?typeid=2&ver=3.4.3&no_cry=1&client=android&c=search&a=hot&location=1",
                KeyWordBeans.class, new Response.Listener<KeyWordBeans>() {
            @Override
            public void onResponse(KeyWordBeans response) {
                linesrList.addAll(response.getData());
                initLinearSet();
            }
        }, null
        );
        queue.add(request);
    }

    private void initLinearSet() {
        for (int i = 0; i < linesrList.size(); i++) {
            View linearView = LayoutInflater.from(context).inflate(R.layout.item_hsimage,null);
            ImageView hsImage = (ImageView) linearView.findViewById(R.id.hs_image);
            TextView hsText = (TextView) linearView.findViewById(R.id.hs_text);
            final int finalI = i;
            hsImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(),DetailActivity.class);
                    intent.putExtra("keyWord",linesrList.get(finalI).getKeyword());
                    startActivity(intent);
                }
            });
            hsText.setText(linesrList.get(i).getKeyword());
            Glide.with(context).load(linesrList.get(i).getImgs().get(0)).placeholder(R.drawable.loading1).into(hsImage);
            hsLayout.addView(linearView);
        }
    }

    private void initPager() {
        initData();
//        initAdapter();
    }

    private void initAdapter() {
        adapter = new MyAdapter();
        scrollPager.setAdapter(adapter);

    }

    private void initData() {
        BeansRequest<TopicBeans> request = new BeansRequest<>(
                "http://bz.budejie.com/?typeid=2&ver=3.4.3&no_cry=1&client=android&c=topic&a=list&topictype=2&size=10",
                TopicBeans.class, new Response.Listener<TopicBeans>() {
            @Override
            public void onResponse(TopicBeans response) {
                topicList.addAll(response.getData().getTopic());
                initSet();
            }
        }, null
        );
        queue.add(request);
    }

    private void initSet() {
        Log.i("tdd", "initSet: "+topicList.size());
        for (int i = 0; i < topicList.size(); i=i+2) {
            View imageView = LayoutInflater.from(context).inflate(R.layout.item_image,null);
            ImageView image1 = (ImageView) imageView.findViewById(R.id.item_image1);
            ImageView image2 = (ImageView) imageView.findViewById(R.id.item_image2);
            Glide.with(context).load(topicList.get(i).getCover_path()).placeholder(R.drawable.loading1).into(image1);
            Glide.with(context).load(topicList.get(i+1).getCover_path()).placeholder(R.drawable.loading1).into(image2);
            final int finalI = i;
            image1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(),DetailActivity.class);
                    intent.putExtra("id",topicList.get(finalI).getId());
                    intent.putExtra("keyWord",topicList.get(finalI).getSubject());
                    startActivity(intent);
                }
            });
            image2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(),DetailActivity.class);
                    intent.putExtra("id",topicList.get(finalI+1).getId());
                    intent.putExtra("keyWord",topicList.get(finalI+1).getSubject());
                    startActivity(intent);
                }
            });

            imageList.add(imageView);

            RadioButton rb = new RadioButton(context);
            rb.setId(i/2);
            group.addView(rb);

        }
        group.check(0);
        handler.postDelayed(r,2000);
        initAdapter();
    }

    class MyAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(imageList.get(position%imageList.size()));
            return imageList.get(position%imageList.size());
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(imageList.get(position%imageList.size()));
        }
    }
}

package com.qf.administrator.wallpaper.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qf.administrator.wallpaper.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecommendFragment extends Fragment {
    private TextView textView;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String[] title = new String[]{"最新","热门","随机"};
    private String[] url = new String[3];
    private ArrayList<android.support.v4.app.Fragment> list = new ArrayList<>();
    private MyAdapter adapter;

    public RecommendFragment() {
        // Required empty public constructor
    }

    public static RecommendFragment newInstance(String cate,String[] url) {
        Log.i("tmd", "newInstance: "+cate+url[0]+url[1]+url[2]);
        Bundle args = new Bundle();
        args.putString("cate",cate);
        args.putString("new",url[0]);
        args.putString("hot",url[1]);
        args.putString("random",url[2]);
        RecommendFragment fragment = new RecommendFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recommend, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textView = (TextView) view.findViewById(R.id.text_view);
        tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        viewPager = (ViewPager)view.findViewById(R.id.view_pager);
        textView.setText(getArguments().getString("cate"));
        url[0]=getArguments().getString("new");
        url[1]=getArguments().getString("hot");
        url[2]=getArguments().getString("random");
        initData();

        initAdapter();
        tabLayout.setTabTextColors(Color.BLACK, Color.rgb(10,191,76));
        tabLayout.setSelectedTabIndicatorColor(Color.rgb(10,191,76));
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initAdapter() {
        adapter = new MyAdapter(getFragmentManager());
        viewPager.setAdapter(adapter);
    }
    private void initData() {
        for (int i = 0; i < title.length; i++) {
            list.add(GridViewFragment.newInstance(url[i]));
        }
    }


    class MyAdapter extends FragmentStatePagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return title[position];
        }
    }


}

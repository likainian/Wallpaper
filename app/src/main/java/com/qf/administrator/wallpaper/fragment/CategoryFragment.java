package com.qf.administrator.wallpaper.fragment;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.qf.administrator.wallpaper.R;
import com.qf.administrator.wallpaper.activity.CateActivity;
import com.qf.administrator.wallpaper.asynctask.TextAsyncTask;
import com.qf.administrator.wallpaper.beans.Listbeans;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment {

    private Context context;
    private ListView listView;
    private ArrayList<Listbeans.DataBean> list = new ArrayList<>();
    private MyAdapter adapter;


    public CategoryFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity();
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = (ListView) view.findViewById(R.id.list_view);
        listView.setDividerHeight(0);
        initDate();
        initAdapter();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(),CateActivity.class);
                intent.putExtra("position",position);
                intent.putExtra("ID",list.get(position).getID());
                intent.putExtra("cate",list.get(position).getPicCategoryName());
                startActivity(intent);
            }
        });
    }

    private void initDate() {
        new TextAsyncTask<Listbeans>(new TextAsyncTask.TextCallback<Listbeans>() {
            @Override
            public void callback(Listbeans result) {
                list.addAll(result.getData());
                initAdapter();
            }
        }, context, Listbeans.class).execute("http://bz.budejie.com/?typeid=2&ver=3.4.3&no_cry=1&client=android&c=wallPaper&a=category");

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
            Holder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_listview, parent, false);
                holder = new Holder();
                holder.listImage = (ImageView) convertView.findViewById(R.id.list_image);
                holder.listName = (TextView) convertView.findViewById(R.id.list_name);
                holder.listWord = (TextView) convertView.findViewById(R.id.list_word);
                convertView.setTag(holder);
            }else{
                holder = (Holder) convertView.getTag();
            }
            Glide.with(context).load(list.get(position).getCategoryPic()).placeholder(R.drawable.loading1).into(holder.listImage);
            holder.listName.setText(list.get(position).getPicCategoryName());
            holder.listWord.setText(list.get(position).getDescWords());
            return convertView;
        }

        public class Holder {
            public ImageView listImage;
            public TextView listName;
            public TextView listWord;

        }
    }
    class GlideRound extends BitmapTransformation {

        public GlideRound(Context context) {
            super(context);
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            Bitmap bitmap1 = Bitmap.createBitmap(toTransform.getWidth(),toTransform.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap1);
            RectF rect = new RectF(0,0,toTransform.getWidth(),toTransform.getHeight());
            Paint paint = new Paint();
            paint.setAntiAlias(true);  //抗锯齿效果
            paint.setColor(Color.RED);
            canvas.drawRoundRect(rect,30,30,paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
            canvas.drawBitmap(toTransform,0,0,paint);
            return bitmap1;
        }

        @Override
        public String getId() {
            return getClass().getName();
        }
    }
}

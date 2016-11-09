package com.qf.administrator.wallpaper.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.qf.administrator.wallpaper.R;
import com.qf.administrator.wallpaper.activity.DetailActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {
    private Context context;
    private Button ScButton;

    private EditText editText;
    private Button button;
    private PopupWindow pw;


    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        // Inflate the layout for this fragment
        getChildFragmentManager().beginTransaction().add(R.id.frame_horizontal_scroll,new HorizontalFragment()).commit();
        getChildFragmentManager().beginTransaction().add(R.id.frame_list_scroll,new ListScrollFragment()).commit();
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ScButton = (Button) view.findViewById(R.id.but_search);
        ScButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View editView=LayoutInflater.from(context).inflate(R.layout.pw_edit, null);
                editView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(pw.isShowing()){
                            pw.dismiss();
                        }
                    }
                });
                editText = (EditText) editView.findViewById(R.id.editText);
                button = (Button) editView.findViewById(R.id.button);
                initclick();
                pw = new PopupWindow(editView, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                pw.setOutsideTouchable(true);
                pw.setFocusable(true);
                pw.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                pw.showAtLocation(view, Gravity.CENTER_HORIZONTAL,0,0);
            }
        });

    }
    public void initclick(){
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() != 0){
                    button.setText("搜索");
                }else{
                    button.setText("取消");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = button.getText().toString();
                if(s.equals("搜索")){
                    String keyWord = editText.getText().toString();
                    if(keyWord!=""&&keyWord!=null){
                        Intent intent = new Intent(getActivity(),DetailActivity.class);
                        intent.putExtra("keyWord",keyWord);
                        startActivity(intent);
                    }
                }else{
                    pw.dismiss();
                }

            }
        });
    }
}

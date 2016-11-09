package com.qf.administrator.wallpaper.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 16/10/24.
 */

public class TextAsyncTask<T> extends AsyncTask<String, Void, T> {
    public interface TextCallback<T> {
        void callback(T result);
    }

    private TextCallback textCallback;
    private ProgressDialog pd;
    private Class<T> clazz;

    public TextAsyncTask(TextCallback back, Context context,Class<T> clazz) {
        super();
        this.textCallback = back;
        this.clazz = clazz;
//        pd = new ProgressDialog(context);
//        pd.setMessage("loading... ...");
//        pd.setCancelable(false);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
//        pd.show();
    }

    @Override
    protected T doInBackground(String... params) {
        Log.i("ttt", "doInBackground: "+params[0]);
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(params[0]).openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            if (conn.getResponseCode() == 200 ) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
//                InputStream is = conn.getInputStream();
//                byte[] b = new byte[1024];
//                int num = -1;
                StringBuffer sb = new StringBuffer();
//                while((num = is.read(b)) != -1) {
//                    sb.append(new String(b,0,num));
//                }
                String line;
                while ((line =br.readLine())!=null){
                    sb.append(line);
                }
                String json = sb.toString();
                Log.i("ttt", "doInBackground:json "+json);
                T bean = new Gson().fromJson(json, clazz);

                return bean;
            }

        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(T Beans) {
        super.onPostExecute(Beans);
//        pd.dismiss();
        if (textCallback != null && Beans != null) {
            textCallback.callback(Beans);
        }
    }
}

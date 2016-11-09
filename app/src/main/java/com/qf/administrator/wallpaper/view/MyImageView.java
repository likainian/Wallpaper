package com.qf.administrator.wallpaper.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Administrator on 16/10/25.
 */

public class MyImageView extends ImageView {
    private Bitmap mSrcBitmap;

    private Paint paint;
    public MyImageView(Context context) {
        this(context,null);
    }

    public MyImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint  = new Paint();
        int srcResource = attrs.getAttributeResourceValue(
                "http://schemas.android.com/apk/res/android", "src", 0);
        if (srcResource != 0)
            mSrcBitmap = BitmapFactory.decodeResource(getResources(),
                    srcResource);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Bitmap bitmap = drawableToBitmap(this.getDrawable());
        if(bitmap!=null){
            //创建一个与直角图片等宽等高的纯色的Bitmap对象
            Bitmap bitmap1 = Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            //创建mCanvas画布对象
            Canvas mcanvas = new Canvas(bitmap1);

            // 绘制一个与直角图片等宽等高的圆角矩形

            RectF rectf = new RectF(0,0,bitmap.getWidth(),bitmap.getHeight());
            Paint paint = new Paint();
            paint.setAntiAlias(true);  //抗锯齿效果
            paint.setColor(Color.RED);
            mcanvas.drawRoundRect(rectf,20,20,paint);


            //设置连个图形的相交保留原则
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

            // 绘制直角图片
            mcanvas.drawBitmap(bitmap,0,0,paint);

            Rect rectSrc = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            Rect rectDest = new Rect(0,0,getWidth(),getHeight());
            canvas.drawBitmap(bitmap1, rectSrc , rectDest, null);
        }else {
            super.onDraw(canvas);
        }



    }
    private Bitmap drawableToBitmap(Drawable drawable) {

        if (drawable == null) {
            if (mSrcBitmap != null) {
                return mSrcBitmap;
            } else {
                return null;
            }
        } else if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }
}

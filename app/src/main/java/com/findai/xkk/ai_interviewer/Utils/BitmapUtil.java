package com.findai.xkk.ai_interviewer.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

import java.io.ByteArrayOutputStream;

public class BitmapUtil {

    public static byte[] Bitmap2Bytes(android.graphics.Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(android.graphics.Bitmap.CompressFormat.PNG, 50, baos);
        return baos.toByteArray();
    }


    public static Bitmap Bytes2Bimap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }

    public static Bitmap toRoundBitmap(Bitmap bitmap) {
        // 前面同上，绘制图像分别需要bitmap，canvas，paint对象
        bitmap = Bitmap.createScaledBitmap(bitmap, 410, 410, true);
        Bitmap bm = Bitmap.createBitmap(410, 410, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bm);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // 这里需要先画出一个圆
        canvas.drawCircle(210, 210, 210, paint);
        // 圆画好之后将画笔重置一下
        paint.reset();
        // 设置图像合成模式，该模式为只在源图像和目标图像相交的地方绘制源图像
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return bm;
    }

}

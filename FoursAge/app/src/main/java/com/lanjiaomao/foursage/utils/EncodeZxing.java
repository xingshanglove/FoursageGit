package com.lanjiaomao.foursage.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

/**
 * Created by root on 2016/5/12.
 */
public class EncodeZxing {
    public static Bitmap createBitmap(String str, String path) {
        // 生成二维矩阵,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败
        int IMAGE_HALFWIDTH = 25;
        Bitmap dBitmap = BitmapFactory.decodeFile(path);
        if(dBitmap==null)
            return null;
        // 缩放图片
        Matrix dMatrix = new Matrix();
        float sx = (float) 2 * 20 / dBitmap.getWidth();
        float sy = (float) 2 * 20 / dBitmap.getHeight();
        dMatrix.setScale(sx, sy);
        // 重新构造一个40*40的图片
        dBitmap = Bitmap.createBitmap(dBitmap, 0, 0, dBitmap.getWidth(),
                dBitmap.getHeight(), dMatrix, false);

        Bitmap bitmap = Bitmap.createBitmap(50, 50, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.save();
        canvas.drawBitmap(dBitmap, 5, 5, null);
        canvas.restore();

        try {
            BitMatrix matrix = new MultiFormatWriter().encode(str,
                    BarcodeFormat.QR_CODE, 300, 300);
            int width = matrix.getWidth();
            int height = matrix.getHeight();
            // 二维矩阵转为一维像素数组,也就是一直横着排了
            int halfW = width / 2;
            int halfH = height / 2;
            int[] pixels = new int[width * height];
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (x > halfW - IMAGE_HALFWIDTH
                            && x < halfW + IMAGE_HALFWIDTH
                            && y > halfH - IMAGE_HALFWIDTH
                            && y < halfH + IMAGE_HALFWIDTH) {
                        pixels[y * width + x] = bitmap.getPixel(x - halfW
                                + IMAGE_HALFWIDTH, y - halfH + IMAGE_HALFWIDTH);
                    } else {
                        if (matrix.get(x, y)) {
                            pixels[y * width + x] = 0xff000000;
                        }
                    }

                }
            }
            Bitmap rBitmap = Bitmap.createBitmap(width, height,
                    Bitmap.Config.ARGB_8888);
            // 通过像素数组生成bitmap
            rBitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            return rBitmap;
        } catch (WriterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;

    }
}

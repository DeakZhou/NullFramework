package com.ricky.f.util.lib.picasso;

import android.graphics.Bitmap;

import com.squareup.picasso.Transformation;

/**
 * Created by Deak on 16/10/31.
 */

public class ReSizeTransformation implements Transformation {

    private int width;
    private int height;
    private String key;

    public ReSizeTransformation(int width, int height) {
        this(width, height, width + "*" + height);
    }

    public ReSizeTransformation(int width, int height, String key) {
        this.width = width;
        this.height = height;
        this.key = key;
    }

    @Override
    public Bitmap transform(Bitmap source) {
        int size = Math.min(source.getWidth(), source.getHeight());
        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;

        Bitmap bitmap = Bitmap.createBitmap(source, x, y, size, size);
        if (bitmap != source) {
            source.recycle();
        }
        return bitmap;
    }

    @Override
    public String key() {

        return key;
    }
}

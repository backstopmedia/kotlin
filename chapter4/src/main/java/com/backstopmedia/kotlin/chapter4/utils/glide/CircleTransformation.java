package com.backstopmedia.kotlin.chapter4.utils.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * Created by Tudor Luca on 15/10/15.
 */
public class CircleTransformation extends BitmapTransformation {

    public static final int PAINT_FLAGS = Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG | Paint.FILTER_BITMAP_FLAG;
    private final float mBorderWidth;
    private final int mBorderColor;

    public CircleTransformation(Context context) {
        super(context);
        mBorderWidth = 0;
        mBorderColor = Color.TRANSPARENT;
    }

    public CircleTransformation(Context context, float borderWidth, int borderColor) {
        super(context);
        mBorderWidth = borderWidth;
        mBorderColor = borderColor;
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        final Bitmap toReuse = pool.get(outWidth, outHeight, Bitmap.Config.ARGB_8888);
        Bitmap transformed = circleCrop(toReuse, toTransform, outWidth, outHeight, mBorderWidth, mBorderColor);
        if (toReuse != null && toReuse != transformed && !pool.put(toReuse)) {
            toReuse.recycle();
        }
        return transformed;
    }

    public Bitmap circleCrop(Bitmap recycled, Bitmap toTransform, int width, int height,
                             float borderWidth, int borderColor) {
        if (toTransform == null) {
            return null;
        }

        final Bitmap result;
        if (recycled != null) {
            result = recycled;
        } else {
            result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        }

        final float size = Math.min(width, height);
        final float x = width / 2f;
        final float y = height / 2f;
        final float imageSize = size - 2 * borderWidth;

        Shader shader = new BitmapShader(toTransform, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Matrix m = computeCenterFitMatrix(toTransform, width, height);
        m.postTranslate(borderWidth, borderWidth);
        shader.setLocalMatrix(m);

        Canvas canvas = new Canvas(result);
        Paint paint = new Paint(PAINT_FLAGS);
        paint.setShader(shader);

        float r = imageSize / 2;
        canvas.drawCircle(x, y, r, paint);

        Paint borderPaint = new Paint(PAINT_FLAGS);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(borderWidth);
        borderPaint.setColor(borderColor);

        r = (size - borderWidth) / 2;
        canvas.drawCircle(x, y, r, borderPaint);

        return result;
    }

    private Matrix computeCenterFitMatrix(Bitmap toFit, float width, float height) {
        RectF bitmap = new RectF(0, 0, toFit.getWidth(), toFit.getHeight());
        RectF dest = new RectF(0, 0, width, height);
        Matrix m = new Matrix();
        m.setRectToRect(bitmap, dest, Matrix.ScaleToFit.CENTER);
        return m;
    }

    @Override
    public String getId() {
        return "CircleTransformation";
    }
}

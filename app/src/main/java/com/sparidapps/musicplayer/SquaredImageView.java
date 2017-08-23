package com.sparidapps.musicplayer;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Tarek Zoubi on 17/8/2017.
 */

public class SquaredImageView extends android.support.v7.widget.AppCompatImageView {

    public SquaredImageView(Context context) {
        super(context);
    }

    public SquaredImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SquaredImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredHeight = getMeasuredHeight();
        int meausredWidth = getMeasuredWidth();
        if(measuredHeight != meausredWidth)
        {
            setMeasuredDimension(meausredWidth, meausredWidth);
        }

    }
}

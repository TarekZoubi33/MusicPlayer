package com.sparidapps.musicplayer;

import android.support.v4.view.ViewPager;
import android.view.View;


/**
 * Created by Tarek Zoubi on 2/8/2017.
 */

public class ZoomOutPagerTransformer implements ViewPager.PageTransformer {
    private static final float MIN_SCALE = 0.85f;
    private static final float MIN_ALPHA = 0.5f;

    @Override
    public void transformPage(View page, float position) {
        int pageWidth = page.getWidth();
        int pageHeight = page.getHeight();

        if(position < -1){
            page.setAlpha(0);
        }
        else if (position <= 1){
            float scaleFactor = Math.max(MIN_SCALE,1 - Math.abs(position));
            float veritcalMargin = pageHeight *(1 - scaleFactor) / 2;
            float horiztonalMargin = pageWidth *(1 - scaleFactor) / 2;
            if(position < 0){
                page.setTranslationX(horiztonalMargin - veritcalMargin / 2);
            }
            else{
                page.setTranslationX(-horiztonalMargin + veritcalMargin / 2);
            }

            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);

            page.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE) / (1- MIN_SCALE) * (1 - MIN_ALPHA));
        }
        else{
            page.setAlpha(0);
        }

    }
}

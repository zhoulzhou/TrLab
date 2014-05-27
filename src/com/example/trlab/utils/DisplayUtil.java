package com.example.trlab.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class DisplayUtil{
	private static float mDensity = 1.5f;

    public static void initDensity(Context context) {
        DisplayMetrics outMetrics = new DisplayMetrics();
        WindowManager mWindowManage = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mWindowManage.getDefaultDisplay().getMetrics(outMetrics);
        mDensity = outMetrics.density;
    }

    public static int dpToPx(int dpValue) {
        return (int) (dpValue * mDensity);
    }
}
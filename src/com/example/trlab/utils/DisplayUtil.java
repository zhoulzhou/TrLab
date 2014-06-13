package com.example.trlab.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class DisplayUtil{
	public static float DENSITY = 1.5f;
	public static int WIDTH = 0;
	public static int HEIGHT = 0;

    public static void initDensity(Context context) {
        DisplayMetrics outMetrics = new DisplayMetrics();
        WindowManager mWindowManage = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mWindowManage.getDefaultDisplay().getMetrics(outMetrics);
        DENSITY = outMetrics.density;
        WIDTH = outMetrics.widthPixels;
        HEIGHT = outMetrics.heightPixels;
    }

    public static int dpToPx(int dpValue) {
        return (int) (dpValue * DENSITY);
    }
    
}
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:background="@drawable/springlistviewbackground" >

    <RelativeLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content" >

        <LinearLayout
            android:id="@+id/tab_title"
            android:layout_alignParentLeft="true"
            android:layout_alignParentTop="true"
            android:layout_width="fill_parent"
            android:layout_height="wrap_content"
            android:orientation="horizontal"
            android:paddingTop="10dp"
            android:background="@drawable/actionbar_tab_bg" >
        </LinearLayout>

        <com.example.trlab.vp.SlideTitleView
            android:id="@+id/slide_title"
            android:layout_width="fill_parent"
            android:layout_height="wrap_content"
            android:layout_alignParentLeft="true"
            android:layout_alignParentTop="true"
            android:background="@color/transparent" />
        
    </RelativeLayout>

    <com.example.trlab.vp.MyViewPager
        android:id="@+id/tab_pager"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_gravity="center"
        android:flipInterval="30"
        android:persistentDrawingCache="animation"
        android:background="@color/transparent"
        android:overScrollMode="never"
        android:layout_marginTop="2dp" />

</LinearLayout>
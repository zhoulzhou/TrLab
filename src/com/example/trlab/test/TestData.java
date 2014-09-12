package com.example.trlab.test;

import java.util.ArrayList;
import java.util.List;

public class TestData {
    public String picUrl;
    public String name;
    public String date;
    public int type;
    public List<Child> childList = new ArrayList<Child>();
    public int size;
    
    public List<Child> getProductList() {
        return childList;
    }

    public int getProductCount() {
        return size;
    }

    public Child getProduct(int position) {
        return childList.get(position);
    }

    public String getName() {
        return name;
    }
    

    public int getResType() {
        return type;
    }

    public class Child {
        public String picUrl;
        public String name;
        public String date;
        public int type;

        public int getAppType() {
            return type;
        }

        public String getName() {
            return name;
        }

        public String getPicUrl(int i) {
            return picUrl;
        }
    }
    
    
    private static final int TYPE_THEME = 0;
    private static final int TYPE_WALLPAPER = 0;
    private static final int TYPE_LOCK = 0;
    private static final int TYPE_DYNAMIC_WALLPAPER = 0;
    private static final int TYPE_FONT = 0;
    
    public static List<TestData> initData(){
        List<TestData> dataList = new ArrayList<TestData>();
        TestData data1 = new TestData();
        data1.name = "theme title";
        data1.type = TYPE_THEME;
        for(int i=0 ; i<4; i++){
           TestData.Child child = data1.new Child();
           child.name = "item " + i;
           child.type = TYPE_THEME;
           data1.childList.add(child);
        }
        data1.size = data1.childList.size();
        
        TestData data2 = new TestData();
        data2.name = "wallpaper title";
        data2.type = TYPE_WALLPAPER;
        for(int i=0 ; i<2; i++){
           TestData.Child child = data2.new Child();
           child.name = "item " + i;
           child.type = TYPE_WALLPAPER;
           data2.childList.add(child);
        }
        data2.size = data2.childList.size();
        
        TestData data3 = new TestData();
        data3.name = "lock title";
        data3.type = TYPE_LOCK;
        for(int i=0 ; i<2; i++){
           TestData.Child child = data3.new Child();
           child.name = "item " + i;
           child.type = TYPE_LOCK;
           data3.childList.add(child);
        }
        data3.size = data3.childList.size();
        
        TestData data4 = new TestData();
        data4.name = "dynamic wallpaper title";
        data4.type = TYPE_DYNAMIC_WALLPAPER;
        for(int i=0 ; i<10; i++){
           TestData.Child child = data4.new Child();
           child.name = "item " + i;
           child.type = TYPE_DYNAMIC_WALLPAPER;
           data4.childList.add(child);
        }
        data4.size = data4.childList.size();
        
        TestData data5 = new TestData();
        data5.name = "font title";
        data5.type = TYPE_FONT;
        for(int i=0 ; i<6; i++){
           TestData.Child child = data5.new Child();
           child.name = "item " + i;
           child.type = TYPE_FONT;
           data5.childList.add(child);
        }
        data5.size = data5.childList.size();
        
        dataList.add(data1);
        dataList.add(data2);
        dataList.add(data3);
        dataList.add(data4);
        dataList.add(data5);
        return dataList;
    }

}
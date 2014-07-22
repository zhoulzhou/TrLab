package com.example.trlab.hlv;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

public class ElasticHLV extends HorizontalListView{
//	private View mHeadView,mTailView;
//
	public ElasticHLV(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
//	
//	@SuppressWarnings("deprecation")
//    private void init(boolean isHeadViewNeed, boolean isTailViewNeed) {
//        Log.d("zhou", "isHeadViewNeed=" + isHeadViewNeed);
//        Log.d("zhou", "isTailViewNeed=" + isTailViewNeed);
//        if(isHeadViewNeed) {
//            // 监听滚动状态
//            setOnScrollListener(this);
//            // 创建PullListView的HeadView
//            mHeadView = new View(this.getContext());
//            // 默认白色背景,可以改变颜色, 也可以设置背景图片
//            mHeadView.setBackgroundColor(Color.parseColor("#4F9D9D"));
//            // 默认高度为0
//            mHeadView.setLayoutParams(new AbsListView.LayoutParams(
//                    LayoutParams.FILL_PARENT, 0));
//            super.addHeaderView(mHeadView);
//        } 
//         
//        if(isTailViewNeed) {
//            // 监听滚动状态
//            setOnScrollListener(this);
//            // 创建PullListView的HeadView
//            mTailView = new View(this.getContext());
//            // 默认白色背景,可以改变颜色, 也可以设置背景图片
//            mTailView.setBackgroundColor(Color.parseColor("#4F9D9D"));
//            // 默认高度为0
//            mTailView.setLayoutParams(new AbsListView.LayoutParams(
//                    LayoutParams.FILL_PARENT, 0));
//            super.addFootView(mTailView);
//        }
//    }
//	
//	boolean isRecordPullDown,isRecordPullUp;
//	ScheduledExecutorService schedulor;
//	@SuppressWarnings("deprecation")
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
// 
//        switch (event.getAction()) {
//        case MotionEvent.ACTION_DOWN:
//            break;
//        case MotionEvent.ACTION_CANCEL:
//        case MotionEvent.ACTION_UP:
//            if (!isRecordPullDown && !isRecordPullUp) {
//                // it's not in pull down state or pull up state, break
////                Log.d(TAG, "ACTION_UP it's not in pull down state or pull up state, break");
//                break;
//            }
//            
//			if(isPullDownState()) {
//                Log.d(TAG, "isRecordPullDown=" + isRecordPullDown);
//                // 以一定的频率递减HeadView的高度,实现平滑回弹
//                schedulor = Executors.newScheduledThreadPool(1);
//                schedulor.scheduleAtFixedRate(new Runnable() {
//     
//                    @Override
//                    public void run() {
//                        mHandler.sendEmptyMessage(PULL_DOWN_BACK_ACTION);
//     
//                    }
//                }, 0, PULL_BACK_TASK_PERIOD, TimeUnit.NANOSECONDS);
//     
//                setPullDownState(!isRecordPullDown);
//            } else if(isPullUpState()) {
//                Log.d(TAG, "isRecordPullUp=" + isRecordPullUp);
//                // 以一定的频率递减HeadView的高度,实现平滑回弹
//                schedulor = Executors.newScheduledThreadPool(1);
//                schedulor.scheduleAtFixedRate(new Runnable() {
//     
//                    @Override
//                    public void run() {
//                        mHandler.sendEmptyMessage(PULL_UP_BACK_ACTION);
//     
//                    }
//                }, 0, PULL_BACK_TASK_PERIOD, TimeUnit.NANOSECONDS);
//     
//                setPullUpState(!isRecordPullUp);
//            }
// 
//            break;
// 
//        case MotionEvent.ACTION_MOVE:
////            Log.d(TAG, "firstItemIndex=" + firstItemIndex);
//            if (!isRecordPullDown && firstItemIndex == 0) {
////                Log.d(TAG, "firstItemIndex=" + firstItemIndex + " set isRecordPullDown=true");
//                startPullDownY = (int) event.getY();
//                setPullType(PULL_DOWN_BACK_ACTION);
//            }else if (!isRecordPullUp && lastItemIndex == getCount()) {
//                Log.d(TAG, "lastItemIndex == getCount()" + " set isRecordPullUp=true");
//                startPullUpY = (int) event.getY();
//                setPullType(PULL_UP_BACK_ACTION);
//            }
// 
//            if (!isRecordPullDown && !isRecordPullUp) {
//                // it's not in pull down state or pull up state, break
//                Log.d(TAG, "ACTION_MOVE it's not in pull down state or pull up state, break");
//                break;
//            }
// 
//            if(isRecordPullDown) {
//                int tempY = (int) event.getY();
//                int moveY = tempY - startPullDownY;
//                if (moveY < 0) {
//                    setPullDownState(false);
//                    break;
//                }
//     
//                Log.d(TAG, "tempY=" + tempY);
//                Log.d(TAG, "startPullDownY=" + startPullDownY);
//                Log.d(TAG, "moveY=" + moveY);
//                mHeadView.setLayoutParams(new AbsListView.LayoutParams(
//                        LayoutParams.FILL_PARENT, (int) (moveY * PULL_FACTOR)));
//                mHeadView.invalidate();
//            } else if(isRecordPullUp) {
//                int tempY = (int) event.getY();
//                int moveY = startPullUpY - tempY;
//                if (moveY < 0) {
//                    setPullUpState(false);
//                    break;
//                }
//     
//                Log.d(TAG, "tempY=" + tempY);
//                Log.d(TAG, "startPullUpY=" + startPullUpY);
//                Log.d(TAG, "moveY=" + moveY);
//                mTailView.setLayoutParams(new AbsListView.LayoutParams(
//                        LayoutParams.FILL_PARENT, (int) (moveY * PULL_FACTOR)));
//                mTailView.invalidate();
//            }
// 
//            break;
//        }
//        return super.onTouchEvent(event);
//    }
//	
////	@SuppressLint("HandlerLeak")
//    private Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch(msg.what) {
//            case PULL_DOWN_BACK_ACTION:
//                AbsListView.LayoutParams headerParams = (android.widget.AbsListView.LayoutParams) mHeadView
//                        .getLayoutParams();
//                // 递减高度
//                headerParams.height -= PULL_BACK_REDUCE_STEP;
//                mHeadView.setLayoutParams(headerParams);
//                // 重绘
//                mHeadView.invalidate();
//                // 停止回弹时递减headView高度的任务
//                if (headerParams.height <= 0) {
//                    schedulor.shutdownNow();
//                }
//                 
//                break;
//            case PULL_UP_BACK_ACTION:
//                AbsListView.LayoutParams footerParams = (android.widget.AbsListView.LayoutParams) mTailView
//                .getLayoutParams();
//                // 递减高度
//                footerParams.height -= PULL_BACK_REDUCE_STEP;
//                mTailView.setLayoutParams(footerParams);
//                // 重绘
//                mTailView.invalidate();
//                // 停止回弹时递减headView高度的任务
//                if (footerParams.height <= 0) {
//                    schedulor.shutdownNow();
//                }
//                 
//                break;
//            }
//        }
//    };
//    
//    @Override
//    public void onScroll(AbsListView view, int firstVisiableItem,
//            int visibleItemCount, int totalItemCount) {
//        this.firstItemIndex = firstVisiableItem;
//        this.lastItemIndex = firstVisiableItem + visibleItemCount;
//    }
// 
//    @Override
//    public void onScrollStateChanged(AbsListView view, int scrollState) {
//        currentScrollState = scrollState;
//        Log.d(TAG, "scrollState: " + getScrollStateString(currentScrollState));
//    }
// 
//    private String getScrollStateString(int flag) {
//        String str = "";
//        switch(flag) {
//        case OnScrollListener.SCROLL_STATE_IDLE:
//            str = "SCROLL_STATE_IDLE";
//            break;
//        case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
//            str = "SCROLL_STATE_TOUCH_SCROLL";
//            break;
//        case OnScrollListener.SCROLL_STATE_FLING:
//            str = "SCROLL_STATE_FLING";
//            break;
//        default:
//            str = "wrong state";
//        }
//         
//        return str;
//    }
}
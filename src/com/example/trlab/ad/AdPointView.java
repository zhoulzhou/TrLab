package com.example.trlab.ad;

import com.example.trlab.R;
import com.example.trlab.utils.DisplayUtil;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

public class AdPointView extends ImageView{
	private Context mContext;
	private Bitmap mPointNormalImage = null;
	private Bitmap mPointFocusedImage = null;
	
	private int mBitmapWidth;
	private int mBitmapHeight;
	private int mInternalSpace;
	
	private int mTotalNum;
	private int mCurr;
	
	private Paint mPaint = new Paint();
	
	public AdPointView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initViews(context);
	}

	public AdPointView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}
	
	public AdPointView(Context context) {
		super(context);
		initViews(context);
	}
	
	private void initViews(Context context){
		mContext = context;
        Resources mRe = getResources();
        mPointNormalImage = BitmapFactory.decodeResource(mRe, R.drawable.ic_page_point_normal);
        mPointFocusedImage = BitmapFactory.decodeResource(mRe, R.drawable.ic_page_point_focused);
        mBitmapWidth = DisplayUtil.dpToPx(6);
        mBitmapHeight = DisplayUtil.dpToPx(6);
        mInternalSpace = DisplayUtil.dpToPx(10);
        mPointNormalImage = scaleBitmap(mPointNormalImage);
        mPointFocusedImage = scaleBitmap(mPointFocusedImage);
        
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int height = MeasureSpec.getSize(heightMeasureSpec);
		int spaceNum = mTotalNum > 0 ? mTotalNum -1 : 0;
		
		int widthSize = mTotalNum * mBitmapWidth + spaceNum * mInternalSpace;
		int heightSize = (height < mBitmapHeight) ? mBitmapHeight : (height > 2 * mBitmapHeight) ? 2 * mBitmapHeight : height;
		setMeasuredDimension(widthSize, heightSize);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
//		super.onDraw(canvas);
		int height = getHeight();
		int left = 0;
		int top  = (height - mBitmapHeight) / 2;
		for(int i=0; i<mTotalNum; i++){
			if(i == mCurr){
				canvas.drawBitmap(mPointFocusedImage, left, top, mPaint);
			}else{
				canvas.drawBitmap(mPointNormalImage, left, top, mPaint);
			}
			
			left += (mBitmapWidth + mInternalSpace);
		}
	}
	
	public void updateIndicator(int curr, int total){
		if(curr > total){
			return ;
		}
		mCurr = curr;
		int oldTotalNum = mTotalNum;
		if(total >= 1){
			mTotalNum = total;
		}
		
		if(oldTotalNum != mTotalNum){
			requestLayout();
		}else{
			invalidate();
		}
		
	}

	private Bitmap scaleBitmap(Bitmap bitmap){
		Bitmap bm = null;
		if(bitmap != null){
			int width = bitmap.getWidth();
			int height = bitmap.getHeight();
			float scale = 1.0f;
			if(mBitmapWidth == width){
				return bitmap;
			}else{
				scale = (float) mBitmapWidth / (float) height;
			}
			Matrix matrix = new Matrix();
			matrix.postScale(scale, scale);
			bm = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
			if(bitmap != null || !bitmap.isRecycled()){
				bitmap.recycle();
				bitmap = null;
			}
		}
		return bm;
	}
}
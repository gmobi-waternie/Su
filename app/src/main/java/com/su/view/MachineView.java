package com.su.view;






import com.su.utils.DipHelper;
import com.waternie.su.R;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class MachineView extends View {
	private int count = 0; 
	private static final int MAX_COUNT = 1000000;
	private int[] countNums = {0,0,0,0,0,0};
	private static final int MAX_NUM = 6;
	private static final int  BROAD_WIDTH = 10;
	private int mFrameWidth,mCellWidth;
	private Paint mPaint,mTextPaint;
	
	
	public MachineView(Context context) {
		this(context, null);
	}

	public MachineView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

	public MachineView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		
		Resources res = getResources();
		mPaint = new Paint();
		mPaint.setColor(res.getColor(R.color.bg_blue));
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeWidth(BROAD_WIDTH);
		
		mTextPaint = new Paint();
		mTextPaint.setColor(res.getColor(R.color.bg_black));
		mTextPaint.setAntiAlias(true);

		mTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = (count >= MAX_COUNT) ? 0 : count;
		invalidate();
	}
	
	public void incCount()
	{
		count ++;
		if(count >= MAX_COUNT)
			count = 0;
		invalidate();
	}
	
	private String splitNum(int count)
	{
		int tmpC = count;
		countNums[0] = tmpC/100000;
		tmpC-=countNums[0]*100000;
		
		countNums[1] = tmpC/10000;
		tmpC-=countNums[1]*10000;
		
		countNums[2] = tmpC/1000;
		tmpC-=countNums[2]*1000;
		
		countNums[3] = tmpC/100;
		tmpC-=countNums[3]*100;
		
		countNums[4] = tmpC/10;
		tmpC-=countNums[4]*10;
		
		countNums[5] = tmpC;
		
		Log.e("mv",countNums[0]+","+countNums[1]+","+countNums[2]+","+countNums[3]+","+countNums[4]+","+countNums[5]);
		return (countNums[0]+","+countNums[1]+","+countNums[2]+","+countNums[3]+","+countNums[4]+","+countNums[5]);
	}

	@Override
	protected void onDraw(Canvas canvas) {	
		
		mFrameWidth = getWidth() - getWidth() % 6;
		mCellWidth = mFrameWidth / 6;
		
		splitNum(count);
		int s1 = canvas.save();
		int x=0;
		
		float cellTextSize = getHeight() * 0.7f;
		mTextPaint.setTextSize(cellTextSize);
		
		for(int i=0; i<MAX_NUM; i++)
		{
			Rect cellRect = new Rect(x,0,x+mCellWidth,getHeight());
			//canvas.drawRect(cellRect, mPaint);
			
			FontMetricsInt fontMetrics = mTextPaint.getFontMetricsInt();  

			int baseline = cellRect.top + (cellRect.bottom - cellRect.top - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;    
			mTextPaint.setTextAlign(Paint.Align.CENTER);  
			canvas.drawText(countNums[i]+"", cellRect.centerX(), baseline, mTextPaint);
			
			x += mCellWidth;
		}
		
		
		canvas.restoreToCount(s1);
	}
	
	
	
	
	
	
}

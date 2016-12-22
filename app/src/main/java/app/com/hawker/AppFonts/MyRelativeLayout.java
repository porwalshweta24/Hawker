package app.com.hawker.AppFonts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

public class MyRelativeLayout extends RelativeLayout{


	public static interface OnPaarentClick
	{
		public boolean onParentClick();
		
	}
	private boolean   allowParentTouch =true;
	public boolean isAllowParentTouch() {
		return allowParentTouch;
	}
	public void setAllowParentTouch(boolean allowParentTouch) {
		this.allowParentTouch = allowParentTouch;
	}

	private OnPaarentClick	onPaarentClick;
	public OnPaarentClick getOnPaarentClick() {
		return onPaarentClick;
	}
	public void setOnPaarentClick(OnPaarentClick onPaarentClick) {
		this.onPaarentClick = onPaarentClick;
	}
	public MyRelativeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	public MyRelativeLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public MyRelativeLayout(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

	@SuppressLint("NewApi")
	public MyRelativeLayout(Context context, AttributeSet attrs,
			int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent e) {
		// do what you need to with the event, and then...
		
		if(e.getAction()==MotionEvent.ACTION_DOWN)
		{
			if(onPaarentClick!=null&&isAllowParentTouch())
			{
				if(onPaarentClick.onParentClick()==true)
				{
					return true;
				}
					
			}
		}
		return super.onInterceptTouchEvent(e);
		
	}

}

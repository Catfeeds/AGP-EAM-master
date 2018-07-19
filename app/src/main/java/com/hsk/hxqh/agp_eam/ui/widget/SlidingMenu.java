package com.hsk.hxqh.agp_eam.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hsk.hxqh.agp_eam.R;
import com.hsk.hxqh.agp_eam.unit.ScreenUtils;

/**侧滑栏**/
public class SlidingMenu extends CustomHorizontalScrollView
{


	private static final String TAG="SlidingMenu";
	/**
	 * 屏幕宽度
	 */
	private int mScreenWidth;
	/**
	 * dp
	 */
	private int mMenuRightPadding;
	/**
	 * 菜单的宽度
	 */
	private int mMenuWidth;
	private int mHalfMenuWidth;

	private boolean isOpen;

	private boolean once;

	public SlidingMenu(Context context, AttributeSet attrs)
	{
		this(context, attrs, 0);

	}

	public SlidingMenu(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		mScreenWidth = ScreenUtils.getScreenWidth(context);

		TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
				R.styleable.SlidingMenu, defStyle, 0);
		int n = a.getIndexCount();
		for (int i = 0; i < n; i++)
		{
			int attr = a.getIndex(i);
			switch (attr)
			{
				case R.styleable.SlidingMenu_rightPadding:
					// 默认50
					mMenuRightPadding = a.getDimensionPixelSize(attr,
							(int) TypedValue.applyDimension(
									TypedValue.COMPLEX_UNIT_DIP, 50f,
									getResources().getDisplayMetrics()));// 默认为10DP
					break;
			}
		}
		a.recycle();
	}

	public SlidingMenu(Context context)
	{
		this(context, null, 0);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
//		/**
//		 * 显示的设置一个宽度
//		 */
//		if (!once)
//		{
//			LinearLayout wrapper = (LinearLayout) getChildAt(0);
//			ViewGroup menu = (ViewGroup) wrapper.getChildAt(0);
//			ViewGroup content = (ViewGroup) wrapper.getChildAt(1);
//			mMenuWidth = mScreenWidth - mMenuRightPadding;
//			mHalfMenuWidth = mMenuWidth / 2;
//			menu.getLayoutParams().width = mMenuWidth;
//			content.getLayoutParams().width = mScreenWidth;
//
//		}
//		super.onMeasure(widthMeasureSpec, heightMeasureSpec);


		if (!once)
		{
			LinearLayout mWapper = (LinearLayout) getChildAt(0);
			ViewGroup mMenu = (ViewGroup) mWapper.getChildAt(0);
			ViewGroup mContent = (ViewGroup) mWapper.getChildAt(1);
			mMenuWidth = mMenu.getLayoutParams().width = mScreenWidth
					- mMenuRightPadding;
			mContent.getLayoutParams().width = mScreenWidth;
			once = true;
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b)
	{
		super.onLayout(changed, l, t, r, b);
		if (changed)
		{
			// 将菜单隐藏
			this.scrollTo(mMenuWidth, 0);
			once = true;
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev)
	{
		int action = ev.getAction();
		switch (action)
		{
			// Up时，进行判断，如果显示区域大于菜单宽度一半则完全显示，否则隐藏
			case MotionEvent.ACTION_UP:
				int scrollX = getScrollX();
				if (scrollX > mHalfMenuWidth)
				{
					this.smoothScrollTo(mMenuWidth, 0);
					isOpen = false;
				} else
				{
					this.smoothScrollTo(0, 0);
					isOpen = true;
				}
				return true;
		}
		return super.onTouchEvent(ev);
	}

	/**
	 * 打开菜单
	 */
	public void openMenu()
	{
		if (isOpen)
			return;
		this.smoothScrollTo(0, 0);
		isOpen = true;
	}

	/**
	 * 关闭菜单
	 */
	public void closeMenu()
	{
		if (isOpen)
		{
			this.smoothScrollTo(mMenuWidth, 0);
			isOpen = false;
		}
	}

	/**
	 * 切换菜单状态
	 */
	public void toggle()
	{
		if (isOpen)
		{
			closeMenu();
		} else
		{
			openMenu();
		}
	}

}

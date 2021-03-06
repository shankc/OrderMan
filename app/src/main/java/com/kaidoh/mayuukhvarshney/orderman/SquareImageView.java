package com.kaidoh.mayuukhvarshney.orderman;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
/**
 * Created by mayuukhvarshney on 31/05/16.
 */
public class SquareImageView extends ImageView
{
    public SquareImageView(Context context)
    {
        super(context);
    }

    public SquareImageView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public SquareImageView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension((getMeasuredWidth()), (int)Math.floor(getMeasuredHeight()/0.92)); //Snap to width
    }
}

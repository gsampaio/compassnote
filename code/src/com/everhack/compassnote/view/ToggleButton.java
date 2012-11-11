package com.everhack.compassnote.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageButton;

public class ToggleButton extends ImageButton {
    private int mOnResource;
    private int mOffResource;
    private boolean mIsOn = true;

    public ToggleButton(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public ToggleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public ToggleButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    public void setToggleResources(int on, int off) {
        mOnResource = on;
        mOffResource = off;
    }
    
    public void toggle(){
        if (mIsOn) {
            mIsOn = false;
            setImageResource(mOffResource);
        } else {
            mIsOn = true;
            setImageResource(mOnResource);
        }
    }
    
    
}

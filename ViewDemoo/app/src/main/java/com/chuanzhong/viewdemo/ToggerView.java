package com.chuanzhong.viewdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2016/8/1.
 */
public class ToggerView extends View {


    private Bitmap background;
    private Bitmap slide;
    private int backgroundheight;
    private int backgroundwidth;
    private int slideleft; //距离滑块左边的位置
    private int slidewidth;
    private int slidMax;
    private boolean saveStat;

    boolean isOpen;
    private OnChangedStatLinstener onChangedStatLinstener;
    private boolean isOpenTem;


    public ToggerView(Context context) {
        super(context);
    }

    public ToggerView(Context context, AttributeSet attrs) {
        super(context, attrs);

        int slide_backgroundID = attrs.getAttributeResourceValue("http://schemas.android.com/apk/res/com.chuanzhong.viewdemo",
                "slide_background", -1
        );

        int slide_iconID = attrs.getAttributeResourceValue("http://schemas.android.com/apk/res/com.chuanzhong.viewdemo",
                "slide_icon", -1
        );

        //读取开关资源属性
        if(slide_backgroundID!=-1&&slide_iconID!=-1){

            setImagee(slide_backgroundID,slide_iconID);

        }


       isOpen = attrs.getAttributeBooleanValue("http://schemas.android.com/apk/res/com.chuanzhong.viewdemo", "stat",
                false);

        setState(isOpen);

    }

    public ToggerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setOnChangedStatLinstener(OnChangedStatLinstener onChangedStatLinstener) {
        this.onChangedStatLinstener = onChangedStatLinstener;
    }


    //状态改变回调
    public interface OnChangedStatLinstener {

        void onChangedStat(boolean state);
    }


    /**
     * 设置状态
     *
     * @param isOpen
     */
    public void setState(boolean isOpen) {
        if (isOpen) {
            //打开状态
            slideleft = slidMax;

        } else {
            //关闭状态
            slideleft = 0;
        }


        saveStat = true;

        invalidate();
    }

    //赊着背景和滑块
    public void setImagee(int slide_background, int slide_icon) {

        background = BitmapFactory.decodeResource(getResources(), slide_background);
        slide = BitmapFactory.decodeResource(getResources(), slide_icon);


        //获取宽和高
        backgroundheight = background.getHeight();
        backgroundwidth = background.getWidth();
        //滑块的宽度
        slidewidth = slide.getWidth();
        //距离右边最大的距离
        slidMax = backgroundwidth - slidewidth;
        L.e("哈哈,滑动距离lift最大距离" + slidMax);
    }


    /**
     * 系统调用
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //设置自定义的togger的宽高测量后
        setMeasuredDimension(backgroundwidth, backgroundheight);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        // super.onDraw(canvas);  canvas ;帆布

        //画背景,画画滑块
        canvas.drawBitmap(background, 0, 0, null);

        //不断的改变左边的距离

        setSlid(canvas);

    }

    private void setSlid(Canvas canvas) {

        if (slideleft < 0) {
            slideleft = 0;
        } else if (slideleft > slidMax) {

            slideleft = slidMax;

        }
        canvas.drawBitmap(slide, slideleft, 0, null);

        if (saveStat) {

            saveStat = false;

            isOpenTem = slideleft != 0;
            if (isOpenTem != isOpen) {

                //状态发生了改变
                if (onChangedStatLinstener != null) {

                    onChangedStatLinstener.onChangedStat(isOpenTem);
                }


                isOpen = isOpenTem;
            }
        }

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {


        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN://按下的坐标

                slideleft = (int) (event.getX() - slidewidth / 2);

                break;
            case MotionEvent.ACTION_MOVE://移动

                slideleft = (int) (event.getX() - slidewidth / 2);
                L.e("哈哈,距离左侧的实时滑动距离" + slideleft);

                break;
            case MotionEvent.ACTION_UP://离开
                //手指松开判断他的状态
                if (event.getX() < backgroundwidth / 2) {
                    //滑到最左边
                    slideleft = 0;

                } else {
                    //滑动到最右边

                    slideleft = slidMax;
                }

                saveStat = true;
                break;
        }


        invalidate();  //重新调用ondraw();
        return true;
    }
}






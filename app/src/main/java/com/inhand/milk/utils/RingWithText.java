package com.inhand.milk.utils;

/**
 * Created by Administrator on 2015/5/15.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import com.inhand.milk.R;


public class RingWithText extends View {
    private float mR,paintWidth,circleR;
    private int ringBgColor = 0,ringColor=0,textColor= Color.BLACK;
    private float maxSweepAngle=0,sweepAngle=0;
    private String[] texts;
    private float[] textSizes;
    private int[] textColors;
    private int timeRing;
    private updateListener listener;
    private android.os.Handler handler;

    public RingWithText(Context context,float r) {
        super(context);
        mR = r;
        paintWidth = mR/15;
        circleR = mR - paintWidth/2 >0? mR-paintWidth/2:0;
    }
    public RingWithText(Context context) {
        super(context);
        mR = 0;
        paintWidth = mR/15;
        circleR = mR - paintWidth/2 >0? mR-paintWidth/2:0;
    }
    public RingWithText(Context context, AttributeSet attrs) {
        super(context, attrs);
        attrsInit(context,attrs);
    }

    public RingWithText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
       attrsInit(context,attrs);
    }
    private void attrsInit(Context context,AttributeSet attrs){
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.RingWithText);
        mR = a.getDimension(R.styleable.RingWithText_R,0);
        paintWidth = a.getDimension(R.styleable.RingWithText_RingWidth,0);
        circleR = mR - paintWidth/2 >0? mR-paintWidth/2:0;

        float textSize1,textSize2,textSize3;
        int count=0;
        textSize1 =a.getDimension(R.styleable.RingWithText_textSizes1,0);
        textSize2 =a.getDimension(R.styleable.RingWithText_textSizes2,0);
        textSize3 =a.getDimension(R.styleable.RingWithText_textSizes3,0);
        if(textSize1 != 0) count++;
        if(textSize2 != 0) count++;
        if(textSize3 != 0) count++;
        textSizes = new float[count];
        count = 0;
        if(textSize1 != 0) textSizes[count++] = textSize1;
        if(textSize2 != 0) textSizes[count++] = textSize2;
        if(textSize3 != 0) textSizes[count++] = textSize3;

    }
    public RingWithText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        attrsInit(context,attrs);
    }

    public void setRingBgColor(int ringBgColor) {
        this.ringBgColor = ringBgColor;
    }
    public void setRingColor(int ringColor) {
        this.ringColor = ringColor;
    }

    public void
    setRingWidth(float paintWidth) {
        this.paintWidth = paintWidth;
        circleR = mR - paintWidth/2 >0? mR-paintWidth/2:0;
    }
    public void setMaxSweepAngle(float a){
        maxSweepAngle = a;
        sweepAngle = maxSweepAngle;
    }

    public void setTexts(String[] strings,float[] sizes){
        if (strings.length != sizes.length)
             return;
        texts = strings;
        textSizes = sizes;
    }
    public void setTexts(String[] strings,float[] sizes,int[] colors){
        if (strings.length != sizes.length)
            return;
        texts = strings;
        textSizes = sizes;
        textColors = colors;
    }
    public void setTexts(String[] strings){
        texts = strings;
    }
    public void setTexts(float[] sizes){
        textSizes = sizes;
    }
    public void setTexts(int[] colors){
        textColors = colors;
    }

    public void setTextColor(int color){
        this.textColor = color;
    }

    public void setListener(updateListener listener) {
        this.listener = listener;
    }

    public void setTimeRing(int timeRing) {
        this.timeRing = timeRing;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawRingBg(canvas);
        drawRing(canvas);
        drawText(canvas);
    }
    private void drawRingBg(Canvas canvas){
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(paintWidth);
        paint.setColor(ringBgColor);
        canvas.drawCircle(mR,mR,circleR,paint);
    }
    private void drawRing(Canvas canvas){
        if(sweepAngle ==0)
            return;
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(paintWidth);
        paint.setColor(ringColor);
        float value = mR -circleR;
        RectF rectF = new RectF(value,value,2*mR-value,2*mR-value);
        canvas.drawArc(rectF,270,sweepAngle,false,paint);
    }
    private void drawText(Canvas canvas){
        float height=0,currentHeight,width;
        int i,count;
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(paintWidth);

        for(i=0;i<texts.length;i++){
            height+= textSizes[i];
        }
        currentHeight = mR - height/2;
        count = texts.length;
        if (count > textSizes.length)
            count = textSizes.length;
        for(i=0;i<count;i++){
            if (textColors == null || i >textColors.length-1) {
                paint.setColor(textColor);
            }
            else
                paint.setColor(textColors[i]);
            paint.setTextSize(textSizes[i]);
            width = paint.measureText(texts[i]);
            currentHeight += textSizes[i];
            canvas.drawText(texts[i],mR - width/2,currentHeight,paint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension((int)(mR*2),(int)(mR*2));
    }

    public void startRing(){
        if (handler == null)
            handler = new Handler();
        sweepAngle = 0;
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
                if (sweepAngle >= maxSweepAngle) {
                    handler.removeCallbacks(this);
                    sweepAngle = maxSweepAngle;
                }
                else{
                    sweepAngle += maxSweepAngle/(timeRing/2);
                    handler.postDelayed(this,2);
                }
                if(listener != null)
                    listener.updateSweepAngle(sweepAngle);
            }
        };
        handler.post(runnable);
    }
    public interface updateListener{
        void updateSweepAngle(float sweepAngle);
    }

}


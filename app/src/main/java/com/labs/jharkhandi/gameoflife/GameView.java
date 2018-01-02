package com.labs.jharkhandi.gameoflife;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by sumit on 2/1/18.
 */

class GameView extends View{

    private GameModel gameModel;

    private Paint mStrokePaint;

    private Paint mFillPaint;

    private int size;

    private boolean isActive;

    public GameView(Context context) {
        super(context);
    }

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GameView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setUp(GameModel up, int size, boolean isActive) {
        this.size = size;
        this.gameModel = up;
        this.isActive = isActive;

        mStrokePaint = new Paint();
        mStrokePaint.setStyle(Paint.Style.STROKE);
        mStrokePaint.setColor(Color.DKGRAY);

        mFillPaint = new Paint();
        mFillPaint.setStyle(Paint.Style.FILL);
        mFillPaint.setColor(Color.BLACK);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < gameModel.getRows(); i++) {
            for (int j = 0; j < gameModel.getColumns(); j++) {
                int top = j * size;
                int left = i * size;
                int right = left + size;
                int bottom = top + size;
                if(gameModel.isAlive(i,j)){
                    canvas.drawRect(left, top, right, bottom, mFillPaint);
                }
                canvas.drawRect(left, top, right, bottom, mStrokePaint);
            }
        }
    }

    /**
     * For explanation check link: https://stackoverflow.com/questions/12266899/onmeasure-custom-view-explanation#12267248
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int desiredWidth = gameModel.getRows() * size;
        int desiredHeight = gameModel.getColumns() * size;

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        //Measure Width
        if (widthMode == MeasureSpec.EXACTLY) {
            //Must be this size
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            width = Math.min(desiredWidth, widthSize);
        } else {
            //Be whatever you want
            width = desiredWidth;
        }

        //Measure Height
        if (heightMode == MeasureSpec.EXACTLY) {
            //Must be this size
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            height = Math.min(desiredHeight, heightSize);
        } else {
            //Be whatever you want
            height = desiredHeight;
        }

        //MUST CALL THIS
        setMeasuredDimension(width, height);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(isActive){
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    break;
                case MotionEvent.ACTION_UP:
                    int x = (int)event.getX()/size;
                    int y= (int)event.getY()/size;
                    if(gameModel.isAlive(x,y)){
                        gameModel.makeDead(x,y);
                    }else{
                        gameModel.makeAlive(x,y);
                    }
                    invalidate();
                    break;
            }
            return true;
        }
        return super.onTouchEvent(event);
    }
}

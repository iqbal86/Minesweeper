package com.example.minesweeper;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.view.MotionEvent;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MinesBoard extends View {
    private String mExampleString;
    private int BlockColor;
    private int RectangleColor;
    private int TextColor,RedColor,YellowColor;


    private float mExampleDimension = 0;
    private Drawable mExampleDrawable;

    private TextPaint mTextPaint;
    private float mTextWidth;
    private float mTextHeight;
    private Paint red, green, blue,black,white, p;
    int paddingLeft ,paddingTop,paddingRight,paddingBottom;


    Rect square;
    int rectBounds;

    public MinesBoard(Context context) {
        super(context);
        init(null, 0);
    }

    public MinesBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public MinesBoard(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs,defStyleAttr);
    }

//    public MinesBoard(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//    }
//
    public void init(AttributeSet attrs, int defStyle){
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.MinesBoard, defStyle, 0);

        //Read a string from string.xml.
        mExampleString = getResources().getString(R.string.example_string);
        //Get a color from color.xml.
        BlockColor = getResources().getColor(R.color.white);
        RectangleColor=getResources().getColor(R.color.black);
        TextColor=getResources().getColor(R.color.white);
        RedColor=getResources().getColor(R.color.red);

        YellowColor=getResources().getColor(R.color.yellow);
        // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
        // values that should fall on pixel boundaries.
        mExampleDimension = a.getDimension(
                R.styleable.MinesBoard_exampleDimension,
                mExampleDimension);


        if (a.hasValue(R.styleable.MinesBoard_exampleDrawable)) {
            mExampleDrawable = a.getDrawable(
                    R.styleable.MinesBoard_exampleDrawable);
            mExampleDrawable.setCallback(this);
        }
        a.recycle();

        mTextPaint = new TextPaint();
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.LEFT);
        setPadding(10, 10, 10, 10);
        paddingLeft = getPaddingLeft();
        paddingTop = getPaddingTop();
        paddingRight = getPaddingRight();
        paddingBottom = getPaddingBottom();
        // Update TextPaint and text measurements from attributes
        //invalidateTextPaintAndMeasurements();

    }

    private void invalidateTextPaintAndMeasurements() {
        mTextPaint.setTextSize(mExampleDimension);
        mTextPaint.setColor(BlockColor);
        mTextWidth = mTextPaint.measureText(mExampleString);

        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        mTextHeight = fontMetrics.bottom;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //setPadding(10, 10, 10, 10);


        // TODO: consider storing these as member variables to reduce
        // allocations per draw cycle.
       // int paddingLeft = getPaddingLeft();
        //int paddingTop = getPaddingTop();
        //int paddingRight = getPaddingRight();
        //int paddingBottom = getPaddingBottom();

        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;

        setBackgroundColor(BlockColor);


        //Just a template example of drawing text.
        canvas.drawText(mExampleString,
                paddingLeft + (contentWidth - mTextWidth) / 2,
                paddingTop + (contentHeight + mTextHeight) / 2,
                mTextPaint);

        p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(RectangleColor);

        Paint textPainter = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPainter.setColor(TextColor);
        textPainter.setTextSize(30);



        red = new Paint(Paint.ANTI_ALIAS_FLAG);
        red.setColor(RedColor);



        //canvas.drawRect(-100, -120, 50, 80, p );
        canvas.save();
        canvas.translate(0,0);

        int xorig = 20;
        int yorig = 10;
        int sideLength = (getWidth()/10) - 10;
        rectBounds = sideLength + 10;



        square = new Rect(xorig, yorig, sideLength, sideLength);

        int i;
        int j;

        float x,y;
        x = paddingLeft;
        y = paddingTop;

        //Nested for loops for multiple rows of squares.
        //i is columns (y coord). j is rows (x coord).
        for(i=0; i<=9; i++) {

            //Save the canvas origin onto the stack.
            canvas.save();

            //Draw rows.
            for(j=0; j<=9; j++){
                //Save the current origin.
                canvas.save();

                //Move to origin of this column.
                canvas.translate( (i * rectBounds), (j * rectBounds));



                //Draw a square in this i,j position.
                canvas.drawRect(square, p);
                //canvas.drawCircle((rectBounds/2), (sideLength/2),20, red);
                //canvas.drawText("(" + i * rectBounds + ", " + j * rectBounds + ")", rectBounds/2, rectBounds/2, textPainter);
                canvas.drawText("(" + i  + ", " + j   + ")", (rectBounds/2)-30, (rectBounds/2), textPainter);
                //Restore to the starting origin.
                canvas.restore();

            }//for j inner loop.

            //Restore the canvas to the starting origin. Remember that restores must match saves (stack/LIFO).
            canvas.restore();

        }//for i outer loop.



        // Draw the example drawable on top of the text.
        if (mExampleDrawable != null) {
            mExampleDrawable.setBounds(paddingLeft, paddingTop,
                    paddingLeft + contentWidth, paddingTop + contentHeight);
            mExampleDrawable.draw(canvas);
        }

    }//onDraw()
}

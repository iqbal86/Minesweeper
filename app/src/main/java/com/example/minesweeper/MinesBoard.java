package com.example.minesweeper;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
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

import java.util.Random;

public class MinesBoard extends View {
    private String mExampleString;
    private int BlockColor;
    private int RectangleColor,UncoveredRectangleColor;
    private int TextColor,RedColor,YellowColor;
    private MineObject [][] minesarray= new MineObject[10][10];

    private float mExampleDimension = 0;
    private Drawable mExampleDrawable;

    private TextPaint mTextPaint;
    private float mTextWidth;
    private float mTextHeight;
    private Paint red, UncoveredBlock, blue,black,white, p;
    int paddingLeft ,paddingTop,paddingRight,paddingBottom,contentWidth,contentHeight;
    //Canvas mycanvas;

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
        UncoveredRectangleColor=getResources().getColor(R.color.grey);

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
        contentWidth = getWidth() - paddingLeft - paddingRight;
        contentHeight = getHeight() - paddingTop - paddingBottom;
        initMinesArray();
        // Update TextPaint and text measurements from attributes
        //invalidateTextPaintAndMeasurements();

    }
    public void initMinesArray(){
       //minesarray = new MineObject[10][10];
        for(int i=0;i<10;i++){
            for( int j=0;j<10;j++){
                minesarray[i][j]=new MineObject();
                minesarray[i][j].col=j+1;
                minesarray[i][j].row=i+1;
                minesarray[i][j].covered=true;
                minesarray[i][j].mine=false;
            }
        }
        placemines();
    }
    public void placemines(){
        int count=0;
       while(count<20){
           int row=getRandom();
           int col= getRandom();
           if(minesarray[row][col].mine==false){
               minesarray[row][col].mine=true;
               count++;
           }
       }
    }
    public int getRandom(){
        final int min = 0;
        final int max = 9;
        final int random = new Random().nextInt((max - min) + 1) + min;
    return random;
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

        //int contentWidth = getWidth() - paddingLeft - paddingRight;
        //int contentHeight = getHeight() - paddingTop - paddingBottom;

        setBackgroundColor(BlockColor);


        //Just a template example of drawing text.
        canvas.drawText(mExampleString,
                paddingLeft + (contentWidth - mTextWidth) / 2,
                paddingTop + (contentHeight + mTextHeight) / 2,
                mTextPaint);

        p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(RectangleColor);

        UncoveredBlock = new Paint(Paint.ANTI_ALIAS_FLAG);
        UncoveredBlock.setColor(UncoveredRectangleColor);

        Paint textPainter = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPainter.setColor(TextColor);
        textPainter.setTextSize(30);
       // Typeface plain = Typeface.createFromAsset(assetManager, pathToFont);
        //Typeface bold = Typeface.create(plain, Typeface.DEFAULT_BOLD);
        Paint MinestextPainter = new Paint(Paint.ANTI_ALIAS_FLAG);
        MinestextPainter.setColor(RectangleColor);
        MinestextPainter.setTextSize(70);
        MinestextPainter.setTypeface(Typeface.DEFAULT_BOLD);




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



                //Draw a square for a covered block
                if(drawUncoveredRectangle(i,j)){
                    canvas.drawRect(square,p );


                }else{ // Draw the square for an uncovered block
                    if(minesarray[i][j].mine==true){ // check if to draw a mine

                        canvas.drawRect(square,red );

                    }else{
                        canvas.drawRect(square, UncoveredBlock);

                    }


                }
                // if block contains mine, then Draw a M as well
                if(minesarray[i][j].mine==true){

                    canvas.drawText("M", (rectBounds/2)-30, (rectBounds/2)+30, MinestextPainter);

                }
                //canvas.drawText("(" + i  + ", " + j   + ")", (rectBounds/2)-30, (rectBounds/2), textPainter);

                //canvas.drawCircle((rectBounds/2), (sideLength/2),20, red);
                //canvas.drawText("(" + i * rectBounds + ", " + j * rectBounds + ")", rectBounds/2, rectBounds/2, textPainter);

                //canvas.drawText("(" + i  + ", " + j   + ")", (rectBounds/2)-30, (rectBounds/2), textPainter);
                //Restore to the starting origin.
                canvas.restore();

            }//for j inner loop.

            //Restore the canvas to the starting origin. Remember that restores must match saves (stack/LIFO).
            canvas.restore();
            //mycanvas=canvas;

        }//for i outer loop.



        // Draw the example drawable on top of the text.
        if (mExampleDrawable != null) {
            mExampleDrawable.setBounds(paddingLeft, paddingTop,
                    paddingLeft + contentWidth, paddingTop + contentHeight);
            mExampleDrawable.draw(canvas);
        }

    }//onDraw()
    public boolean drawUncoveredRectangle(int col, int row){
        if(minesarray[row][col].covered)
            return true;
        else
            return false;
    }
    //Handle events.
    public boolean onTouchEvent(MotionEvent event){
        Log.d("Touch","Touch event occured");
        //Get where the event occurred.
        float x = event.getX();
        float y = event.getY();

        //The first argument to Toast must always be a context. Not always an activity.this reference.
        //Here, getContext() must be used.
        Toast.makeText(getContext(),"Touch at (" + x + ", " +  y + ")", Toast.LENGTH_SHORT ).show();

        //This width and height of the touch area.
        float width = getWidth();
        float height = rectBounds * 10;

        //float height = getHeight();

        //10 x 10 matrix. The row height and column width.
        float rowHeight = height/10;
        float colWidth = width/10;

        //Get the square that was touched.
        int row = 0;
        int col = 0;
        int[][] rowCol = new int[10][10];

        int i, j;
        for(i=1; i<=10; i++) {
            if (x < (i * colWidth)) {
                col = i;
                break;
            }// if cols
        }//for cols

        for (j=1; j<=10; j++) {
            if (y < (j * rowHeight)) {
                row = j;
                break;
            }//if rows.
        }//for rows.


        Toast.makeText(getContext(), "Row:" + row + "Col:" + col, Toast.LENGTH_SHORT).show();
        uncoverPoint(row,col);
        invalidate();


        return super.onTouchEvent(event);


    }//onTouchEvent()
    public void uncoverPoint(int i, int j){
        minesarray[i-1][j-1].covered=false;
    }
}

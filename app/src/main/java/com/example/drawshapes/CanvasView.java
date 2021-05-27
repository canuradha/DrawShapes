package com.example.drawshapes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * TODO: document your custom view class.
 */
public class CanvasView extends View {
    private int activityType = 0;


    private  float minX[] , minY[], maxX[], maxY[];


    private ArrayList<ShapeDetails> shapes = new ArrayList<>();
    private ShapeDetails currentShape;



    public CanvasView(Context context) {
        super(context);
        init(null, 0);
        this.maxX = new float[2];
        this.maxY = new float[2];
        this.minX = new float[2];
        this.minY = new float[2];
    }

    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public CanvasView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        this.initShape();
    }

    private  Paint initPencil(boolean isStroke){
        Paint pencil = new Paint();
        pencil.setStrokeWidth(5);
        pencil.setAntiAlias(true);
        pencil.setDither(true);
        pencil.setStrokeCap(Paint.Cap.SQUARE);
        pencil.setStyle(Paint.Style.STROKE);
        if(isStroke){
            pencil.setColor(Color.BLACK);
        }else{
            pencil.setColor(Color.BLUE);
        }
        return  pencil;
    }

    private void initShape(){
        Path newPath = new Path();
        Paint pencil = initPencil(true);
        this.currentShape = new ShapeDetails(newPath, pencil);
    }

//    private void getChangeDirection(float newX, float newY){
//        float x_diff = newX- this.prevX;
//        float y_diff = newY - this.prevY;
//
//        if(Math.abs(x_diff) > Math.abs(y_diff) && Math.abs(x_diff) > 10 ){
//            if(x_diff < 0){
//                Log.i("Direction: ", "Horizontal X Dir Change");
//            }else if(y_diff < 0){
//                Log.i("Direction: ", "Horizontal Y Dir Change");
//            }else{
//                Log.i("Direction: ", "Horizontal");
//            }
//        }else if(Math.abs(x_diff) < Math.abs(y_diff) && Math.abs(y_diff) > 10){
//            if(y_diff < 0){
//                Log.i("Direction: ", "Vertical Y Dir Change");
//            }else if(x_diff < 0){
//                Log.i("Direction: ", "Vertical X Dir Change");
//            }else{
//                Log.i("Direction: ", "Vertical");
//            }
//        }
//
//    }

    private void findMinMax(float x, float y){
        if(x < this.minX[0]) {
            this.minX[0] = x;
            this.minX[1] = y;
        }
        if(y < minY[1]) {
            this.minY[0] = x;
            this.minY[1] = y;
        }
        if(x > this.maxX[0]) {
            this.maxX[0] = x;
            this.maxX[1] = y;
        }
        if(y > this.maxY[1]) {
            this.maxY[0] = x;
            this.maxY[1] = y;
        }
    }

    private Path drawLine(float ex, float ey){

        Path temp = new Path();
        temp.moveTo(startx,starty);
        temp.lineTo(ex, ey);
        return temp;
    }

    private Path drawRect(){
        Path temp = new Path();
        if(minX[1] < maxX[1]){
            temp.addRect(minX[0], minX[1], maxX[0], maxX[1], Path.Direction.CW);
        }else{
            temp.addRect(minX[0], maxX[1], maxX[0], minX[1], Path.Direction.CW);
        }
        return temp;
    }

    private Path drawTriangle(){
        Path temp = new Path();
        temp.moveTo(minX[0], minX[1]);
        temp.lineTo(maxX[0], maxX[1]);
        if(minY[0]== maxX[0] && maxY[0] == minX[0] || minX[0] == minY[0] && maxX[0] == maxY[0]){
            temp.lineTo((minX[0] + maxX[0])/2, (minX[1] + maxX[1])/4);
        }else{
            if(Math.abs(minY[1] - (minX[1] + maxX[1])/2) < Math.abs(maxY[1] - (minX[1] + maxX[1])/2) ){
                temp.lineTo(maxY[0], maxY[1]);
            }else {
                temp.lineTo(minY[0], minY[1]);
            }
        }
        temp.close();
        return temp;
    }

    private Path drawCircle(){
        Path temp = new Path();
        float radius = Math.abs(minX[0] - maxX[0])/ 2;
        temp.addCircle(minX[0] + radius, minY[1] + radius  , radius, Path.Direction.CW);
        return temp;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(currentShape != null){
            canvas.drawPath(currentShape.getShapePath(), currentShape.getStrokeInfo());
        }
        for(int i=0;i<shapes.size();i++){
            canvas.drawPath(shapes.get(i).getShapePath(), shapes.get(i).getStrokeInfo());
        }

    }

    float startx = 0;
    float starty = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x=event.getX();
        float y=event.getY();

        if(event.getAction() == MotionEvent.ACTION_DOWN){
            this.initShape();
            this.startx = x;
            this.starty = y;
            this.minX[0] = this.maxX[0] = x;
            this.minY[0] = this.maxY[0] = x;
            this.minX[1] = this.maxX[1] = y;
            this.minY[1] = this.maxY[1] = y;
            currentShape.getShapePath().moveTo(x,y);
        }else if(event.getAction() == MotionEvent.ACTION_MOVE){
            currentShape.getShapePath().lineTo(x,y);
            this.findMinMax(x, y);

        }else if(event.getAction()== MotionEvent.ACTION_UP){
            currentShape.setStrokeInfo(initPencil(false));
            if(this.activityType == 1){
                currentShape.setShapePath(drawLine(event.getX(), event.getY()));
            }else if(this.activityType == 2){
                currentShape.setShapePath(drawTriangle());
            }else if(this.activityType == 3){
                currentShape.setShapePath(drawRect());
            }else if(this.activityType == 4){
                currentShape.setShapePath(drawCircle());
            }
            shapes.add(currentShape);
            this.currentShape = null;
        }
        invalidate();
        return true;
    }

    public void clearCanvas(){
        if(!this.shapes.isEmpty()){
            this.shapes.clear();
        }
        invalidate();
    }

    public void undoLast(){
        if(!this.shapes.isEmpty()) {
            this.shapes.remove(shapes.size() - 1);
        }
        invalidate();
    }

    public int getActivityType() {
        return activityType;
    }

    public void setActivityType(int activityType) {
        this.activityType = activityType;
    }
}


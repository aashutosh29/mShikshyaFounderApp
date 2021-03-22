package com.bihanitech.shikshyaprasasak.utility.photoeditorsdk;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

import com.bihanitech.shikshyaprasasak.utility.DrawingTool;


/**
 * Created by Ahmed Adel on 5/8/17.
 */

public class BrushDrawingView extends View {

    private final RectF mAuxRect = new RectF();
    private float brushSize = 10;
    private float brushEraserSize = 100;
    private Path drawPath;
    private Canvas drawCanvastemp;
    private Paint drawPaint;
    private Paint canvasPaint;
    private Canvas drawCanvas;
    private Bitmap canvasBitmap;
    private Bitmap canvasBitmap2;
    private boolean brushDrawMode;
    private float startX = 0;
    private float startY = 0;
    private float endX = 0;
    private float endY = 0;
    private DrawingTool mDrawingTool;

    private OnPhotoEditorSDKListener onPhotoEditorSDKListener;

    public BrushDrawingView(Context context) {
        this(context, null);
    }

    public BrushDrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupBrushDrawing();
    }

    public BrushDrawingView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setupBrushDrawing();
    }

    void setupBrushDrawing() {
        drawPath = new Path();
        drawPaint = new Paint();
        drawPaint.setAntiAlias(true);
        drawPaint.setDither(true);
        drawPaint.setColor(Color.BLACK);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
        drawPaint.setStrokeWidth(brushSize);
        drawPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DARKEN));
        canvasPaint = new Paint(Paint.DITHER_FLAG);
        mDrawingTool = DrawingTool.PEN;
        this.setVisibility(View.GONE);
    }

    private void refreshBrushDrawing() {
        brushDrawMode = true;
        drawPaint.setAntiAlias(true);
        drawPaint.setDither(true);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
        drawPaint.setStrokeWidth(brushSize);
        drawPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DARKEN));
    }

    void brushEraser() {
        mDrawingTool = DrawingTool.PEN;
        drawPaint.setStrokeWidth(brushEraserSize);
        drawPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
    }

    void setBrushDrawingMode(boolean brushDrawMode) {
        this.brushDrawMode = brushDrawMode;
        if (brushDrawMode) {
            this.setVisibility(View.VISIBLE);
            refreshBrushDrawing();
        }
    }

    void setDrawingTool(DrawingTool drawingTool) {
        mDrawingTool = drawingTool;
        refreshBrushDrawing();
    }

    void setBrushEraserSize(float brushEraserSize) {
        this.brushEraserSize = brushEraserSize;
    }

    void setBrushEraserColor(@ColorInt int color) {
        drawPaint.setColor(color);
        refreshBrushDrawing();
    }

    float getEraserSize() {
        return brushEraserSize;
    }

    float getBrushSize() {
        return brushSize;
    }

    void setBrushSize(float size) {
        brushSize = size;
        refreshBrushDrawing();
    }

    int getBrushColor() {
        return drawPaint.getColor();
    }

    void setBrushColor(@ColorInt int color) {
        drawPaint.setColor(color);
        refreshBrushDrawing();
    }

    void clearAll() {
        if (drawCanvas != null) {
            drawCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
            invalidate();
        }

    }

    public void setOnPhotoEditorSDKListener(OnPhotoEditorSDKListener onPhotoEditorSDKListener) {
        this.onPhotoEditorSDKListener = onPhotoEditorSDKListener;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        canvasBitmap2 = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);
        drawCanvastemp = new Canvas(canvasBitmap2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
        canvas.drawBitmap(canvasBitmap2, 0, 0, canvasPaint);
        canvas.drawPath(drawPath, drawPaint);
//        canvas.drawPath(drawPath, drawPaint);
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        if (brushDrawMode) {
            float touchX = event.getX();
            float touchY = event.getY();
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    drawPath.moveTo(touchX, touchY);
                    startX = touchX;
                    startY = touchY;
                    if (onPhotoEditorSDKListener != null)
                        onPhotoEditorSDKListener.onStartViewChangeListener(ViewType.BRUSH_DRAWING);
                    break;
                case MotionEvent.ACTION_MOVE:
                    endX = touchX;
                    endY = touchY;

                    if (drawCanvastemp != null) {
                        drawCanvastemp.drawColor(0, PorterDuff.Mode.CLEAR);
//                        invalidate();
                    }

                    switch (mDrawingTool) {
                        case PEN:
                            drawPath.lineTo(touchX, touchY);
                            break;
                        case LINE:
                            drawCanvastemp.drawLine(startX, startY, endX, endY, drawPaint);
                            break;
                        case RECTANGLE:
                            drawCanvastemp.drawRect(startX, startY,
                                    endX, endY, drawPaint);
                            break;
                        case CIRCLE:
                            if (endX > startX) {
                                drawCanvastemp.drawCircle(startX, startY,
                                        endX - startX, drawPaint);
                            } else {
                                drawCanvastemp.drawCircle(startX, startY,
                                        startX - endX, drawPaint);
                            }
                            break;
                        case ELLIPSE:
                            mAuxRect.set(endX - Math.abs(endX - startX),
                                    endY - Math.abs(endY - startY),
                                    endX + Math.abs(endX - startX),
                                    endY + Math.abs(endY - startY));
                            drawCanvastemp.drawOval(mAuxRect, drawPaint);
                            break;
                        case ARROW:
                            drawCanvastemp.drawLine(startX, startY,
                                    endX, endY, drawPaint);
                            float angle = (float) Math.toDegrees(Math.atan2(endY - startY,
                                    endX - startX)) - 90;
                            angle = angle < 0 ? angle + 360 : angle;
                            float middleWidth = 8f + drawPaint.getStrokeWidth();
                            float arrowHeadLarge = 30f + drawPaint.getStrokeWidth();

                            drawCanvastemp.save();
                            drawCanvastemp.translate(endX, endY);
                            drawCanvastemp.rotate(angle);
                            drawCanvastemp.drawLine(0f, 0f, middleWidth, 0f, drawPaint);
                            drawCanvastemp.drawLine(middleWidth, 0f, 0f, arrowHeadLarge, drawPaint);
                            drawCanvastemp.drawLine(0f, arrowHeadLarge, -middleWidth, 0f, drawPaint);
                            drawCanvastemp.drawLine(-middleWidth, 0f, 0f, 0f, drawPaint);
                            drawCanvastemp.restore();
                            break;
                    }

                    break;
                case MotionEvent.ACTION_UP:


                    switch (mDrawingTool) {
                        case PEN:
                            drawCanvas.drawPath(drawPath, drawPaint);
                            break;
                        case LINE:
                            drawCanvas.drawLine(startX, startY, endX, endY, drawPaint);
                            break;

                        case RECTANGLE:
                            drawCanvas.drawRect(startX, startY,
                                    endX, endY, drawPaint);
                            break;
                        case CIRCLE:
                            if (endX > startX) {
                                drawCanvas.drawCircle(startX, startY,
                                        endX - startX, drawPaint);
                            } else {
                                drawCanvas.drawCircle(startX, startY,
                                        startX - endX, drawPaint);
                            }
                            break;
                        case ELLIPSE:
                            mAuxRect.set(endX - Math.abs(endX - startX),
                                    endY - Math.abs(endY - startY),
                                    endX + Math.abs(endX - startX),
                                    endY + Math.abs(endY - startY));
                            drawCanvas.drawOval(mAuxRect, drawPaint);
                            break;
                        case ARROW:
                            drawCanvas.drawLine(startX, startY,
                                    endX, endY, drawPaint);
                            float angle = (float) Math.toDegrees(Math.atan2(endY - startY,
                                    endX - startX)) - 90;
                            angle = angle < 0 ? angle + 360 : angle;
                            float middleWidth = 12f + drawPaint.getStrokeWidth();
                            float arrowHeadLarge = 30f + drawPaint.getStrokeWidth();

                            drawCanvas.save();
                            drawCanvas.translate(endX, endY);
                            drawCanvas.rotate(angle);
                            drawCanvas.drawLine(0f, 0f, middleWidth, 0f, drawPaint);
                            drawCanvas.drawLine(middleWidth, 0f, 0f, arrowHeadLarge, drawPaint);
                            drawCanvas.drawLine(0f, arrowHeadLarge, -middleWidth, 0f, drawPaint);
                            drawCanvas.drawLine(-middleWidth, 0f, 0f, 0f, drawPaint);
                            drawCanvas.restore();
                            break;
                    }

                    if (drawCanvastemp != null) {
                        drawCanvastemp.drawColor(0, PorterDuff.Mode.CLEAR);
//                        invalidate();
                    }

                    drawPath.reset();

                    if (onPhotoEditorSDKListener != null)
                        onPhotoEditorSDKListener.onStopViewChangeListener(ViewType.BRUSH_DRAWING);
                    break;
            }


            invalidate();
            return true;
        } else {
            return false;
        }
    }
}

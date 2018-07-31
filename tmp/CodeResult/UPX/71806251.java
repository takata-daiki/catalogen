package com.example.gdgexampleapp;

import java.util.ArrayList;
import java.util.HashSet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.shapes.Shape;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.Toast;

public class MyView extends View implements OnTouchListener {
	
	final private int IMGHEIGHT=300;
	final private int IMGWIDTH=300;

	private float downx;
	private float downy;
	private Paint paint;
	private float upy;
	private float upx;
	private Bitmap bmp;
	
	
	private HashSet<Shape> linesList;

	public MyView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setup();
	}

	private void setup() {
		linesList= new HashSet<Shape>();
		
		paint = new Paint();
		// set tools options
		paint.setColor(Color.BLACK);
		paint.setStrokeWidth(5f);
		bmp= BitmapFactory.decodeResource(this.getResources(),R.drawable.orru);
		bmp = Bitmap.createScaledBitmap(bmp, IMGWIDTH, IMGHEIGHT, true);
		this.setOnTouchListener(this);
	}
	
	

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		paint.setFilterBitmap(true);
		canvas.drawBitmap(bmp,(this.getWidth()-IMGWIDTH)/2,
			(this.getHeight()-IMGHEIGHT)/2, paint);
		for(Shape s:linesList)
			s.draw(canvas, paint);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		int action = event.getAction();
		
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			downx = event.getX();
			downy = event.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			upx = event.getX();
			upy = event.getY();

			linesList.add(new Line(downx, downy, upx, upy));
			this.invalidate();
			
			downx = upx;
			downy = upy;
			break;
		case MotionEvent.ACTION_UP:
			upx = event.getX();
			upy = event.getY();
			
			//Draw and refresh
			linesList.add(new Line(downx, downy, upx, upy));
			this.invalidate();
			
			break;
		case MotionEvent.ACTION_CANCEL:
		default:
			Log.d("ok", action + "");
			break;
		}
		return true;
	}
	
	public class Line extends Shape{
		private float startX,startY,stopX,stopY;
		
		public Line(float startX, float startY, float stopX, float stopY) {
			super();
			this.startX = startX;
			this.startY = startY;
			this.stopX = stopX;
			this.stopY = stopY;
		}

		@Override
		public void draw(Canvas canvas, Paint paint) {
			canvas.drawLine(startX, startY, stopX, stopY, paint);
		}
		
	}
}

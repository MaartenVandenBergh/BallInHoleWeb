/*
package ballInHole.view.widgets;

import ballInHole.controller.IController;
import ballInHole.controller.IModelController;
import ballInHole.controller.MainModelController;
import ballInHole.model.gameElements.ICircle;
import android.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;

public class DrawingPanel extends View implements SensorEventListener{
	private IModelController controller;
	
	private Paint drawFillDark_Gray = new Paint();
	private Paint drawFillBlack = new Paint();
	private Paint drawFillWhite = new Paint();
	
	private Canvas drawCanvas;
	private Bitmap canvasBitmap;
	
	private SensorManager sensorManager;
	private Sensor accelerometer;
	private Display display;
	
	private float widthOrigin;
	private float heightOrigin;
	private float heightBorder;
	private float widthBorder;
	
	private int width;
	private int height;

	private float sensorX;
	private float sensorY;
	private long sensorTimeStamp;
	private float sensorZ;
	
	private float xOrigin;
    private float yOrigin;
    
	public DrawingPanel(Context context,AttributeSet attrs) {
		super(context,attrs);
		
		drawFillDark_Gray.setColor(this.getContext().getResources().getColor(R.color.darker_gray));
		drawFillDark_Gray.setAntiAlias(true);
		drawFillDark_Gray.setStrokeWidth(2);
		drawFillDark_Gray.setStyle(Paint.Style.FILL);
		drawFillDark_Gray.setStrokeJoin(Paint.Join.ROUND);
		drawFillDark_Gray.setStrokeCap(Paint.Cap.ROUND);
		
		drawFillBlack.setColor(this.getContext().getResources().getColor(R.color.black));
		drawFillBlack.setAntiAlias(true);
		drawFillBlack.setStrokeWidth(2);
		drawFillBlack.setStyle(Paint.Style.FILL);
		drawFillBlack.setStrokeJoin(Paint.Join.ROUND);
		drawFillBlack.setStrokeCap(Paint.Cap.ROUND);
		
		drawFillWhite.setColor(this.getContext().getResources().getColor(R.color.white));
		drawFillWhite.setAntiAlias(true);
		drawFillWhite.setStrokeWidth(2);
		drawFillWhite.setStyle(Paint.Style.FILL);
		drawFillWhite.setStrokeJoin(Paint.Join.ROUND);
		drawFillWhite.setStrokeCap(Paint.Cap.ROUND);
		
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		display = windowManager.getDefaultDisplay();
		sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		
	}
	public IModelController getController(){
		return this.controller;
	}
	public void setController(IModelController cont){
		this.controller = cont;
	}
	
	public DrawingPanel(Context context) {
	    this(context, null);
	  }
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		
		width = w;
		height = h;
		
		canvasBitmap = Bitmap.createBitmap(w,h, Bitmap.Config.ARGB_8888);
		drawCanvas = new Canvas(canvasBitmap);
		
		widthOrigin = w*0.5f;
		heightOrigin = h*0.5f;
		heightBorder = ((h-controller.getBall().getR())*0.5f);
		widthBorder = ((w-controller.getBall().getR())*0.5f);
		
		repositionGameElements(controller.getHole().getX(),controller.getHole().getY(),controller.getBall().getX(),controller.getBall().getY(),w, h, oldw, oldh);
		
	}
	public void repositionGameElements(float oldXHole, float oldYHole, float oldXBall, float oldYBall){
		repositionGameElements(oldXHole, oldYHole, oldXBall, oldYBall ,width, height, 0, 0);
	}
	private void repositionGameElements(float oldXHole, float oldYHole, float oldXBall, float oldYBall ,int w, int h, int oldw, int oldh) {
		if(oldw == 0){
			oldw = 100;
		}
		if(oldh == 0){
			oldh = 100;
		}
		
		controller.getBall().setX(oldXBall/oldw*w);
		controller.getBall().setY(oldYBall/oldh*h);
		controller.getHole().setX(oldXHole/oldw*w);
		controller.getHole().setY(oldYHole/oldh*h);
	}

	private void drawHole(Canvas canvas, float x, float y, float r) {
		canvas.drawCircle(x, y, r+5, drawFillDark_Gray);
		canvas.drawCircle(x, y, r, drawFillBlack);
	}
	private void drawBall(Canvas canvas, float x, float y, float r){
		canvas.drawCircle(x, y, r, drawFillWhite);
	}
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		//Log.v("test","drawing");
		
		
		drawHole(canvas,controller.getHole().getX(), controller.getHole().getY(),controller.getHole().getR());
		drawBall(canvas,controller.getBall().getX(), controller.getBall().getY(),controller.getBall().getR());
		
		controller.getBall().updatePosition(sensorX, sensorY,sensorZ,sensorTimeStamp);
		controller.getBall().reactToCollisionWithBorders(canvas.getWidth(), canvas.getHeight());
		if(controller.getBall().reactToCollisionWithGameElement(controller.getHole())){
			controller.winActions();
		}
		
	}
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		//nothing
	}
	@Override
	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER){
	        return;
		}
	    switch (display.getRotation()) {
		    case Surface.ROTATION_0:
		        sensorX = event.values[0];
		        sensorY = event.values[1];
		        break;
		    case Surface.ROTATION_90:
		        sensorX = -event.values[1];
		        sensorY = event.values[0];
		        break;
		    case Surface.ROTATION_180:
		        sensorX = -event.values[0];
		        sensorY = -event.values[1];
		        break;
		    case Surface.ROTATION_270:
		        sensorX = event.values[1];
		        sensorY = -event.values[0];
		        break;
	    }
	    sensorZ = event.values[2];
	    sensorTimeStamp = event.timestamp;
	    
	    invalidate();
	}
	public void startListening() {
	    sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
	}
	 
	public void stopListening() {
	    sensorManager.unregisterListener(this);
	}
}
*/
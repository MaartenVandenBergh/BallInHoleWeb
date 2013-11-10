/*
package ballInHole.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import db.interfaces.IRecord;
import ballInHole.controller.IModelController;
import ballInHole.controller.MainModelController;
import ballInHole.controller.IController;
import ballInHole.model.IModel;
import ballInHole.model.Model;
import ballInHole.model.triggers.ITriggerFactory;
import ballInHole.view.R;
import ballInHole.view.widgets.DrawingPanel;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements IView{
	
	IModelController controller;
	
	private DrawingPanel drawingPanel;
	private ImageButton playAndPauseButton;
	private ImageButton stopButton;
	
	private Toast scoreboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        if(controller == null){
			IModel model = new Model(ITriggerFactory.UTIL_TRIGGER, 1000);
			model.addView(this);
			this.controller = new MainModelController(model);
		}
        
        drawingPanel = ((DrawingPanel) findViewById(R.id.drawingPanel));
        drawingPanel.setController(controller);
        playAndPauseButton = ((ImageButton) findViewById(R.id.playAndPauseButton));
        stopButton = ((ImageButton) findViewById(R.id.stopButton));
        
        scoreboard = null;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
	public void update() {
		runOnUiThread(new Runnable() {
		     public void run() {
		    	 if(controller.getWinToggle()){
		    		 stopActions();
		    		 
		    		 showScoreboard();
		    	 }
		    	 TextView t = (TextView) findViewById(R.id.textView1);
		 		String s = formatChrono(controller.getHours(), controller.getMinutes(), controller.getSeconds());
		 		t.setText(s);

		    }
		});
	}
    private void showScoreboard(){
    	scoreboard = Toast.makeText(getApplicationContext(), generateScoreboard(), Toast.LENGTH_LONG);
    	scoreboard.show();
    }
    private String generateScoreboard() {
		StringBuffer sbMain = new StringBuffer();
		 List<IRecord> rList = controller.getRecords();
		 
		 IRecord lastAddedRecord = controller.getLastAddedRecord();
		 int lastAddedRecordCounter  = 0;
		 
		 Collections.sort(rList);
		 sbMain.append("Congratulations!\n");
		 sbMain.append("----------------------------\n");
		 
		 StringBuffer sbSub = new StringBuffer();
		 int counter = 1;
		 for(IRecord r : rList){
			 if(lastAddedRecord != null){
				 if(r.compareTo(lastAddedRecord)==0){
					 lastAddedRecordCounter = counter;
				 }
			 }
			 sbSub.append(formatRecordLine(counter,r));
			 counter++;
		 }
		 if(lastAddedRecord != null){
			 sbMain.append("Your time:\n");
			 sbMain.append(lastAddedRecordCounter).append(".: ").append(formatChrono(lastAddedRecord.getHours(),lastAddedRecord.getMinutes(),lastAddedRecord.getSeconds())).append("\n");
			 sbMain.append("----------------------------\n");
		 }
		 sbMain.append(sbSub);
		 sbMain.append("----------------------------");
		return sbMain.toString();
	}
    private String formatRecordLine(int number,IRecord r) {
		StringBuffer sb = new StringBuffer();
		sb.append(number).append(".: ").append(formatChrono(r.getHours(),r.getMinutes(),r.getSeconds())).append("\n");
		
		return sb.toString();
	}
	private String formatChrono(int hours, int minutes, int seconds){
		String sec = Integer.toString(seconds);
		String min = Integer.toString(minutes);
		String h = Integer.toString(hours);
		
		if(seconds <10){
			sec = "0"+sec;
		}
		if(minutes <10){
			min = "0"+min;
		}
		if(hours <10){
			h = "0"+h;
		}
		return h+":"+min+":"+sec;
	}
    public void clickPlayAndPauseButton(View view){
    	if(controller.isTriggerRunning()){
			controller.stopTrigger();
			((ImageButton)view).setImageResource(R.drawable.ic_play);
			drawingPanel.stopListening();
		}
		else{
			controller.startTrigger();
			((ImageButton)view).setImageResource(R.drawable.ic_pause);
			drawingPanel.startListening();
		}
    }
    public void clickStopButton(View view){
		stopActions();
    }


	private void stopActions() {
		if(controller.isTriggerRunning()){
			controller.stopTrigger();
			((ImageButton) findViewById(R.id.playAndPauseButton)).setImageResource(R.drawable.ic_play);
		}
		drawingPanel.stopListening();
		controller.reset();
		drawingPanel.repositionGameElements(controller.getHole().getX(), controller.getHole().getY(), controller.getBall().getX(), controller.getBall().getX());
		drawingPanel.invalidate();
	}
	@Override
	public void onResume(){
		super.onResume();
	}
	@Override
	public void onPause(){
		super.onPause();
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		    case R.id.scoreboard_menuItem:
		    	this.showScoreboard();
		    	return true;
		    default:
		    	return super.onOptionsItemSelected(item);
		}
	}
}
*/
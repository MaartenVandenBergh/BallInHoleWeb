package ballInHole.model;

import java.util.ArrayList;
import java.util.List;

import db.MainIRecordDB;
import db.Record;
import db.interfaces.IRecord;
import db.interfaces.IRecordDB;

import ballInHole.model.gameElements.Circle;
import ballInHole.model.gameElements.ICircle;
import ballInHole.model.triggers.ITrigger;
import ballInHole.model.triggers.ITriggerFactory;
import ballInHole.model.triggers.TriggerFactory;
import ballInHole.view.IView;


public class Model implements IModel{
	IRecordDB recordDb;
	
	ICircle hole;
	ICircle ball;
	
	int seconds;
	int minutes;
	int hours;
	
	ITrigger trigger;
	List<IView> views;
	private boolean winToggle;
	
	public Model(int utilTrigger, int period) {
		ITriggerFactory tf = new TriggerFactory();
		setTrigger(tf.createTrigger(this, utilTrigger, period));
		views = new ArrayList<IView>();
		winToggle = false;
		recordDb = new MainIRecordDB();
		
		resetGameElements();
		
	}
	public void resetGameElements() {
		ball = new Circle(10,10,20);
		hole = new Circle(80,80,25);
	}
	@Override
	public void setTrigger(ITrigger trigger) throws IllegalArgumentException{
		if(trigger != null){
			this.trigger = trigger;
		}
		else{
			throw new IllegalArgumentException("Invalid ITrigger");
		}
	}
	@Override
	public ITrigger getTrigger(){
		return this.trigger;
	}
	@Override
	public void addSecond(){
		if(seconds<59){
			this.seconds++;
		}
		else{
			this.seconds = 0;
			if(minutes <59){
				this.minutes++;
			}
			else{
				this.minutes = 0;
				this.hours++;
			}
		}
	}
	@Override
	public int getSeconds() {
		return this.seconds;
	}
	
	@Override
	public void setSecond(int sec) throws IllegalArgumentException {
		if(sec >= 0 && sec <60){
			this.seconds = sec;
		}
		else{
			throw new IllegalArgumentException("Invalid sec");
		}
	}
	@Override
	public int getMinutes() {
		return this.minutes;
	}
	@Override
	public void setMinutes(int min) throws IllegalArgumentException {
		if(min >= 0 && min <60){
			this.minutes = min;
		}
		else{
			throw new IllegalArgumentException("Invalid min");
		}
	}
	@Override
	public int getHours() {
		return this.hours;
	}
	@Override
	public void setHours(int h) throws IllegalArgumentException {
		if(h >= 0){
			this.hours = h;
		}
		else{
			throw new IllegalArgumentException("Invalid h");
		}
	}
	@Override
	public void updateFromTrigger() {
		this.addSecond();
		this.notifyView(0);
	}
	@Override
	public void addView(IView view) throws IllegalArgumentException {
		if(view != null){
			if(!views.contains(view)){
				this.views.add(view);
			}
			else{
				throw new IllegalArgumentException("View already known");
			}
		}
		else{
			throw new IllegalArgumentException("Invalid View");
		}
	}
	@Override
	public void removeView(IView view) throws IllegalArgumentException {
		if(view != null){
			if(views.contains(view)){
				this.views.remove(view);
			}
			else{
				throw new IllegalArgumentException("View unknown");
			}
		}
		else{
			throw new IllegalArgumentException("Invalid View");
		}
	}
	@Override
	public void notifyView(int index) throws IllegalArgumentException {
		if(index < views.size()){
			views.get(index).update();
		}
		else{
			throw new IllegalArgumentException("Invalid index");
		}
	}
	@Override
	public void notifyAllViews() {
		for(IView v : views){
			v.update();
		}
	}
	@Override
	public void startTrigger() {
		this.trigger.startTrigger();
	}
	@Override
	public void stopTrigger() {
		this.trigger.stopTrigger();
	}
	@Override
	public void reset() {
		this.setSecond(0);
		this.setMinutes(0);
		this.setHours(0);
		
		resetGameElements();
		this.winToggle = false;
		
		this.notifyView(0);
	}
	@Override
	public boolean isTriggerRunning() {
		return this.trigger.isRunning();
	}
	@Override
	public ICircle getHole() {
		return this.hole;
		
	}
	@Override
	public ICircle getBall() {
		return this.ball;
		
	}
	@Override
	public void winActions() {
		this.winToggle = true;
		IRecord r = new Record(this.hours, this.minutes,this.seconds);
		if(!this.recordDb.contains(r)){
			this.recordDb.addRecord(r);
		}
		
		this.notifyView(0);
	}
	@Override
	public boolean getWinToggle() {
		return this.winToggle;
	}
	@Override
	public List<IRecord> getRecords() {
		return this.recordDb.getAllRecords();
	}
	@Override
	public IRecord getLastAddedRecord() {
		return this.recordDb.getLastAddedRecord();
	}
}

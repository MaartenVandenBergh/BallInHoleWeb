package ballInHole.controller;

import java.util.List;

import db.interfaces.IRecord;
import ballInHole.model.gameElements.ICircle;

public interface IModelController extends IController{
	public void addSecond();
	public int getSeconds();
	public void setSecond(int sec) throws IllegalArgumentException;
	public int getMinutes();
	public void setMinutes(int min) throws IllegalArgumentException;
	public int getHours();
	public void setHours(int h) throws IllegalArgumentException;
	
	void startTrigger();
	void stopTrigger();
	void reset();
	boolean isTriggerRunning();
	
	ICircle getHole();
	ICircle getBall();
	
	void winActions();
	boolean getWinToggle();
	List<IRecord> getRecords();
	IRecord getLastAddedRecord();
}

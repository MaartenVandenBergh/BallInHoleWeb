package ballInHole.controller;

import java.util.List;

import db.interfaces.IRecord;
import ballInHole.model.IModel;
import ballInHole.model.gameElements.ICircle;


public class MainModelController implements IModelController{
	IModel model;

	public MainModelController(IModel model){
		setModel(model);
	}
	
	@Override
	public void addSecond() {
		this.model.addSecond();
		
	}

	@Override
	public int getSeconds() {
		return this.model.getSeconds();
	}

	@Override
	public void setSecond(int sec) throws IllegalArgumentException {
		this.model.setSecond(sec);
		
	}

	@Override
	public int getMinutes() {
		return this.model.getMinutes();
	}

	@Override
	public void setMinutes(int min) throws IllegalArgumentException {
		this.model.setMinutes(min);
		
	}

	@Override
	public int getHours() {
		return this.model.getHours();
	}

	@Override
	public void setHours(int h) throws IllegalArgumentException {
		this.model.setHours(h);
		
	}

	@Override
	public void startTrigger() {
		this.model.startTrigger();	
	}
	@Override
	public void stopTrigger() {
		this.model.stopTrigger();
	}

	@Override
	public void reset() {
		this.model.reset();
		
	}
	@Override
	public IModel getModel() {
		return this.model;
	}
	@Override
	public void setModel(IModel model) throws IllegalArgumentException {
		if(model != null){
			this.model = model;
		}
		else{
			throw new IllegalArgumentException("Invalid model");
		}
	}

	@Override
	public boolean isTriggerRunning() {
		return this.model.isTriggerRunning();
	}

	@Override
	public ICircle getHole() {
		return this.model.getHole();
	}

	@Override
	public ICircle getBall() {
		return this.model.getBall();
	}

	@Override
	public void winActions() {
		this.model.winActions();
		
	}

	@Override
	public boolean getWinToggle() {
		return this.model.getWinToggle();
	}

	@Override
	public List<IRecord> getRecords() {
		return this.model.getRecords();
	}

	@Override
	public IRecord getLastAddedRecord() {
		return this.model.getLastAddedRecord();
	}

}

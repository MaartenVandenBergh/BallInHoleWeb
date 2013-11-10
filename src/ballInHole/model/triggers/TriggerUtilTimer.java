package ballInHole.model.triggers;

import java.util.Timer;
import java.util.TimerTask;

import ballInHole.model.IModel;

public class TriggerUtilTimer implements ITrigger{
	
	IModel model;
	int period;
	Timer timer;
	boolean running;
	
	public TriggerUtilTimer(IModel model,int period){
		setIModel(model);
		setPeriod(period);
		setTimer(new Timer());
		running = false;
	}
	
	public Timer getTimer(){
		return timer;
	}
	public void setTimer(Timer timer) {
		this.timer = timer;
	}

	public int getPeriod(){
		return period;
	}
	public void setPeriod(int period)throws IllegalArgumentException {
		if(period>=0){
			this.period = period;
		}
		else{
			throw new IllegalArgumentException("Invalid period");
		}
	}
	@Override
	public void startTrigger() {
		timer = new Timer();
		TimerTask task = new TimerTask(){
			@Override
			public void run(){
				model.updateFromTrigger();
			}
		};
		timer.schedule(task, 1000, 1000);
		this.running = true;
	}

	@Override
	public void stopTrigger() {
		timer.cancel();
		this.running = false;
	}

	@Override
	public void setIModel(IModel model) throws IllegalArgumentException {
		if(model != null){
			this.model = model;
		}
		else{
			throw new IllegalArgumentException("Invalid Model");
		}
	}

	@Override
	public IModel getModel() {
		return this.model;
	}

	@Override
	public boolean isRunning() {
		return this.running;
	}
}

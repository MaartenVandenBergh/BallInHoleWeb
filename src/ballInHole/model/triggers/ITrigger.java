package ballInHole.model.triggers;

import ballInHole.model.IModel;

public interface ITrigger {
	
	void setIModel(IModel model)throws IllegalArgumentException;
	IModel getModel();
	int getPeriod();
	void setPeriod(int period)throws IllegalArgumentException;
	
	void startTrigger();
	void stopTrigger();
	boolean isRunning();
}

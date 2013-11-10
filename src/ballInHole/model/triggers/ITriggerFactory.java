package ballInHole.model.triggers;

import ballInHole.model.IModel;

public interface ITriggerFactory {
	
	public static final int UTIL_TRIGGER = 0;
	public static final int ANDROID_TRIGGER = 1;
	
	ITrigger createTrigger(IModel model,int triggerType, int period)throws IllegalArgumentException;
}

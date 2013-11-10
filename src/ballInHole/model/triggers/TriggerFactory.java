package ballInHole.model.triggers;

import ballInHole.model.IModel;

public class TriggerFactory implements ITriggerFactory{

	@Override
	public ITrigger createTrigger(IModel model, int triggerType, int period)throws IllegalArgumentException {
		ITrigger trigger = null;
		if(triggerType == ITriggerFactory.UTIL_TRIGGER){
			trigger = new TriggerUtilTimer(model, period);
		}
		else if(triggerType == ITriggerFactory.ANDROID_TRIGGER){
			trigger = new TriggerAndroidHandler(period);
		}
		else{
			throw new IllegalArgumentException("Unknown triggerType");
		}
		return trigger;
	}

}

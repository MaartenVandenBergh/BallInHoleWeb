package ballInHole.controller;

import ballInHole.model.IModel;

public interface IController {
	IModel getModel();
	void setModel(IModel model)throws IllegalArgumentException;
	
}

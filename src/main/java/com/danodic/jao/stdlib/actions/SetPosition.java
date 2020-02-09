package com.danodic.jao.stdlib.actions;

import com.danodic.jao.action.Action;
import com.danodic.jao.action.IAction;
import com.danodic.jao.core.JaoLayer;
import com.danodic.jao.model.ActionModel;

@Action(name = "SetPosition", library = "jao.standards")
public class SetPosition implements IAction {

	private boolean done;

	private float positionX;
	private float positionY;

	public SetPosition() {
		this(0, 0);
	}

	public SetPosition(float positionX, float positionY) {

		// Initialize stuff
		done = false;

		// Define standard opacity values
		this.positionX = positionX;
		this.positionY = positionY;
		
		// Initialize values
		reset();
	}

	@Override
	public boolean isDone() {
		return done;
	}

	@Override
	public void run(JaoLayer layer) {
		layer.getParameters().put("position_x", positionX);
		layer.getParameters().put("position_y", positionY);
		done = true;
	}

	@Override
	public void reset() {
		done = false;
	}

	@Override
	public void loadModel(ActionModel model) {
		if(model.getAttributes().containsKey("position_x"))
			positionX = Float.parseFloat(model.getAttributes().get("position_x"));
		
		if(model.getAttributes().containsKey("position_y"))
			positionY = Float.parseFloat(model.getAttributes().get("position_y"));
	}

	@Override
	public void setLoop(boolean loop) {}

	@Override
	public boolean isLoop() {
		return false;
	}
}

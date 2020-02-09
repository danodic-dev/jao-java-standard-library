package com.danodic.jao.stdlib.actions;

import com.danodic.jao.action.Action;
import com.danodic.jao.action.IAction;
import com.danodic.jao.core.JaoLayer;
import com.danodic.jao.model.ActionModel;

@Action(name = "SetOpacity", library = "jao.standards")
public class SetOpacity implements IAction {

	// The start and end point of the opacity for pulse
	private float opacity;

	// Define if the event is done
	private boolean done;

	public SetOpacity() {
		this(1f);
	}

	public SetOpacity(float opacity) {

		// Initialize stuff
		done = false;

		// Define standard opacity values
		this.opacity = opacity;
		
		// Initialize values
		reset();
	}

	@Override
	public boolean isDone() {
		return done;
	}

	@Override
	public void run(JaoLayer layer) {
		layer.getParameters().put("opacity", opacity);
		done = true;
	}

	@Override
	public void reset() {
		done = false;
	}

	@Override
	public void loadModel(ActionModel model) {
		if(model.getAttributes().containsKey("target_opacity"))
			opacity = Float.parseFloat(model.getAttributes().get("target_opacity"));
	}

	@Override
	public void setLoop(boolean loop) {}

	@Override
	public boolean isLoop() {
		return false;
	}
}

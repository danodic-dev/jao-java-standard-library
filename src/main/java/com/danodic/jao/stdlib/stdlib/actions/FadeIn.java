package com.danodic.jao.stdlib.stdlib.actions;

import com.danodic.jao.action.Action;
import com.danodic.jao.action.IAction;
import com.danodic.jao.core.JaoLayer;
import com.danodic.jao.model.ActionModel;

@Action(name = "FadeIn", library = "jao.standards")
public class FadeIn implements IAction {

	// The start and end point of the opacity for pulse
	private float startOpacity;
	private float endOpacity;
	private float step;
	private float currentOpacity;

	// Define if the event is done
	private boolean done;

	public FadeIn() {
		this(0f, 1f, 0.1f);
	}

	public FadeIn(float startOpacity, float endOpacity, float step) {

		// Initialize stuff
		done = false;

		// Define standard opacity values
		this.startOpacity = startOpacity;
		this.endOpacity = endOpacity;
		this.step = step;
		
		// Initialize values
		reset();
	}

	@Override
	public boolean isDone() {
		return done;
	}

	@Override
	public void run(JaoLayer layer) {

		// Increment/decrement step
		currentOpacity += step;

		// Set the object current opacity
		layer.getParameters().put("opacity", currentOpacity);

		// Check if we reached the target
		if (Float.compare(currentOpacity, endOpacity) >= 0) {
			layer.getParameters().put("opacity", endOpacity);
			done = true;
			return;
		}
	}

	@Override
	public void reset() {
		currentOpacity = startOpacity;
	}

	@Override
	public void loadModel(ActionModel model) {

		if(model.getAttributes().containsKey("start_opacity"))
			startOpacity = Float.parseFloat(model.getAttributes().get("start_opacity"));
		
		if(model.getAttributes().containsKey("end_opacity"))
			endOpacity = Float.parseFloat(model.getAttributes().get("end_opacity"));
		
		if(model.getAttributes().containsKey("step"))
			step = Float.parseFloat(model.getAttributes().get("step"));
	}

	@Override
	public void setLoop(boolean loop) {}

	@Override
	public boolean isLoop() {
		return false;
	}
}

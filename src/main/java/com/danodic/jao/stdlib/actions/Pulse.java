package com.danodic.jao.stdlib.actions;

import com.danodic.jao.action.Action;
import com.danodic.jao.action.IAction;
import com.danodic.jao.core.JaoLayer;
import com.danodic.jao.model.ActionModel;

@Action(name = "Pulse", library = "jao.standards")
public class Pulse implements IAction {

	// The start and end point of the opacity for pulse
	private float startOpacity;
	private float endOpacity;
	private float currentOpacity;
	private float targetOpacity;
	private float step;

	// Defines if it should loop
	private boolean loop;
	private boolean originalLoop;
	private boolean bounce;

	// Define if the event is done
	private boolean done;

	public Pulse() {
		this(1f, 0f, -0.1f);
	}

	public Pulse(float startOpacity, float endOpacity, float step) {

		// Initialize stuff
		done = false;
		loop = false;
		originalLoop = false;
		bounce = false;

		// Define standard opacity values
		this.startOpacity = startOpacity;
		this.endOpacity = endOpacity;
		this.step = step;

		// Reset targets
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
		
		// In case we exceed the limits
		if(Float.compare(currentOpacity, endOpacity)>=0)
			currentOpacity = endOpacity;
		
		if(Float.compare(currentOpacity, startOpacity)<=0)
			currentOpacity = startOpacity;

		// Set the object current opacity
		layer.getParameters().put("opacity", currentOpacity);

		// Check if we reached the target
		if (Float.compare(currentOpacity, startOpacity)<=0 || Float.compare(currentOpacity, endOpacity)>=0) {
			
			// In case this is not a loop element, mark as done
			if (!loop) {
				done = true;
				return;
			}

			// In case this is not a bounce animation, just reset it
			if (!bounce) {
				reset();
			} else {
				invert();
			}
		}
	}

	@Override
	public void setLoop(boolean loop) {
		this.loop = loop;
	}

	public void setBounce(boolean bounce) {
		this.bounce = bounce;
	}

	@Override
	public void reset() {
		// Set the opacity back to the original value
		loop = originalLoop;
		currentOpacity = startOpacity;
		targetOpacity = endOpacity;
	}

	private void invert() {
		// Else, go ahead and invert the target and step and values
		if (Float.compare(targetOpacity, startOpacity)==0) {
			targetOpacity = endOpacity;
		} else {
			targetOpacity = startOpacity;
		}

		// Invert the step
		step = step * -1;
	}
	
	@Override
	public void loadModel(ActionModel model) {
		if (model.getAttributes().containsKey("start_opacity"))
			startOpacity = Float.parseFloat(model.getAttributes().get("start_opacity"));

		if (model.getAttributes().containsKey("end_opacity"))
			endOpacity = Float.parseFloat(model.getAttributes().get("end_opacity"));

		if (model.getAttributes().containsKey("step"))
			step = Float.parseFloat(model.getAttributes().get("step"));

		if (model.getAttributes().containsKey("loop")) {
			loop = Boolean.parseBoolean(model.getAttributes().get("loop"));
			originalLoop = loop;
		}
	}

	@Override
	public boolean isLoop() {
		return loop;
	}

}

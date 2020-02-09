package com.danodic.jao.stdlib.actions;

import com.danodic.jao.action.Action;
import com.danodic.jao.action.IAction;
import com.danodic.jao.core.JaoLayer;
import com.danodic.jao.model.ActionModel;

@Action(name = "PulseOverBPM", library = "jao.standards")
public class PulseOverBpm implements IAction {
	
	private float startOpacity;
	private float endOpacity;
	private float currentOpacity;
	private double bpm;

	private boolean loop;

	private boolean done;

	private Long elapsed;
	private Long startTime;
	private Long step;

	@Override
	public void run(JaoLayer layer) {
		
		if(startTime==null) {
			startTime = layer.getElapsed();
			step = (long) ((60d / bpm) * 1000d);
		}
		
		// Get the elapsed time
		elapsed = layer.getElapsed() - startTime;

		// Increment/decrement step
		if(elapsed >= step.floatValue()) {
			currentOpacity = endOpacity;
			startTime = layer.getElapsed() - (elapsed - step);
		} else {			
			currentOpacity = startOpacity + ((elapsed.floatValue() / step.floatValue()) * (endOpacity - startOpacity));
		}
		
		layer.getParameters().put("opacity", currentOpacity);
		
	}

	public void loadModel(ActionModel model) {

		if (model.getAttributes().containsKey("start_opacity")) {
			startOpacity = Float.parseFloat(model.getAttributes().get("start_opacity"));
		}

		if (model.getAttributes().containsKey("end_opacity")) {
			endOpacity = Float.parseFloat(model.getAttributes().get("end_opacity"));
		}

		if (model.getAttributes().containsKey("bpm"))
			bpm = Double.parseDouble(model.getAttributes().get("bpm"));

		if (model.getAttributes().containsKey("loop")) {
			loop = Boolean.parseBoolean(model.getAttributes().get("loop"));
		}

	}

	@Override
	public void reset() {
		startTime = null;
	}

	@Override
	public boolean isDone() {
		return done;
	}

	@Override
	public void setLoop(boolean loop) {
		this.loop = loop;
	}

	@Override
	public boolean isLoop() {
		return loop;
	}
	
}

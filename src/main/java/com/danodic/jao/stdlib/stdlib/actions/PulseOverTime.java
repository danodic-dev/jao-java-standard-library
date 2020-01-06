package com.danodic.jao.stdlib.stdlib.actions;

import com.danodic.jao.action.Action;
import com.danodic.jao.action.IAction;
import com.danodic.jao.core.JaoLayer;
import com.danodic.jao.model.ActionModel;
import com.danodic.jao.parser.expressions.TimeExpressionParser;

@Action(name = "PulseOverTime", library = "jao.standards")
public class PulseOverTime implements IAction {

	// The start and end point of the opacity for pulse
	private float startOpacity;
	private float endOpacity;
	private float currentOpacity;
	private float endTargetOpacity;
	private float startTargetOpacity;

	// Defines if it should loop
	private boolean loop;
	private boolean originalLoop;
	private boolean bounce;

	// Define if the event is done
	private boolean done;

	private Long duration;
	private Long elapsed;
	private Long startTime;
	private Long endTime;

	public PulseOverTime() {
		this(1f, 0f, 1000L);
	}

	public PulseOverTime(float startOpacity, float endOpacity, Long duration) {

		// Initialize stuff
		done = false;
		loop = false;
		originalLoop = false;
		bounce = false;

		// Define standard opacity values
		this.startOpacity = startOpacity;
		this.endOpacity = endOpacity;
		this.startTargetOpacity = startOpacity;
		this.endTargetOpacity = endOpacity;
		this.startTime = null;
		this.endTime = null;

		// Reset targets
		reset();
	}

	@Override
	public boolean isDone() {
		return done;
	}

	@Override
	public void run(JaoLayer layer) {

		// Reset the start and end time
		if (startTime == null || endTime == null) {
			startTime = layer.getElapsed();
			endTime = startTime + duration;
		}

		// Get the elapsed time
		elapsed = layer.getElapsed() - startTime;

		// Increment/decrement step
		currentOpacity = startTargetOpacity
				+ ((elapsed.floatValue() / duration.floatValue()) * (endTargetOpacity - startTargetOpacity));

		// Set the object current opacity
		layer.getParameters().put("opacity", Math.max(0f, Math.min(1f, currentOpacity)));

		// Check if we reached the target
		if ((Float.compare(endTargetOpacity, startTargetOpacity) > 0
				&& (Float.compare(currentOpacity, endTargetOpacity) > 0
						|| Float.compare(currentOpacity, startTargetOpacity) < 0))
				|| (Float.compare(endTargetOpacity, startTargetOpacity) < 0
						&& (Float.compare(currentOpacity, endTargetOpacity) < 0
								|| Float.compare(currentOpacity, startTargetOpacity) > 0))) {

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
		endTargetOpacity = endOpacity;
		startTargetOpacity = startOpacity;
		currentOpacity = startTargetOpacity;
	}

	private void invert() {
		startTime = null;
		// Else, go ahead and invert the target and step and values
		if (Float.compare(endTargetOpacity, endOpacity) == 0) {
			endTargetOpacity = startOpacity;
			startTargetOpacity = endOpacity;
		} else {
			endTargetOpacity = endOpacity;
			startTargetOpacity = startOpacity;
		}
	}

	@Override
	public void loadModel(ActionModel model) {

		if (model.getAttributes().containsKey("start_opacity")) {
			startOpacity = Float.parseFloat(model.getAttributes().get("start_opacity"));
			startTargetOpacity = startOpacity;
			currentOpacity = startTargetOpacity;
		}

		if (model.getAttributes().containsKey("end_opacity")) {
			endOpacity = Float.parseFloat(model.getAttributes().get("end_opacity"));
			endTargetOpacity = endOpacity;
		}

		if (model.getAttributes().containsKey("duration"))
			duration = TimeExpressionParser.parseExpression(model.getAttributes().get("duration"));

		if (model.getAttributes().containsKey("loop")) {
			loop = Boolean.parseBoolean(model.getAttributes().get("loop"));
			originalLoop = loop;
		}

		if (model.getAttributes().containsKey("bounce"))
			bounce = Boolean.parseBoolean(model.getAttributes().get("bounce"));

	}

	@Override
	public boolean isLoop() {
		return loop;
	}
}

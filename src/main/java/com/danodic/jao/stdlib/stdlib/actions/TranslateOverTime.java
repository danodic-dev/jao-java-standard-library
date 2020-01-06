package com.danodic.jao.stdlib.stdlib.actions;

import com.danodic.jao.action.Action;
import com.danodic.jao.action.IAction;
import com.danodic.jao.core.JaoLayer;
import com.danodic.jao.model.ActionModel;
import com.danodic.jao.parser.expressions.TimeExpressionParser;

@Action(name = "TranslateOverTime", library = "jao.standards")
public class TranslateOverTime implements IAction {

	// The start and end point of the opacity for pulse
	private Long duration;
	private Long elapsed;
	private Long startTime;
	private Long endTime;

	private Float startPosX;
	private Float startPosY;
	private Float endPosX;
	private Float endPosY;
	private Float currentPosX;
	private Float currentPosY;

	// Define if the event is done
	private boolean done;

	public TranslateOverTime() {
		this(0f, 1f, 1000L);
	}

	public TranslateOverTime(float endPosX, float endPosY, Long duration) {

		// Initialize stuff
		done = false;

		// Define standard opacity values
		startPosX = null;
		startPosY = null;
		this.endPosX = endPosX;
		this.endPosY = endPosY;
		this.duration = duration;

		// Initialize values
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

		// Check if we need to capture positions from the object
		if (startPosX == null || startPosY == null) {
			startPosX = layer.getParameters().getAsFloat("position_x");
			startPosY = layer.getParameters().getAsFloat("position_y");
			currentPosX = startPosX;
			currentPosY = startPosY;
		}

		// Get the elapsed time
		elapsed = layer.getElapsed() - startTime;

		// Increment/decrement position
		currentPosX = startPosX + ((elapsed.floatValue() / duration.floatValue()) * (endPosX - startPosX));
		currentPosY = startPosY + ((elapsed.floatValue() / duration.floatValue()) * (endPosY - startPosY));

		// Set the object current opacity
		layer.getParameters().put("position_x", currentPosX);
		layer.getParameters().put("position_y", currentPosY);

		// Check if we reached the target
		if (Long.compare(startTime + elapsed, endTime) >= 0) {
			layer.getParameters().put("position_x", endPosX);
			layer.getParameters().put("position_y", endPosY);
			done = true;
			return;
		}
	}

	@Override
	public void reset() {
		startTime = null;
		endTime = null;
		done = false;
	}

	@Override
	public void loadModel(ActionModel model) {

		if (model.getAttributes().containsKey("target_position_x")) {
			endPosX = Float.parseFloat(model.getAttributes().get("target_position_x"));
		}

		if (model.getAttributes().containsKey("target_position_y")) {
			endPosY = Float.parseFloat(model.getAttributes().get("target_position_y"));
		}

		if (model.getAttributes().containsKey("duration")) {
			duration = TimeExpressionParser.parseExpression(model.getAttributes().get("duration"));
		}
	}

	@Override
	public void setLoop(boolean loop) {
	}

	@Override
	public boolean isLoop() {
		return false;
	}
}

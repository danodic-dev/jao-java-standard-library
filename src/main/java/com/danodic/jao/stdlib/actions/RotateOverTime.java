package com.danodic.jao.stdlib.actions;

import com.danodic.jao.action.Action;
import com.danodic.jao.action.IAction;
import com.danodic.jao.core.JaoLayer;
import com.danodic.jao.model.ActionModel;
import com.danodic.jao.parser.expressions.TimeExpressionParser;

@Action(name = "RotateOverTime", library = "jao.standards")
public class RotateOverTime implements IAction {

    // The start and end point of the opacity for pulse
    private Long duration;
    private Long elapsed;
    private Long startTime;
    private Long endTime;

    private Float startRotation;
    private Float endRotation;
    private Float currentRotation;

    // Define if the event is done
    private boolean done;

    public RotateOverTime() {
        this(0f, 45f, 1000L);
    }

    public RotateOverTime(float scaleX, float scaleY, Long duration) {

        // Initialize stuff
        done = false;

        // Define standard opacity values
        startRotation = null;
        this.endRotation = scaleX;
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

        if (startRotation == null) {
            if (layer.getParameters().containsKey("rotation")) {
                startRotation = layer.getParameters().getAsFloat("rotation");
            } else {
                startRotation = 1f;
            }
            currentRotation = startRotation;
        }

        // Get the elapsed time
        elapsed = layer.getElapsed() - startTime;

        // Increment/decrement position
        currentRotation = startRotation + ((elapsed.floatValue() / duration.floatValue()) * (endRotation - startRotation));

        // Set the object current opacity
        layer.getParameters().put("rotation", currentRotation);

        // Check if we reached the target
        if (Long.compare(startTime + elapsed, endTime) >= 0) {
            layer.getParameters().put("rotation", endRotation);
                done = true;
        }
    }

    @Override
    public final void reset() {
        startTime = null;
        endTime = null;
        currentRotation = startRotation;
        done = false;
    }

    @Override
    public void loadModel(ActionModel model) {

        if (model.getAttributes().containsKey("target_rotation")) {
            endRotation = Float.parseFloat(model.getAttributes().get("target_rotation"));
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

    @Override
    public IAction clone() {
        RotateOverTime clone = new RotateOverTime();
        clone.duration = duration;
        clone.elapsed = elapsed;
        clone.startTime = startTime;
        clone.endTime = endTime;
        clone.startRotation = startRotation;
        clone.endRotation = endRotation;
        clone.currentRotation = currentRotation;
        clone.done = done;
        return clone;
    }
}

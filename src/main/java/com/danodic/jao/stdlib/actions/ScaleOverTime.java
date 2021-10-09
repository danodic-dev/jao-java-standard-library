package com.danodic.jao.stdlib.actions;

import com.danodic.jao.action.Action;
import com.danodic.jao.action.IAction;
import com.danodic.jao.core.JaoLayer;
import com.danodic.jao.model.ActionModel;
import com.danodic.jao.parser.expressions.TimeExpressionParser;

@Action(name = "ScaleOverTime", library = "jao.standards")
public class ScaleOverTime implements IAction {

    // The start and end point of the opacity for pulse
    private Long duration;
    private Long elapsed;
    private Long startTime;
    private Long endTime;

    private Float startScaleX;
    private Float startScaleY;
    private Float endScaleX;
    private Float endScaleY;
    private Float currentScaleX;
    private Float currentScaleY;

    // Define if the event is done
    private boolean done;

    public ScaleOverTime() {
        this(0f, 1f, 1000L);
    }

    public ScaleOverTime(float scaleX, float scaleY, Long duration) {

        // Initialize stuff
        done = false;

        // Define standard opacity values
        startScaleX = null;
        startScaleY = null;
        this.endScaleX = scaleX;
        this.endScaleY = scaleY;
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

        if (startScaleX == null || startScaleY == null) {
            if (layer.getParameters().containsKey("scale_x")) {
                startScaleX = layer.getParameters().getAsFloat("scale_x");
            } else {
                startScaleX = 1f;
            }

            if (layer.getParameters().containsKey("scale_y")) {
                startScaleY = layer.getParameters().getAsFloat("scale_y");
            } else {
                startScaleY = 1f;
            }

            currentScaleX = startScaleX;
            currentScaleY = startScaleY;
        }

        // Get the elapsed time
        elapsed = layer.getElapsed() - startTime;

        // Increment/decrement position
        currentScaleX = startScaleX + ((elapsed.floatValue() / duration.floatValue()) * (endScaleX - startScaleX));
        currentScaleY = startScaleY + ((elapsed.floatValue() / duration.floatValue()) * (endScaleY - startScaleY));

        // Set the object current opacity
        layer.getParameters().put("scale_x", currentScaleX);
        layer.getParameters().put("scale_y", currentScaleY);

        // Check if we reached the target
        if (Long.compare(startTime + elapsed, endTime) >= 0) {
            layer.getParameters().put("scale_x", endScaleX);
            layer.getParameters().put("scale_y", endScaleY);
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

        if (model.getAttributes().containsKey("target_scale_x")) {
            endScaleX = Float.parseFloat(model.getAttributes().get("target_scale_x"));
        }

        if (model.getAttributes().containsKey("target_scale_y")) {
            endScaleY = Float.parseFloat(model.getAttributes().get("target_scale_y"));
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
        ScaleOverTime clone = new ScaleOverTime();
        clone.duration = duration;
        clone.elapsed = elapsed;
        clone.startTime = startTime;
        clone.endTime = endTime;
        clone.startScaleX = startScaleX;
        clone.startScaleY = startScaleY;
        clone.endScaleX = endScaleX;
        clone.endScaleY = endScaleY;
        clone.currentScaleX = currentScaleX;
        clone.currentScaleY = currentScaleY;
        clone.done = done;
        return clone;
    }
}

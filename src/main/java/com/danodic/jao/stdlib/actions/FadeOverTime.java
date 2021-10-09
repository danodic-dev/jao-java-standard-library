package com.danodic.jao.stdlib.actions;

import com.danodic.jao.action.Action;
import com.danodic.jao.action.IAction;
import com.danodic.jao.core.JaoLayer;
import com.danodic.jao.model.ActionModel;
import com.danodic.jao.parser.expressions.TimeExpressionParser;

@Action(name = "FadeOverTime", library = "jao.standards")
public class FadeOverTime implements IAction {

    // The start and end point of the opacity for pulse
    private float startOpacity;
    private float endOpacity;
    private float currentOpacity;
    private Long duration;
    private Long elapsed;
    private Long startTime;
    private Long endTime;

    // Define if the event is done
    private boolean done;
    private boolean copyOpacity;
    private boolean copiedOpacity;

    public FadeOverTime() {
        this(0f, 1f, 1000L);
    }

    public FadeOverTime(float startOpacity, float endOpacity, Long duration) {

        // Initialize stuff
        done = false;

        // Define standard opacity values
        this.startOpacity = startOpacity;
        this.endOpacity = endOpacity;
        this.duration = duration;
        this.startTime = null;
        this.endTime = null;
        this.currentOpacity = startOpacity;
        this.copyOpacity = false;
        this.copiedOpacity = false;

        // Initialize values
        reset();
    }

    @Override
    public boolean isDone() {
        return done;
    }

    @Override
    public void run(JaoLayer object) {

        // Reset the start and end time
        if (startTime == null || endTime == null) {
            startTime = object.getElapsed();
            endTime = startTime + duration;
        }

        if (copyOpacity && !copiedOpacity) {
            startOpacity = object.getParameters().getAsFloat("opacity");
        }

        // Get the elapsed time
        elapsed = object.getElapsed() - startTime;

        // Increment/decrement step
        currentOpacity = startOpacity + ((elapsed.floatValue() / duration.floatValue()) * (endOpacity - startOpacity));

        // Set the object current opacity
        object.getParameters().put("opacity", currentOpacity);

        // Check if we reached the target
        if (Long.compare(elapsed, duration) >= 0) {
            object.getParameters().put("opacity", endOpacity);
            done = true;
            return;
        }
    }

    @Override
    public void reset() {
        startTime = null;
        endTime = null;
        done = false;
        this.currentOpacity = startOpacity;
        copiedOpacity = false;
    }

    @Override
    public void loadModel(ActionModel model) {
        if (model.getAttributes().containsKey("start_opacity")) {
            if (model.getAttributes().get("start_opacity").equalsIgnoreCase("current")) {
                copyOpacity = true;
                copiedOpacity = false;
            } else {
                startOpacity = Float.parseFloat(model.getAttributes().get("start_opacity"));
            }
        }

        if (model.getAttributes().containsKey("end_opacity")) {
            endOpacity = Float.parseFloat(model.getAttributes().get("end_opacity"));
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
        FadeOverTime clone = new FadeOverTime();
        clone.startOpacity = startOpacity;
        clone.endOpacity = endOpacity;
        clone.currentOpacity = currentOpacity;
        clone.duration = duration;
        clone.elapsed = elapsed;
        clone.startTime = startTime;
        clone.endTime = endTime;
        clone.done = done;
        clone.copyOpacity = copyOpacity;
        clone.copiedOpacity = copiedOpacity;
        return clone;
    }

}

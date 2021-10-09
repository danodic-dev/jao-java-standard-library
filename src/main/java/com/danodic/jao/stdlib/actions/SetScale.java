package com.danodic.jao.stdlib.actions;

import com.danodic.jao.action.Action;
import com.danodic.jao.action.IAction;
import com.danodic.jao.core.JaoLayer;
import com.danodic.jao.model.ActionModel;

@Action(name = "SetScale", library = "jao.standards")
public class SetScale implements IAction {

    // The start and end point of the opacity for pulse
    private float scaleX;
    private float scaleY;

    // Define if the event is done
    private boolean done;

    public SetScale() {
        this(1, 1);
    }

    public SetScale(float scaleX, float scaleY) {

        // Initialize stuff
        done = false;

        // Define standard opacity values
        this.scaleX = scaleX;
        this.scaleY = scaleY;

        // Initialize values
        reset();
    }

    @Override
    public boolean isDone() {
        return done;
    }

    @Override
    public void run(JaoLayer layer) {
        layer.getParameters().put("scale_x", scaleX);
        layer.getParameters().put("scale_y", scaleY);
        done = true;
    }

    @Override
    public void reset() {
        done = false;
    }

    @Override
    public void loadModel(ActionModel model) {
        if (model.getAttributes().containsKey("scale_x")) {
            scaleX = Float.parseFloat(model.getAttributes().get("scale_x"));
        }

        if (model.getAttributes().containsKey("scale_y")) {
            scaleY = Float.parseFloat(model.getAttributes().get("scale_y"));
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
        SetScale clone = new SetScale();
        clone.scaleX = scaleX;
        clone.scaleY = scaleY;
        clone.done = done;
        return clone;
    }
}

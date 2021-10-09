package com.danodic.jao.stdlib.actions;

import com.danodic.jao.action.Action;
import com.danodic.jao.action.IAction;
import com.danodic.jao.core.JaoLayer;
import com.danodic.jao.model.ActionModel;

@Action(name = "SetRotation", library = "jao.standards")
public class SetRotation implements IAction {

    // The start and end point of the opacity for pulse
    private float rotation;

    // Define if the event is done
    private boolean done;

    public SetRotation() {
        this(1);
    }

    public SetRotation(float rotation) {

        // Initialize stuff
        done = false;

        // Define standard opacity values
        this.rotation = rotation;

        // Initialize values
        reset();
    }

    @Override
    public boolean isDone() {
        return done;
    }

    @Override
    public void run(JaoLayer layer) {
        layer.getParameters().put("rotation", rotation);
        done = true;
    }

    @Override
    public final void reset() {
        done = false;
    }

    @Override
    public void loadModel(ActionModel model) {
        if (model.getAttributes().containsKey("rotation")) {
            rotation = Float.parseFloat(model.getAttributes().get("rotation"));
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
        SetRotation clone = new SetRotation();
        clone.rotation = rotation;
        clone.done = done;
        return clone;
    }
}

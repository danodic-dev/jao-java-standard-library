package com.danodic.jao.stdlib.actions;

import com.danodic.jao.action.Action;
import com.danodic.jao.action.IAction;
import com.danodic.jao.core.JaoLayer;
import com.danodic.jao.model.ActionModel;

@Action(name = "SetFrame", library = "jao.standards")
public class SetFrame implements IAction {

    // The start and end point of the opacity for pulse
    private int frame;

    // Define if the event is done
    private boolean done;

    public SetFrame() {
        this(0);
    }

    public SetFrame(int frame) {

        // Initialize stuff
        done = false;

        // Define standard opacity values
        this.frame = frame;

        // Initialize values
        reset();
    }

    @Override
    public boolean isDone() {
        return done;
    }

    @Override
    public void run(JaoLayer layer) {
        layer.getParameters().put("current_frame", frame);
        done = true;
    }

    @Override
    public void reset() {
        done = false;
    }

    @Override
    public void loadModel(ActionModel model) {
        if (model.getAttributes().containsKey("frame")) {
            frame = Integer.parseInt(model.getAttributes().get("frame"));
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
        SetFrame clone = new SetFrame();
        clone.frame = frame;
        clone.done = done;
        return clone;
    }
}

package com.danodic.jao.stdlib.actions;

import com.danodic.jao.action.Action;
import com.danodic.jao.action.IAction;
import com.danodic.jao.core.JaoLayer;
import com.danodic.jao.model.ActionModel;

@Action(name = "SetLoop", library = "jao.standards")
public class Loop implements IAction {

    private boolean done;

    private boolean loop;

    public Loop() {
        this(false);
    }

    public Loop(Boolean loop) {

        // Initialize stuff
        done = false;
        this.loop = loop;

        // Initialize values
        reset();
    }

    @Override
    public boolean isDone() {
        return done;
    }

    @Override
    public void run(JaoLayer layer) {
        layer.getJao().setLoop(loop);
        done = true;
    }

    @Override
    public void reset() {
        done = false;
    }

    @Override
    public void loadModel(ActionModel model) {
        if (model.getAttributes().containsKey("value")) {
            loop = Boolean.valueOf(model.getAttributes().get("value"));
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
        Loop clone = new Loop();
        clone.done = done;
        clone.loop = loop;
        clone.reset();
        return clone;
    }
}

package com.danodic.jao.stdlib.actions;

import com.danodic.jao.action.Action;
import com.danodic.jao.action.IAction;
import com.danodic.jao.core.JaoLayer;
import com.danodic.jao.model.ActionModel;

@Action(name = "PlaySample", library = "jao.standards")
public class PlaySample implements IAction {

    // Define if the event is done
    private boolean done;

    public PlaySample() {

        // Initialize stuff
        done = false;

        // Initialize values
        reset();
    }

    @Override
    public boolean isDone() {
        return done;
    }

    @Override
    public void run(JaoLayer layer) {
        layer.getParameters().put("play_sample", true);
        done = true;
    }

    @Override
    public void reset() {
    }

    @Override
    public void setLoop(boolean loop) {
    }

    @Override
    public void loadModel(ActionModel arg0) {
    }

    @Override
    public boolean isLoop() {
        return false;
    }

    @Override
    public IAction clone() {
        PlaySample clone = new PlaySample();
        clone.done = done;
        return clone;
    }
}

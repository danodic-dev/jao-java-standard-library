package com.danodic.jao.stdlib.actions;

import com.danodic.jao.action.Action;
import com.danodic.jao.action.IAction;
import com.danodic.jao.core.JaoLayer;
import com.danodic.jao.model.ActionModel;

@Action(name = "SpawnAnimation", library = "jao.standards")
public class SpawnAnimation implements IAction {

    // Define if the event is done
    private boolean done;

    public SpawnAnimation() {
        this(0);
    }

    public SpawnAnimation(int frame) {

        // Initialize stuff
        done = false;
    }

    @Override
    public boolean isDone() {
        return done;
    }

    @Override
    public void run(JaoLayer layer) {
        layer.getParameters().put("spawn_animation", null);
        done = true;
    }

    @Override
    public void reset() {
        done = false;
    }

    @Override
    public void loadModel(ActionModel model) {

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
        SpawnAnimation clone = new SpawnAnimation();
        clone.done = done;
        return clone;
    }
}

package com.danodic.jao.stdlib.actions.video;

import com.danodic.jao.action.Action;
import com.danodic.jao.action.IAction;
import com.danodic.jao.core.JaoLayer;
import com.danodic.jao.model.ActionModel;

@Action(name = "Video:Dispose", library = "jao.standards")
public class VideoDispose implements IAction {

    private Boolean done;

    public VideoDispose() {
        reset();
    }

    @Override
    public void reset() {
        done = Boolean.FALSE;
    }

    @Override
    public void run(JaoLayer layer) {
        layer.getParameters().put("video:dispose", true);
        done = Boolean.TRUE;
    }

    @Override
    public IAction clone() {
        VideoDispose newAction = new VideoDispose();
        newAction.done = done;
        return newAction;
    }

    @Override
    public boolean isDone() {
        return done;
    }

    @Override
    public boolean isLoop() {
        return false;
    }

    @Override
    public void loadModel(ActionModel model) {
        // No model attributes
    }

    @Override
    public void setLoop(boolean loopFlag) {
        // Do nothing.
    }
    
}

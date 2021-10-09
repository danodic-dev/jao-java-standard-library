package com.danodic.jao.stdlib.actions.video;

import com.danodic.jao.action.Action;
import com.danodic.jao.action.IAction;
import com.danodic.jao.core.JaoLayer;
import com.danodic.jao.model.ActionModel;

@Action(name = "Video:Stop", library = "jao.standards")
public class VideoStop implements IAction {

    private Boolean done;
    private Boolean dispose;

    public VideoStop() {
        dispose = Boolean.FALSE;
        reset();
    }

    @Override
    public void reset() {
        done = Boolean.FALSE;
    }

    @Override
    public void run(JaoLayer layer) {
        layer.getParameters().put("video:stop", true);
        if(dispose.booleanValue()) {
            layer.getParameters().put("video:dispose", true);
        }
        done = Boolean.TRUE;
    }

    @Override
    public IAction clone() {
        VideoStop newAction = new VideoStop();
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
        if(model.getAttributes().containsKey("dispose")) {
            dispose = Boolean.valueOf(model.getAttributes().get("dispose"));
        }
    }

    @Override
    public void setLoop(boolean loopFlag) {
        // Do nothing.
    }
    
}

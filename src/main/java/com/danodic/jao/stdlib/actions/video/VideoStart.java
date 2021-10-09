package com.danodic.jao.stdlib.actions.video;

import com.danodic.jao.action.Action;
import com.danodic.jao.action.IAction;
import com.danodic.jao.core.JaoLayer;
import com.danodic.jao.model.ActionModel;

@Action(name = "Video:Start", library = "jao.standards")
public class VideoStart implements IAction {

    private Boolean done;
    private Boolean autoplay;

    public VideoStart() {
        autoplay = Boolean.FALSE;
        reset();
    }

    @Override
    public void reset() {
        done = Boolean.FALSE;
    }

    @Override
    public void run(JaoLayer layer) {
        layer.getParameters().put("video:start", true);
        if(autoplay.booleanValue()) {
            layer.getParameters().put("video:play", true);
        }
        done = Boolean.TRUE;
    }

    @Override
    public IAction clone() {
        VideoStart newAction = new VideoStart();
        newAction.autoplay = autoplay;
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
        if(model.getAttributes().containsKey("autoplay")) {
            autoplay = Boolean.valueOf(model.getAttributes().get("autoplay"));
        }
    }

    @Override
    public void setLoop(boolean loopFlag) {
        // Do nothing.
    }
    
}

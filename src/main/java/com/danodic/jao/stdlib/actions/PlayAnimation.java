package com.danodic.jao.stdlib.actions;

import com.danodic.jao.action.Action;
import com.danodic.jao.action.IAction;
import com.danodic.jao.core.JaoLayer;
import com.danodic.jao.model.ActionModel;

@Action(name = "PlayAnimation", library = "jao.standards")
public class PlayAnimation implements IAction {

    private int startFrame;
    private int endFrame;
    private int currentFrame;
    private int loopFromFrame;
    private boolean loop;
    private boolean originalLoop;
    private boolean done;
    private boolean returnToFirstFrame;
    private boolean playFromCurrent;
    private boolean getCurrent;

    public PlayAnimation() {
        this(0, 0, false);
    }

    public PlayAnimation(int startFrame, int endFrame, boolean loop) {
        this.startFrame = startFrame;
        this.endFrame = endFrame;
        this.currentFrame = startFrame;
        this.done = false;
        this.loop = loop;
        this.originalLoop = loop;
        this.returnToFirstFrame = false;
        this.loopFromFrame = startFrame;
        this.playFromCurrent = false;
        this.getCurrent = false;
    }

    @Override
    public void run(JaoLayer layer) {
        if (currentFrame >= endFrame) {
            if (loop) {
                currentFrame = loopFromFrame;
            } else {
                currentFrame--;
                done = true;

                if (returnToFirstFrame) {
                    currentFrame = startFrame;
                }
            }
        }

        if (playFromCurrent && getCurrent && layer.getParameters().containsKey("current_frame")) {
            currentFrame = layer.getParameters().getAsInteger("current_frame");
            getCurrent = false;
        }

        layer.getParameters().put("current_frame", currentFrame);
        currentFrame++;
    }

    @Override
    public void reset() {
        this.currentFrame = this.startFrame;
        this.loop = originalLoop;
        this.done = false;
        this.getCurrent = true;
    }

    @Override
    public boolean isDone() {
        return done;
    }

    @Override
    public void loadModel(ActionModel model) {

        if (model.getAttributes().containsKey("start_frame")) {
            startFrame = Integer.parseInt(model.getAttributes().get("start_frame"));
            loopFromFrame = startFrame;
        }

        if (model.getAttributes().containsKey("end_frame")) {
            endFrame = Integer.parseInt(model.getAttributes().get("end_frame"));
        }

        if (model.getAttributes().containsKey("loop")) {
            loop = Boolean.parseBoolean(model.getAttributes().get("loop"));
            originalLoop = loop;
        }

        if (model.getAttributes().containsKey("return_to_first_frame")) {
            returnToFirstFrame = Boolean.parseBoolean(model.getAttributes().get("return_to_first_frame"));
        }

        if (model.getAttributes().containsKey("loop_from_frame")) {
            loopFromFrame = Integer.parseInt(model.getAttributes().get("loop_from_frame"));
        }

        if (model.getAttributes().containsKey("play_from_current")) {
            playFromCurrent = Boolean.parseBoolean(model.getAttributes().get("play_from_current"));
            getCurrent = true;
        }
    }

    @Override
    public void setLoop(boolean loop) {
        this.loop = loop;
    }

    @Override
    public boolean isLoop() {
        return loop;
    }

    @Override
    public IAction clone() {
        PlayAnimation clone = new PlayAnimation();
        clone.startFrame = startFrame;
        clone.endFrame = endFrame;
        clone.currentFrame = currentFrame;
        clone.loopFromFrame = loopFromFrame;
        clone.loop = loop;
        clone.originalLoop = originalLoop;
        clone.done = done;
        clone.returnToFirstFrame = returnToFirstFrame;
        clone.playFromCurrent = playFromCurrent;
        clone.getCurrent = getCurrent;
        return clone;
    }
}

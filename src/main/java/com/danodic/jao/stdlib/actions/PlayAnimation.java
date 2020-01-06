package com.danodic.jao.stdlib.stdlib.actions;

import com.danodic.jao.action.Action;
import com.danodic.jao.action.IAction;
import com.danodic.jao.core.JaoLayer;
import com.danodic.jao.model.ActionModel;

@Action(name = "PlayAnimation", library = "jao.standards")
public class PlayAnimation implements IAction {

	private int startFrame;
	private int endFrame;
	private int currentFrame;
	private boolean loop;
	private boolean originalLoop;
	private boolean done;

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
	}

	@Override
	public void run(JaoLayer layer) {
		if (currentFrame >= endFrame) {
			if (loop) {
				currentFrame = startFrame;
			} else {
				currentFrame--;
				done = true;
			}
		}
		layer.getParameters().put("current_frame", currentFrame);
		currentFrame++;
	}

	@Override
	public void reset() {
		this.currentFrame = this.startFrame;
		this.loop = originalLoop;
		this.done = false;
	}

	@Override
	public boolean isDone() {
		return done;
	}

	@Override
	public void loadModel(ActionModel model) {

		if (model.getAttributes().containsKey("start_frame"))
			startFrame = Integer.parseInt(model.getAttributes().get("start_frame"));

		if (model.getAttributes().containsKey("end_frame"))
			endFrame = Integer.parseInt(model.getAttributes().get("end_frame"));

		if (model.getAttributes().containsKey("loop")) {
			loop = Boolean.parseBoolean(model.getAttributes().get("loop"));
			originalLoop = loop;
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
}

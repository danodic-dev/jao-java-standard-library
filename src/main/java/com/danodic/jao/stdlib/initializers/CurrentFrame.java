package com.danodic.jao.stdlib.initializers;

import com.danodic.jao.action.Action;
import com.danodic.jao.action.IInitializer;
import com.danodic.jao.core.JaoLayer;
import com.danodic.jao.model.ActionModel;

@Action(name = "CurrentFrame", library = "jao.standards")
public class CurrentFrame implements IInitializer {

	private int currentFrame = 0;
	
	@Override
	public void run(JaoLayer layer) {
		layer.getParameters().put("current_frame", currentFrame);
	}

	@Override
	public void loadModel(ActionModel model) {
		if(model.getAttribute()!=null) {
			currentFrame = Integer.parseInt(model.getAttribute());
		}
	}

}

package com.danodic.jao.stdlib.stdlib.initializers;

import com.danodic.jao.action.Action;
import com.danodic.jao.action.IInitializer;
import com.danodic.jao.core.JaoLayer;
import com.danodic.jao.model.ActionModel;

@Action(name = "Opacity", library = "jao.standards")
public class Opacity implements IInitializer {

	private float opacity = 1f;

	@Override
	public void run(JaoLayer layer) {
		layer.getParameters().put("opacity", opacity);
	}

	@Override
	public void loadModel(ActionModel model) {
		if (model.getAttribute() != null) {
			opacity = Integer.parseInt(model.getAttribute());
		}
	}

}

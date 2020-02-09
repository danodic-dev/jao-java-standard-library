package com.danodic.jao.stdlib.initializers;

import com.danodic.jao.action.Action;
import com.danodic.jao.action.IInitializer;
import com.danodic.jao.core.JaoLayer;
import com.danodic.jao.model.ActionModel;

@Action(name = "Opacity", library = "jao.standards")
public class Opacity implements IInitializer {

	private float opacity = 1f;
	private boolean noReset = false;
	private boolean initialized = false;

	@Override
	public void run(JaoLayer layer) {
		if (noReset && !initialized || !noReset) {
			layer.getParameters().put("opacity", opacity);
			initialized = true;
		}
	}

	@Override
	public void loadModel(ActionModel model) {
		if (model.getAttribute() != null) {
			if (model.getAttribute().toLowerCase().contains("no reset")) {
				opacity = Float.parseFloat(model.getAttribute().split(" ")[0]);
				noReset = true;
			} else {
				opacity = Float.parseFloat(model.getAttribute());
			}

		}
	}

}

package com.danodic.jao.stdlib.initializers;

import com.danodic.jao.action.Action;
import com.danodic.jao.action.IInitializer;
import com.danodic.jao.core.JaoLayer;
import com.danodic.jao.model.ActionModel;

@Action(name = "Scale", library = "jao.standards")
public class Scale implements IInitializer {

    private float scaleX = 0f;
    private float scaleY = 0f;

    @Override
    public void run(JaoLayer layer) {
        layer.getParameters().put("scale_x", scaleX);
        layer.getParameters().put("scale_y", scaleY);
    }

    @Override
    public void loadModel(ActionModel model) {
        if (model.getAttributes().containsKey("scale_x")) {
            scaleX = Float.parseFloat(model.getAttributes().get("scale_x"));
        }
        if (model.getAttributes().containsKey("scale_y")) {
            scaleY = Float.parseFloat(model.getAttributes().get("scale_y"));
        }
    }
    
    @Override
    public IInitializer clone() {
        Scale clone = new Scale();
        clone.scaleX = scaleX;
        clone.scaleY = scaleY;
        return clone;
    }

}

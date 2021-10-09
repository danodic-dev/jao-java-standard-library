package com.danodic.jao.stdlib.initializers;

import com.danodic.jao.action.Action;
import com.danodic.jao.action.IInitializer;
import com.danodic.jao.core.JaoLayer;
import com.danodic.jao.model.ActionModel;

@Action(name = "Position", library = "jao.standards")
public class Position implements IInitializer {

    private float posX = 0f;
    private float posY = 0f;

    @Override
    public void run(JaoLayer layer) {
        layer.getParameters().put("position_x", posX);
        layer.getParameters().put("position_y", posY);
    }

    @Override
    public void loadModel(ActionModel model) {
        if (model.getAttributes().containsKey("position_x")) {
            posX = Float.parseFloat(model.getAttributes().get("position_x"));
        }
        if (model.getAttributes().containsKey("position_y")) {
            posY = Float.parseFloat(model.getAttributes().get("position_y"));
        }
    }
    
    @Override
    public IInitializer clone() {
        Position clone = new Position();
        clone.posX = posX;
        clone.posY = posY;
        return clone;
    }

}

package com.danodic.jao.stdlib.initializers;

import com.danodic.jao.action.Action;
import com.danodic.jao.action.IInitializer;
import com.danodic.jao.core.JaoLayer;
import com.danodic.jao.model.ActionModel;

@Action(name = "Align", library = "jao.standards")
public class Align implements IInitializer {

    private String alignX;
    private String alignY;

    @Override
    public void run(JaoLayer layer) {
        if(alignX != null) {
            layer.getParameters().put("align_x", alignX);
        }
        if(alignY != null) {            
            layer.getParameters().put("align_y", alignY);
        }
    }

    @Override
    public void loadModel(ActionModel model) {
        if (model.getAttributes().containsKey("align_x")) {
            String alignment = model.getAttributes().get("align_x");
            if(alignment.equalsIgnoreCase("center") || alignment.equalsIgnoreCase("left") || alignment.equalsIgnoreCase("right")) {
                alignX = alignment.toLowerCase();
            }
        }
        if (model.getAttributes().containsKey("align_y")) {
            String alignment = model.getAttributes().get("align_y");
            if(alignment.equalsIgnoreCase("center") || alignment.equalsIgnoreCase("left") || alignment.equalsIgnoreCase("right")) {
                alignY = alignment.toLowerCase();
            }
        }
    }
    
    @Override
    public IInitializer clone() {
        Align clone = new Align();
        clone.alignX = alignX;
        clone.alignY = alignY;
        return clone;
    }

}

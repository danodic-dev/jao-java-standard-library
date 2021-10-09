package com.danodic.jao.stdlib.initializers;

import com.danodic.jao.action.Action;
import com.danodic.jao.action.IInitializer;
import com.danodic.jao.core.JaoLayer;
import com.danodic.jao.model.ActionModel;

@Action(name = "Loop", library = "jao.standards")
public class Loop implements IInitializer {

    private Boolean loop = false;

    @Override
    public void run(JaoLayer layer) {
        layer.getJao().setLoop(loop);
    }

    @Override
    public void loadModel(ActionModel model) {
        if (model.getAttributes().containsKey("value")) {
            loop = Boolean.valueOf(model.getAttributes().get("value"));
        }
    }
    
    @Override
    public IInitializer clone() {
        Loop clone = new Loop();
        clone.loop = loop;
        return clone;
    }

}

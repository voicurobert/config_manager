package ro.dev.ree.cross_config_manager.ui;

import ro.dev.ree.cross_config_manager.ui.link_type.LinkTypeGui;
import ro.dev.ree.cross_config_manager.ui.node_type.NodeTypeGui;
import ro.dev.ree.cross_config_manager.ui.node_type_rules.NodeTypeRulesGui;
import ro.dev.ree.cross_config_manager.ui.utils.Drawable;

import java.util.ArrayList;
import java.util.List;

public class ConfigManagerComposites {

    private final List<Drawable> composites = new ArrayList<>();

    public ConfigManagerComposites() {
        composites.add(new NodeTypeGui());
        composites.add(new LinkTypeGui());
        composites.add(new NodeTypeRulesGui());
        //composites.add(new LinkTypeRulesGui());
        //composites.add(new LinkTypeNodeTypeRulesGui());
    }

    public List<Drawable> getComposites() {
        return composites;
    }
}

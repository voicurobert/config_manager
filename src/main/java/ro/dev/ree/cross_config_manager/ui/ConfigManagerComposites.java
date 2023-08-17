package ro.dev.ree.cross_config_manager.ui;

import ro.dev.ree.cross_config_manager.ui.component_status.ComponentStatusGui;
import ro.dev.ree.cross_config_manager.ui.core_class_type.CoreClassTypeGui;
import ro.dev.ree.cross_config_manager.ui.link_status.LinkStatusGui;
import ro.dev.ree.cross_config_manager.ui.link_type.LinkTypeGui;
import ro.dev.ree.cross_config_manager.ui.link_type_node_type_rules.LinkTypeNodeTypeRulesGui;
import ro.dev.ree.cross_config_manager.ui.link_type_rules.LinkTypeRulesGui;
import ro.dev.ree.cross_config_manager.ui.node_status.NodeStatusGui;
import ro.dev.ree.cross_config_manager.ui.node_type.NodeTypeGui;
import ro.dev.ree.cross_config_manager.ui.node_type_rules.NodeTypeRulesGui;
import ro.dev.ree.cross_config_manager.ui.service_status.ServiceStatusGui;
import ro.dev.ree.cross_config_manager.ui.technology_tree.TechnologyTreeGui;
import ro.dev.ree.cross_config_manager.ui.utils.ManageableComponent;

import java.util.ArrayList;
import java.util.List;

public class ConfigManagerComposites {

    private final List<ManageableComponent> composites = new ArrayList<>();

    public ConfigManagerComposites() {
        composites.add(new CoreClassTypeGui());
        composites.add(new NodeTypeGui());
        composites.add(new NodeStatusGui());
        composites.add(new LinkTypeGui());
        composites.add(new LinkStatusGui());
        composites.add(new NodeTypeRulesGui());
        composites.add(new LinkTypeRulesGui());
        composites.add(new LinkTypeNodeTypeRulesGui());
        composites.add(new ComponentStatusGui());
        composites.add(new ServiceStatusGui());
        composites.add(new TechnologyTreeGui());

    }

    public List<ManageableComponent> getComposites() {
        return composites;
    }
}

package ro.dev.ree.cross_config_manager.ui.node_type;

import org.springframework.context.annotation.Configuration;
import ro.dev.ree.cross_config_manager.model.node_type.NodeTypeService;

@Configuration
public class NodeTypeModule {

    private NodeTypeService nodeTypeService;

    public NodeTypeModule(NodeTypeService nodeTypeService) {
        this.nodeTypeService = nodeTypeService;
    }
}

package ro.dev.ree.cross_config_manager.model.node_type_rules;

import org.springframework.beans.BeanUtils;
import ro.dev.ree.cross_config_manager.model.node_type.NodeType;
import ro.dev.ree.cross_config_manager.model.node_type.NodeTypeDto;
import ro.dev.ree.cross_config_manager.model.node_type.NodeTypeRepository;

public class NodeTypeRulesService {
    private final NodeTypeRulesRepository repository;

    public NodeTypeRulesService(NodeTypeRulesRepository repository) {
        this.repository = repository;
    }

    public void save(NodeTypeRulesDto nodeTypeRulesDto) {
        NodeTypeRules nodeType = new NodeTypeRules();
        BeanUtils.copyProperties(nodeTypeRulesDto, nodeType);
        repository.save(nodeType);
    }
}

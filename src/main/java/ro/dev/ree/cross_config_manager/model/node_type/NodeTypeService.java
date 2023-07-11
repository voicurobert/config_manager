package ro.dev.ree.cross_config_manager.model.node_type;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class NodeTypeService {

    private final NodeTypeRepository repository;

    public NodeTypeService(NodeTypeRepository repository) {
        this.repository = repository;
    }

    public void save(NodeTypeDto nodeTypeDto) {
        NodeType nodeType = new NodeType();
        BeanUtils.copyProperties(nodeTypeDto, nodeType);
        repository.save(nodeType);
    }

}

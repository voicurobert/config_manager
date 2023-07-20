package ro.dev.ree.cross_config_manager.model.node_type;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import ro.dev.ree.cross_config_manager.model.RecordDto;
import ro.dev.ree.cross_config_manager.model.ServiceRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NodeTypeService implements ServiceRepository {

    private final NodeTypeRepository repository;

    public NodeTypeService(NodeTypeRepository repository) {
        this.repository = repository;
    }

    public void save(NodeTypeDto nodeTypeDto) {
        NodeType nodeType = new NodeType();
        BeanUtils.copyProperties(nodeTypeDto, nodeType);
        repository.save(nodeType);
    }


    @Override
    public RecordDto insert(RecordDto recordDto) {
        return null;
    }

    @Override
    public RecordDto update(RecordDto recordDto) {
        return null;
    }

    @Override
    public void delete(RecordDto recordDto) {

    }

    @Override
    public List<RecordDto> findAll() {
        return null;
    }

    public List<RecordDto> findAllByConfigId(String configId) {
        return repository.findAll().stream().
                filter(nodeType -> nodeType.getConfigId().equals(configId)).
                map(nodeType -> {
                    NodeTypeDto dto = new NodeTypeDto();
                    BeanUtils.copyProperties(dto, nodeType);
                    return dto;
                }).
                collect(Collectors.toList());
    }

//    public List<NodeTypeDto> findAllByConfigIdNew(String configId) {
//        repository.findAll(ExampleMatcher.)
//    }
}

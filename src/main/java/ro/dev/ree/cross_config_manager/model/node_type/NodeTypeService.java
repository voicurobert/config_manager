package ro.dev.ree.cross_config_manager.model.node_type;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import ro.dev.ree.cross_config_manager.ConfigManagerContextProvider;
import ro.dev.ree.cross_config_manager.model.RecordDto;
import ro.dev.ree.cross_config_manager.model.ServiceRepository;
import ro.dev.ree.cross_config_manager.model.config_type.ConfigSingleton;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NodeTypeService implements ServiceRepository {

    private final NodeTypeRepository repository;

    public NodeTypeService(NodeTypeRepository repository) {
        this.repository = repository;
    }

    @Override
    public String insertOrUpdate(RecordDto recordDto) {
        NodeType nodeType = new NodeType();
        NodeTypeDto nodeTypeDto = (NodeTypeDto) recordDto;

        BeanUtils.copyProperties(nodeTypeDto, nodeType);
        NodeType insert = repository.save(nodeType);

        nodeTypeDto.setId(insert.getId());

        return nodeTypeDto.getId();
    }

    @Override
    public void delete(RecordDto recordDto) {
        NodeType nodeType = new NodeType();

        NodeTypeDto nodeTypeDto = (NodeTypeDto) recordDto;
        BeanUtils.copyProperties(nodeTypeDto, nodeType);

        repository.delete(nodeType);
    }

    public List<RecordDto> findAllByConfigId(String configId) {
        return repository.findAll().stream().
                filter(nodeType -> nodeType.getConfigId().equals(configId)).
                map(nodeType -> {
                    NodeTypeDto dto = new NodeTypeDto();
                    BeanUtils.copyProperties(nodeType, dto);
                    return dto;
                }).
                collect(Collectors.toList());
    }

    @Override
    public RecordDto findById(String Id) {
        return repository.findAll().stream().
                filter(nodeType -> nodeType.getId().equals(Id)).
                map(nodeType -> {
                    NodeTypeDto dto = new NodeTypeDto();
                    BeanUtils.copyProperties(nodeType, dto);
                    return dto;
                }).
                findFirst().orElse(null);
    }
}


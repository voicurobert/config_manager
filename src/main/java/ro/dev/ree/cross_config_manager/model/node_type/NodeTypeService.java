package ro.dev.ree.cross_config_manager.model.node_type;

import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import ro.dev.ree.cross_config_manager.model.RecordDto;
import ro.dev.ree.cross_config_manager.model.ServiceRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NodeTypeService implements ServiceRepository {

    private final NodeTypeRepository repository;
    private final MongoTemplate mongoTemplate;

    public NodeTypeService(NodeTypeRepository repository, MongoTemplate mongoTemplate) {
        this.repository = repository;
        this.mongoTemplate = mongoTemplate;
    }

    public void save(NodeTypeDto nodeTypeDto) {
        NodeType nodeType = new NodeType();
        BeanUtils.copyProperties(nodeTypeDto, nodeType);
        repository.save(nodeType);
    }


    @Override
    public RecordDto insert(RecordDto recordDto) {
        NodeType nodeType = new NodeType();

        NodeTypeDto nodeTypeDto = (NodeTypeDto) recordDto;
        BeanUtils.copyProperties(nodeTypeDto, nodeType);

        NodeType insert = repository.insert(nodeType);
        nodeTypeDto.setId(insert.getId());

        return nodeTypeDto;
    }

    @Override
    public RecordDto update(RecordDto recordDto) {
        NodeType nodeType = new NodeType();

        NodeTypeDto nodeTypeDto = (NodeTypeDto) recordDto;
        BeanUtils.copyProperties(nodeTypeDto, nodeType);

        NodeType insert = repository.save(nodeType);
        nodeTypeDto.setId(insert.getId());

        return nodeTypeDto;
    }

    @Override
    public void delete(RecordDto recordDto) {
        NodeType nodeType = new NodeType();

        NodeTypeDto nodeTypeDto = (NodeTypeDto) recordDto;
        BeanUtils.copyProperties(nodeTypeDto, nodeType);

        repository.delete(nodeType);
    }

    @Override
    public List<RecordDto> findAll() {
        return repository.findAll().stream().map(nodeType -> {
            NodeTypeDto nodeTypeDto = new NodeTypeDto();
            BeanUtils.copyProperties(nodeType, nodeTypeDto);
            return nodeTypeDto;
        }).collect(Collectors.toList());
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

    public List<RecordDto> findAllByConfigIdNew(String configId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("configId").is(configId));

        return mongoTemplate.find(query, NodeType.class).stream().
                map(nodeType -> {
                    NodeTypeDto dto = new NodeTypeDto();
                    BeanUtils.copyProperties(dto, nodeType);
                    return dto;
                }).
                collect(Collectors.toList());
    }
}

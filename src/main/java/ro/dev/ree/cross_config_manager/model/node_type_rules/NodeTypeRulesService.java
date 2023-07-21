package ro.dev.ree.cross_config_manager.model.node_type_rules;

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
public class NodeTypeRulesService implements ServiceRepository {

    private final NodeTypeRulesRepository repository;
    private final MongoTemplate mongoTemplate;

    public NodeTypeRulesService(NodeTypeRulesRepository repository, MongoTemplate mongoTemplate) {

        this.repository = repository;
        this.mongoTemplate = mongoTemplate;
    }

    public void save(NodeTypeRulesDto nodeTypeRulesDto) {
        NodeTypeRules nodeTypeRules = new NodeTypeRules();
        BeanUtils.copyProperties(nodeTypeRulesDto, nodeTypeRules);
        repository.save(nodeTypeRules);
    }

    @Override
    public RecordDto insert(RecordDto recordDto) {
        NodeTypeRules nodeTypeRules = new NodeTypeRules();

        NodeTypeRulesDto nodeTypeRulesDto = (NodeTypeRulesDto) recordDto;
        BeanUtils.copyProperties(nodeTypeRulesDto, nodeTypeRules);

        NodeTypeRules insert = repository.insert(nodeTypeRules);
        nodeTypeRulesDto.setId(insert.getId());

        return nodeTypeRulesDto;
    }

    @Override
    public RecordDto update(RecordDto recordDto) {
        NodeTypeRules nodeTypeRules = new NodeTypeRules();

        NodeTypeRulesDto nodeTypeRulesDto = (NodeTypeRulesDto) recordDto;
        BeanUtils.copyProperties(nodeTypeRulesDto, nodeTypeRules);

        NodeTypeRules insert = repository.save(nodeTypeRules);
        nodeTypeRulesDto.setId(insert.getId());

        return nodeTypeRulesDto;
    }

    @Override
    public void delete(RecordDto recordDto) {
        NodeTypeRules nodeTypeRules = new NodeTypeRules();

        NodeTypeRulesDto nodeTypeRulesDto = (NodeTypeRulesDto) recordDto;
        BeanUtils.copyProperties(nodeTypeRulesDto, nodeTypeRules);

        repository.delete(nodeTypeRules);
    }

    @Override
    public List<RecordDto> findAll() {
        return repository.findAll().stream().map(nodeTypeRules -> {
            NodeTypeRulesDto nodeTypeRulesDto = new NodeTypeRulesDto();
            BeanUtils.copyProperties(nodeTypeRules, nodeTypeRulesDto);
            return nodeTypeRulesDto;
        }).collect(Collectors.toList());
    }

    public List<RecordDto> findAllByConfigId(String configId) {
        return repository.findAll().stream().
                filter(nodeTypeRules -> nodeTypeRules.getConfigId().equals(configId)).
                map(nodeTypeRules -> {
                    NodeTypeRulesDto dto = new NodeTypeRulesDto();
                    BeanUtils.copyProperties(dto, nodeTypeRules);
                    return dto;
                }).
                collect(Collectors.toList());
    }
    public List<RecordDto> findAllByConfigIdNew(String configId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("configId").is(configId));

        return mongoTemplate.find(query, NodeTypeRules.class).stream().
                map(nodeTypeRules -> {
                    NodeTypeRulesDto dto = new NodeTypeRulesDto();
                    BeanUtils.copyProperties(dto, nodeTypeRules);
                    return dto;
                }).
                collect(Collectors.toList());
    }
}

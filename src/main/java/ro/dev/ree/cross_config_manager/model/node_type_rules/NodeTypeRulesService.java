package ro.dev.ree.cross_config_manager.model.node_type_rules;

import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import ro.dev.ree.cross_config_manager.model.RecordDto;
import ro.dev.ree.cross_config_manager.model.ServiceRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class NodeTypeRulesService implements ServiceRepository {

    private final NodeTypeRulesRepository repository;
    private final MongoTemplate mongoTemplate;

    public NodeTypeRulesService(NodeTypeRulesRepository repository, MongoTemplate mongoTemplate) {

        this.repository = repository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public String insertOrUpdate(Map<String,Object> oldColumnValues, RecordDto recordDto) {
        NodeTypeRules nodeTypeRules = new NodeTypeRules();
        NodeTypeRulesDto nodeTypeRulesDto = (NodeTypeRulesDto) recordDto;

        BeanUtils.copyProperties(nodeTypeRulesDto, nodeTypeRules);
        NodeTypeRules insert = repository.save(nodeTypeRules);

        if(nodeTypeRulesDto.getId() == null){
            nodeTypeRulesDto.setId(insert.getId());
        }

        return nodeTypeRulesDto.getId();
    }

    @Override
    public void delete(RecordDto recordDto) {
        NodeTypeRules nodeTypeRules = new NodeTypeRules();

        NodeTypeRulesDto nodeTypeRulesDto = (NodeTypeRulesDto) recordDto;
        BeanUtils.copyProperties(nodeTypeRulesDto, nodeTypeRules);

        repository.delete(nodeTypeRules);
    }

    @Override
    public RecordDto findById(String Id) {
        return repository.findAll().stream().
                filter(nodeTypeRules -> nodeTypeRules.getId().equals(Id)).
                map(nodeTypeRules -> {
                    NodeTypeRulesDto dto = new NodeTypeRulesDto();
                    BeanUtils.copyProperties(nodeTypeRules, dto);
                    return dto;
                }).
                findFirst().orElse(null);
    }

    @Override
    public List<RecordDto> findAllByConfigId(String configId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("configId").is(configId));

        return mongoTemplate.find(query, NodeTypeRules.class).stream().
                map(nodeTypeRules -> {
                    NodeTypeRulesDto dto = new NodeTypeRulesDto();
                    BeanUtils.copyProperties(nodeTypeRules, dto);
                    return dto;
                }).
                collect(Collectors.toList());
    }
}

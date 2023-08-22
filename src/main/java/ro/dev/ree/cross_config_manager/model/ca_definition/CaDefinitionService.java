package ro.dev.ree.cross_config_manager.model.ca_definition;

import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import ro.dev.ree.cross_config_manager.ConfigManagerContextProvider;
import ro.dev.ree.cross_config_manager.model.RecordDto;
import ro.dev.ree.cross_config_manager.model.ServiceRepository;
import ro.dev.ree.cross_config_manager.model.ca_definition_set.CaDefinitionSet;
import ro.dev.ree.cross_config_manager.model.ca_definition_set.CaDefinitionSetDto;
import ro.dev.ree.cross_config_manager.model.ca_definition_set.CaDefinitionSetService;
import ro.dev.ree.cross_config_manager.model.link_type_node_type_rules.LinkTypeNodeTypeRules;
import ro.dev.ree.cross_config_manager.model.link_type_node_type_rules.LinkTypeNodeTypeRulesDto;
import ro.dev.ree.cross_config_manager.model.link_type_node_type_rules.LinkTypeNodeTypeRulesService;
import ro.dev.ree.cross_config_manager.model.node_type.NodeTypeDto;
import ro.dev.ree.cross_config_manager.model.node_type_rules.NodeTypeRules;
import ro.dev.ree.cross_config_manager.model.node_type_rules.NodeTypeRulesDto;
import ro.dev.ree.cross_config_manager.model.node_type_rules.NodeTypeRulesService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CaDefinitionService implements ServiceRepository {
    private final CaDefinitionRepository repository;
    private final MongoTemplate mongoTemplate;

    public CaDefinitionService(CaDefinitionRepository repository, MongoTemplate mongoTemplate) {
        this.repository = repository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public String insertOrUpdate(Map<String, Object> oldColumnValues, RecordDto recordDto) {
        CaDefinition caDefinition = new CaDefinition();
        CaDefinitionDto caDefinitionDto = (CaDefinitionDto) recordDto;

        BeanUtils.copyProperties(caDefinitionDto, caDefinition);
        CaDefinition insert = repository.save(caDefinition);

        if(caDefinitionDto.getId() == null) {
            caDefinitionDto.setId(insert.getId());
        }
        else if(oldColumnValues != null){
            // Search for object with this old caDefinition.attributeName and change it with the new caDefinition.attributeName
            findByName((String) oldColumnValues.get("attributeName"), recordDto);
        }

        return caDefinitionDto.getId();
    }

    public void findByName(String name, RecordDto recordDto) {
        Query query = new Query();
        query.addCriteria(Criteria.where("caDefinitionName").is(name));
        for (CaDefinitionSet caDefinitionSet: mongoTemplate.find(query, CaDefinitionSet.class)) {
            caDefinitionSet.setCaDefinitionName(((CaDefinitionDto) recordDto).getAttributeName());
            CaDefinitionSetService caDefinitionSetService = ConfigManagerContextProvider.getBean(CaDefinitionSetService.class);
            CaDefinitionSetDto caDefinitionSetDto = new CaDefinitionSetDto();
            BeanUtils.copyProperties(caDefinitionSet, caDefinitionSetDto);
            caDefinitionSetService.insertOrUpdate(null, caDefinitionSetDto);
        }
    }

    @Override
    public void delete(RecordDto recordDto) {
        CaDefinition caDefinition = new CaDefinition();

        CaDefinitionDto caDefinitionDto = (CaDefinitionDto) recordDto;
        BeanUtils.copyProperties(caDefinitionDto, caDefinition);

        repository.delete(caDefinition);
    }

    @Override
    public List<RecordDto> findAllByConfigId(String configId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("configId").is(configId));

        return mongoTemplate.find(query, CaDefinition.class).stream().
                map(caDefinition -> {
                    CaDefinitionDto dto = new CaDefinitionDto();
                    BeanUtils.copyProperties(caDefinition, dto);
                    return dto;
                }).
                collect(Collectors.toList());
    }

    @Override
    public RecordDto findById(String Id) {
        return repository.findAll().stream().
                filter(caDefinition -> caDefinition.getId().equals(Id)).
                map(caDefinition -> {
                    CaDefinitionDto dto = new CaDefinitionDto();
                    BeanUtils.copyProperties(caDefinition, dto);
                    return dto;
                }).
                findFirst().orElse(null);
    }
}

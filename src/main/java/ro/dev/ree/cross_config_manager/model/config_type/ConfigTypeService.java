package ro.dev.ree.cross_config_manager.model.config_type;

import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import ro.dev.ree.cross_config_manager.model.RecordDto;
import ro.dev.ree.cross_config_manager.model.component_status.ComponentStatus;
import ro.dev.ree.cross_config_manager.model.core_class_type.CoreClassType;
import ro.dev.ree.cross_config_manager.model.link_status.LinkStatus;
import ro.dev.ree.cross_config_manager.model.link_type.LinkType;
import ro.dev.ree.cross_config_manager.model.link_type_node_type_rules.LinkTypeNodeTypeRules;
import ro.dev.ree.cross_config_manager.model.link_type_rules.LinkTypeRules;
import ro.dev.ree.cross_config_manager.model.node_status.NodeStatus;
import ro.dev.ree.cross_config_manager.model.node_type.NodeType;
import ro.dev.ree.cross_config_manager.model.node_type_rules.NodeTypeRules;
import ro.dev.ree.cross_config_manager.model.service_status.ServiceStatus;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class ConfigTypeService {
    private final ConfigRepository repository;
    private final MongoTemplate mongoTemplate;

    public ConfigTypeService(ConfigRepository repository, MongoTemplate mongoTemplate) {
        this.repository = repository;
        this.mongoTemplate = mongoTemplate;
    }

    public ConfigDto save(ConfigDto configDto) {
        Config configType = new Config();
        BeanUtils.copyProperties(configDto, configType);
        Config config = repository.save(configType);
        configDto.setId(config.getId());

        return configDto;
    }

    public void update(ConfigDto configDto) {
        Config configType = new Config();
        BeanUtils.copyProperties(configDto, configType);
        repository.save(configType);
    }

    public void delete(ConfigDto configDto) {
        Query query = new Query();
        query.addCriteria(Criteria.where("configId").is(configDto.getId()));
        mongoTemplate.remove(query, ComponentStatus.class);
        mongoTemplate.remove(query, CoreClassType.class);
        mongoTemplate.remove(query, LinkStatus.class);
        mongoTemplate.remove(query, LinkType.class);
        mongoTemplate.remove(query, LinkTypeNodeTypeRules.class);
        mongoTemplate.remove(query, LinkTypeRules.class);
        mongoTemplate.remove(query, NodeStatus.class);
        mongoTemplate.remove(query, NodeType.class);
        mongoTemplate.remove(query, NodeTypeRules.class);
        mongoTemplate.remove(query, ServiceStatus.class);
        query.addCriteria(Criteria.where("id").is(configDto.getId()));
        mongoTemplate.remove(query, Config.class);
        Config configType = new Config();
        BeanUtils.copyProperties(configDto, configType);
        repository.delete(configType);
    }

    public List<Config> findAll() {
        return repository.findAll();
    }

    public List<RecordDto> findByConfigName(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));

        return mongoTemplate.find(query, Config.class).stream().
                map(config -> {
                    ConfigDto dto = new ConfigDto();
                    BeanUtils.copyProperties(config, dto);
                    return dto;
                }).
                collect(Collectors.toList());
    }

}


package ro.dev.ree.cross_config_manager.model.config_type;

import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import ro.dev.ree.cross_config_manager.model.RecordDto;

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
                    BeanUtils.copyProperties(dto, config);
                    return dto;
                }).
                collect(Collectors.toList());
    }

}


package ro.dev.ree.cross_config_manager.model.ca_definition;

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
        //TODO de adaugat aici update pentru CaDefinitionSet

        return caDefinitionDto.getId();
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

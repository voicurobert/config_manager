package ro.dev.ree.cross_config_manager.model.ca_definition_set;

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
public class CaDefinitionSetService implements ServiceRepository {
    private final CaDefinitionSetRepository repository;
    private final MongoTemplate mongoTemplate;

    public CaDefinitionSetService(CaDefinitionSetRepository repository, MongoTemplate mongoTemplate) {
        this.repository = repository;
        this.mongoTemplate = mongoTemplate;
    }


    @Override
    public String insertOrUpdate(Map<String, Object> oldColumnValues, RecordDto recordDto) {
        CaDefinitionSet caDefinitionSet = new CaDefinitionSet();
        CaDefinitionSetDto caDefinitionSetDto = (CaDefinitionSetDto) recordDto;

        BeanUtils.copyProperties(caDefinitionSetDto, caDefinitionSet);
        CaDefinitionSet insert = repository.save(caDefinitionSet);

        if(caDefinitionSetDto.getId() == null) {
            caDefinitionSetDto.setId(insert.getId());
        }

        return caDefinitionSetDto.getId();
    }

    @Override
    public void delete(RecordDto recordDto) {
        CaDefinitionSet caDefinitionSet = new CaDefinitionSet();

        CaDefinitionSetDto caDefinitionSetDto = (CaDefinitionSetDto) recordDto;
        BeanUtils.copyProperties(caDefinitionSetDto, caDefinitionSet);

        repository.delete(caDefinitionSet);
    }

    @Override
    public List<RecordDto> findAllByConfigId(String configId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("configId").is(configId));

        return mongoTemplate.find(query, CaDefinitionSet.class).stream().
                map(caDefinitionSet -> {
                    CaDefinitionSetDto dto = new CaDefinitionSetDto();
                    BeanUtils.copyProperties(caDefinitionSet, dto);
                    return dto;
                }).
                collect(Collectors.toList());
    }

    @Override
    public RecordDto findById(String Id) {
        return repository.findAll().stream().
                filter(caDefinitionSet -> caDefinitionSet.getId().equals(Id)).
                map(caDefinitionSet -> {
                    CaDefinitionSetDto dto = new CaDefinitionSetDto();
                    BeanUtils.copyProperties(caDefinitionSet, dto);
                    return dto;
                }).
                findFirst().orElse(null);
    }
}

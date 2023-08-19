package ro.dev.ree.cross_config_manager.model.technologies;

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
public class TechnologiesService implements ServiceRepository {

    private final TechnologiesRepository repository;
    private final MongoTemplate mongoTemplate;

    public TechnologiesService(TechnologiesRepository repository, MongoTemplate mongoTemplate) {
        this.repository = repository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public String insertOrUpdate(Map<String, Object> oldColumnValues, RecordDto recordDto) {
        Technologies technologies = new Technologies();
        TechnologiesDto technologiesDto = (TechnologiesDto) recordDto;

        BeanUtils.copyProperties(technologiesDto, technologies);
        Technologies insert = repository.save(technologies);

        if (technologiesDto.getId() == null) {
            technologiesDto.setId(insert.getId());
        }

        return technologiesDto.getId();
    }

    @Override
    public void delete(RecordDto recordDto) {
        Technologies technologies = new Technologies();

        TechnologiesDto technologiesDto = (TechnologiesDto) recordDto;
        BeanUtils.copyProperties(technologiesDto, technologies);

        repository.delete(technologies);
    }

    @Override
    public RecordDto findById(String Id) {
        return repository.findAll().stream().
                filter(technologies -> technologies.getId().equals(Id)).
                map(technologies -> {
                    TechnologiesDto dto = new TechnologiesDto();
                    BeanUtils.copyProperties(technologies, dto);
                    return dto;
                }).
                findFirst().orElse(null);
    }

    @Override
    public List<RecordDto> findAllByConfigId(String configId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("configId").is(configId));

        return mongoTemplate.find(query, Technologies.class).stream().
                map(technologies -> {
                    TechnologiesDto dto = new TechnologiesDto();
                    BeanUtils.copyProperties(technologies, dto);
                    return dto;
                }).
                collect(Collectors.toList());
    }
}

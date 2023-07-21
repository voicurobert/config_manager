package ro.dev.ree.cross_config_manager.model.class_type;

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
public class ClassTypeService implements ServiceRepository {

    private final ClassTypeRepository repository;
    private final MongoTemplate mongoTemplate;

    public ClassTypeService(ClassTypeRepository repository, MongoTemplate mongoTemplate) {
        this.repository = repository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public RecordDto insert(RecordDto recordDto) {
        ClassType classType = new ClassType();

        ClassTypeDto classTypeDto = (ClassTypeDto) recordDto;
        BeanUtils.copyProperties(classTypeDto, classType);

        ClassType insert = repository.insert(classType);
        classTypeDto.setId(insert.getId());

        return classTypeDto;
    }

    @Override
    public RecordDto update(RecordDto recordDto) {
        ClassType classType = new ClassType();

        ClassTypeDto classTypeDto = (ClassTypeDto) recordDto;
        BeanUtils.copyProperties(classTypeDto, classType);

        ClassType insert = repository.save(classType);
        classTypeDto.setId(insert.getId());

        return classTypeDto;
    }

    @Override
    public void delete(RecordDto recordDto) {
        ClassTypeDto classTypeDto = (ClassTypeDto) recordDto;
        ClassType classType = new ClassType();
        BeanUtils.copyProperties(classTypeDto, classType);

        repository.delete(classType);
    }

    @Override
    public List<RecordDto> findAll() {
        return repository.findAll().stream().map(classType -> {
            ClassTypeDto classTypeDto = new ClassTypeDto();
            BeanUtils.copyProperties(classType, classTypeDto);
            return classTypeDto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<RecordDto> findAllByConfigId(String configId) {
        return repository.findAll().stream().
                filter(classType -> classType.getConfigId().equals(configId)).
                map(classType -> {
                    ClassTypeDto dto = new ClassTypeDto();
                    BeanUtils.copyProperties(dto, classType);
                    return dto;
                }).
                collect(Collectors.toList());
    }

    public List<RecordDto> findAllByConfigIdNew(String configId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("configId").is(configId));

        return mongoTemplate.find(query, ClassType.class).stream().
                map(classType -> {
                    ClassTypeDto dto = new ClassTypeDto();
                    BeanUtils.copyProperties(dto, classType);
                    return dto;
                }).
                collect(Collectors.toList());
    }
}

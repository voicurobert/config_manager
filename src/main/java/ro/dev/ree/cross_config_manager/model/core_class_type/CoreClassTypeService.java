package ro.dev.ree.cross_config_manager.model.core_class_type;

import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import ro.dev.ree.cross_config_manager.model.RecordDto;
import ro.dev.ree.cross_config_manager.model.ServiceRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CoreClassTypeService implements ServiceRepository {

    private final CoreClassTypeRepository repository;
    private final MongoTemplate mongoTemplate;

    public CoreClassTypeService(CoreClassTypeRepository repository, MongoTemplate mongoTemplate) {
        this.repository = repository;
        this.mongoTemplate = mongoTemplate;
    }

//    public void save(CoreClassTypeDto classTypeDto) {
//        CoreClassType classType = new CoreClassType();
//        BeanUtils.copyProperties(classTypeDto, classType);
//        repository.save(classType);
//    }

    @Override
    public String insertOrUpdate(RecordDto recordDto) {
        CoreClassType coreClassType = new CoreClassType();
        CoreClassTypeDto coreClassTypeDto = (CoreClassTypeDto) recordDto;

        BeanUtils.copyProperties(coreClassTypeDto, coreClassType);
        CoreClassType insert = repository.save(coreClassType);

        coreClassTypeDto.setId(insert.getId());

        return coreClassTypeDto.getId();
    }


    @Override
    public void delete(RecordDto recordDto) {
        CoreClassTypeDto coreClassTypeDto = (CoreClassTypeDto) recordDto;
        CoreClassType coreClassType = new CoreClassType();
        BeanUtils.copyProperties(coreClassTypeDto, coreClassType);

        repository.delete(coreClassType);
    }

//    @Override
//    public List<RecordDto> findAll(String[] columns, String[] old_columns) {
//        return repository.findAll().stream().
//                filter(classType -> classType.getName().equals(old_columns[0])
//                        && classType.getPath().equals(old_columns[1])
//                        && classType.getParentPath().equals(old_columns[2])).
//                map(classType -> {
//                    classType.setName(columns[0]);
//                    classType.setPath(columns[1]);
//                    classType.setParentPath(columns[2]);
//                    CoreClassTypeDto dto = new CoreClassTypeDto();
//                    BeanUtils.copyProperties(classType, dto);
//                    return dto;
//                }).
//                collect(Collectors.toList());
//    }

    @Override
    public List<RecordDto> findAllByConfigId(String configId) {
        return repository.findAll().stream().
                filter(coreClassType -> coreClassType.getConfigId().equals(configId)).
                map(coreClassType -> {
                    CoreClassTypeDto dto = new CoreClassTypeDto();
                    BeanUtils.copyProperties(coreClassType, dto);
                    return dto;
                }).
                collect(Collectors.toList());
    }

    @Override
    public RecordDto findById(String Id) {
        return repository.findAll().stream().
                filter(coreClassType -> coreClassType.getId().equals(Id)).
                map(coreClassType -> {
                    CoreClassTypeDto dto = new CoreClassTypeDto();
                    BeanUtils.copyProperties(coreClassType, dto);
                    return dto;
                }).
                findFirst().orElse(null);
    }

//    public List<RecordDto> findAllByConfigIdNew(String configId) {
//        Query query = new Query();
//        query.addCriteria(Criteria.where("configId").is(configId));
//
//        return mongoTemplate.find(query, CoreClassType.class).stream().
//                map(classType -> {
//                    CoreClassTypeDto dto = new CoreClassTypeDto();
//                    BeanUtils.copyProperties(classType, dto);
//                    return dto;
//                }).
//                collect(Collectors.toList());
//    }
}

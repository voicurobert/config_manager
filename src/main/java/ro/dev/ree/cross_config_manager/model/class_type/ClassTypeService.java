package ro.dev.ree.cross_config_manager.model.class_type;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import ro.dev.ree.cross_config_manager.model.RecordDto;
import ro.dev.ree.cross_config_manager.model.ServiceRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClassTypeService implements ServiceRepository {

    private final ClassTypeRepository repository;

    public ClassTypeService(ClassTypeRepository repository) {
        this.repository = repository;
    }

    @Override
    public RecordDto insert(RecordDto recordDto) {
        ClassType configType = new ClassType();

        ClassTypeDto classTypeDto = (ClassTypeDto) recordDto;
        BeanUtils.copyProperties(classTypeDto, configType);

        ClassType insert = repository.insert(configType);
        classTypeDto.setId(insert.getId());

        return classTypeDto;
    }

    @Override
    public RecordDto update(RecordDto recordDto) {
        ClassType configType = new ClassType();

        ClassTypeDto classTypeDto = (ClassTypeDto) recordDto;
        BeanUtils.copyProperties(classTypeDto, configType);

        ClassType insert = repository.save(configType);
        classTypeDto.setId(insert.getId());

        return classTypeDto;
    }

    @Override
    public void delete(RecordDto recordDto) {
        ClassTypeDto classTypeDto = (ClassTypeDto) recordDto;
        ClassType configType = new ClassType();
        BeanUtils.copyProperties(classTypeDto, configType);

        repository.delete(configType);
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
}

package ro.dev.ree.cross_config_manager.model.class_type;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClassTypeService {

    private final ClassTypeRepository repository;

    public ClassTypeService(ClassTypeRepository repository) {
        this.repository = repository;
    }

    public ClassType save(ClassTypeDto classTypeDto) {
        ClassType configType = new ClassType();
        BeanUtils.copyProperties(classTypeDto, configType);
        return repository.save(configType);

    }

    public void update(ClassType classTypeDto) {
        ClassType configType = new ClassType();
        BeanUtils.copyProperties(classTypeDto, configType);
        repository.save(configType);
    }

    public void delete(ClassTypeDto classTypeDto) {
        ClassType configType = new ClassType();
        BeanUtils.copyProperties(classTypeDto, configType);
        repository.delete(configType);
    }

    public List<ClassTypeDto> findAllByConfigId(String configId) {
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

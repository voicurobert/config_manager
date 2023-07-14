package ro.dev.ree.cross_config_manager.model.class_type;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

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
}

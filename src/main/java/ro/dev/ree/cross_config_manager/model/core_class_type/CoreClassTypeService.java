package ro.dev.ree.cross_config_manager.model.core_class_type;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import ro.dev.ree.cross_config_manager.ConfigManagerContextProvider;
import ro.dev.ree.cross_config_manager.model.RecordDto;
import ro.dev.ree.cross_config_manager.model.ServiceRepository;
import ro.dev.ree.cross_config_manager.model.config_type.ConfigSingleton;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CoreClassTypeService implements ServiceRepository {

    private final CoreClassTypeRepository repository;

    public CoreClassTypeService(CoreClassTypeRepository repository) {
        this.repository = repository;
    }

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
}

package ro.dev.ree.cross_config_manager.model.component_status;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import ro.dev.ree.cross_config_manager.ConfigManagerContextProvider;
import ro.dev.ree.cross_config_manager.model.RecordDto;
import ro.dev.ree.cross_config_manager.model.ServiceRepository;
import ro.dev.ree.cross_config_manager.model.config_type.ConfigSingleton;
import ro.dev.ree.cross_config_manager.model.core_class_type.CoreClassTypeDto;
import ro.dev.ree.cross_config_manager.model.core_class_type.CoreClassTypeService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComponentStatusService implements ServiceRepository {

    private final ComponentStatusRepository repository;

    public ComponentStatusService(ComponentStatusRepository repository) {
        this.repository = repository;
    }

    @Override
    public String insertOrUpdate(RecordDto recordDto) {
        ComponentStatus componentStatus = new ComponentStatus();
        ComponentStatusDto componentStatusDto = (ComponentStatusDto) recordDto;

        BeanUtils.copyProperties(componentStatusDto, componentStatus);
        ComponentStatus insert = repository.save(componentStatus);

        componentStatusDto.setId(insert.getId());

        return componentStatusDto.getId();
    }

    @Override
    public void delete(RecordDto recordDto) {
        ComponentStatus componentStatus = new ComponentStatus();

        ComponentStatusDto componentStatusDto = (ComponentStatusDto) recordDto;
        BeanUtils.copyProperties(componentStatusDto, componentStatus);

        repository.delete(componentStatus);
    }


    public List<RecordDto> findAllByConfigId(String configId) {
        return repository.findAll().stream().
                filter(componentStatus -> componentStatus.getConfigId().equals(configId)).
                map(componentStatus -> {
                    ComponentStatusDto dto = new ComponentStatusDto();
                    BeanUtils.copyProperties(componentStatus, dto);
                    return dto;
                }).
                collect(Collectors.toList());
    }

    @Override
    public RecordDto findById(String Id) {
        return repository.findAll().stream().
                filter(componentStatus -> componentStatus.getId().equals(Id)).
                map(componentStatus -> {
                    ComponentStatusDto dto = new ComponentStatusDto();
                    BeanUtils.copyProperties(componentStatus, dto);
                    return dto;
                }).
                findFirst().orElse(null);
    }
}
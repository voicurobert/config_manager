package ro.dev.ree.cross_config_manager.model.service_status;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import ro.dev.ree.cross_config_manager.ConfigManagerContextProvider;
import ro.dev.ree.cross_config_manager.model.RecordDto;
import ro.dev.ree.cross_config_manager.model.ServiceRepository;
import ro.dev.ree.cross_config_manager.model.config_type.ConfigSingleton;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceStatusService implements ServiceRepository {

    private final ServiceStatusRepository repository;

    public ServiceStatusService(ServiceStatusRepository repository) {

        this.repository = repository;
    }

    @Override
    public String insertOrUpdate(RecordDto recordDto) {
        ServiceStatus serviceStatus = new ServiceStatus();
        ServiceStatusDto serviceStatusDto = (ServiceStatusDto) recordDto;

        BeanUtils.copyProperties(serviceStatusDto, serviceStatus);
        ServiceStatus insert = repository.save(serviceStatus);

        serviceStatusDto.setId(insert.getId());

        return serviceStatusDto.getId();
    }

    @Override
    public void delete(RecordDto recordDto) {
        ServiceStatus serviceStatus = new ServiceStatus();

        ServiceStatusDto serviceStatusDto = (ServiceStatusDto) recordDto;
        BeanUtils.copyProperties(serviceStatusDto, serviceStatus);

        repository.delete(serviceStatus);
    }

    public List<RecordDto> findAllByConfigId(String configId) {
        return repository.findAll().stream().
                filter(serviceStatus -> serviceStatus.getConfigId().equals(configId)).
                map(serviceStatus -> {
                    ServiceStatusDto dto = new ServiceStatusDto();
                    BeanUtils.copyProperties(serviceStatus, dto);
                    return dto;
                }).
                collect(Collectors.toList());
    }

    @Override
    public RecordDto findById(String Id) {
        return repository.findAll().stream().
                filter(serviceStatus -> serviceStatus.getId().equals(Id)).
                map(serviceStatus -> {
                    ServiceStatusDto dto = new ServiceStatusDto();
                    BeanUtils.copyProperties(serviceStatus, dto);
                    return dto;
                }).
                findFirst().orElse(null);
    }
}
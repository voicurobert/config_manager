package ro.dev.ree.cross_config_manager.model.service_status;

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
public class ServiceStatusService implements ServiceRepository {

    private final ServiceStatusRepository repository;
    private final MongoTemplate mongoTemplate;

    public ServiceStatusService(ServiceStatusRepository repository, MongoTemplate mongoTemplate) {

        this.repository = repository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public String insertOrUpdate(Map<String,Object> oldColumnValues, RecordDto recordDto) {
        ServiceStatus serviceStatus = new ServiceStatus();
        ServiceStatusDto serviceStatusDto = (ServiceStatusDto) recordDto;

        BeanUtils.copyProperties(serviceStatusDto, serviceStatus);
        ServiceStatus insert = repository.save(serviceStatus);

        if(serviceStatusDto.getId() == null) {
            serviceStatusDto.setId(insert.getId());
        }

        return serviceStatusDto.getId();
    }

    @Override
    public void delete(RecordDto recordDto) {
        ServiceStatus serviceStatus = new ServiceStatus();

        ServiceStatusDto serviceStatusDto = (ServiceStatusDto) recordDto;
        BeanUtils.copyProperties(serviceStatusDto, serviceStatus);

        repository.delete(serviceStatus);
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

    @Override
    public List<RecordDto> findAllByConfigId(String configId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("configId").is(configId));

        return mongoTemplate.find(query, ServiceStatus.class).stream().
                map(serviceStatus -> {
                    ServiceStatusDto dto = new ServiceStatusDto();
                    BeanUtils.copyProperties(serviceStatus, dto);
                    return dto;
                }).
                collect(Collectors.toList());
    }
}
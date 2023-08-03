package ro.dev.ree.cross_config_manager.model.service_status;

import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import ro.dev.ree.cross_config_manager.model.RecordDto;
import ro.dev.ree.cross_config_manager.model.ServiceRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceStatusService implements ServiceRepository {

    private final ServiceStatusRepository repository;
    private final MongoTemplate mongoTemplate;

    public ServiceStatusService(ServiceStatusRepository repository, MongoTemplate mongoTemplate) {

        this.repository = repository;
        this.mongoTemplate = mongoTemplate;
    }

//    public void save(NodeTypeRulesDto nodeTypeRulesDto) {
//        NodeTypeRules nodeTypeRules = new NodeTypeRules();
//        BeanUtils.copyProperties(nodeTypeRulesDto, nodeTypeRules);
//        repository.save(nodeTypeRules);
//    }

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

//    @Override
//    public List<RecordDto> findAll(String[] columns, String[] old_columns) {
//        return repository.findAll().stream().
//                filter(nodeTypeRules -> nodeTypeRules.getChild().equals(old_columns[0])
//                        && nodeTypeRules.getParent().equals(old_columns[1])
//                        && nodeTypeRules.getCapacityCalculatorName().equals(old_columns[2])
//                        && nodeTypeRules.getMandatoryParent().equals(old_columns[3])).
//                map(nodeTypeRules -> {
//                    nodeTypeRules.setChild(columns[0]);
//                    nodeTypeRules.setParent(columns[1]);
//                    nodeTypeRules.setCapacityCalculatorName(columns[2]);
//                    nodeTypeRules.setMandatoryParent(columns[3]);
//                    NodeTypeRulesDto dto = new NodeTypeRulesDto();
//                    BeanUtils.copyProperties(nodeTypeRules, dto);
//                    return dto;
//                }).
//                collect(Collectors.toList());
//    }

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
//    public List<RecordDto> findAllByConfigIdNew(String configId) {
//        Query query = new Query();
//        query.addCriteria(Criteria.where("configId").is(configId));
//
//        return mongoTemplate.find(query, NodeTypeRules.class).stream().
//                map(nodeTypeRules -> {
//                    NodeTypeRulesDto dto = new NodeTypeRulesDto();
//                    BeanUtils.copyProperties(nodeTypeRules, dto);
//                    return dto;
//                }).
//                collect(Collectors.toList());
//    }
}
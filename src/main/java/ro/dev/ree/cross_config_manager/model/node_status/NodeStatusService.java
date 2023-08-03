package ro.dev.ree.cross_config_manager.model.node_status;

import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import ro.dev.ree.cross_config_manager.model.RecordDto;
import ro.dev.ree.cross_config_manager.model.ServiceRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NodeStatusService implements ServiceRepository {

    private final NodeStatusRepository repository;
    private final MongoTemplate mongoTemplate;

    public NodeStatusService(NodeStatusRepository repository, MongoTemplate mongoTemplate) {
        this.repository = repository;
        this.mongoTemplate = mongoTemplate;
    }


    @Override
    public String insertOrUpdate(RecordDto recordDto) {
        NodeStatus nodeStatus = new NodeStatus();
        NodeStatusDto nodeStatusDto = (NodeStatusDto) recordDto;

        BeanUtils.copyProperties(nodeStatusDto, nodeStatus);
        NodeStatus insert = repository.save(nodeStatus);

        nodeStatusDto.setId(insert.getId());

        return nodeStatusDto.getId();
    }

    @Override
    public void delete(RecordDto recordDto) {
        NodeStatus nodeStatus = new NodeStatus();

        NodeStatusDto nodeStatusDto = (NodeStatusDto) recordDto;
        BeanUtils.copyProperties(nodeStatusDto, nodeStatus);

        repository.delete(nodeStatus);
    }


    public List<RecordDto> findAllByConfigId(String configId) {
        return repository.findAll().stream().
                filter(nodeStatus -> nodeStatus.getConfigId().equals(configId)).
                map(nodeStatus -> {
                    NodeStatusDto dto = new NodeStatusDto();
                    BeanUtils.copyProperties(nodeStatus, dto);
                    return dto;
                }).
                collect(Collectors.toList());
    }

    @Override
    public RecordDto findById(String Id) {
        return repository.findAll().stream().
                filter(nodeStatus -> nodeStatus.getId().equals(Id)).
                map(nodeStatus -> {
                    NodeStatusDto dto = new NodeStatusDto();
                    BeanUtils.copyProperties(nodeStatus, dto);
                    return dto;
                }).
                findFirst().orElse(null);
    }
}
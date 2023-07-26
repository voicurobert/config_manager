package ro.dev.ree.cross_config_manager.model.node_type;

import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import ro.dev.ree.cross_config_manager.model.RecordDto;
import ro.dev.ree.cross_config_manager.model.ServiceRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NodeTypeService implements ServiceRepository {

    private final NodeTypeRepository repository;
    private final MongoTemplate mongoTemplate;

    public NodeTypeService(NodeTypeRepository repository, MongoTemplate mongoTemplate) {
        this.repository = repository;
        this.mongoTemplate = mongoTemplate;
    }

//    public void save(NodeTypeDto nodeTypeDto) {
//        NodeType nodeType = new NodeType();
//        BeanUtils.copyProperties(nodeTypeDto, nodeType);
//        repository.save(nodeType);
//    }


    @Override
    public String insertOrUpdate(RecordDto recordDto) {
        NodeType nodeType = new NodeType();
        NodeTypeDto nodeTypeDto = (NodeTypeDto) recordDto;

        BeanUtils.copyProperties(nodeTypeDto, nodeType);
        NodeType insert = repository.save(nodeType);

        nodeTypeDto.setId(insert.getId());

        return nodeTypeDto.getId();
    }

    @Override
    public void delete(RecordDto recordDto) {
        NodeType nodeType = new NodeType();

        NodeTypeDto nodeTypeDto = (NodeTypeDto) recordDto;
        BeanUtils.copyProperties(nodeTypeDto, nodeType);

        repository.delete(nodeType);
    }

//    @Override
//    public List<RecordDto> findAll(String[] columns, String[] old_columns) {
//        return repository.findAll().stream().
//                filter(nodeType -> nodeType.getDiscriminator().equals(old_columns[0])
//                        && nodeType.getName().equals(old_columns[1])
//                        && nodeType.getAppIcon().equals(old_columns[2])
//                        && nodeType.getMapIcon().equals(old_columns[3])
//                        && nodeType.getCapacityFull().equals(old_columns[4])
//                        && nodeType.getCapacityUnitName().equals(old_columns[5])
//                        && nodeType.getTypeClassPath().equals(old_columns[6])
//                        && nodeType.getRootType().equals(old_columns[7])
//                        && nodeType.getSystem().equals(old_columns[8])
//                        && nodeType.getMultiparentAllowed().equals(old_columns[9])
//                        && nodeType.getUniquenessType().equals(old_columns[10])).
//                map(nodeType -> {
//                    nodeType.setDiscriminator(columns[0]);
//                    nodeType.setName(columns[1]);
//                    nodeType.setAppIcon(columns[2]);
//                    nodeType.setMapIcon(columns[3]);
//                    nodeType.setCapacityFull(columns[4]);
//                    nodeType.setCapacityUnitName(columns[5]);
//                    nodeType.setTypeClassPath(columns[6]);
//                    nodeType.setRootType(columns[7]);
//                    nodeType.setSystem(columns[8]);
//                    nodeType.setMultiparentAllowed(columns[9]);
//                    nodeType.setUniquenessType(columns[10]);
//                    NodeTypeDto dto = new NodeTypeDto();
//                    BeanUtils.copyProperties(nodeType, dto);
//                    return dto;
//                }).
//                collect(Collectors.toList());
//    }

    public List<RecordDto> findAllByConfigId(String configId) {
        return repository.findAll().stream().
                filter(nodeType -> nodeType.getConfigId().equals(configId)).
                map(nodeType -> {
                    NodeTypeDto dto = new NodeTypeDto();
                    BeanUtils.copyProperties(nodeType, dto);
                    return dto;
                }).
                collect(Collectors.toList());
    }

//    public List<RecordDto> findAllByConfigIdNew(String configId) {
//        Query query = new Query();
//        query.addCriteria(Criteria.where("configId").is(configId));
//
//        return mongoTemplate.find(query, NodeType.class).stream().
//                map(nodeType -> {
//                    NodeTypeDto dto = new NodeTypeDto();
//                    BeanUtils.copyProperties(nodeType, dto);
//                    return dto;
//                }).
//                collect(Collectors.toList());
//    }
}

package ro.dev.ree.cross_config_manager.model.core_class_type;

import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import ro.dev.ree.cross_config_manager.ConfigManagerContextProvider;
import ro.dev.ree.cross_config_manager.model.RecordDto;
import ro.dev.ree.cross_config_manager.model.ServiceRepository;
import ro.dev.ree.cross_config_manager.model.link_type.LinkType;
import ro.dev.ree.cross_config_manager.model.link_type.LinkTypeDto;
import ro.dev.ree.cross_config_manager.model.link_type.LinkTypeService;
import ro.dev.ree.cross_config_manager.model.node_type.NodeType;
import ro.dev.ree.cross_config_manager.model.node_type.NodeTypeDto;
import ro.dev.ree.cross_config_manager.model.node_type.NodeTypeService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CoreClassTypeService implements ServiceRepository {

    private final CoreClassTypeRepository repository;
    private final MongoTemplate mongoTemplate;

    public CoreClassTypeService(CoreClassTypeRepository repository, MongoTemplate mongoTemplate) {
        this.repository = repository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public String insertOrUpdate(Map<String,Object> oldColumnValues, RecordDto recordDto) {
        CoreClassType coreClassType = new CoreClassType();
        CoreClassTypeDto coreClassTypeDto = (CoreClassTypeDto) recordDto;

        BeanUtils.copyProperties(coreClassTypeDto, coreClassType);
        CoreClassType insert = repository.save(coreClassType);

        if(coreClassTypeDto.getId() == null) {
            coreClassTypeDto.setId(insert.getId());
        }
        else if(oldColumnValues != null) {
            // Search for object with this old coreClassType.path and change it with the new coreClassType.path
            findByName((String) oldColumnValues.get("path"), recordDto);
        }

        return coreClassTypeDto.getId();
    }

    public void findByName(String name, RecordDto recordDto) {
        Query query = new Query();
        query.addCriteria(Criteria.where("typeClassPath").is(name));
        for (LinkType linkType: mongoTemplate.find(query, LinkType.class)) {
            linkType.setTypeClassPath(((CoreClassTypeDto) recordDto).getPath());
            LinkTypeService linkTypeService = ConfigManagerContextProvider.getBean(LinkTypeService.class);
            LinkTypeDto linkTypeDto = new LinkTypeDto();
            BeanUtils.copyProperties(linkType, linkTypeDto);
            linkTypeService.insertOrUpdate(null, linkTypeDto);
        }
        for (NodeType nodeType: mongoTemplate.find(query, NodeType.class)) {
            nodeType.setTypeClassPath(((CoreClassTypeDto) recordDto).getPath());
            NodeTypeService nodeTypeService = ConfigManagerContextProvider.getBean(NodeTypeService.class);
            NodeTypeDto nodeTypeDto = new NodeTypeDto();
            BeanUtils.copyProperties(nodeType, nodeTypeDto);
            nodeTypeService.insertOrUpdate(null, nodeTypeDto);
        }
    }

    @Override
    public void delete(RecordDto recordDto) {
        CoreClassTypeDto coreClassTypeDto = (CoreClassTypeDto) recordDto;
        CoreClassType coreClassType = new CoreClassType();
        BeanUtils.copyProperties(coreClassTypeDto, coreClassType);

        repository.delete(coreClassType);
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

    @Override
    public List<RecordDto> findAllByConfigId(String configId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("configId").is(configId));

        return mongoTemplate.find(query, CoreClassType.class).stream().
                map(coreClassType -> {
                    CoreClassTypeDto dto = new CoreClassTypeDto();
                    BeanUtils.copyProperties(coreClassType, dto);
                    return dto;
                }).
                collect(Collectors.toList());
    }
}

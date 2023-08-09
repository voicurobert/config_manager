package ro.dev.ree.cross_config_manager.model.node_type;

import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import ro.dev.ree.cross_config_manager.ConfigManagerContextProvider;
import ro.dev.ree.cross_config_manager.model.RecordDto;
import ro.dev.ree.cross_config_manager.model.ServiceRepository;
import ro.dev.ree.cross_config_manager.model.link_type_node_type_rules.LinkTypeNodeTypeRules;
import ro.dev.ree.cross_config_manager.model.link_type_node_type_rules.LinkTypeNodeTypeRulesDto;
import ro.dev.ree.cross_config_manager.model.link_type_node_type_rules.LinkTypeNodeTypeRulesService;
import ro.dev.ree.cross_config_manager.model.node_type_rules.NodeTypeRules;
import ro.dev.ree.cross_config_manager.model.node_type_rules.NodeTypeRulesDto;
import ro.dev.ree.cross_config_manager.model.node_type_rules.NodeTypeRulesService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class NodeTypeService implements ServiceRepository {

    private final NodeTypeRepository repository;
    private final MongoTemplate mongoTemplate;

    public NodeTypeService(NodeTypeRepository repository, MongoTemplate mongoTemplate) {
        this.repository = repository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public String insertOrUpdate(Map<String,Object> oldColumnValues, RecordDto recordDto) {
        NodeType nodeType = new NodeType();
        NodeTypeDto nodeTypeDto = (NodeTypeDto) recordDto;

        BeanUtils.copyProperties(nodeTypeDto, nodeType);
        NodeType insert = repository.save(nodeType);

        if(nodeTypeDto.getId() == null) {
            nodeTypeDto.setId(insert.getId());
        }
        else if(oldColumnValues != null) {
            // Search for object with this old nodeType.discriminator and change it with the new nodeType.discriminator
            findByName((String) oldColumnValues.get("discriminator"), recordDto);
        }

        return nodeTypeDto.getId();
    }

    public void findByName(String name, RecordDto recordDto) {
        Query query = new Query();
        query.addCriteria(Criteria.where("nodeType").is(name));
        for (LinkTypeNodeTypeRules linkTypeNodeTypeRules: mongoTemplate.find(query, LinkTypeNodeTypeRules.class)) {
            linkTypeNodeTypeRules.setNodeType(((NodeTypeDto) recordDto).getDiscriminator());
            LinkTypeNodeTypeRulesService linkTypeNodeTypeRulesService = ConfigManagerContextProvider.getBean(LinkTypeNodeTypeRulesService.class);
            LinkTypeNodeTypeRulesDto linkTypeNodeTypeRulesDto = new LinkTypeNodeTypeRulesDto();
            BeanUtils.copyProperties(linkTypeNodeTypeRules, linkTypeNodeTypeRulesDto);
            linkTypeNodeTypeRulesService.insertOrUpdate(null, linkTypeNodeTypeRulesDto);
        }
        query = new Query();
        query.addCriteria(Criteria.where("child").is(name));
        for (NodeTypeRules nodeTypeRules: mongoTemplate.find(query, NodeTypeRules.class)) {
            nodeTypeRules.setChild(((NodeTypeDto) recordDto).getDiscriminator());
            NodeTypeRulesService nodeTypeRulesService = ConfigManagerContextProvider.getBean(NodeTypeRulesService.class);
            NodeTypeRulesDto nodeTypeRulesDto = new NodeTypeRulesDto();
            BeanUtils.copyProperties(nodeTypeRules, nodeTypeRulesDto);
            nodeTypeRulesService.insertOrUpdate(null, nodeTypeRulesDto);
        }
        query = new Query();
        query.addCriteria(Criteria.where("parent").is(name));
        for (NodeTypeRules nodeTypeRules: mongoTemplate.find(query, NodeTypeRules.class)) {
            nodeTypeRules.setParent(((NodeTypeDto) recordDto).getDiscriminator());
            NodeTypeRulesService nodeTypeRulesService = ConfigManagerContextProvider.getBean(NodeTypeRulesService.class);
            NodeTypeRulesDto nodeTypeRulesDto = new NodeTypeRulesDto();
            BeanUtils.copyProperties(nodeTypeRules, nodeTypeRulesDto);
            nodeTypeRulesService.insertOrUpdate(null, nodeTypeRulesDto);
        }
    }

    @Override
    public void delete(RecordDto recordDto) {
        NodeType nodeType = new NodeType();

        NodeTypeDto nodeTypeDto = (NodeTypeDto) recordDto;
        BeanUtils.copyProperties(nodeTypeDto, nodeType);

        repository.delete(nodeType);
    }

    @Override
    public RecordDto findById(String Id) {
        return repository.findAll().stream().
                filter(nodeType -> nodeType.getId().equals(Id)).
                map(nodeType -> {
                    NodeTypeDto dto = new NodeTypeDto();
                    BeanUtils.copyProperties(nodeType, dto);
                    return dto;
                }).
                findFirst().orElse(null);
    }

    @Override
    public List<RecordDto> findAllByConfigId(String configId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("configId").is(configId));

        return mongoTemplate.find(query, NodeType.class).stream().
                map(nodeType -> {
                    NodeTypeDto dto = new NodeTypeDto();
                    BeanUtils.copyProperties(nodeType, dto);
                    return dto;
                }).
                collect(Collectors.toList());
    }
}

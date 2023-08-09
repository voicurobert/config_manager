package ro.dev.ree.cross_config_manager.model.link_type;

import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import ro.dev.ree.cross_config_manager.ConfigManagerContextProvider;
import ro.dev.ree.cross_config_manager.model.RecordDto;
import ro.dev.ree.cross_config_manager.model.ServiceRepository;
import ro.dev.ree.cross_config_manager.model.core_class_type.CoreClassTypeDto;
import ro.dev.ree.cross_config_manager.model.link_type_node_type_rules.LinkTypeNodeTypeRules;
import ro.dev.ree.cross_config_manager.model.link_type_node_type_rules.LinkTypeNodeTypeRulesDto;
import ro.dev.ree.cross_config_manager.model.link_type_node_type_rules.LinkTypeNodeTypeRulesService;
import ro.dev.ree.cross_config_manager.model.link_type_rules.LinkTypeRules;
import ro.dev.ree.cross_config_manager.model.link_type_rules.LinkTypeRulesDto;
import ro.dev.ree.cross_config_manager.model.link_type_rules.LinkTypeRulesService;
import ro.dev.ree.cross_config_manager.model.node_type.NodeType;
import ro.dev.ree.cross_config_manager.model.node_type_rules.NodeTypeRules;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LinkTypeService implements ServiceRepository {

    private final LinkTypeRepository repository;
    private final MongoTemplate mongoTemplate;

    public LinkTypeService(LinkTypeRepository repository, MongoTemplate mongoTemplate) {
        this.repository = repository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public String insertOrUpdate(Map<String,Object> oldColumnValues, RecordDto recordDto) {
        LinkType linkType = new LinkType();
        LinkTypeDto linkTypeDto = (LinkTypeDto) recordDto;

        BeanUtils.copyProperties(linkTypeDto, linkType);
        LinkType insert = repository.save(linkType);

        if(linkTypeDto.getId() == null)
        {
            linkTypeDto.setId(insert.getId());
        }
        else if(oldColumnValues != null) {
            // Search for object with this old linkType.discriminator and change it with the new linkType.discriminator
            findByName((String) oldColumnValues.get("discriminator"), recordDto);
        }

        return linkTypeDto.getId();
    }

    public void findByName(String name, RecordDto recordDto) {
        Query query = new Query();
        query.addCriteria(Criteria.where("linkType").is(name));
        for (LinkTypeNodeTypeRules linkTypeNodeTypeRules: mongoTemplate.find(query, LinkTypeNodeTypeRules.class)) {
            linkTypeNodeTypeRules.setLinkType(((LinkTypeDto) recordDto).getDiscriminator());
            LinkTypeNodeTypeRulesService linkTypeNodeTypeRulesService = ConfigManagerContextProvider.getBean(LinkTypeNodeTypeRulesService.class);
            LinkTypeNodeTypeRulesDto linkTypeNodeTypeRulesDto = new LinkTypeNodeTypeRulesDto();
            BeanUtils.copyProperties(linkTypeNodeTypeRules, linkTypeNodeTypeRulesDto);
            linkTypeNodeTypeRulesService.insertOrUpdate(null, linkTypeNodeTypeRulesDto);

        }
        query = new Query();
        query.addCriteria(Criteria.where("consumer").is(name));
        for (LinkTypeRules linkTypeRules: mongoTemplate.find(query, LinkTypeRules.class)) {
            linkTypeRules.setConsumer(((LinkTypeDto) recordDto).getDiscriminator());
            LinkTypeRulesService linkTypeRulesService = ConfigManagerContextProvider.getBean(LinkTypeRulesService.class);
            LinkTypeRulesDto linkTypeRulesDto = new LinkTypeRulesDto();
            BeanUtils.copyProperties(linkTypeRules, linkTypeRulesDto);
            linkTypeRulesService.insertOrUpdate(null, linkTypeRulesDto);
        }
        query = new Query();
        query.addCriteria(Criteria.where("provider").is(name));
        for (LinkTypeRules linkTypeRules: mongoTemplate.find(query, LinkTypeRules.class)) {
            linkTypeRules.setProvider(((LinkTypeDto) recordDto).getDiscriminator());
            LinkTypeRulesService linkTypeRulesService = ConfigManagerContextProvider.getBean(LinkTypeRulesService.class);
            LinkTypeRulesDto linkTypeRulesDto = new LinkTypeRulesDto();
            BeanUtils.copyProperties(linkTypeRules, linkTypeRulesDto);
            linkTypeRulesService.insertOrUpdate(null, linkTypeRulesDto);
        }
    }

    @Override
    public void delete(RecordDto recordDto) {
        LinkType linkType = new LinkType();

        LinkTypeDto linkTypeDto = (LinkTypeDto) recordDto;
        BeanUtils.copyProperties(linkTypeDto, linkType);

        repository.delete(linkType);
    }

    @Override
    public RecordDto findById(String Id) {
        return repository.findAll().stream().
                filter(linkType -> linkType.getId().equals(Id)).
                map(linkType -> {
                    LinkTypeDto dto = new LinkTypeDto();
                    BeanUtils.copyProperties(linkType, dto);
                    return dto;
                }).
                findFirst().orElse(null);
    }

    @Override
    public List<RecordDto> findAllByConfigId(String configId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("configId").is(configId));

        return mongoTemplate.find(query, LinkType.class).stream().
                map(linkType -> {
                    LinkTypeDto dto = new LinkTypeDto();
                    BeanUtils.copyProperties(linkType, dto);
                    return dto;
                }).
                collect(Collectors.toList());
    }
}

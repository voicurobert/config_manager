package ro.dev.ree.cross_config_manager.model.link_type_node_type_rules;

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
public class LinkTypeNodeTypeRulesService implements ServiceRepository {

    private final LinkTypeNodeTypeRulesRepository repository;
    private final MongoTemplate mongoTemplate;


    public LinkTypeNodeTypeRulesService(LinkTypeNodeTypeRulesRepository repository, MongoTemplate mongoTemplate) {

        this.repository = repository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public String insertOrUpdate(Map<String,Object> oldColumnValues, RecordDto recordDto) {
        LinkTypeNodeTypeRules linkTypeNodeTypeRules = new LinkTypeNodeTypeRules();
        LinkTypeNodeTypeRulesDto linkTypeNodeTypeRulesDto = (LinkTypeNodeTypeRulesDto) recordDto;

        BeanUtils.copyProperties(linkTypeNodeTypeRulesDto, linkTypeNodeTypeRules);
        LinkTypeNodeTypeRules insert = repository.save(linkTypeNodeTypeRules);

        if(linkTypeNodeTypeRulesDto.getId() == null) {
            linkTypeNodeTypeRulesDto.setId(insert.getId());
        }

        return linkTypeNodeTypeRulesDto.getId();
    }

    @Override
    public void delete(RecordDto recordDto) {
        LinkTypeNodeTypeRules linkTypeNodeTypeRules = new LinkTypeNodeTypeRules();

        LinkTypeNodeTypeRulesDto linkTypeNodeTypeRulesDto = (LinkTypeNodeTypeRulesDto) recordDto;
        BeanUtils.copyProperties(linkTypeNodeTypeRulesDto, linkTypeNodeTypeRules);

        repository.delete(linkTypeNodeTypeRules);
    }

    @Override
    public RecordDto findById(String Id) {
        return repository.findAll().stream().
                filter(linkTypeNodeTypeRules -> linkTypeNodeTypeRules.getId().equals(Id)).
                map(linkTypeNodeTypeRules -> {
                    LinkTypeNodeTypeRulesDto dto = new LinkTypeNodeTypeRulesDto();
                    BeanUtils.copyProperties(linkTypeNodeTypeRules, dto);
                    return dto;
                }).
                findFirst().orElse(null);
    }

    @Override
    public List<RecordDto> findAllByConfigId(String configId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("configId").is(configId));

        return mongoTemplate.find(query, LinkTypeNodeTypeRules.class).stream().
                map(linkTypeNodeTypeRules -> {
                    LinkTypeNodeTypeRulesDto dto = new LinkTypeNodeTypeRulesDto();
                    BeanUtils.copyProperties(linkTypeNodeTypeRules, dto);
                    return dto;
                }).
                collect(Collectors.toList());
    }
}

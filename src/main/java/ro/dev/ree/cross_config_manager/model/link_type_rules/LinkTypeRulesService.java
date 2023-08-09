package ro.dev.ree.cross_config_manager.model.link_type_rules;

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
public class LinkTypeRulesService implements ServiceRepository {

    private final LinkTypeRulesRepository repository;
    private final MongoTemplate mongoTemplate;

    public LinkTypeRulesService(LinkTypeRulesRepository repository, MongoTemplate mongoTemplate) {
        this.repository = repository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public String insertOrUpdate(Map<String,Object> oldColumnValues, RecordDto recordDto) {
        LinkTypeRules linkTypeRules = new LinkTypeRules();
        LinkTypeRulesDto linkTypeRulesDto = (LinkTypeRulesDto) recordDto;

        BeanUtils.copyProperties(linkTypeRulesDto, linkTypeRules);
        LinkTypeRules insert = repository.save(linkTypeRules);

        if(linkTypeRulesDto.getId() == null){
            linkTypeRulesDto.setId(insert.getId());
        }

        return linkTypeRulesDto.getId();
    }

    @Override
    public void delete(RecordDto recordDto) {
        LinkTypeRules linkTypeRules = new LinkTypeRules();

        LinkTypeRulesDto linkTypeRulesDto = (LinkTypeRulesDto) recordDto;
        BeanUtils.copyProperties(linkTypeRulesDto, linkTypeRules);

        repository.delete(linkTypeRules);
    }

    @Override
    public RecordDto findById(String Id) {
        return repository.findAll().stream().
                filter(linkTypeRules -> linkTypeRules.getId().equals(Id)).
                map(linkTypeRules -> {
                    LinkTypeRulesDto dto = new LinkTypeRulesDto();
                    BeanUtils.copyProperties(linkTypeRules, dto);
                    return dto;
                }).
                findFirst().orElse(null);
    }

    @Override
    public List<RecordDto> findAllByConfigId(String configId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("configId").is(configId));

        return mongoTemplate.find(query, LinkTypeRules.class).stream().
                map(linkTypeRules -> {
                    LinkTypeRulesDto dto = new LinkTypeRulesDto();
                    BeanUtils.copyProperties(linkTypeRules, dto);
                    return dto;
                }).
                collect(Collectors.toList());
    }
}

package ro.dev.ree.cross_config_manager.model.link_type_rules;

import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import ro.dev.ree.cross_config_manager.model.RecordDto;
import ro.dev.ree.cross_config_manager.model.ServiceRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LinkTypeRulesService implements ServiceRepository {

    private final LinkTypeRulesRepository repository;
    private final MongoTemplate mongoTemplate;

    public LinkTypeRulesService(LinkTypeRulesRepository repository, MongoTemplate mongoTemplate) {
        this.repository = repository;
        this.mongoTemplate = mongoTemplate;
    }

    public void save(LinkTypeRulesDto linkTypeRulesDto) {
        LinkTypeRules linkTypeRules = new LinkTypeRules();
        BeanUtils.copyProperties(linkTypeRulesDto, linkTypeRules);
        repository.save(linkTypeRules);
    }

    @Override
    public RecordDto insert(RecordDto recordDto) {
        LinkTypeRules linkTypeRules = new LinkTypeRules();

        LinkTypeRulesDto linkTypeRulesDto = (LinkTypeRulesDto) recordDto;
        BeanUtils.copyProperties(linkTypeRulesDto, linkTypeRules);

        LinkTypeRules insert = repository.insert(linkTypeRules);
        linkTypeRulesDto.setId(insert.getId());

        return linkTypeRulesDto;
    }

    @Override
    public RecordDto update(RecordDto recordDto) {
        LinkTypeRules linkTypeRules = new LinkTypeRules();

        LinkTypeRulesDto linkTypeRulesDto = (LinkTypeRulesDto) recordDto;
        BeanUtils.copyProperties(linkTypeRulesDto, linkTypeRules);

        LinkTypeRules insert = repository.save(linkTypeRules);
        linkTypeRulesDto.setId(insert.getId());

        return linkTypeRulesDto;
    }

    @Override
    public void delete(RecordDto recordDto) {
        LinkTypeRules linkTypeRules = new LinkTypeRules();

        LinkTypeRulesDto linkTypeRulesDto = (LinkTypeRulesDto) recordDto;
        BeanUtils.copyProperties(linkTypeRulesDto, linkTypeRules);

        repository.delete(linkTypeRules);
    }

    @Override
    public List<RecordDto> findAll() {
        return repository.findAll().stream().map(linkTypeRules -> {
            LinkTypeRulesDto linkTypeRulesDto = new LinkTypeRulesDto();
            BeanUtils.copyProperties(linkTypeRules, linkTypeRulesDto);
            return linkTypeRulesDto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<RecordDto> findAllByConfigId(String configId) {
        return repository.findAll().stream().
                filter(linkTypeRules -> linkTypeRules.getConfigId().equals(configId)).
                map(linkTypeRules -> {
                    LinkTypeRulesDto dto = new LinkTypeRulesDto();
                    BeanUtils.copyProperties(dto, linkTypeRules);
                    return dto;
                }).
                collect(Collectors.toList());
    }

    public List<RecordDto> findAllByConfigIdNew(String configId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("configId").is(configId));

        return mongoTemplate.find(query, LinkTypeRules.class).stream().
                map(linkTypeRules -> {
                    LinkTypeRulesDto dto = new LinkTypeRulesDto();
                    BeanUtils.copyProperties(dto, linkTypeRules);
                    return dto;
                }).
                collect(Collectors.toList());
    }
}

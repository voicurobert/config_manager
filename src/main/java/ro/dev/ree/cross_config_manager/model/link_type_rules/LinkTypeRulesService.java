package ro.dev.ree.cross_config_manager.model.link_type_rules;

import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
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

//    public void save(LinkTypeRulesDto linkTypeRulesDto) {
//        LinkTypeRules linkTypeRules = new LinkTypeRules();
//        BeanUtils.copyProperties(linkTypeRulesDto, linkTypeRules);
//        repository.save(linkTypeRules);
//    }

    @Override
    public void insertOrUpdate(RecordDto recordDto) {
        LinkTypeRules linkTypeRules = new LinkTypeRules();

        LinkTypeRulesDto linkTypeRulesDto = (LinkTypeRulesDto) recordDto;
        BeanUtils.copyProperties(linkTypeRulesDto, linkTypeRules);

        LinkTypeRules insert = repository.insert(linkTypeRules);
        linkTypeRulesDto.setId(insert.getId());
    }

    @Override
    public void delete(RecordDto recordDto) {
        LinkTypeRules linkTypeRules = new LinkTypeRules();

        LinkTypeRulesDto linkTypeRulesDto = (LinkTypeRulesDto) recordDto;
        BeanUtils.copyProperties(linkTypeRulesDto, linkTypeRules);

        repository.delete(linkTypeRules);
    }

//    @Override
//    public List<RecordDto> findAll(String[] columns, String[] old_columns) {
//        return repository.findAll().stream().
//                filter(linkTypeRules -> linkTypeRules.getConsumer().equals(old_columns[0])
//                        && linkTypeRules.getProvider().equals(old_columns[1])
//                        && linkTypeRules.getRoutingPolicy().equals(old_columns[2])
//                        && linkTypeRules.getCapacityCalculatorName().equals(old_columns[3])
//                        && linkTypeRules.getNumberOfChannels().equals(old_columns[4])).
//                map(linkTypeRules -> {
//                    linkTypeRules.setConsumer(columns[0]);
//                    linkTypeRules.setProvider(columns[1]);
//                    linkTypeRules.setRoutingPolicy(columns[2]);
//                    linkTypeRules.setCapacityCalculatorName(columns[3]);
//                    linkTypeRules.setNumberOfChannels(columns[4]);
//                    LinkTypeRulesDto dto = new LinkTypeRulesDto();
//                    BeanUtils.copyProperties(linkTypeRules, dto);
//                    return dto;
//                }).
//                collect(Collectors.toList());
//    }

    @Override
    public List<RecordDto> findAllByConfigId(String configId) {
        return repository.findAll().stream().
                filter(linkTypeRules -> linkTypeRules.getConfigId().equals(configId)).
                map(linkTypeRules -> {
                    LinkTypeRulesDto dto = new LinkTypeRulesDto();
                    BeanUtils.copyProperties(linkTypeRules, dto);
                    return dto;
                }).
                collect(Collectors.toList());
    }

//    public List<RecordDto> findAllByConfigIdNew(String configId) {
//        Query query = new Query();
//        query.addCriteria(Criteria.where("configId").is(configId));
//
//        return mongoTemplate.find(query, LinkTypeRules.class).stream().
//                map(linkTypeRules -> {
//                    LinkTypeRulesDto dto = new LinkTypeRulesDto();
//                    BeanUtils.copyProperties(linkTypeRules, dto);
//                    return dto;
//                }).
//                collect(Collectors.toList());
//    }
}

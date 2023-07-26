package ro.dev.ree.cross_config_manager.model.link_type_node_type_rules;

import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import ro.dev.ree.cross_config_manager.model.RecordDto;
import ro.dev.ree.cross_config_manager.model.ServiceRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LinkTypeNodeTypeRulesService implements ServiceRepository {

    private final LinkTypeNodeTypeRulesRepository repository;
    private final MongoTemplate mongoTemplate;


    public LinkTypeNodeTypeRulesService(LinkTypeNodeTypeRulesRepository repository, MongoTemplate mongoTemplate) {

        this.repository = repository;
        this.mongoTemplate = mongoTemplate;
    }

//    public void save(LinkTypeNodeTypeRulesDto linkTypeNodeTypeRulesDto) {
//        LinkTypeNodeTypeRules linkTypeNodeTypeRules = new LinkTypeNodeTypeRules();
//        BeanUtils.copyProperties(linkTypeNodeTypeRulesDto, linkTypeNodeTypeRules);
//        repository.save(linkTypeNodeTypeRules);
//    }

    @Override
    public String insertOrUpdate(RecordDto recordDto) {
        LinkTypeNodeTypeRules linkTypeNodeTypeRules = new LinkTypeNodeTypeRules();
        LinkTypeNodeTypeRulesDto linkTypeNodeTypeRulesDto = (LinkTypeNodeTypeRulesDto) recordDto;

        BeanUtils.copyProperties(linkTypeNodeTypeRulesDto, linkTypeNodeTypeRules);
        LinkTypeNodeTypeRules insert = repository.save(linkTypeNodeTypeRules);

        linkTypeNodeTypeRulesDto.setId(insert.getId());

        return linkTypeNodeTypeRulesDto.getId();
    }

    @Override
    public void delete(RecordDto recordDto) {
        LinkTypeNodeTypeRules linkTypeNodeTypeRules = new LinkTypeNodeTypeRules();

        LinkTypeNodeTypeRulesDto linkTypeNodeTypeRulesDto = (LinkTypeNodeTypeRulesDto) recordDto;
        BeanUtils.copyProperties(linkTypeNodeTypeRulesDto, linkTypeNodeTypeRules);

        repository.delete(linkTypeNodeTypeRules);
    }

//    @Override
//    public List<RecordDto> findAll(String[] columns, String[] old_columns) {
//        return repository.findAll().stream().
//                filter(linkTypeNodeTypeRules -> linkTypeNodeTypeRules.getLinkType().equals(old_columns[0])
//                        && linkTypeNodeTypeRules.getNodeType().equals(old_columns[1])
//                        && linkTypeNodeTypeRules.getQuality().equals(old_columns[2])).
//                map(linkTypeNodeTypeRules -> {
//                    linkTypeNodeTypeRules.setLinkType(columns[0]);
//                    linkTypeNodeTypeRules.setNodeType(columns[1]);
//                    linkTypeNodeTypeRules.setQuality(columns[2]);
//                    LinkTypeNodeTypeRulesDto dto = new LinkTypeNodeTypeRulesDto();
//                    BeanUtils.copyProperties(linkTypeNodeTypeRules, dto);
//                    return dto;
//                }).
//                collect(Collectors.toList());
//    }

    @Override
    public List<RecordDto> findAllByConfigId(String configId) {
        return repository.findAll().stream().
                filter(linkTypeNodeTypeRules -> linkTypeNodeTypeRules.getConfigId().equals(configId)).
                map(linkTypeNodeTypeRules -> {
                    LinkTypeNodeTypeRulesDto dto = new LinkTypeNodeTypeRulesDto();
                    BeanUtils.copyProperties(linkTypeNodeTypeRules, dto);
                    return dto;
                }).
                collect(Collectors.toList());
    }

//    public List<RecordDto> findAllByConfigIdNew(String configId) {
//        Query query = new Query();
//        query.addCriteria(Criteria.where("configId").is(configId));
//
//        return mongoTemplate.find(query, LinkTypeNodeTypeRules.class).stream().
//                map(linkTypeNodeTypeRules -> {
//                    LinkTypeNodeTypeRulesDto dto = new LinkTypeNodeTypeRulesDto();
//                    BeanUtils.copyProperties(linkTypeNodeTypeRules,dto);
//                    return dto;
//                }).
//                collect(Collectors.toList());
//    }
}

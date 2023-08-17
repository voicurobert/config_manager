package ro.dev.ree.cross_config_manager.model.technology_tree;

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
public class TechnologyTreeService implements ServiceRepository {

    private final TechnologyTreeRepository repository;
    private final MongoTemplate mongoTemplate;

    public TechnologyTreeService(TechnologyTreeRepository repository, MongoTemplate mongoTemplate) {

        this.repository = repository;
        this.mongoTemplate = mongoTemplate;
    }


    @Override
    public String insertOrUpdate(Map<String, Object> oldColumnValues, RecordDto recordDto) {
        TechnologyTree technologyTree = new TechnologyTree();
        TechnologyTreeDto technologyTreeDto = (TechnologyTreeDto) recordDto;

        BeanUtils.copyProperties(technologyTreeDto, technologyTree);
        TechnologyTree insert = repository.save(technologyTree);

        if (technologyTree.getId() == null) {
            technologyTreeDto.setId(insert.getId());
        }

        return technologyTreeDto.getId();
    }

    @Override
    public void delete(RecordDto recordDto) {
        TechnologyTree technologyTree = new TechnologyTree();

        TechnologyTreeDto technologyTreeDto = (TechnologyTreeDto) recordDto;
        BeanUtils.copyProperties(technologyTreeDto, technologyTree);

        repository.delete(technologyTree);
    }


    @Override
    public RecordDto findById(String Id) {
        return repository.findAll().stream().
                filter(technologyTree -> technologyTree.getId().equals(Id)).
                map(technologyTree -> {
                    TechnologyTreeDto dto = new TechnologyTreeDto();
                    BeanUtils.copyProperties(technologyTree, dto);
                    return dto;
                }).
                findFirst().orElse(null);
    }

    @Override
    public List<RecordDto> findAllByConfigId(String configId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("configId").is(configId));

        return mongoTemplate.find(query, TechnologyTree.class).stream().
                map(technologyTree -> {
                    TechnologyTreeDto dto = new TechnologyTreeDto();
                    BeanUtils.copyProperties(technologyTree, dto);
                    return dto;
                }).
                collect(Collectors.toList());
    }

    public List<RecordDto> findAllRoots(String configId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("configId").is(configId));
        query.addCriteria(Criteria.where("linkType").is(null));
        query.addCriteria(Criteria.where("nodeType").is(null));
        return mongoTemplate.find(query, TechnologyTree.class).stream().
                map(technologyTree -> {
                    TechnologyTreeDto dto = new TechnologyTreeDto();
                    BeanUtils.copyProperties(technologyTree, dto);
                    return dto;
                }).
                collect(Collectors.toList());
    }

    public List<RecordDto> findAllByNameAndConfigId(String name, String configId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("configId").is(configId));
//        query.addCriteria((Criteria.where("linkType").exists(true).andOperator(Criteria.where("nodeType").exists(false)).
//                orOperator(Criteria.where("linkType").exists(true).andOperator(Criteria.where("nodeType").exists(true)))));
        query.addCriteria(Criteria.where("linkType").exists(true).andOperator(Criteria.where("nodeType").exists(true)));
        //query.addCriteria(Criteria.where("nodeType").is(true));

        query.addCriteria(Criteria.where("name").is(name));


        return mongoTemplate.find(query, TechnologyTree.class).stream().
                map(technologyTree -> {
                    TechnologyTreeDto dto = new TechnologyTreeDto();
                    BeanUtils.copyProperties(technologyTree, dto);
                    return dto;
                }).
                collect(Collectors.toList());
    }

    public List<RecordDto> findLinkTypesByNameAndConfigId(String name, String configId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("configId").is(configId));
        query.addCriteria(Criteria.where("linkType").exists(false).andOperator(Criteria.where("nodeType").exists(true)));
        query.addCriteria(Criteria.where("name").is(name));


        return mongoTemplate.find(query, TechnologyTree.class).stream().
                map(technologyTree -> {
                    TechnologyTreeDto dto = new TechnologyTreeDto();
                    BeanUtils.copyProperties(technologyTree, dto);
                    return dto;
                }).
                collect(Collectors.toList());
    }
}

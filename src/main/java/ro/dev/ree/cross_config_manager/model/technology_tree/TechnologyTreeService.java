package ro.dev.ree.cross_config_manager.model.technology_tree;

import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import ro.dev.ree.cross_config_manager.ConfigManagerContextProvider;
import ro.dev.ree.cross_config_manager.model.RecordDto;
import ro.dev.ree.cross_config_manager.model.ServiceRepository;
import ro.dev.ree.cross_config_manager.model.technologies.Technologies;
import ro.dev.ree.cross_config_manager.model.technologies.TechnologiesDto;
import ro.dev.ree.cross_config_manager.model.technologies.TechnologiesService;

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
        else if(oldColumnValues != null) {
            // Search for object with this old nodeType.discriminator and change it with the new nodeType.discriminator
            findByName((String) oldColumnValues.get("name"), recordDto);
        }

        return technologyTreeDto.getId();
    }

    private void findByName(String name, RecordDto recordDto) {
        Query query = new Query();
        query.addCriteria(Criteria.where("technologyTree").is(name));
        for (Technologies technologies: mongoTemplate.find(query, Technologies.class)) {
            technologies.setTechnologyTree(((TechnologyTreeDto) recordDto).getName());
            TechnologiesService technologiesService = ConfigManagerContextProvider.getBean(TechnologiesService.class);
            TechnologiesDto technologiesDto = new TechnologiesDto();
            BeanUtils.copyProperties(technologies, technologiesDto);
            technologiesService.insertOrUpdate(null, technologiesDto);
        }
        query = new Query();
        query.addCriteria(Criteria.where("parentTechnology").is(name));
        for (Technologies technologies: mongoTemplate.find(query, Technologies.class)) {
            technologies.setParentTechnology(((TechnologyTreeDto) recordDto).getName());
            TechnologiesService technologiesService = ConfigManagerContextProvider.getBean(TechnologiesService.class);
            TechnologiesDto technologiesDto = new TechnologiesDto();
            BeanUtils.copyProperties(technologies, technologiesDto);
            technologiesService.insertOrUpdate(null, technologiesDto);
        }
        query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        for (TechnologyTree technologyTree: mongoTemplate.find(query, TechnologyTree.class)) {
            technologyTree.setName(((TechnologyTreeDto) recordDto).getName());
            TechnologyTreeService technologyTreeService = ConfigManagerContextProvider.getBean(TechnologyTreeService.class);
            TechnologyTreeDto technologyTreeDto = new TechnologyTreeDto();
            BeanUtils.copyProperties(technologyTree, technologyTreeDto);
            technologyTreeService.insertOrUpdate(null, technologyTreeDto);
        }
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

    public List<RecordDto> findLinkTypesByNameAndConfigIdNew(String name, String configId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("configId").is(configId));
        query.addCriteria(Criteria.where("nodeType").exists(false));
        query.addCriteria(Criteria.where("linkType").exists(true));
        query.addCriteria(Criteria.where("name").is(name));


        return mongoTemplate.find(query, TechnologyTree.class).stream().
                map(technologyTree -> {
                    TechnologyTreeDto dto = new TechnologyTreeDto();
                    BeanUtils.copyProperties(technologyTree, dto);
                    return dto;
                }).
                collect(Collectors.toList());
    }

    public List<RecordDto> findNodeTypesByNameAndConfigIdNew(String name, String configId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("configId").is(configId));
        query.addCriteria(Criteria.where("nodeType").exists(true));
        query.addCriteria(Criteria.where("linkType").exists(false));
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

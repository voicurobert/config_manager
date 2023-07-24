package ro.dev.ree.cross_config_manager.model.link_type;

import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import ro.dev.ree.cross_config_manager.model.RecordDto;
import ro.dev.ree.cross_config_manager.model.ServiceRepository;
import ro.dev.ree.cross_config_manager.model.class_type.ClassTypeDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LinkTypeService implements ServiceRepository {

    private final LinkTypeRepository repository;
    private final MongoTemplate mongoTemplate;

    public LinkTypeService(LinkTypeRepository repository, MongoTemplate mongoTemplate) {
        this.repository = repository;
        this.mongoTemplate = mongoTemplate;
    }

    public void save(LinkTypeDto linkTypeDto) {
        LinkType linkType = new LinkType();
        BeanUtils.copyProperties(linkTypeDto, linkType);

//        // sets the config id for this link type object
//        linkType.setConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());

        repository.save(linkType);
    }

    @Override
    public RecordDto insert(RecordDto recordDto) {
        LinkType linkType = new LinkType();

        LinkTypeDto linkTypeDto = (LinkTypeDto) recordDto;
        BeanUtils.copyProperties(linkTypeDto, linkType);

        LinkType insert = repository.insert(linkType);
        linkTypeDto.setId(insert.getId());

        return linkTypeDto;
    }

    @Override
    public RecordDto update(RecordDto recordDto) {
        LinkType linkType = new LinkType();

        LinkTypeDto linkTypeDto = (LinkTypeDto) recordDto;
        BeanUtils.copyProperties(linkTypeDto, linkType);

        LinkType insert = repository.save(linkType);
        linkTypeDto.setId(insert.getId());

        return linkTypeDto;
    }

    @Override
    public void delete(RecordDto recordDto) {
        LinkType linkType = new LinkType();

        LinkTypeDto linkTypeDto = (LinkTypeDto) recordDto;
        BeanUtils.copyProperties(linkTypeDto, linkType);

        repository.delete(linkType);
    }

    @Override
    public List<RecordDto> findAll(String[] columns, String[] old_columns) {
        return repository.findAll().stream().
                filter(linkType -> linkType.getDiscriminator().equals(old_columns[0])
                        && linkType.getName().equals(old_columns[1])
                        && linkType.getAppIcon().equals(old_columns[2])
                        && linkType.getMapIcon().equals(old_columns[3])
                        && linkType.getCapacityFull().equals(old_columns[4])
                        && linkType.getCapacityUnitName().equals(old_columns[5])
                        && linkType.getTypeClassPath().equals(old_columns[6])
                        && linkType.getSystem().equals(old_columns[7])
                        && linkType.getUnique().equals(old_columns[8])).
                map(linkType -> {
                    linkType.setDiscriminator(columns[0]);
                    linkType.setName(columns[1]);
                    linkType.setAppIcon(columns[2]);
                    linkType.setMapIcon(columns[3]);
                    linkType.setCapacityFull(columns[4]);
                    linkType.setCapacityUnitName(columns[5]);
                    linkType.setTypeClassPath(columns[6]);
                    linkType.setSystem(columns[7]);
                    linkType.setUnique(columns[8]);
                    LinkTypeDto dto = new LinkTypeDto();
                    BeanUtils.copyProperties(linkType, dto);
                    return dto;
                }).
                collect(Collectors.toList());
    }

    public List<RecordDto> findAllByConfigId(String configId) {
        return repository.findAll().stream().
                filter(linkType -> linkType.getConfigId().equals(configId)).
                map(linkType -> {
                    LinkTypeDto dto = new LinkTypeDto();
                    BeanUtils.copyProperties(linkType, dto);
                    return dto;
                }).
                collect(Collectors.toList());
    }

    public List<RecordDto> findAllByConfigIdNew(String configId) {
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

package ro.dev.ree.cross_config_manager.model.link_status;

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
public class LinkStatusService implements ServiceRepository {

    private final LinkStatusRepository repository;
    private final MongoTemplate mongoTemplate;

    public LinkStatusService(LinkStatusRepository repository, MongoTemplate mongoTemplate) {
        this.repository = repository;
        this.mongoTemplate = mongoTemplate;
    }


    @Override
    public String insertOrUpdate(RecordDto recordDto) {
        LinkStatus linkStatus = new LinkStatus();
        LinkStatusDto linkStatusDto = (LinkStatusDto) recordDto;

        BeanUtils.copyProperties(linkStatusDto, linkStatus);
        LinkStatus insert = repository.save(linkStatus);

        linkStatusDto.setId(insert.getId());

        return linkStatusDto.getId();
    }

    @Override
    public void delete(RecordDto recordDto) {
        LinkStatus linkStatus = new LinkStatus();

        LinkStatusDto linkStatusDto = (LinkStatusDto) recordDto;
        BeanUtils.copyProperties(linkStatusDto, linkStatus);

        repository.delete(linkStatus);
    }


//    public List<RecordDto> findAllByConfigId(String configId) {
//        return repository.findAll().stream().
//                filter(linkStatus -> linkStatus.getConfigId().equals(configId)).
//                map(linkStatus -> {
//                    LinkStatusDto dto = new LinkStatusDto();
//                    BeanUtils.copyProperties(linkStatus, dto);
//                    return dto;
//                }).
//                collect(Collectors.toList());
//    }

    @Override
    public RecordDto findById(String Id) {
        return repository.findAll().stream().
                filter(linkStatus -> linkStatus.getId().equals(Id)).
                map(linkStatus -> {
                    LinkStatusDto dto = new LinkStatusDto();
                    BeanUtils.copyProperties(linkStatus, dto);
                    return dto;
                }).
                findFirst().orElse(null);
    }

    @Override
    public List<RecordDto> findAllByConfigId(String configId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("configId").is(configId));

        return mongoTemplate.find(query, LinkStatus.class).stream().
                map(linkStatus -> {
                    LinkStatusDto dto = new LinkStatusDto();
                    BeanUtils.copyProperties(linkStatus, dto);
                    return dto;
                }).
                collect(Collectors.toList());
    }
}
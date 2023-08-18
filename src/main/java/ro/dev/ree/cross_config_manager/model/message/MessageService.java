package ro.dev.ree.cross_config_manager.model.message;

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
public class MessageService implements ServiceRepository {
    private final MessageRepository repository;
    private final MongoTemplate mongoTemplate;

    public MessageService(MessageRepository repository, MongoTemplate mongoTemplate) {
        this.repository = repository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public String insertOrUpdate(Map<String, Object> oldColumnValues, RecordDto recordDto) {
        Message message = new Message();
        MessageDto messageDto = (MessageDto) recordDto;

        BeanUtils.copyProperties(messageDto, message);
        Message insert = repository.save(message);

        if(messageDto.getId() == null) {
            messageDto.setId(insert.getId());
        }

        return messageDto.getId();
    }

    @Override
    public void delete(RecordDto recordDto) {
        Message message = new Message();

        MessageDto messageDto = (MessageDto) recordDto;
        BeanUtils.copyProperties(messageDto, message);

        repository.delete(message);
    }

    @Override
    public List<RecordDto> findAllByConfigId(String configId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("configId").is(configId));

        return mongoTemplate.find(query, Message.class).stream().
                map(message -> {
                    MessageDto dto = new MessageDto();
                    BeanUtils.copyProperties(message, dto);
                    return dto;
                }).
                collect(Collectors.toList());
    }

    @Override
    public RecordDto findById(String Id) {
        return repository.findAll().stream().
                filter(message -> message.getId().equals(Id)).
                map(message -> {
                    MessageDto dto = new MessageDto();
                    BeanUtils.copyProperties(message, dto);
                    return dto;
                }).
                findFirst().orElse(null);
    }
}

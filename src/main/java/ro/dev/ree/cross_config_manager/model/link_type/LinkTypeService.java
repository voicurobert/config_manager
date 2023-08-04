package ro.dev.ree.cross_config_manager.model.link_type;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import ro.dev.ree.cross_config_manager.ConfigManagerContextProvider;
import ro.dev.ree.cross_config_manager.model.RecordDto;
import ro.dev.ree.cross_config_manager.model.ServiceRepository;
import ro.dev.ree.cross_config_manager.model.config_type.ConfigSingleton;
import ro.dev.ree.cross_config_manager.model.core_class_type.CoreClassTypeDto;
import ro.dev.ree.cross_config_manager.model.core_class_type.CoreClassTypeService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LinkTypeService implements ServiceRepository {

    private final LinkTypeRepository repository;

    public LinkTypeService(LinkTypeRepository repository) {
        this.repository = repository;
    }

    @Override
    public String insertOrUpdate(RecordDto recordDto) {
        LinkType linkType = new LinkType();
        LinkTypeDto linkTypeDto = (LinkTypeDto) recordDto;

        BeanUtils.copyProperties(linkTypeDto, linkType);
        LinkType insert = repository.save(linkType);

        linkTypeDto.setId(insert.getId());

        return linkTypeDto.getId();

    }

    @Override
    public void delete(RecordDto recordDto) {
        LinkType linkType = new LinkType();

        LinkTypeDto linkTypeDto = (LinkTypeDto) recordDto;
        BeanUtils.copyProperties(linkTypeDto, linkType);

        repository.delete(linkType);
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

    @Override
    public RecordDto findById(String Id) {
        return repository.findAll().stream().
                filter(linkType -> linkType.getId().equals(Id)).
                map(linkType -> {
                    LinkTypeDto dto = new LinkTypeDto();
                    BeanUtils.copyProperties(linkType, dto);
                    return dto;
                }).
                findFirst().orElse(null);
    }
}

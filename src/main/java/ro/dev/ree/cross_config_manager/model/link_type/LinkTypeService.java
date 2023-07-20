package ro.dev.ree.cross_config_manager.model.link_type;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import ro.dev.ree.cross_config_manager.model.config_type.ConfigSingleton;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LinkTypeService {

    private final LinkTypeRepository repository;

    public LinkTypeService(LinkTypeRepository repository) {
        this.repository = repository;
    }

    public LinkType save(LinkTypeDto linkTypeDto) {
        LinkType linkType = new LinkType();
        BeanUtils.copyProperties(linkTypeDto, linkType);

        // sets the config id for this link type object
        linkType.setConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());

        return repository.save(linkType);
    }

    public List<LinkTypeDto> findAllByConfigId(String configId) {
        return repository.findAll().stream().
                filter(linkType -> linkType.getConfigId().equals(configId)).
                map(linkType -> {
                    LinkTypeDto dto = new LinkTypeDto();
                    BeanUtils.copyProperties(dto, linkType);
                    return dto;
                }).
                collect(Collectors.toList());
    }
}

package ro.dev.ree.cross_config_manager.model.link_type;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import ro.dev.ree.cross_config_manager.model.config_type.ConfigSingleton;

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
        linkType.setConfigId(ConfigSingleton.getSingleton().getConfigId());

        return repository.save(linkType);
    }
}

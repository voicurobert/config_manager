package ro.dev.ree.cross_config_manager.model.link_type;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class LinkTypeService {

    private final LinkTypeRepository repository;

    public LinkTypeService(LinkTypeRepository repository){
        this.repository = repository;
    }

    public void save(LinkTypeDto linkTypeDto){
        LinkType linkType = new LinkType();
        BeanUtils.copyProperties(linkTypeDto, linkType);
        repository.save(linkType);
    }
}

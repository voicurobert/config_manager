package ro.dev.ree.cross_config_manager.model.link_type_rules;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class LinkTypeRulesService {

    private final LinkTypeRulesRepository repository;

    public LinkTypeRulesService(LinkTypeRulesRepository repository){
        this.repository = repository;
    }

    public void save(LinkTypeRulesDto linkTypeRulesDto){
        LinkTypeRules linkTypeRules = new LinkTypeRules();
        BeanUtils.copyProperties(linkTypeRulesDto, linkTypeRules);
        repository.save(linkTypeRules);
    }
}

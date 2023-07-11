package ro.dev.ree.cross_config_manager.model.link_type_node_type_rules;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class LinkTypeNodeTypeRulesService {

    private final LinkTypeNodeTypeRulesRepository repository;

    public LinkTypeNodeTypeRulesService(LinkTypeNodeTypeRulesRepository repository){
        this.repository = repository;
    }

    public void save(LinkTypeNodeTypeRulesDto linkTypeNodeTypeRulesDto){
        LinkTypeNodeTypeRules linkTypeNodeTypeRules = new LinkTypeNodeTypeRules();
        BeanUtils.copyProperties(linkTypeNodeTypeRulesDto, linkTypeNodeTypeRules);
        repository.save(linkTypeNodeTypeRules);
    }
}

package ro.dev.ree.cross_config_manager.model.link_type_node_type_rules;

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
public class LinkTypeNodeTypeRulesService implements ServiceRepository {

    private final LinkTypeNodeTypeRulesRepository repository;


    public LinkTypeNodeTypeRulesService(LinkTypeNodeTypeRulesRepository repository) {
        this.repository = repository;
    }

    @Override
    public String insertOrUpdate(RecordDto recordDto) {
        LinkTypeNodeTypeRules linkTypeNodeTypeRules = new LinkTypeNodeTypeRules();
        LinkTypeNodeTypeRulesDto linkTypeNodeTypeRulesDto = (LinkTypeNodeTypeRulesDto) recordDto;

        BeanUtils.copyProperties(linkTypeNodeTypeRulesDto, linkTypeNodeTypeRules);
        LinkTypeNodeTypeRules insert = repository.save(linkTypeNodeTypeRules);

        linkTypeNodeTypeRulesDto.setId(insert.getId());

        return linkTypeNodeTypeRulesDto.getId();
    }

    @Override
    public void delete(RecordDto recordDto) {
        LinkTypeNodeTypeRules linkTypeNodeTypeRules = new LinkTypeNodeTypeRules();

        LinkTypeNodeTypeRulesDto linkTypeNodeTypeRulesDto = (LinkTypeNodeTypeRulesDto) recordDto;
        BeanUtils.copyProperties(linkTypeNodeTypeRulesDto, linkTypeNodeTypeRules);

        repository.delete(linkTypeNodeTypeRules);
    }

    @Override
    public List<RecordDto> findAllByConfigId(String configId) {
        return repository.findAll().stream().
                filter(linkTypeNodeTypeRules -> linkTypeNodeTypeRules.getConfigId().equals(configId)).
                map(linkTypeNodeTypeRules -> {
                    LinkTypeNodeTypeRulesDto dto = new LinkTypeNodeTypeRulesDto();
                    BeanUtils.copyProperties(linkTypeNodeTypeRules, dto);
                    return dto;
                }).
                collect(Collectors.toList());
    }

    @Override
    public RecordDto findById(String Id) {
        return repository.findAll().stream().
                filter(linkTypeNodeTypeRules -> linkTypeNodeTypeRules.getId().equals(Id)).
                map(linkTypeNodeTypeRules -> {
                    LinkTypeNodeTypeRulesDto dto = new LinkTypeNodeTypeRulesDto();
                    BeanUtils.copyProperties(linkTypeNodeTypeRules, dto);
                    return dto;
                }).
                findFirst().orElse(null);
    }
}

package ro.dev.ree.cross_config_manager.model.link_type_rules;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import ro.dev.ree.cross_config_manager.ConfigManagerContextProvider;
import ro.dev.ree.cross_config_manager.model.RecordDto;
import ro.dev.ree.cross_config_manager.model.ServiceRepository;
import ro.dev.ree.cross_config_manager.model.config_type.ConfigSingleton;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LinkTypeRulesService implements ServiceRepository {

    private final LinkTypeRulesRepository repository;

    public LinkTypeRulesService(LinkTypeRulesRepository repository) {
        this.repository = repository;
    }

    @Override
    public String insertOrUpdate(RecordDto recordDto) {
        LinkTypeRules linkTypeRules = new LinkTypeRules();
        LinkTypeRulesDto linkTypeRulesDto = (LinkTypeRulesDto) recordDto;

        BeanUtils.copyProperties(linkTypeRulesDto, linkTypeRules);
        LinkTypeRules insert = repository.save(linkTypeRules);

        linkTypeRulesDto.setId(insert.getId());

        return linkTypeRulesDto.getId();

    }

    @Override
    public void delete(RecordDto recordDto) {
        LinkTypeRules linkTypeRules = new LinkTypeRules();

        LinkTypeRulesDto linkTypeRulesDto = (LinkTypeRulesDto) recordDto;
        BeanUtils.copyProperties(linkTypeRulesDto, linkTypeRules);

        repository.delete(linkTypeRules);
    }

    @Override
    public List<RecordDto> findAllByConfigId(String configId) {
        return repository.findAll().stream().
                filter(linkTypeRules -> linkTypeRules.getConfigId().equals(configId)).
                map(linkTypeRules -> {
                    LinkTypeRulesDto dto = new LinkTypeRulesDto();
                    BeanUtils.copyProperties(linkTypeRules, dto);
                    return dto;
                }).
                collect(Collectors.toList());
    }

    @Override
    public RecordDto findById(String Id) {
        return repository.findAll().stream().
                filter(linkTypeRules -> linkTypeRules.getId().equals(Id)).
                map(linkTypeRules -> {
                    LinkTypeRulesDto dto = new LinkTypeRulesDto();
                    BeanUtils.copyProperties(linkTypeRules, dto);
                    return dto;
                }).
                findFirst().orElse(null);
    }
}

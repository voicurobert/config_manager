package ro.dev.ree.cross_config_manager.model.node_type_rules;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import ro.dev.ree.cross_config_manager.ConfigManagerContextProvider;
import ro.dev.ree.cross_config_manager.model.RecordDto;
import ro.dev.ree.cross_config_manager.model.ServiceRepository;
import ro.dev.ree.cross_config_manager.model.config_type.ConfigSingleton;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NodeTypeRulesService implements ServiceRepository {

    private final NodeTypeRulesRepository repository;

    public NodeTypeRulesService(NodeTypeRulesRepository repository) {

        this.repository = repository;
    }

    @Override
    public String insertOrUpdate(RecordDto recordDto) {
        NodeTypeRules nodeTypeRules = new NodeTypeRules();
        NodeTypeRulesDto nodeTypeRulesDto = (NodeTypeRulesDto) recordDto;

        BeanUtils.copyProperties(nodeTypeRulesDto, nodeTypeRules);
        NodeTypeRules insert = repository.save(nodeTypeRules);

        nodeTypeRulesDto.setId(insert.getId());

        return nodeTypeRulesDto.getId();
    }

    @Override
    public void delete(RecordDto recordDto) {
        NodeTypeRules nodeTypeRules = new NodeTypeRules();

        NodeTypeRulesDto nodeTypeRulesDto = (NodeTypeRulesDto) recordDto;
        BeanUtils.copyProperties(nodeTypeRulesDto, nodeTypeRules);

        repository.delete(nodeTypeRules);
    }

    public List<RecordDto> findAllByConfigId(String configId) {
        return repository.findAll().stream().
                filter(nodeTypeRules -> nodeTypeRules.getConfigId().equals(configId)).
                map(nodeTypeRules -> {
                    NodeTypeRulesDto dto = new NodeTypeRulesDto();
                    BeanUtils.copyProperties(nodeTypeRules, dto);
                    return dto;
                }).
                collect(Collectors.toList());
    }

    @Override
    public RecordDto findById(String Id) {
        return repository.findAll().stream().
                filter(nodeTypeRules -> nodeTypeRules.getId().equals(Id)).
                map(nodeTypeRules -> {
                    NodeTypeRulesDto dto = new NodeTypeRulesDto();
                    BeanUtils.copyProperties(nodeTypeRules, dto);
                    return dto;
                }).
                findFirst().orElse(null);
    }
}

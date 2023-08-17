package ro.dev.ree.cross_config_manager.model;

import ro.dev.ree.cross_config_manager.ConfigManagerContextProvider;
import ro.dev.ree.cross_config_manager.model.config_type.ConfigSingleton;
import ro.dev.ree.cross_config_manager.model.core_class_type.CoreClassTypeDto;
import ro.dev.ree.cross_config_manager.model.core_class_type.CoreClassTypeService;
import ro.dev.ree.cross_config_manager.model.link_type.LinkTypeDto;
import ro.dev.ree.cross_config_manager.model.link_type.LinkTypeService;
import ro.dev.ree.cross_config_manager.model.node_type.NodeTypeDto;
import ro.dev.ree.cross_config_manager.model.node_type.NodeTypeService;
import ro.dev.ree.cross_config_manager.model.technology_tree.TechnologyTreeDto;
import ro.dev.ree.cross_config_manager.model.technology_tree.TechnologyTreeService;

import java.util.List;
import java.util.Map;

public interface ServiceRepository {

    String insertOrUpdate(Map<String, Object> oldColumnValues, RecordDto recordDto);

    void delete(RecordDto recordDto);

    default List<CoreClassTypeDto> listOfCoreClassTypeDtos() {
        CoreClassTypeService coreClassTypeService = ConfigManagerContextProvider.getBean(CoreClassTypeService.class);
        return coreClassTypeService.findAllByConfigId(ConfigSingleton.getSingleton().getConfigDto().getId()).stream().
                map(recordDto -> (CoreClassTypeDto) recordDto).toList();
    }

    default List<NodeTypeDto> listOfNodeTypeDtos() {
        NodeTypeService nodeTypeService = ConfigManagerContextProvider.getBean(NodeTypeService.class);
        return nodeTypeService.findAllByConfigId(ConfigSingleton.getSingleton().getConfigDto().getId()).stream().
                map(recordDto -> (NodeTypeDto) recordDto).toList();
    }

    default List<LinkTypeDto> listOfLinkTypeDtos() {
        LinkTypeService linkTypeService = ConfigManagerContextProvider.getBean(LinkTypeService.class);
        return linkTypeService.findAllByConfigId(ConfigSingleton.getSingleton().getConfigDto().getId()).stream().
                map(recordDto -> (LinkTypeDto) recordDto).toList();
    }

    default List<TechnologyTreeDto> listOfTechnologyType() {
        TechnologyTreeService technologyTreeService = ConfigManagerContextProvider.getBean(TechnologyTreeService.class);
        return technologyTreeService.findAllByConfigId(ConfigSingleton.getSingleton().getConfigDto().getId()).stream().
                map(recordDto -> (TechnologyTreeDto) recordDto).toList();
    }

    List<RecordDto> findAllByConfigId(String configId);


    RecordDto findById(String Id);
}

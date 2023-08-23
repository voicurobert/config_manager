package ro.dev.ree.cross_config_manager.model;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TreeItem;
import ro.dev.ree.cross_config_manager.ConfigManagerContextProvider;
import ro.dev.ree.cross_config_manager.model.ca_definition.CaDefinition;
import ro.dev.ree.cross_config_manager.model.ca_definition.CaDefinitionDto;
import ro.dev.ree.cross_config_manager.model.ca_definition.CaDefinitionService;
import ro.dev.ree.cross_config_manager.model.ca_definition_set.CaDefinitionSetDto;
import ro.dev.ree.cross_config_manager.model.config_type.ConfigSingleton;
import ro.dev.ree.cross_config_manager.model.core_class_type.CoreClassTypeDto;
import ro.dev.ree.cross_config_manager.model.core_class_type.CoreClassTypeService;
import ro.dev.ree.cross_config_manager.model.link_type.LinkTypeDto;
import ro.dev.ree.cross_config_manager.model.link_type.LinkTypeService;
import ro.dev.ree.cross_config_manager.model.node_type.NodeTypeDto;
import ro.dev.ree.cross_config_manager.model.node_type.NodeTypeService;
import ro.dev.ree.cross_config_manager.model.technologies.TechnologiesDto;
import ro.dev.ree.cross_config_manager.model.technologies.TechnologiesService;
import ro.dev.ree.cross_config_manager.model.technology_tree.TechnologyTreeDto;
import ro.dev.ree.cross_config_manager.model.technology_tree.TechnologyTreeService;

import java.util.ArrayList;
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

    default List<RecordDto> listOfTechnologyType() {
        TechnologiesService technologiesService = ConfigManagerContextProvider.getBean(TechnologiesService.class);
        TechnologyTreeService technologyTreeService = ConfigManagerContextProvider.getBean(TechnologyTreeService.class);

        List<TechnologiesDto> technologiesDtoList = technologiesService.findAllByConfigId(ConfigSingleton.getSingleton().getConfigDto().getId()).stream().
                map(recordDto -> (TechnologiesDto) recordDto).toList();
        List<TechnologyTreeDto> technologyTreeDtoList = technologyTreeService.findAllByConfigId(ConfigSingleton.getSingleton().getConfigDto().getId()).stream().
                map(recordDto -> (TechnologyTreeDto) recordDto).toList();

        List<RecordDto> added = new ArrayList<>(0);
        for (TechnologyTreeDto technologyTreeDto : technologyTreeDtoList) {
            boolean exists = false;
            if(!added.isEmpty()){
                for (RecordDto add : added) {
                    if(add instanceof TechnologyTreeDto ?
                            ((TechnologyTreeDto)add).getName().equals(technologyTreeDto.getName())
                            : ((TechnologiesDto)add).getTechnologyTree().equals(technologyTreeDto.getName())){
                        exists = true;
                        break;
                    }
                }
            }
            if(!exists){
                added.add(technologyTreeDto);
            }
        }
        for (TechnologiesDto technologyDto : technologiesDtoList) {
            boolean exists = false;
            if(!added.isEmpty()){
                for (RecordDto add : added) {
                    if(add instanceof TechnologiesDto ?
                            ((TechnologiesDto)add).getTechnologyTree().equals(technologyDto.getTechnologyTree())
                            : ((TechnologyTreeDto)add).getName().equals(technologyDto.getTechnologyTree())){
                        exists = true;
                        break;
                    }
                }
            }
            if(!exists){
                added.add(technologyDto);
            }
        }
        return added;
    }

        default List<CaDefinitionDto> listOfCaDefinitionDtos() {
        CaDefinitionService caDefinitionService = ConfigManagerContextProvider.getBean(CaDefinitionService.class);
        return caDefinitionService.findAllByConfigId(ConfigSingleton.getSingleton().getConfigDto().getId()).stream().
                map(recordDto -> (CaDefinitionDto) recordDto).toList();
    }

    List<RecordDto> findAllByConfigId(String configId);


    RecordDto findById(String Id);
}

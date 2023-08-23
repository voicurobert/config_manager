package ro.dev.ree.cross_config_manager.model.technology_tree;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import ro.dev.ree.cross_config_manager.ConfigManagerContextProvider;
import ro.dev.ree.cross_config_manager.model.RecordDto;
import ro.dev.ree.cross_config_manager.model.config_type.ConfigSingleton;
import ro.dev.ree.cross_config_manager.xml.writer.XmlElement;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TechnologyTreeDto extends RecordDto implements XmlElement {
    private String configId;
    private String name;
    private String nodeType;
    private String linkType;

    public static TechnologyTreeDto InsertOrUpdateFromItems(List<String> columnValues, String action) {
        var technologyTreeDto = new TechnologyTreeDto();
        if (action.equals("Update")) {
            technologyTreeDto.setId(columnValues.get(0));
        }
        technologyTreeDto.setConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());
        technologyTreeDto.setName(columnValues.get(1));
        if(columnValues.get(2).isEmpty()){
            technologyTreeDto.setNodeType(null);
        }
        else {
            technologyTreeDto.setNodeType(columnValues.get(2));
        }
        if(columnValues.get(3).isEmpty()){
            technologyTreeDto.setLinkType(null);
        }
        else {
            technologyTreeDto.setLinkType(columnValues.get(3));
        }
        return technologyTreeDto;
    }

    @Override
    public void asXml(Document document, Element rootElement) {
        Element technologyTree = document.createElement("technologyTree");
        // add classType to root
        rootElement.appendChild(technologyTree);

        Element name = document.createElement("name");
        name.setTextContent(this.name);
        technologyTree.appendChild(name);

        TechnologyTreeService technologyTreeService = ConfigManagerContextProvider.getBean(TechnologyTreeService.class);
        List<RecordDto> linkTypes = technologyTreeService.findLinkTypesByNameAndConfigIdNew(this.name, ConfigSingleton.getSingleton().getConfigDto().getId());
        List<RecordDto> nodeTypes = technologyTreeService.findNodeTypesByNameAndConfigIdNew(this.name, ConfigSingleton.getSingleton().getConfigDto().getId());

        for (RecordDto node : nodeTypes) {
            TechnologyTreeDto technologyTreeDto = (TechnologyTreeDto) node;
            Element nodeType = document.createElement("nodeType");
            nodeType.setTextContent(technologyTreeDto.getNodeType());

            technologyTree.appendChild(nodeType);


        }

        for (RecordDto link : linkTypes) {
            TechnologyTreeDto technologyTreeDto = (TechnologyTreeDto) link;
            Element linkType = document.createElement("linkType");
            linkType.setTextContent(technologyTreeDto.getLinkType());

            technologyTree.appendChild(linkType);

        }
    }
}

package ro.dev.ree.cross_config_manager.model.technology_tree;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import ro.dev.ree.cross_config_manager.model.RecordDto;
import ro.dev.ree.cross_config_manager.model.config_type.ConfigSingleton;
import ro.dev.ree.cross_config_manager.xml.writer.XmlElement;

import java.lang.reflect.Field;
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
        technologyTreeDto.setNodeType(columnValues.get(2));
        technologyTreeDto.setLinkType(columnValues.get(3));

        return technologyTreeDto;
    }

    @Override
    public void asXml(Document document, Element rootElement) {

        // add xml elements
        Element technologyTree = document.createElement("technologyTree");
        // add classType to root
        rootElement.appendChild(technologyTree);

        Field[] fields = getClass().getDeclaredFields();
        for (int i = 1; i < fields.length; i++) {
            Element name = document.createElement(fields[i].getName());
            switch (fields[i].getName()) {
                case "name" -> name.setTextContent(this.name);
                case "nodeType" -> name.setTextContent(this.nodeType);
                case "linkType" -> name.setTextContent(this.linkType);
                default -> {
                }
            }
            technologyTree.appendChild(name);
        }
    }
}

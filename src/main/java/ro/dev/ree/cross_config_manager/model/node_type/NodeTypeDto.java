package ro.dev.ree.cross_config_manager.model.node_type;

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
public class NodeTypeDto extends RecordDto implements XmlElement {

    private String configId;

    private String discriminator;

    private String name;

    private String appIcon;

    private String mapIcon;

    private String capacityFull;

    private String capacityUnitName;

    private String typeClassPath;

    private String rootType;

    private String system;

    private String multiparentAllowed;

    private String uniquenessType;

    // add a new method similar to insertOrUpdateFromItems by accepting List instead of String; get rid of action

    public static NodeTypeDto InsertOrUpdateFromItems(List<String> columnValues, String action) {
        var nodeTypeDto = new NodeTypeDto();
        if(action.equals("Update"))
        {
            nodeTypeDto.setId(columnValues.get(0));
        }
        nodeTypeDto.setConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());
        nodeTypeDto.setDiscriminator(columnValues.get(1));
        nodeTypeDto.setName(columnValues.get(2));
        nodeTypeDto.setAppIcon(columnValues.get(3));
        nodeTypeDto.setMapIcon(columnValues.get(4));
        nodeTypeDto.setCapacityFull(columnValues.get(5));
        nodeTypeDto.setCapacityUnitName(columnValues.get(6));
        nodeTypeDto.setTypeClassPath(columnValues.get(7));
        nodeTypeDto.setRootType(columnValues.get(8));
        nodeTypeDto.setSystem(columnValues.get(9));
        nodeTypeDto.setMultiparentAllowed(columnValues.get(10));
        nodeTypeDto.setUniquenessType(columnValues.get(11));

        return nodeTypeDto;
    }

    @Override
    public void asXml(Document document, Element rootElement) {

        // add xml elements
        Element nodeType = document.createElement("nodeType");
        // add classType to root
        rootElement.appendChild(nodeType);

        Field[] fields = getClass().getDeclaredFields();
        for (int i = 1; i < fields.length; i++) {
            Element name = document.createElement(fields[i].getName());
            switch (fields[i].getName()) {
                case "discriminator" -> name.setTextContent(this.discriminator);
                case "name" -> name.setTextContent(this.name);
                case "appIcon" -> name.setTextContent(this.appIcon);
                case "mapIcon" -> name.setTextContent(this.mapIcon);
                case "capacityFull" -> name.setTextContent(this.capacityFull);
                case "capacityUnitName" -> name.setTextContent(this.capacityUnitName);
                case "typeClassPath" -> name.setTextContent(this.typeClassPath);
                case "rootType" -> name.setTextContent(this.rootType);
                case "system" -> name.setTextContent(this.system);
                case "multiparentAllowed" -> name.setTextContent(this.multiparentAllowed);
                case "uniquenessType" -> name.setTextContent(this.uniquenessType);
                default -> {
                }
            }
            nodeType.appendChild(name);
        }
    }
}

package ro.dev.ree.cross_config_manager.model.node_type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import ro.dev.ree.cross_config_manager.model.RecordDto;
import ro.dev.ree.cross_config_manager.model.config_type.ConfigSingleton;
import ro.dev.ree.cross_config_manager.xml.writer.XmlElement;

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

    public static NodeTypeDto InsertOrUpdateFromItems(String[] columnValues, String action) {
        var nodeTypeDto = new NodeTypeDto();
        if(action.equals("Update"))
        {
            nodeTypeDto.setId(columnValues[0]);
        }
        nodeTypeDto.setConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());
        nodeTypeDto.setDiscriminator(columnValues[1]);
        nodeTypeDto.setName(columnValues[2]);
        nodeTypeDto.setAppIcon(columnValues[3]);
        nodeTypeDto.setMapIcon(columnValues[4]);
        nodeTypeDto.setCapacityFull(columnValues[5]);
        nodeTypeDto.setCapacityUnitName(columnValues[6]);
        nodeTypeDto.setTypeClassPath(columnValues[7]);
        nodeTypeDto.setRootType(columnValues[8]);
        nodeTypeDto.setSystem(columnValues[9]);
        nodeTypeDto.setMultiparentAllowed(columnValues[10]);
        nodeTypeDto.setUniquenessType(columnValues[11]);

        return nodeTypeDto;
    }

    @Override
    public void asXml(Document document, Element rootElement) {

        // add xml elements
        Element nodeType = document.createElement("nodeType");
        // add classType to root
        rootElement.appendChild(nodeType);
        // add xml attribute
        nodeType.setAttribute("id", getId());

        Element discriminator = document.createElement("discriminator");
        discriminator.setTextContent(this.discriminator);
        nodeType.appendChild(discriminator);

        Element name = document.createElement("name");
        name.setTextContent(this.name);
        nodeType.appendChild(name);

        Element appIcon = document.createElement("appIcon");
        appIcon.setTextContent(this.appIcon);
        nodeType.appendChild(appIcon);

        Element mapIcon = document.createElement("mapIcon");
        mapIcon.setTextContent(this.mapIcon);
        nodeType.appendChild(mapIcon);

        Element capacityFull = document.createElement("capacityFull");
        capacityFull.setTextContent(this.capacityFull);
        nodeType.appendChild(capacityFull);

        Element capacityUnitName = document.createElement("capacityUnitName");
        capacityUnitName.setTextContent(this.capacityUnitName);
        nodeType.appendChild(capacityUnitName);

        Element typeClassPath = document.createElement("typeClassPath");
        typeClassPath.setTextContent(this.typeClassPath);
        nodeType.appendChild(typeClassPath);

        Element rootType = document.createElement("rootType");
        rootType.setTextContent(this.rootType);
        nodeType.appendChild(rootType);

        Element system = document.createElement("system");
        system.setTextContent(this.system);
        nodeType.appendChild(system);

        Element multiparentAllowed = document.createElement("multiparentAllowed");
        multiparentAllowed.setTextContent(this.multiparentAllowed);
        nodeType.appendChild(multiparentAllowed);

        Element uniquenessType = document.createElement("uniquenessType");
        uniquenessType.setTextContent(this.uniquenessType);
        nodeType.appendChild(uniquenessType);

    }
}

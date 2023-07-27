package ro.dev.ree.cross_config_manager.model.link_type;

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
public class LinkTypeDto extends RecordDto implements XmlElement {

    private String configId;

    private String discriminator;

    private String name;

    private String appIcon;

    private String mapIcon;

    private String capacityFull;

    private String capacityUnitName;

    private String typeClassPath;

    private String system;

    private String unique;

    public static LinkTypeDto InsertOrUpdateFromItems(String[] columnValues, String action) {
        var linkTypeDto = new LinkTypeDto();
        if(action.equals("Update"))
        {
            linkTypeDto.setId(columnValues[0]);
        }
        linkTypeDto.setConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());
        linkTypeDto.setDiscriminator(columnValues[1]);
        linkTypeDto.setName(columnValues[2]);
        linkTypeDto.setAppIcon(columnValues[3]);
        linkTypeDto.setMapIcon(columnValues[4]);
        linkTypeDto.setCapacityFull(columnValues[5]);
        linkTypeDto.setCapacityUnitName(columnValues[6]);
        linkTypeDto.setTypeClassPath(columnValues[7]);
        linkTypeDto.setSystem(columnValues[8]);
        linkTypeDto.setUnique(columnValues[9]);

        return linkTypeDto;
    }

    @Override
    public void asXml(Document document, Element rootElement) {
        // add xml elements
        Element linkType = document.createElement("linkType");
        // add classType to root
        rootElement.appendChild(linkType);
        // add xml attribute
        linkType.setAttribute("id", getId());

        Element discriminator = document.createElement("discriminator");
        discriminator.setTextContent(this.discriminator);
        linkType.appendChild(discriminator);

        Element name = document.createElement("name");
        name.setTextContent(this.name);
        linkType.appendChild(name);

        Element appIcon = document.createElement("appIcon");
        appIcon.setTextContent(this.appIcon);
        linkType.appendChild(appIcon);

        Element mapIcon = document.createElement("mapIcon");
        mapIcon.setTextContent(this.mapIcon);
        linkType.appendChild(mapIcon);

        Element capacityFull = document.createElement("capacityFull");
        capacityFull.setTextContent(this.capacityFull);
        linkType.appendChild(capacityFull);

        Element capacityUnitName = document.createElement("capacityUnitName");
        capacityUnitName.setTextContent(this.capacityUnitName);
        linkType.appendChild(capacityUnitName);

        Element typeClassPath = document.createElement("typeClassPath");
        typeClassPath.setTextContent(this.typeClassPath);
        linkType.appendChild(typeClassPath);

        Element system = document.createElement("system");
        system.setTextContent(this.system);
        linkType.appendChild(system);

        Element unique = document.createElement("unique");
        unique.setTextContent(this.unique);
        linkType.appendChild(unique);

    }
}

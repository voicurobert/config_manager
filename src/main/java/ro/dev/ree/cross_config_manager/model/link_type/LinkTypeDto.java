package ro.dev.ree.cross_config_manager.model.link_type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import ro.dev.ree.cross_config_manager.model.RecordDto;
import ro.dev.ree.cross_config_manager.model.config_type.ConfigSingleton;
import ro.dev.ree.cross_config_manager.xml.writer.XmlElement;

import java.lang.reflect.Field;

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
                case "system" -> name.setTextContent(this.system);
                case "unique" -> name.setTextContent(this.unique);
                default -> {
                }
            }
            linkType.appendChild(name);
        }
    }
}

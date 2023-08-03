package ro.dev.ree.cross_config_manager.model.component_status;

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
public class ComponentStatusDto extends RecordDto implements XmlElement {
    private String configId;
    private String name;
    private String description;
    private String color;

    public static ComponentStatusDto InsertOrUpdateFromItems(String[] columnValues, String action) {
        var componentStatusDto = new ComponentStatusDto();
        if (action.equals("Update")) {
            componentStatusDto.setId(columnValues[0]);
        }
        componentStatusDto.setConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());
        componentStatusDto.setName(columnValues[1]);
        componentStatusDto.setDescription(columnValues[2]);
        componentStatusDto.setColor(columnValues[3]);


        return componentStatusDto;
    }

    @Override
    public void asXml(Document document, Element rootElement) {

        // add xml elements
        Element componentStatus = document.createElement("componentStatus");
        // add classType to root
        rootElement.appendChild(componentStatus);

        Field[] fields = getClass().getDeclaredFields();
        for (int i = 1; i < fields.length; i++) {
            Element name = document.createElement(fields[i].getName());
            switch (fields[i].getName()) {
                case "name" -> name.setTextContent(this.name);
                case "description" -> name.setTextContent(this.description);
                case "color" -> name.setTextContent(this.color);

                default -> {
                }
            }
            componentStatus.appendChild(name);
        }
    }
}

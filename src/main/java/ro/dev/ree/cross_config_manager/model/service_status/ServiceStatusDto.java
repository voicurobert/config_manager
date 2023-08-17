package ro.dev.ree.cross_config_manager.model.service_status;

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
public class ServiceStatusDto extends RecordDto implements XmlElement {
    private String configId;
    private String name;
    private String description;
    private String color;

    public static ServiceStatusDto InsertOrUpdateFromItems(List<String> columnValues, String action) {
        var serviceStatusDto = new ServiceStatusDto();
        if (action.equals("Update")) {
            serviceStatusDto.setId(columnValues.get(0));
        }
        serviceStatusDto.setConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());
        serviceStatusDto.setName(columnValues.get(1));
        serviceStatusDto.setDescription(columnValues.get(2));
        serviceStatusDto.setColor(columnValues.get(3));

        return serviceStatusDto;
    }

    @Override
    public void asXml(Document document, Element rootElement) {

        // add xml elements
        Element serviceStatus = document.createElement("serviceStatus");
        // add classType to root
        rootElement.appendChild(serviceStatus);

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
            serviceStatus.appendChild(name);
        }
    }
}
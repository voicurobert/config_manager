package ro.dev.ree.cross_config_manager.model.node_status;

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
public class NodeStatusDto extends RecordDto implements XmlElement {
    private String configId;
    private String discriminator;
    private String name;
    private String colorCode;
    private String capacityConsumer;

    public static NodeStatusDto InsertOrUpdateFromItems(List<String> columnValues, String action) {
        var nodeStatusDto = new NodeStatusDto();
        if (action.equals("Update")) {
            nodeStatusDto.setId(columnValues.get(0));
        }
        nodeStatusDto.setConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());
        nodeStatusDto.setDiscriminator(columnValues.get(1));
        nodeStatusDto.setName(columnValues.get(2));
        nodeStatusDto.setColorCode(columnValues.get(3));
        nodeStatusDto.setCapacityConsumer(columnValues.get(4));


        return nodeStatusDto;
    }

    @Override
    public void asXml(Document document, Element rootElement) {

        // add xml elements
        Element nodeStatus = document.createElement("nodeType");
        // add classType to root
        rootElement.appendChild(nodeStatus);

        Field[] fields = getClass().getDeclaredFields();
        for (int i = 1; i < fields.length; i++) {
            Element name = document.createElement(fields[i].getName());
            switch (fields[i].getName()) {
                case "discriminator" -> name.setTextContent(this.discriminator);
                case "name" -> name.setTextContent(this.name);
                case "colorCode" -> name.setTextContent(this.colorCode);
                case "capacityConsumer" -> name.setTextContent(this.capacityConsumer);
                default -> {
                }
            }
            nodeStatus.appendChild(name);
        }
    }
}


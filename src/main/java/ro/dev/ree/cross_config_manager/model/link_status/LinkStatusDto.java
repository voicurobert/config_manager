package ro.dev.ree.cross_config_manager.model.link_status;

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
public class LinkStatusDto extends RecordDto implements XmlElement {
    private String configId;
    private String discriminator;
    private String name;
    private String colorCode;
    private String capacityConsumer;
    private String consumerCandidate;
    private String providerCandidate;

    public static LinkStatusDto InsertOrUpdateFromItems(String[] columnValues, String action) {
        var linkStatusDto = new LinkStatusDto();
        if (action.equals("Update")) {
            linkStatusDto.setId(columnValues[0]);
        }
        linkStatusDto.setConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());
        linkStatusDto.setDiscriminator(columnValues[1]);
        linkStatusDto.setName(columnValues[2]);
        linkStatusDto.setColorCode(columnValues[3]);
        linkStatusDto.setCapacityConsumer(columnValues[4]);
        linkStatusDto.setConsumerCandidate(columnValues[5]);
        linkStatusDto.setProviderCandidate(columnValues[6]);

        return linkStatusDto;
    }

    @Override
    public void asXml(Document document, Element rootElement) {

        // add xml elements
        Element linkStatus = document.createElement("linkStatus");
        // add classType to root
        rootElement.appendChild(linkStatus);

        Field[] fields = getClass().getDeclaredFields();
        for (int i = 1; i < fields.length; i++) {
            Element name = document.createElement(fields[i].getName());
            switch (fields[i].getName()) {
                case "discriminator" -> name.setTextContent(this.discriminator);
                case "name" -> name.setTextContent(this.name);
                case "colorCode" -> name.setTextContent(this.colorCode);
                case "capacityConsumer" -> name.setTextContent(this.capacityConsumer);
                case "consumerCandidate" -> name.setTextContent(this.consumerCandidate);
                case "providerCandidate" -> name.setTextContent(this.providerCandidate);
                default -> {
                }
            }
            linkStatus.appendChild(name);
        }
    }
}


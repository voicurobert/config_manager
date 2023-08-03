package ro.dev.ree.cross_config_manager.model.link_type_rules;

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
public class LinkTypeRulesDto extends RecordDto implements XmlElement {

    private String configId;

    private String consumer; //type LinkType

    private String provider; //type LinkType

    private String routingPolicy;

    private String capacityCalculatorName;

    private String numberOfChannels;

    public static LinkTypeRulesDto InsertOrUpdateFromItems(List<String> columnValues, String action) {
        var linkTypeRulesDto = new LinkTypeRulesDto();
        if(action.equals("Update"))
        {
            linkTypeRulesDto.setId(columnValues.get(0));
        }
        linkTypeRulesDto.setConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());
        linkTypeRulesDto.setConsumer(columnValues.get(1));
        linkTypeRulesDto.setProvider(columnValues.get(2));
        linkTypeRulesDto.setRoutingPolicy(columnValues.get(3));
        linkTypeRulesDto.setCapacityCalculatorName(columnValues.get(4));
        linkTypeRulesDto.setNumberOfChannels(columnValues.get(5));

        return linkTypeRulesDto;
    }

    @Override
    public void asXml(Document document, Element rootElement) {

        // add xml elements
        Element linkTypeRule = document.createElement("linkTypeRule");
        // add classType to root
        rootElement.appendChild(linkTypeRule);

        Field[] fields = getClass().getDeclaredFields();
        for (int i = 1; i < fields.length; i++) {
            Element name = document.createElement(fields[i].getName());
            switch (fields[i].getName()) {
                case "consumer" -> name.setTextContent(this.consumer);
                case "provider" -> name.setTextContent(this.provider);
                case "routingPolicy" -> name.setTextContent(this.routingPolicy);
                case "capacityCalculatorName" -> name.setTextContent(this.capacityCalculatorName);
                case "numberOfChannels" -> name.setTextContent(this.numberOfChannels);
                default -> {
                }
            }
            linkTypeRule.appendChild(name);
        }
    }
}

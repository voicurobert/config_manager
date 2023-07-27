package ro.dev.ree.cross_config_manager.model.link_type_rules;

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
public class LinkTypeRulesDto extends RecordDto implements XmlElement {

    private String configId;

    private String consumer; //type LinkType

    private String provider; //type LinkType

    private String routingPolicy;

    private String capacityCalculatorName;

    private String numberOfChannels;

    public static LinkTypeRulesDto InsertOrUpdateFromItems(String[] columnValues, String action) {
        var linkTypeRulesDto = new LinkTypeRulesDto();
        if(action.equals("Update"))
        {
            linkTypeRulesDto.setId(columnValues[0]);
        }
        linkTypeRulesDto.setConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());
        linkTypeRulesDto.setConsumer(columnValues[1]);
        linkTypeRulesDto.setProvider(columnValues[2]);
        linkTypeRulesDto.setRoutingPolicy(columnValues[3]);
        linkTypeRulesDto.setCapacityCalculatorName(columnValues[4]);
        linkTypeRulesDto.setNumberOfChannels(columnValues[5]);

        return linkTypeRulesDto;
    }

    @Override
    public void asXml(Document document, Element rootElement) {

        // add xml elements
        Element linkTypeRule = document.createElement("linkTypeRule");
        // add classType to root
        rootElement.appendChild(linkTypeRule);
        // add xml attribute
        linkTypeRule.setAttribute("id", getId());

        Element consumer = document.createElement("consumer");
        consumer.setTextContent(this.consumer);
        linkTypeRule.appendChild(consumer);

        Element provider = document.createElement("provider");
        provider.setTextContent(this.provider);
        linkTypeRule.appendChild(provider);

        Element routingPolicy = document.createElement("routingPolicy");
        routingPolicy.setTextContent(this.routingPolicy);
        linkTypeRule.appendChild(routingPolicy);

        Element capacityCalculatorName = document.createElement("capacityCalculatorName");
        capacityCalculatorName.setTextContent(this.capacityCalculatorName);
        linkTypeRule.appendChild(capacityCalculatorName);

        Element numberOfChannels = document.createElement("numberOfChannels");
        numberOfChannels.setTextContent(this.numberOfChannels);
        linkTypeRule.appendChild(numberOfChannels);

    }
}

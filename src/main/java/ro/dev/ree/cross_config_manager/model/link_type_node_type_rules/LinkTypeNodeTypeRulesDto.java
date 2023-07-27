package ro.dev.ree.cross_config_manager.model.link_type_node_type_rules;

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
public class LinkTypeNodeTypeRulesDto extends RecordDto implements XmlElement {

    private String configId;

    private String linkType; //type LinkType

    private String nodeType; //type NodeType

    private String quality;

    public static LinkTypeNodeTypeRulesDto InsertOrUpdateFromItems(String[] columnValues, String action) {
        var linkTypeNodeTypeRulesDto = new LinkTypeNodeTypeRulesDto();
        if(action.equals("Update"))
        {
            linkTypeNodeTypeRulesDto.setId(columnValues[0]);
        }
        linkTypeNodeTypeRulesDto.setConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());
        linkTypeNodeTypeRulesDto.setLinkType(columnValues[1]);
        linkTypeNodeTypeRulesDto.setNodeType(columnValues[2]);
        linkTypeNodeTypeRulesDto.setQuality(columnValues[3]);

        return linkTypeNodeTypeRulesDto;
    }

    @Override
    public void asXml(Document document, Element rootElement) {

        // add xml elements
        Element linkTypeNodeTypeRules = document.createElement("linkTypeNodeTypeRule");
        // add classType to root
        rootElement.appendChild(linkTypeNodeTypeRules);
        // add xml attribute
        linkTypeNodeTypeRules.setAttribute("id", getId());

        Element linkType = document.createElement("linkType");
        linkType.setTextContent(this.linkType);
        linkTypeNodeTypeRules.appendChild(linkType);

        Element nodeType = document.createElement("nodeType");
        nodeType.setTextContent(this.nodeType);
        linkTypeNodeTypeRules.appendChild(nodeType);

        Element quality = document.createElement("quality");
        quality.setTextContent(this.quality);
        linkTypeNodeTypeRules.appendChild(quality);
    }
}

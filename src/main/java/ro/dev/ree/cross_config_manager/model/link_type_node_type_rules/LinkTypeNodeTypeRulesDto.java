package ro.dev.ree.cross_config_manager.model.link_type_node_type_rules;

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
public class LinkTypeNodeTypeRulesDto extends RecordDto implements XmlElement {

    private String configId;

    private String linkType; //type LinkType

    private String nodeType; //type NodeType

    private String quality;

    public static LinkTypeNodeTypeRulesDto InsertOrUpdateFromItems(List<String> columnValues, String action) {
        var linkTypeNodeTypeRulesDto = new LinkTypeNodeTypeRulesDto();
        if(action.equals("Update"))
        {
            linkTypeNodeTypeRulesDto.setId(columnValues.get(0));
        }
        linkTypeNodeTypeRulesDto.setConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());
        linkTypeNodeTypeRulesDto.setLinkType(columnValues.get(1));
        linkTypeNodeTypeRulesDto.setNodeType(columnValues.get(2));
        linkTypeNodeTypeRulesDto.setQuality(columnValues.get(3));

        return linkTypeNodeTypeRulesDto;
    }

    @Override
    public void asXml(Document document, Element rootElement) {

        // add xml elements
        Element linkTypeNodeTypeRule = document.createElement("linkTypeNodeTypeRule");
        // add classType to root
        rootElement.appendChild(linkTypeNodeTypeRule);

        Field[] fields = getClass().getDeclaredFields();
        for (int i = 1; i < fields.length; i++) {
            Element name = document.createElement(fields[i].getName());
            switch (fields[i].getName()) {
                case "linkType" -> name.setTextContent(this.linkType);
                case "nodeType" -> name.setTextContent(this.nodeType);
                case "quality" -> name.setTextContent(this.quality);
                default -> {
                }
            }
            linkTypeNodeTypeRule.appendChild(name);
        }
    }
}

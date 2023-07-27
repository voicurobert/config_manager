package ro.dev.ree.cross_config_manager.model.node_type_rules;

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
public class NodeTypeRulesDto extends RecordDto implements XmlElement {

    private String configId;

    private String child;

    private String parent;

    private String capacityCalculatorName;

    private String mandatoryParent;

    public static NodeTypeRulesDto InsertOrUpdateFromItems(String[] columnValues, String action) {
        var nodeTypeRulesDto = new NodeTypeRulesDto();
        if(action.equals("Update"))
        {
            nodeTypeRulesDto.setId(columnValues[0]);
        }
        nodeTypeRulesDto.setConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());
        nodeTypeRulesDto.setChild(columnValues[1]);
        nodeTypeRulesDto.setParent(columnValues[2]);
        nodeTypeRulesDto.setCapacityCalculatorName(columnValues[3]);
        nodeTypeRulesDto.setMandatoryParent(columnValues[4]);

        return nodeTypeRulesDto;
    }

    @Override
    public void asXml(Document document, Element rootElement) {

        // add xml elements
        Element nodeTypeRule = document.createElement("nodeTypeRule");
        // add classType to root
        rootElement.appendChild(nodeTypeRule);
        // add xml attribute
        nodeTypeRule.setAttribute("id", getId());

        Element child = document.createElement("child");
        child.setTextContent(this.child);
        nodeTypeRule.appendChild(child);

        Element parent = document.createElement("parent");
        parent.setTextContent(this.parent);
        nodeTypeRule.appendChild(parent);

        Element capacityCalculatorName = document.createElement("capacityCalculatorName");
        capacityCalculatorName.setTextContent(this.capacityCalculatorName);
        nodeTypeRule.appendChild(capacityCalculatorName);

        Element mandatoryParent = document.createElement("mandatoryParent");
        mandatoryParent.setTextContent(this.mandatoryParent);
        nodeTypeRule.appendChild(mandatoryParent);

    }
}

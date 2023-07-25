package ro.dev.ree.cross_config_manager.model.link_type_node_type_rules;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.dev.ree.cross_config_manager.model.RecordDto;
import ro.dev.ree.cross_config_manager.model.config_type.ConfigSingleton;
import ro.dev.ree.cross_config_manager.model.link_type.LinkType;
import ro.dev.ree.cross_config_manager.model.node_type.NodeType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LinkTypeNodeTypeRulesDto extends RecordDto {

    private String configId;

    private String linkType; //type LinkType

    private String nodeType; //type NodeType

    private String quality;

    public static LinkTypeNodeTypeRulesDto NewOrUpdateFromItems(String[] columnValues, String action, String ID) {
        var linkTypeNodeTypeRulesDto = new LinkTypeNodeTypeRulesDto();
        if(action.equals("Update")){
            linkTypeNodeTypeRulesDto.setId(ID);
        }
        for (int i = 0; i < columnValues.length; i++) {
            linkTypeNodeTypeRulesDto.setConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());
            linkTypeNodeTypeRulesDto.setLinkType(columnValues[0]);
            linkTypeNodeTypeRulesDto.setNodeType(columnValues[1]);
            linkTypeNodeTypeRulesDto.setQuality(columnValues[2]);
        }

        return linkTypeNodeTypeRulesDto;
    }

}

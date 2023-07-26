package ro.dev.ree.cross_config_manager.model.link_type_node_type_rules;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.dev.ree.cross_config_manager.model.RecordDto;
import ro.dev.ree.cross_config_manager.model.class_type.ClassTypeDto;
import ro.dev.ree.cross_config_manager.model.config_type.ConfigSingleton;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LinkTypeNodeTypeRulesDto extends RecordDto {

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

}

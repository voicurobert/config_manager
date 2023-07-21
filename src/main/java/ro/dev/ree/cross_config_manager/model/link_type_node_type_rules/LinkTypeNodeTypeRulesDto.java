package ro.dev.ree.cross_config_manager.model.link_type_node_type_rules;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.dev.ree.cross_config_manager.model.RecordDto;
import ro.dev.ree.cross_config_manager.model.link_type.LinkType;
import ro.dev.ree.cross_config_manager.model.node_type.NodeType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LinkTypeNodeTypeRulesDto extends RecordDto {

    @Override
    public void setId(String id) {
        super.setId(id);
    }

    @Override
    public String getId() {
        return super.getId();
    }

    private String configId;

    private String linkType; //type LinkType

    private String nodeType; //type NodeType

    private String quality;

    public static LinkTypeNodeTypeRulesDto newFromItems(String[] vec) {
        var linkTypeNodeTypeRulesDto = new LinkTypeNodeTypeRulesDto();

        for (int i = 0; i < vec.length; i++) {
            linkTypeNodeTypeRulesDto.setId(vec[0]);
            linkTypeNodeTypeRulesDto.setConfigId(vec[1]);
            linkTypeNodeTypeRulesDto.setLinkType(vec[2]);
            linkTypeNodeTypeRulesDto.setNodeType(vec[3]);
            linkTypeNodeTypeRulesDto.setQuality(vec[4]);
        }

        return linkTypeNodeTypeRulesDto;
    }
}

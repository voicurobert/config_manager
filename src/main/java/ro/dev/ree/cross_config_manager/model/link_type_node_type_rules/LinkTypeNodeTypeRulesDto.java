package ro.dev.ree.cross_config_manager.model.link_type_node_type_rules;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.dev.ree.cross_config_manager.model.link_type.LinkType;
import ro.dev.ree.cross_config_manager.model.node_type.NodeType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LinkTypeNodeTypeRulesDto {

    private String configId;

    private LinkType linkType;

    private NodeType nodeType;

    private String quality;
}

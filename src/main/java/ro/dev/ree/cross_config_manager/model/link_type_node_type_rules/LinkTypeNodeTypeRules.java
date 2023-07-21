package ro.dev.ree.cross_config_manager.model.link_type_node_type_rules;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import ro.dev.ree.cross_config_manager.model.link_type.LinkType;
import ro.dev.ree.cross_config_manager.model.node_type.NodeType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "link_types_node_types_rules")
public class LinkTypeNodeTypeRules {

    @Id
    private String id;

    private String configId;

    private String linkType; //type LinkType

    private String nodeType; //type NodeType

    private String quality;
}

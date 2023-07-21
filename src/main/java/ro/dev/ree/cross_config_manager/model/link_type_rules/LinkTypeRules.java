package ro.dev.ree.cross_config_manager.model.link_type_rules;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import ro.dev.ree.cross_config_manager.model.link_type.LinkType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "link_types_rules")
public class LinkTypeRules {

    @Id
    private String id;

    private String configId;

    private String consumer; //type LinkType

    private String provider; //type LinkType

    private String routingPolicy;

    private String capacityCalculatorName;

    private String numberOfChannels;
}

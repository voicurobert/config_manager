package ro.dev.ree.cross_config_manager.model.link_type_rules;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.dev.ree.cross_config_manager.model.RecordDto;
import ro.dev.ree.cross_config_manager.model.link_type.LinkType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LinkTypeRulesDto extends RecordDto {

    private String configId;

    private LinkType consumer;

    private LinkType provider;

    private String routingPolicy;

    private String capacityCalculatorName;

    private String numberOfChannels;
}

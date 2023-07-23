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

    private String consumer; //type LinkType

    private String provider; //type LinkType

    private String routingPolicy;

    private String capacityCalculatorName;

    private String numberOfChannels;

    public static LinkTypeRulesDto newFromItems(String[] vec) {
        var linkTypeRulesDto = new LinkTypeRulesDto();

        for (int i = 0; i < vec.length; i++) {
            linkTypeRulesDto.setId(linkTypeRulesDto.getId());
            linkTypeRulesDto.setConfigId(linkTypeRulesDto.getConfigId());
            linkTypeRulesDto.setConsumer(vec[0]);
            linkTypeRulesDto.setProvider(vec[1]);
            linkTypeRulesDto.setRoutingPolicy(vec[2]);
            linkTypeRulesDto.setCapacityCalculatorName(vec[3]);
            linkTypeRulesDto.setNumberOfChannels(vec[4]);

        }

        return linkTypeRulesDto;
    }
}

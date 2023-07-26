package ro.dev.ree.cross_config_manager.model.link_type_rules;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.dev.ree.cross_config_manager.model.RecordDto;
import ro.dev.ree.cross_config_manager.model.config_type.ConfigSingleton;

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

    public static LinkTypeRulesDto InsertOrUpdateFromItems(String[] columnValues, String action) {
        var linkTypeRulesDto = new LinkTypeRulesDto();
        if(action.equals("Update"))
        {
            linkTypeRulesDto.setId(columnValues[0]);
        }
        linkTypeRulesDto.setConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());
        linkTypeRulesDto.setConsumer(columnValues[1]);
        linkTypeRulesDto.setProvider(columnValues[2]);
        linkTypeRulesDto.setRoutingPolicy(columnValues[3]);
        linkTypeRulesDto.setCapacityCalculatorName(columnValues[4]);
        linkTypeRulesDto.setNumberOfChannels(columnValues[5]);

        return linkTypeRulesDto;
    }

}

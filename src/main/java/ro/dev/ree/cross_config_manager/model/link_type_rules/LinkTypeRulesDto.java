package ro.dev.ree.cross_config_manager.model.link_type_rules;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.dev.ree.cross_config_manager.model.RecordDto;
import ro.dev.ree.cross_config_manager.model.config_type.ConfigSingleton;
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

    public static LinkTypeRulesDto NewOrUpdateFromItems(String[] columnValues, String action, String ID) {
        var linkTypeRulesDto = new LinkTypeRulesDto();
        if(action.equals("Update"))
        {
            linkTypeRulesDto.setId(ID);
        }
        for (int i = 0; i < columnValues.length; i++) {
            linkTypeRulesDto.setConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());
            linkTypeRulesDto.setConsumer(columnValues[0]);
            linkTypeRulesDto.setProvider(columnValues[1]);
            linkTypeRulesDto.setRoutingPolicy(columnValues[2]);
            linkTypeRulesDto.setCapacityCalculatorName(columnValues[3]);
            linkTypeRulesDto.setNumberOfChannels(columnValues[4]);

        }

        return linkTypeRulesDto;
    }

}

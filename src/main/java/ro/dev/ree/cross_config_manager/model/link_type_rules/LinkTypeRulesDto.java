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

    @Override
    public void setId(String id) {
        super.setId(id);
    }

    @Override
    public String getId() {
        return super.getId();
    }

    private String configId;

    private String consumer; //type LinkType

    private String provider; //type LinkType

    private String routingPolicy;

    private String capacityCalculatorName;

    private String numberOfChannels;

    public static LinkTypeRulesDto newFromItems(String[] vec) {
        var linkTypeRulesDto = new LinkTypeRulesDto();

        for (int i = 0; i < vec.length; i++) {
            linkTypeRulesDto.setId(vec[0]);
            linkTypeRulesDto.setConfigId(vec[1]);
            linkTypeRulesDto.setConsumer(vec[2]);
            linkTypeRulesDto.setProvider(vec[3]);
            linkTypeRulesDto.setRoutingPolicy(vec[4]);
            linkTypeRulesDto.setCapacityCalculatorName(vec[5]);
            linkTypeRulesDto.setNumberOfChannels(vec[6]);

        }

        return linkTypeRulesDto;
    }
}

package ro.dev.ree.cross_config_manager.model.node_type_rules;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.dev.ree.cross_config_manager.model.RecordDto;
import ro.dev.ree.cross_config_manager.model.config_type.ConfigSingleton;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NodeTypeRulesDto extends RecordDto {

    private String configId;

    private String child;

    private String parent;

    private String capacityCalculatorName;

    private String mandatoryParent;

    public static NodeTypeRulesDto newFromItems(String[] columnValues) {
        var nodeTypeRulesDto = new NodeTypeRulesDto();

        for (int i = 0; i < columnValues.length; i++) {
            nodeTypeRulesDto.setConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());
            nodeTypeRulesDto.setChild(columnValues[0]);
            nodeTypeRulesDto.setParent(columnValues[1]);
            nodeTypeRulesDto.setCapacityCalculatorName(columnValues[2]);
            nodeTypeRulesDto.setMandatoryParent(columnValues[3]);
        }

        return nodeTypeRulesDto;
    }

}

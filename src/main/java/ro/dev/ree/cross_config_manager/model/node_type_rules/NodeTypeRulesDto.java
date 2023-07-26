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

    public static NodeTypeRulesDto InsertOrUpdateFromItems(String[] columnValues, String action) {
        var nodeTypeRulesDto = new NodeTypeRulesDto();
        if(action.equals("Update"))
        {
            nodeTypeRulesDto.setId(columnValues[0]);
        }
        nodeTypeRulesDto.setConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());
        nodeTypeRulesDto.setChild(columnValues[1]);
        nodeTypeRulesDto.setParent(columnValues[2]);
        nodeTypeRulesDto.setCapacityCalculatorName(columnValues[3]);
        nodeTypeRulesDto.setMandatoryParent(columnValues[4]);

        return nodeTypeRulesDto;
    }

}

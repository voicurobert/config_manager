package ro.dev.ree.cross_config_manager.model.node_type_rules;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.dev.ree.cross_config_manager.model.RecordDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NodeTypeRulesDto extends RecordDto {

    private String configId;

    private String child;

    private String parent;

    private String capacityCalculatorName;

    private String mandatoryParent;

    public static NodeTypeRulesDto newFromItems(String[] vec) {
        var nodeTypeRulesDto = new NodeTypeRulesDto();

        for (int i = 0; i < vec.length; i++) {
            nodeTypeRulesDto.setId(nodeTypeRulesDto.getId());
            nodeTypeRulesDto.setConfigId(nodeTypeRulesDto.getConfigId());
            nodeTypeRulesDto.setChild(vec[0]);
            nodeTypeRulesDto.setParent(vec[1]);
            nodeTypeRulesDto.setCapacityCalculatorName(vec[2]);
            nodeTypeRulesDto.setMandatoryParent(vec[3]);
        }

        return nodeTypeRulesDto;
    }
}

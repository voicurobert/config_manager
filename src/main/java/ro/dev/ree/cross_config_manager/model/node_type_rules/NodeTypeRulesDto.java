package ro.dev.ree.cross_config_manager.model.node_type_rules;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.dev.ree.cross_config_manager.model.RecordDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NodeTypeRulesDto extends RecordDto {

    @Override
    public void setId(String id) {
        super.setId(id);
    }

    @Override
    public String getId() {
        return super.getId();
    }

    private String configId;

    private String child;

    private String parent;

    private String capacityCalculatorName;

    private String mandatoryParent;

    public static NodeTypeRulesDto newFromItems(String[] vec) {
        var nodeTypeRulesDto = new NodeTypeRulesDto();

        for (int i = 0; i < vec.length; i++) {
            nodeTypeRulesDto.setId(vec[0]);
            nodeTypeRulesDto.setConfigId(vec[1]);
            nodeTypeRulesDto.setChild(vec[2]);
            nodeTypeRulesDto.setParent(vec[3]);
            nodeTypeRulesDto.setCapacityCalculatorName(vec[4]);
            nodeTypeRulesDto.setMandatoryParent(vec[5]);
        }

        return nodeTypeRulesDto;
    }
}

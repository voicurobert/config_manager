package ro.dev.ree.cross_config_manager.model.node_type;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.dev.ree.cross_config_manager.model.RecordDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NodeTypeDto extends RecordDto {

    private String configId;

    private String discriminator;

    private String name;

    private String appIcon;

    private String mapIcon;

    private String capacityFull;

    private String capacityUnitName;

    private String typeClassPath;

    private String rootType;

    private String system;

    private String multiparentAllowed;

    private String uniquenessType;

    public static NodeTypeDto newFromItems(String[] vec) {
        var nodeTypeDto = new NodeTypeDto();

        for (int i = 0; i < vec.length; i++) {
            nodeTypeDto.setId(nodeTypeDto.getId());
            nodeTypeDto.setConfigId(nodeTypeDto.getConfigId());
            nodeTypeDto.setDiscriminator(vec[0]);
            nodeTypeDto.setName(vec[1]);
            nodeTypeDto.setAppIcon(vec[2]);
            nodeTypeDto.setMapIcon(vec[3]);
            nodeTypeDto.setCapacityFull(vec[4]);
            nodeTypeDto.setCapacityUnitName(vec[5]);
            nodeTypeDto.setTypeClassPath(vec[6]);
            nodeTypeDto.setRootType(vec[7]);
            nodeTypeDto.setSystem(vec[8]);
            nodeTypeDto.setMultiparentAllowed(vec[9]);
            nodeTypeDto.setUniquenessType(vec[10]);
        }

        return nodeTypeDto;
    }
}

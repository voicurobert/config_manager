package ro.dev.ree.cross_config_manager.model.node_type;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.dev.ree.cross_config_manager.model.RecordDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NodeTypeDto extends RecordDto {

    private String id;
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
            nodeTypeDto.setId(vec[0]);
//        vec[1] = nodeTypeDto.getConfigId();
//        vec[2] = nodeTypeDto.getDiscriminator();
//        vec[3] = nodeTypeDto.getName();
//        vec[4] = nodeTypeDto.getAppIcon();
//        vec[5] = nodeTypeDto.getMapIcon();
//        vec[6] = nodeTypeDto.getCapacityFull();
//        vec[7] = nodeTypeDto.getCapacityUnitName();
//        vec[8] = nodeTypeDto.getTypeClassPath();
//        vec[9] = nodeTypeDto.getRootType();
//        vec[10] = nodeTypeDto.getSystem();
//        vec[11] = nodeTypeDto.getMultiparentAllowed();
//        vec[12] = nodeTypeDto.getUniquenessType();
        }


        return nodeTypeDto;
    }
}

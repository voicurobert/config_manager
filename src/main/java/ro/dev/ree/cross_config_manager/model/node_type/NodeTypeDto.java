package ro.dev.ree.cross_config_manager.model.node_type;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.dev.ree.cross_config_manager.model.RecordDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NodeTypeDto extends RecordDto {

    @Override
    public void setId(String id) {
        super.setId(id);
    }

    @Override
    public String getId() {
        return super.getId();
    }

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
            nodeTypeDto.setConfigId(vec[1]);
            nodeTypeDto.setDiscriminator(vec[2]);
            nodeTypeDto.setName(vec[3]);
            nodeTypeDto.setAppIcon(vec[4]);
            nodeTypeDto.setMapIcon(vec[5]);
            nodeTypeDto.setCapacityFull(vec[6]);
            nodeTypeDto.setCapacityUnitName(vec[7]);
            nodeTypeDto.setTypeClassPath(vec[8]);
            nodeTypeDto.setRootType(vec[9]);
            nodeTypeDto.setSystem(vec[10]);
            nodeTypeDto.setMultiparentAllowed(vec[11]);
            nodeTypeDto.setUniquenessType(vec[12]);
        }

        return nodeTypeDto;
    }
}

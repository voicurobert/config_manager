package ro.dev.ree.cross_config_manager.model.node_type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.dev.ree.cross_config_manager.model.RecordDto;
import ro.dev.ree.cross_config_manager.model.class_type.ClassTypeDto;
import ro.dev.ree.cross_config_manager.model.config_type.ConfigSingleton;

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

    public static NodeTypeDto InsertOrUpdateFromItems(String[] columnValues, String action) {
        var nodeTypeDto = new NodeTypeDto();
        if(action.equals("Update"))
        {
            nodeTypeDto.setId(columnValues[0]);
        }
        nodeTypeDto.setConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());
        nodeTypeDto.setDiscriminator(columnValues[1]);
        nodeTypeDto.setName(columnValues[2]);
        nodeTypeDto.setAppIcon(columnValues[3]);
        nodeTypeDto.setMapIcon(columnValues[4]);
        nodeTypeDto.setCapacityFull(columnValues[5]);
        nodeTypeDto.setCapacityUnitName(columnValues[6]);
        nodeTypeDto.setTypeClassPath(columnValues[7]);
        nodeTypeDto.setRootType(columnValues[8]);
        nodeTypeDto.setSystem(columnValues[9]);
        nodeTypeDto.setMultiparentAllowed(columnValues[10]);
        nodeTypeDto.setUniquenessType(columnValues[11]);

        return nodeTypeDto;
    }

}

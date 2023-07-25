package ro.dev.ree.cross_config_manager.model.node_type;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.dev.ree.cross_config_manager.model.RecordDto;
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

    public static NodeTypeDto NewOrUpdateFromItems(String[] columnValues, String action, String ID) {
        var nodeTypeDto = new NodeTypeDto();
        if(action.equals("Update")){
            nodeTypeDto.setId(ID);
        }
        for (int i = 0; i < columnValues.length; i++) {
            nodeTypeDto.setConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());
            nodeTypeDto.setDiscriminator(columnValues[0]);
            nodeTypeDto.setName(columnValues[1]);
            nodeTypeDto.setAppIcon(columnValues[2]);
            nodeTypeDto.setMapIcon(columnValues[3]);
            nodeTypeDto.setCapacityFull(columnValues[4]);
            nodeTypeDto.setCapacityUnitName(columnValues[5]);
            nodeTypeDto.setTypeClassPath(columnValues[6]);
            nodeTypeDto.setRootType(columnValues[7]);
            nodeTypeDto.setSystem(columnValues[8]);
            nodeTypeDto.setMultiparentAllowed(columnValues[9]);
            nodeTypeDto.setUniquenessType(columnValues[10]);
        }

        return nodeTypeDto;
    }

}

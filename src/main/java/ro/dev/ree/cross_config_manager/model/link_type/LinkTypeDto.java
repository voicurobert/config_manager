package ro.dev.ree.cross_config_manager.model.link_type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.dev.ree.cross_config_manager.model.RecordDto;
import ro.dev.ree.cross_config_manager.model.class_type.ClassTypeDto;
import ro.dev.ree.cross_config_manager.model.config_type.ConfigSingleton;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LinkTypeDto extends RecordDto {

    private String configId;

    private String discriminator;

    private String name;

    private String appIcon;

    private String mapIcon;

    private String capacityFull;

    private String capacityUnitName;

    private String typeClassPath;

    private String system;

    private String unique;

    public static LinkTypeDto InsertOrUpdateFromItems(String[] columnValues, String action) {
        var linkTypeDto = new LinkTypeDto();
        if(action.equals("Update"))
        {
            linkTypeDto.setId(columnValues[0]);
        }
        linkTypeDto.setConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());
        linkTypeDto.setDiscriminator(columnValues[1]);
        linkTypeDto.setName(columnValues[2]);
        linkTypeDto.setAppIcon(columnValues[3]);
        linkTypeDto.setMapIcon(columnValues[4]);
        linkTypeDto.setCapacityFull(columnValues[5]);
        linkTypeDto.setCapacityUnitName(columnValues[6]);
        linkTypeDto.setTypeClassPath(columnValues[7]);
        linkTypeDto.setSystem(columnValues[8]);
        linkTypeDto.setUnique(columnValues[9]);

        return linkTypeDto;
    }

}

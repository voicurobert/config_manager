package ro.dev.ree.cross_config_manager.model.link_type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.dev.ree.cross_config_manager.model.RecordDto;
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

    public static LinkTypeDto newFromItems(String[] columnValues) {
        var linkTypeDto = new LinkTypeDto();

        for (int i = 0; i < columnValues.length; i++) {
            linkTypeDto.setConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());
            linkTypeDto.setDiscriminator(columnValues[0]);
            linkTypeDto.setName(columnValues[1]);
            linkTypeDto.setAppIcon(columnValues[2]);
            linkTypeDto.setMapIcon(columnValues[3]);
            linkTypeDto.setCapacityFull(columnValues[4]);
            linkTypeDto.setCapacityUnitName(columnValues[5]);
            linkTypeDto.setTypeClassPath(columnValues[6]);
            linkTypeDto.setSystem(columnValues[7]);
            linkTypeDto.setUnique(columnValues[8]);
        }

        return linkTypeDto;
    }

}

package ro.dev.ree.cross_config_manager.model.link_type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.dev.ree.cross_config_manager.model.RecordDto;

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

    public static LinkTypeDto newFromItems(String[] vec) {
        var linkTypeDto = new LinkTypeDto();

        for (int i = 0; i < vec.length; i++) {
            linkTypeDto.setId(linkTypeDto.getId());
            linkTypeDto.setConfigId(linkTypeDto.getConfigId());
            linkTypeDto.setDiscriminator(vec[0]);
            linkTypeDto.setName(vec[1]);
            linkTypeDto.setAppIcon(vec[2]);
            linkTypeDto.setMapIcon(vec[3]);
            linkTypeDto.setCapacityFull(vec[4]);
            linkTypeDto.setCapacityUnitName(vec[5]);
            linkTypeDto.setTypeClassPath(vec[6]);
            linkTypeDto.setSystem(vec[7]);
            linkTypeDto.setUnique(vec[8]);
        }

        return linkTypeDto;
    }
}

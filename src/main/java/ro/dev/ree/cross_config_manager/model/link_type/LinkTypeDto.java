package ro.dev.ree.cross_config_manager.model.link_type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.dev.ree.cross_config_manager.model.RecordDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LinkTypeDto extends RecordDto {

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

    private String system;

    private String unique;

    public static LinkTypeDto newFromItems(String[] vec) {
        var linkTypeDto = new LinkTypeDto();

        for (int i = 0; i < vec.length; i++) {
            linkTypeDto.setId(vec[0]);
            linkTypeDto.setConfigId(vec[1]);
            linkTypeDto.setDiscriminator(vec[2]);
            linkTypeDto.setName(vec[3]);
            linkTypeDto.setAppIcon(vec[4]);
            linkTypeDto.setMapIcon(vec[5]);
            linkTypeDto.setCapacityFull(vec[6]);
            linkTypeDto.setCapacityUnitName(vec[7]);
            linkTypeDto.setTypeClassPath(vec[8]);
            linkTypeDto.setSystem(vec[9]);
            linkTypeDto.setUnique(vec[10]);
        }

        return linkTypeDto;
    }
}

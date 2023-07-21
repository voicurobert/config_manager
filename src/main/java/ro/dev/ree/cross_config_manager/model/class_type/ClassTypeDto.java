package ro.dev.ree.cross_config_manager.model.class_type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ro.dev.ree.cross_config_manager.model.RecordDto;
import ro.dev.ree.cross_config_manager.model.link_type.LinkTypeDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassTypeDto extends RecordDto {

    @Override
    public void setId(String id) {
        super.setId(id);
    }

    @Override
    public String getId() {
        return super.getId();
    }

    private String configId;

    private String name;

    private String path;

    private String parentPath;

    public static ClassTypeDto newFromItems(String[] vec) {
        var classypeDto = new ClassTypeDto();

        for (int i = 0; i < vec.length; i++) {
            classypeDto.setId(vec[0]);
            classypeDto.setConfigId(vec[1]);
            classypeDto.setName(vec[2]);
            classypeDto.setPath(vec[3]);
            classypeDto.setParentPath(vec[4]);
        }

        return classypeDto;
    }
}

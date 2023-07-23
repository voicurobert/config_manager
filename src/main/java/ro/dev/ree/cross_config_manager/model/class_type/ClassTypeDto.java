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

    private String configId;

    private String name;

    private String path;

    private String parentPath;

    public static ClassTypeDto newFromItems(String[] vec) {
        var classTypeDto = new ClassTypeDto();

        for (int i = 0; i < vec.length; i++) {
            classTypeDto.setId(classTypeDto.getId());
            classTypeDto.setConfigId(classTypeDto.getConfigId());
            classTypeDto.setName(vec[0]);
            classTypeDto.setPath(vec[1]);
            classTypeDto.setParentPath(vec[2]);
        }

        return classTypeDto;
    }
}

package ro.dev.ree.cross_config_manager.model.class_type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.dev.ree.cross_config_manager.model.RecordDto;
import ro.dev.ree.cross_config_manager.model.config_type.ConfigSingleton;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassTypeDto extends RecordDto {

    private String configId;

    private String name;

    private String path;

    private String parentPath;

    public static ClassTypeDto InsertOrUpdateFromItems(String[] columnValues, String action) {
        var classTypeDto = new ClassTypeDto();
        if(action.equals("Update"))
        {
            classTypeDto.setId(columnValues[0]);
        }
        classTypeDto.setConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());
        classTypeDto.setName(columnValues[1]);
        classTypeDto.setPath(columnValues[2]);
        classTypeDto.setParentPath(columnValues[3]);

        return classTypeDto;
    }

}

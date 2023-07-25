package ro.dev.ree.cross_config_manager.model.class_type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.dev.ree.cross_config_manager.model.RecordDto;
import ro.dev.ree.cross_config_manager.model.config_type.ConfigSingleton;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassTypeDto extends RecordDto {

    private String configId;

    private String name;

    private String path;

    private String parentPath;

    public static ClassTypeDto NewOrUpdateFromItems(String[] columnValues, String action, String ID) {
        var classTypeDto = new ClassTypeDto();
        if(action.equals("Update"))
        {
            classTypeDto.setId(ID);
        }
        for (int i = 0; i < columnValues.length; i++) {
            classTypeDto.setConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());
            classTypeDto.setName(columnValues[0]);
            classTypeDto.setPath(columnValues[1]);
            classTypeDto.setParentPath(columnValues[2]);
        }

        return classTypeDto;
    }

}
